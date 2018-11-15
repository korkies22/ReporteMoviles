/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.os.Binder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi23;
import android.support.v4.media.MediaBrowserServiceCompatApi26;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class MediaBrowserServiceCompat
extends Service {
    static final boolean DEBUG = Log.isLoggable((String)"MBServiceCompat", (int)3);
    private static final float EPSILON = 1.0E-5f;
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static final String KEY_MEDIA_ITEM = "media_item";
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static final String KEY_SEARCH_RESULTS = "search_results";
    static final int RESULT_ERROR = -1;
    static final int RESULT_FLAG_ON_LOAD_ITEM_NOT_IMPLEMENTED = 2;
    static final int RESULT_FLAG_ON_SEARCH_NOT_IMPLEMENTED = 4;
    static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    static final int RESULT_OK = 0;
    static final int RESULT_PROGRESS_UPDATE = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    static final String TAG = "MBServiceCompat";
    final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap();
    ConnectionRecord mCurConnection;
    final ServiceHandler mHandler = new ServiceHandler();
    private MediaBrowserServiceImpl mImpl;
    MediaSessionCompat.Token mSession;

    void addSubscription(String string, ConnectionRecord connectionRecord, IBinder iBinder, Bundle bundle) {
        List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(string);
        List<Pair<IBinder, Bundle>> list2 = list;
        if (list == null) {
            list2 = new ArrayList<Pair<IBinder, Bundle>>();
        }
        for (Pair pair : list2) {
            if (iBinder != pair.first || !MediaBrowserCompatUtils.areSameOptions(bundle, (Bundle)pair.second)) continue;
            return;
        }
        list2.add(new Pair<IBinder, Bundle>(iBinder, bundle));
        connectionRecord.subscriptions.put(string, list2);
        this.performLoadChildren(string, connectionRecord, bundle);
    }

    List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int n = bundle.getInt("android.media.browse.extra.PAGE", -1);
        int n2 = bundle.getInt("android.media.browse.extra.PAGE_SIZE", -1);
        if (n == -1 && n2 == -1) {
            return list;
        }
        int n3 = n2 * n;
        int n4 = n3 + n2;
        if (n >= 0 && n2 >= 1 && n3 < list.size()) {
            n = n4;
            if (n4 > list.size()) {
                n = list.size();
            }
            return list.subList(n3, n);
        }
        return Collections.EMPTY_LIST;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    public final Bundle getBrowserRootHints() {
        return this.mImpl.getBrowserRootHints();
    }

    @Nullable
    public MediaSessionCompat.Token getSessionToken() {
        return this.mSession;
    }

    boolean isValidPackage(String string, int n) {
        if (string == null) {
            return false;
        }
        String[] arrstring = this.getPackageManager().getPackagesForUid(n);
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            if (!arrstring[n].equals(string)) continue;
            return true;
        }
        return false;
    }

    public void notifyChildrenChanged(@NonNull String string) {
        if (string == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mImpl.notifyChildrenChanged(string, null);
    }

    public void notifyChildrenChanged(@NonNull String string, @NonNull Bundle bundle) {
        if (string == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        if (bundle == null) {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
        this.mImpl.notifyChildrenChanged(string, bundle);
    }

    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    public void onCreate() {
        super.onCreate();
        this.mImpl = Build.VERSION.SDK_INT >= 26 ? new MediaBrowserServiceImplApi26() : (Build.VERSION.SDK_INT >= 23 ? new MediaBrowserServiceImplApi23() : (Build.VERSION.SDK_INT >= 21 ? new MediaBrowserServiceImplApi21() : new MediaBrowserServiceImplBase()));
        this.mImpl.onCreate();
    }

    public void onCustomAction(@NonNull String string, Bundle bundle, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String var1, int var2, @Nullable Bundle var3);

    public abstract void onLoadChildren(@NonNull String var1, @NonNull Result<List<MediaBrowserCompat.MediaItem>> var2);

    public void onLoadChildren(@NonNull String string, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result, @NonNull Bundle bundle) {
        result.setFlags(1);
        this.onLoadChildren(string, result);
    }

    public void onLoadItem(String string, @NonNull Result<MediaBrowserCompat.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void onSearch(@NonNull String string, Bundle bundle, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.setFlags(4);
        result.sendResult(null);
    }

    void performCustomAction(String string, Bundle bundle, ConnectionRecord object, ResultReceiver object2) {
        object2 = new Result<Bundle>((Object)string, (ResultReceiver)object2){
            final /* synthetic */ ResultReceiver val$receiver;
            {
                this.val$receiver = resultReceiver;
                super(object);
            }

            @Override
            void onErrorSent(Bundle bundle) {
                this.val$receiver.send(-1, bundle);
            }

            @Override
            void onProgressUpdateSent(Bundle bundle) {
                this.val$receiver.send(1, bundle);
            }

            @Override
            void onResultSent(Bundle bundle) {
                this.val$receiver.send(0, bundle);
            }
        };
        this.mCurConnection = object;
        this.onCustomAction(string, bundle, (Result<Bundle>)object2);
        this.mCurConnection = null;
        if (!object2.isDone()) {
            object = new StringBuilder();
            object.append("onCustomAction must call detach() or sendResult() or sendError() before returning for action=");
            object.append(string);
            object.append(" extras=");
            object.append((Object)bundle);
            throw new IllegalStateException(object.toString());
        }
    }

    void performLoadChildren(final String string, final ConnectionRecord connectionRecord, Bundle object) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>((Object)string, (Bundle)object){
            final /* synthetic */ Bundle val$options;
            {
                this.val$options = bundle;
                super(object);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                if (MediaBrowserServiceCompat.this.mConnections.get((Object)connectionRecord.callbacks.asBinder()) != connectionRecord) {
                    if (MediaBrowserServiceCompat.DEBUG) {
                        list = new StringBuilder();
                        list.append("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
                        list.append(connectionRecord.pkg);
                        list.append(" id=");
                        list.append(string);
                        Log.d((String)MediaBrowserServiceCompat.TAG, (String)list.toString());
                    }
                    return;
                }
                List<MediaBrowserCompat.MediaItem> list2 = list;
                if ((this.getFlags() & 1) != 0) {
                    list2 = MediaBrowserServiceCompat.this.applyOptions(list, this.val$options);
                }
                try {
                    connectionRecord.callbacks.onLoadChildren(string, list2, this.val$options);
                    return;
                }
                catch (RemoteException remoteException) {}
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Calling onLoadChildren() failed for id=");
                stringBuilder.append(string);
                stringBuilder.append(" package=");
                stringBuilder.append(connectionRecord.pkg);
                Log.w((String)MediaBrowserServiceCompat.TAG, (String)stringBuilder.toString());
            }
        };
        this.mCurConnection = connectionRecord;
        if (object == null) {
            this.onLoadChildren(string, result);
        } else {
            this.onLoadChildren(string, result, (Bundle)object);
        }
        this.mCurConnection = null;
        if (!result.isDone()) {
            object = new StringBuilder();
            object.append("onLoadChildren must call detach() or sendResult() before returning for package=");
            object.append(connectionRecord.pkg);
            object.append(" id=");
            object.append(string);
            throw new IllegalStateException(object.toString());
        }
    }

    void performLoadItem(String string, ConnectionRecord object, ResultReceiver object2) {
        object2 = new Result<MediaBrowserCompat.MediaItem>((Object)string, (ResultReceiver)object2){
            final /* synthetic */ ResultReceiver val$receiver;
            {
                this.val$receiver = resultReceiver;
                super(object);
            }

            @Override
            void onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
                if ((this.getFlags() & 2) != 0) {
                    this.val$receiver.send(-1, null);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, (Parcelable)mediaItem);
                this.val$receiver.send(0, bundle);
            }
        };
        this.mCurConnection = object;
        this.onLoadItem(string, (Result<MediaBrowserCompat.MediaItem>)object2);
        this.mCurConnection = null;
        if (!object2.isDone()) {
            object = new StringBuilder();
            object.append("onLoadItem must call detach() or sendResult() before returning for id=");
            object.append(string);
            throw new IllegalStateException(object.toString());
        }
    }

    void performSearch(String string, Bundle object, ConnectionRecord connectionRecord, ResultReceiver object2) {
        object2 = new Result<List<MediaBrowserCompat.MediaItem>>((Object)string, (ResultReceiver)object2){
            final /* synthetic */ ResultReceiver val$receiver;
            {
                this.val$receiver = resultReceiver;
                super(object);
            }

            @Override
            void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
                if ((this.getFlags() & 4) == 0 && list != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[])list.toArray(new MediaBrowserCompat.MediaItem[0]));
                    this.val$receiver.send(0, bundle);
                    return;
                }
                this.val$receiver.send(-1, null);
            }
        };
        this.mCurConnection = connectionRecord;
        this.onSearch(string, (Bundle)object, (Result<List<MediaBrowserCompat.MediaItem>>)object2);
        this.mCurConnection = null;
        if (!object2.isDone()) {
            object = new StringBuilder();
            object.append("onSearch must call detach() or sendResult() before returning for query=");
            object.append(string);
            throw new IllegalStateException(object.toString());
        }
    }

    boolean removeSubscription(String string, ConnectionRecord connectionRecord, IBinder iBinder) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (iBinder == null) {
            bl = bl3;
            if (connectionRecord.subscriptions.remove(string) != null) {
                bl = true;
            }
            return bl;
        }
        List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(string);
        bl3 = bl2;
        if (list != null) {
            Iterator<Pair<IBinder, Bundle>> iterator = list.iterator();
            while (iterator.hasNext()) {
                if (iBinder != iterator.next().first) continue;
                iterator.remove();
                bl = true;
            }
            bl3 = bl;
            if (list.size() == 0) {
                connectionRecord.subscriptions.remove(string);
                bl3 = bl;
            }
        }
        return bl3;
    }

    public void setSessionToken(MediaSessionCompat.Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        }
        if (this.mSession != null) {
            throw new IllegalStateException("The session token has already been set.");
        }
        this.mSession = token;
        this.mImpl.setSessionToken(token);
    }

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String string, @Nullable Bundle bundle) {
            if (string == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.mRootId = string;
            this.mExtras = bundle;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String getRootId() {
            return this.mRootId;
        }
    }

    private class ConnectionRecord
    implements IBinder.DeathRecipient {
        ServiceCallbacks callbacks;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap();

        ConnectionRecord() {
        }

        public void binderDied() {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    MediaBrowserServiceCompat.this.mConnections.remove((Object)ConnectionRecord.this.callbacks.asBinder());
                }
            });
        }

    }

    static interface MediaBrowserServiceImpl {
        public Bundle getBrowserRootHints();

        public void notifyChildrenChanged(String var1, Bundle var2);

        public IBinder onBind(Intent var1);

        public void onCreate();

        public void setSessionToken(MediaSessionCompat.Token var1);
    }

    @RequiresApi(value=21)
    class MediaBrowserServiceImplApi21
    implements MediaBrowserServiceImpl,
    MediaBrowserServiceCompatApi21.ServiceCompatProxy {
        Messenger mMessenger;
        final List<Bundle> mRootExtrasList = new ArrayList<Bundle>();
        Object mServiceObj;

        MediaBrowserServiceImplApi21() {
        }

        @Override
        public Bundle getBrowserRootHints() {
            if (this.mMessenger == null) {
                return null;
            }
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
            }
            if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }

        @Override
        public void notifyChildrenChanged(String string, Bundle bundle) {
            this.notifyChildrenChangedForFramework(string, bundle);
            this.notifyChildrenChangedForCompat(string, bundle);
        }

        void notifyChildrenChangedForCompat(final String string, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    for (Object object : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                        object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object);
                        List<Pair<IBinder, Bundle>> list = object.subscriptions.get(string);
                        if (list == null) continue;
                        for (Pair pair : list) {
                            if (!MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle)pair.second)) continue;
                            MediaBrowserServiceCompat.this.performLoadChildren(string, (ConnectionRecord)object, (Bundle)pair.second);
                        }
                    }
                }
            });
        }

        void notifyChildrenChangedForFramework(String string, Bundle bundle) {
            MediaBrowserServiceCompatApi21.notifyChildrenChanged(this.mServiceObj, string);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, intent);
        }

        @Override
        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi21.createService((Context)MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        @Override
        public MediaBrowserServiceCompatApi21.BrowserRoot onGetRoot(String string, int n, Bundle object) {
            IMediaSession iMediaSession;
            if (object != null && object.getInt("extra_client_version", 0) != 0) {
                object.remove("extra_client_version");
                this.mMessenger = new Messenger((Handler)MediaBrowserServiceCompat.this.mHandler);
                Bundle bundle = new Bundle();
                bundle.putInt("extra_service_version", 2);
                BundleCompat.putBinder(bundle, "extra_messenger", this.mMessenger.getBinder());
                if (MediaBrowserServiceCompat.this.mSession != null) {
                    iMediaSession = MediaBrowserServiceCompat.this.mSession.getExtraBinder();
                    iMediaSession = iMediaSession == null ? null : iMediaSession.asBinder();
                    BundleCompat.putBinder(bundle, "extra_session_binder", (IBinder)iMediaSession);
                    iMediaSession = bundle;
                } else {
                    this.mRootExtrasList.add(bundle);
                    iMediaSession = bundle;
                }
            } else {
                iMediaSession = null;
            }
            object = MediaBrowserServiceCompat.this.onGetRoot(string, n, (Bundle)object);
            if (object == null) {
                return null;
            }
            if (iMediaSession == null) {
                string = object.getExtras();
            } else {
                string = iMediaSession;
                if (object.getExtras() != null) {
                    iMediaSession.putAll(object.getExtras());
                    string = iMediaSession;
                }
            }
            return new MediaBrowserServiceCompatApi21.BrowserRoot(object.getRootId(), (Bundle)string);
        }

        @Override
        public void onLoadChildren(String string, final MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>> object) {
            object = new Result<List<MediaBrowserCompat.MediaItem>>((Object)string){

                @Override
                public void detach() {
                    object.detach();
                }

                @Override
                void onResultSent(List<MediaBrowserCompat.MediaItem> object2) {
                    if (object2 != null) {
                        ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList<MediaBrowserCompat.MediaItem>();
                        Iterator<MediaBrowserCompat.MediaItem> iterator = object2.iterator();
                        do {
                            object2 = arrayList;
                            if (iterator.hasNext()) {
                                object2 = iterator.next();
                                Parcel parcel = Parcel.obtain();
                                object2.writeToParcel(parcel, 0);
                                arrayList.add((MediaBrowserCompat.MediaItem)parcel);
                                continue;
                            }
                            break;
                        } while (true);
                    } else {
                        object2 = null;
                    }
                    object.sendResult(object2);
                }
            };
            MediaBrowserServiceCompat.this.onLoadChildren(string, (Result<List<MediaBrowserCompat.MediaItem>>)object);
        }

        @Override
        public void setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    if (!MediaBrowserServiceImplApi21.this.mRootExtrasList.isEmpty()) {
                        IMediaSession iMediaSession = token.getExtraBinder();
                        if (iMediaSession != null) {
                            Iterator<Bundle> iterator = MediaBrowserServiceImplApi21.this.mRootExtrasList.iterator();
                            while (iterator.hasNext()) {
                                BundleCompat.putBinder(iterator.next(), "extra_session_binder", iMediaSession.asBinder());
                            }
                        }
                        MediaBrowserServiceImplApi21.this.mRootExtrasList.clear();
                    }
                    MediaBrowserServiceCompatApi21.setSessionToken(MediaBrowserServiceImplApi21.this.mServiceObj, token.getToken());
                }
            });
        }

    }

    @RequiresApi(value=23)
    class MediaBrowserServiceImplApi23
    extends MediaBrowserServiceImplApi21
    implements MediaBrowserServiceCompatApi23.ServiceCompatProxy {
        MediaBrowserServiceImplApi23() {
        }

        @Override
        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi23.createService((Context)MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        @Override
        public void onLoadItem(String string, final MediaBrowserServiceCompatApi21.ResultWrapper<Parcel> object) {
            object = new Result<MediaBrowserCompat.MediaItem>((Object)string){

                @Override
                public void detach() {
                    object.detach();
                }

                @Override
                void onResultSent(MediaBrowserCompat.MediaItem mediaItem) {
                    if (mediaItem == null) {
                        object.sendResult(null);
                        return;
                    }
                    Parcel parcel = Parcel.obtain();
                    mediaItem.writeToParcel(parcel, 0);
                    object.sendResult(parcel);
                }
            };
            MediaBrowserServiceCompat.this.onLoadItem(string, (Result<MediaBrowserCompat.MediaItem>)object);
        }

    }

    @RequiresApi(value=26)
    class MediaBrowserServiceImplApi26
    extends MediaBrowserServiceImplApi23
    implements MediaBrowserServiceCompatApi26.ServiceCompatProxy {
        MediaBrowserServiceImplApi26() {
        }

        @Override
        public Bundle getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection != null) {
                if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                    return null;
                }
                return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
            }
            return MediaBrowserServiceCompatApi26.getBrowserRootHints(this.mServiceObj);
        }

        @Override
        void notifyChildrenChangedForFramework(String string, Bundle bundle) {
            if (bundle != null) {
                MediaBrowserServiceCompatApi26.notifyChildrenChanged(this.mServiceObj, string, bundle);
                return;
            }
            super.notifyChildrenChangedForFramework(string, bundle);
        }

        @Override
        public void onCreate() {
            this.mServiceObj = MediaBrowserServiceCompatApi26.createService((Context)MediaBrowserServiceCompat.this, this);
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj);
        }

        @Override
        public void onLoadChildren(String string, MediaBrowserServiceCompatApi26.ResultWrapper object, Bundle bundle) {
            object = new Result<List<MediaBrowserCompat.MediaItem>>((Object)string, (MediaBrowserServiceCompatApi26.ResultWrapper)object){
                final /* synthetic */ MediaBrowserServiceCompatApi26.ResultWrapper val$resultWrapper;
                {
                    this.val$resultWrapper = resultWrapper;
                    super(object);
                }

                @Override
                public void detach() {
                    this.val$resultWrapper.detach();
                }

                @Override
                void onResultSent(List<MediaBrowserCompat.MediaItem> object) {
                    if (object != null) {
                        ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList<MediaBrowserCompat.MediaItem>();
                        Iterator<MediaBrowserCompat.MediaItem> iterator = object.iterator();
                        do {
                            object = arrayList;
                            if (iterator.hasNext()) {
                                object = iterator.next();
                                Parcel parcel = Parcel.obtain();
                                object.writeToParcel(parcel, 0);
                                arrayList.add((MediaBrowserCompat.MediaItem)parcel);
                                continue;
                            }
                            break;
                        } while (true);
                    } else {
                        object = null;
                    }
                    this.val$resultWrapper.sendResult((List<Parcel>)object, this.getFlags());
                }
            };
            MediaBrowserServiceCompat.this.onLoadChildren(string, (Result<List<MediaBrowserCompat.MediaItem>>)object, bundle);
        }

    }

    class MediaBrowserServiceImplBase
    implements MediaBrowserServiceImpl {
        private Messenger mMessenger;

        MediaBrowserServiceImplBase() {
        }

        @Override
        public Bundle getBrowserRootHints() {
            if (MediaBrowserServiceCompat.this.mCurConnection == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
            }
            if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }

        @Override
        public void notifyChildrenChanged(final @NonNull String string, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    for (Object object : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                        object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object);
                        List<Pair<IBinder, Bundle>> list = object.subscriptions.get(string);
                        if (list == null) continue;
                        for (Pair pair : list) {
                            if (!MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle)pair.second)) continue;
                            MediaBrowserServiceCompat.this.performLoadChildren(string, (ConnectionRecord)object, (Bundle)pair.second);
                        }
                    }
                }
            });
        }

        @Override
        public IBinder onBind(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.mMessenger.getBinder();
            }
            return null;
        }

        @Override
        public void onCreate() {
            this.mMessenger = new Messenger((Handler)MediaBrowserServiceCompat.this.mHandler);
        }

        @Override
        public void setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    Iterator<ConnectionRecord> iterator = MediaBrowserServiceCompat.this.mConnections.values().iterator();
                    while (iterator.hasNext()) {
                        ConnectionRecord connectionRecord = iterator.next();
                        try {
                            connectionRecord.callbacks.onConnect(connectionRecord.root.getRootId(), token, connectionRecord.root.getExtras());
                            continue;
                        }
                        catch (RemoteException remoteException) {}
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Connection for ");
                        stringBuilder.append(connectionRecord.pkg);
                        stringBuilder.append(" is no longer valid.");
                        Log.w((String)MediaBrowserServiceCompat.TAG, (String)stringBuilder.toString());
                        iterator.remove();
                    }
                    return;
                }
            });
        }

    }

    public static class Result<T> {
        private final Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendErrorCalled;
        private boolean mSendProgressUpdateCalled;
        private boolean mSendResultCalled;

        Result(Object object) {
            this.mDebug = object;
        }

        private void checkExtraFields(Bundle bundle) {
            float f;
            if (bundle == null) {
                return;
            }
            if (bundle.containsKey("android.media.browse.extra.DOWNLOAD_PROGRESS") && ((f = bundle.getFloat("android.media.browse.extra.DOWNLOAD_PROGRESS")) < -1.0E-5f || f > 1.00001f)) {
                throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0].");
            }
        }

        public void detach() {
            if (this.mDetachCalled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("detach() called when detach() had already been called for: ");
                stringBuilder.append(this.mDebug);
                throw new IllegalStateException(stringBuilder.toString());
            }
            if (this.mSendResultCalled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("detach() called when sendResult() had already been called for: ");
                stringBuilder.append(this.mDebug);
                throw new IllegalStateException(stringBuilder.toString());
            }
            if (this.mSendErrorCalled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("detach() called when sendError() had already been called for: ");
                stringBuilder.append(this.mDebug);
                throw new IllegalStateException(stringBuilder.toString());
            }
            this.mDetachCalled = true;
        }

        int getFlags() {
            return this.mFlags;
        }

        boolean isDone() {
            if (!(this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled)) {
                return false;
            }
            return true;
        }

        void onErrorSent(Bundle object) {
            object = new StringBuilder();
            object.append("It is not supported to send an error for ");
            object.append(this.mDebug);
            throw new UnsupportedOperationException(object.toString());
        }

        void onProgressUpdateSent(Bundle object) {
            object = new StringBuilder();
            object.append("It is not supported to send an interim update for ");
            object.append(this.mDebug);
            throw new UnsupportedOperationException(object.toString());
        }

        void onResultSent(T t) {
        }

        public void sendError(Bundle object) {
            if (!this.mSendResultCalled && !this.mSendErrorCalled) {
                this.mSendErrorCalled = true;
                this.onErrorSent((Bundle)object);
                return;
            }
            object = new StringBuilder();
            object.append("sendError() called when either sendResult() or sendError() had already been called for: ");
            object.append(this.mDebug);
            throw new IllegalStateException(object.toString());
        }

        public void sendProgressUpdate(Bundle object) {
            if (!this.mSendResultCalled && !this.mSendErrorCalled) {
                this.checkExtraFields((Bundle)object);
                this.mSendProgressUpdateCalled = true;
                this.onProgressUpdateSent((Bundle)object);
                return;
            }
            object = new StringBuilder();
            object.append("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: ");
            object.append(this.mDebug);
            throw new IllegalStateException(object.toString());
        }

        public void sendResult(T object) {
            if (!this.mSendResultCalled && !this.mSendErrorCalled) {
                this.mSendResultCalled = true;
                this.onResultSent(object);
                return;
            }
            object = new StringBuilder();
            object.append("sendResult() called when either sendResult() or sendError() had already been called for: ");
            object.append(this.mDebug);
            throw new IllegalStateException(object.toString());
        }

        void setFlags(int n) {
            this.mFlags = n;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    private static @interface ResultFlags {
    }

    private class ServiceBinderImpl {
        ServiceBinderImpl() {
        }

        public void addSubscription(final String string, final IBinder iBinder, final Bundle bundle, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        object = new StringBuilder();
                        object.append("addSubscription for callback that isn't registered id=");
                        object.append(string);
                        Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                        return;
                    }
                    MediaBrowserServiceCompat.this.addSubscription(string, (ConnectionRecord)object, iBinder, bundle);
                }
            });
        }

        public void connect(final String string, int n, Bundle object, final ServiceCallbacks serviceCallbacks) {
            if (!MediaBrowserServiceCompat.this.isValidPackage(string, n)) {
                object = new StringBuilder();
                object.append("Package/uid mismatch: uid=");
                object.append(n);
                object.append(" package=");
                object.append(string);
                throw new IllegalArgumentException(object.toString());
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable((Bundle)object, n){
                final /* synthetic */ Bundle val$rootHints;
                final /* synthetic */ int val$uid;
                {
                    this.val$rootHints = bundle;
                    this.val$uid = n;
                }

                /*
                 * Loose catch block
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(object);
                    ConnectionRecord connectionRecord = new ConnectionRecord();
                    connectionRecord.pkg = string;
                    connectionRecord.rootHints = this.val$rootHints;
                    connectionRecord.callbacks = serviceCallbacks;
                    connectionRecord.root = MediaBrowserServiceCompat.this.onGetRoot(string, this.val$uid, this.val$rootHints);
                    if (connectionRecord.root == null) {
                        object = new StringBuilder();
                        object.append("No root for client ");
                        object.append(string);
                        object.append(" from service ");
                        object.append(this.getClass().getName());
                        Log.i((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                        serviceCallbacks.onConnectFailed();
                        return;
                    }
                    try {
                        MediaBrowserServiceCompat.this.mConnections.put((IBinder)object, connectionRecord);
                        object.linkToDeath((IBinder.DeathRecipient)connectionRecord, 0);
                        if (MediaBrowserServiceCompat.this.mSession == null) return;
                        serviceCallbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras());
                        return;
                    }
                    catch (RemoteException remoteException) {}
                    catch (RemoteException remoteException) {}
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Calling onConnectFailed() failed. Ignoring. pkg=");
                    stringBuilder.append(string);
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)stringBuilder.toString());
                    return;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Calling onConnect() failed. Dropping client. pkg=");
                    stringBuilder2.append(string);
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)stringBuilder2.toString());
                    MediaBrowserServiceCompat.this.mConnections.remove(object);
                }
            });
        }

        public void disconnect(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(object)) != null) {
                        object.callbacks.asBinder().unlinkToDeath((IBinder.DeathRecipient)object, 0);
                    }
                }
            });
        }

        public void getMediaItem(final String string, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (!TextUtils.isEmpty((CharSequence)string)) {
                if (resultReceiver == null) {
                    return;
                }
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                    @Override
                    public void run() {
                        Object object = serviceCallbacks.asBinder();
                        if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                            object = new StringBuilder();
                            object.append("getMediaItem for callback that isn't registered id=");
                            object.append(string);
                            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                            return;
                        }
                        MediaBrowserServiceCompat.this.performLoadItem(string, (ConnectionRecord)object, resultReceiver);
                    }
                });
                return;
            }
        }

        public void registerCallbacks(final ServiceCallbacks serviceCallbacks, final Bundle bundle) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    IBinder iBinder = serviceCallbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder);
                    ConnectionRecord connectionRecord = new ConnectionRecord();
                    connectionRecord.callbacks = serviceCallbacks;
                    connectionRecord.rootHints = bundle;
                    MediaBrowserServiceCompat.this.mConnections.put(iBinder, connectionRecord);
                    try {
                        iBinder.linkToDeath((IBinder.DeathRecipient)connectionRecord, 0);
                        return;
                    }
                    catch (RemoteException remoteException) {}
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)"IBinder is already dead.");
                }
            });
        }

        public void removeSubscription(final String string, final IBinder iBinder, final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        object = new StringBuilder();
                        object.append("removeSubscription for callback that isn't registered id=");
                        object.append(string);
                        Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                        return;
                    }
                    if (!MediaBrowserServiceCompat.this.removeSubscription(string, (ConnectionRecord)object, iBinder)) {
                        object = new StringBuilder();
                        object.append("removeSubscription called for ");
                        object.append(string);
                        object.append(" which is not subscribed");
                        Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                    }
                }
            });
        }

        public void search(final String string, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (!TextUtils.isEmpty((CharSequence)string)) {
                if (resultReceiver == null) {
                    return;
                }
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                    @Override
                    public void run() {
                        Object object = serviceCallbacks.asBinder();
                        if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                            object = new StringBuilder();
                            object.append("search for callback that isn't registered query=");
                            object.append(string);
                            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                            return;
                        }
                        MediaBrowserServiceCompat.this.performSearch(string, bundle, (ConnectionRecord)object, resultReceiver);
                    }
                });
                return;
            }
        }

        public void sendCustomAction(final String string, final Bundle bundle, final ResultReceiver resultReceiver, final ServiceCallbacks serviceCallbacks) {
            if (!TextUtils.isEmpty((CharSequence)string)) {
                if (resultReceiver == null) {
                    return;
                }
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                    @Override
                    public void run() {
                        Object object = serviceCallbacks.asBinder();
                        if ((object = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                            object = new StringBuilder();
                            object.append("sendCustomAction for callback that isn't registered action=");
                            object.append(string);
                            object.append(", extras=");
                            object.append((Object)bundle);
                            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                            return;
                        }
                        MediaBrowserServiceCompat.this.performCustomAction(string, bundle, (ConnectionRecord)object, resultReceiver);
                    }
                });
                return;
            }
        }

        public void unregisterCallbacks(final ServiceCallbacks serviceCallbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    IBinder iBinder = serviceCallbacks.asBinder();
                    ConnectionRecord connectionRecord = (ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder);
                    if (connectionRecord != null) {
                        iBinder.unlinkToDeath((IBinder.DeathRecipient)connectionRecord, 0);
                    }
                }
            });
        }

    }

    private static interface ServiceCallbacks {
        public IBinder asBinder();

        public void onConnect(String var1, MediaSessionCompat.Token var2, Bundle var3) throws RemoteException;

        public void onConnectFailed() throws RemoteException;

        public void onLoadChildren(String var1, List<MediaBrowserCompat.MediaItem> var2, Bundle var3) throws RemoteException;
    }

    private static class ServiceCallbacksCompat
    implements ServiceCallbacks {
        final Messenger mCallbacks;

        ServiceCallbacksCompat(Messenger messenger) {
            this.mCallbacks = messenger;
        }

        private void sendRequest(int n, Bundle bundle) throws RemoteException {
            Message message = Message.obtain();
            message.what = n;
            message.arg1 = 2;
            message.setData(bundle);
            this.mCallbacks.send(message);
        }

        @Override
        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        @Override
        public void onConnect(String string, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException {
            Bundle bundle2 = bundle;
            if (bundle == null) {
                bundle2 = new Bundle();
            }
            bundle2.putInt("extra_service_version", 2);
            bundle = new Bundle();
            bundle.putString("data_media_item_id", string);
            bundle.putParcelable("data_media_session_token", (Parcelable)token);
            bundle.putBundle("data_root_hints", bundle2);
            this.sendRequest(1, bundle);
        }

        @Override
        public void onConnectFailed() throws RemoteException {
            this.sendRequest(2, null);
        }

        @Override
        public void onLoadChildren(String arrayList, List<MediaBrowserCompat.MediaItem> list, Bundle bundle) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", (String)((Object)arrayList));
            bundle2.putBundle("data_options", bundle);
            if (list != null) {
                arrayList = list instanceof ArrayList ? (ArrayList)list : new ArrayList<MediaBrowserCompat.MediaItem>(list);
                bundle2.putParcelableArrayList("data_media_item_list", arrayList);
            }
            this.sendRequest(3, bundle2);
        }
    }

    private final class ServiceHandler
    extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl;

        ServiceHandler() {
            this.mServiceBinderImpl = new ServiceBinderImpl();
        }

        public void handleMessage(Message message) {
            Object object = message.getData();
            switch (message.what) {
                default: {
                    object = new StringBuilder();
                    object.append("Unhandled message: ");
                    object.append((Object)message);
                    object.append("\n  Service version: ");
                    object.append(2);
                    object.append("\n  Client version: ");
                    object.append(message.arg1);
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                    return;
                }
                case 9: {
                    this.mServiceBinderImpl.sendCustomAction(object.getString("data_custom_action"), object.getBundle("data_custom_action_extras"), (ResultReceiver)object.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 8: {
                    this.mServiceBinderImpl.search(object.getString("data_search_query"), object.getBundle("data_search_extras"), (ResultReceiver)object.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 7: {
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 6: {
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(message.replyTo), object.getBundle("data_root_hints"));
                    return;
                }
                case 5: {
                    this.mServiceBinderImpl.getMediaItem(object.getString("data_media_item_id"), (ResultReceiver)object.getParcelable("data_result_receiver"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 4: {
                    this.mServiceBinderImpl.removeSubscription(object.getString("data_media_item_id"), BundleCompat.getBinder((Bundle)object, "data_callback_token"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 3: {
                    this.mServiceBinderImpl.addSubscription(object.getString("data_media_item_id"), BundleCompat.getBinder((Bundle)object, "data_callback_token"), object.getBundle("data_options"), new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 2: {
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(message.replyTo));
                    return;
                }
                case 1: 
            }
            this.mServiceBinderImpl.connect(object.getString("data_package_name"), object.getInt("data_calling_uid"), object.getBundle("data_root_hints"), new ServiceCallbacksCompat(message.replyTo));
        }

        public void postOrRun(Runnable runnable) {
            if (Thread.currentThread() == this.getLooper().getThread()) {
                runnable.run();
                return;
            }
            this.post(runnable);
        }

        public boolean sendMessageAtTime(Message message, long l) {
            Bundle bundle = message.getData();
            bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            bundle.putInt("data_calling_uid", Binder.getCallingUid());
            return super.sendMessageAtTime(message, l);
        }
    }

}
