/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.util.List;
import java.util.Map;
import java.util.Set;

static class MediaBrowserCompat.MediaBrowserImplBase
implements MediaBrowserCompat.MediaBrowserImpl,
MediaBrowserCompat.MediaBrowserServiceCallbackImpl {
    static final int CONNECT_STATE_CONNECTED = 3;
    static final int CONNECT_STATE_CONNECTING = 2;
    static final int CONNECT_STATE_DISCONNECTED = 1;
    static final int CONNECT_STATE_DISCONNECTING = 0;
    static final int CONNECT_STATE_SUSPENDED = 4;
    final MediaBrowserCompat.ConnectionCallback mCallback;
    Messenger mCallbacksMessenger;
    final Context mContext;
    private Bundle mExtras;
    final MediaBrowserCompat.CallbackHandler mHandler = new MediaBrowserCompat.CallbackHandler(this);
    private MediaSessionCompat.Token mMediaSessionToken;
    final Bundle mRootHints;
    private String mRootId;
    MediaBrowserCompat.ServiceBinderWrapper mServiceBinderWrapper;
    final ComponentName mServiceComponent;
    MediaServiceConnection mServiceConnection;
    int mState = 1;
    private final ArrayMap<String, MediaBrowserCompat.Subscription> mSubscriptions = new ArrayMap();

    public MediaBrowserCompat.MediaBrowserImplBase(Context object, ComponentName componentName, MediaBrowserCompat.ConnectionCallback connectionCallback, Bundle bundle) {
        if (object == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (componentName == null) {
            throw new IllegalArgumentException("service component must not be null");
        }
        if (connectionCallback == null) {
            throw new IllegalArgumentException("connection callback must not be null");
        }
        this.mContext = object;
        this.mServiceComponent = componentName;
        this.mCallback = connectionCallback;
        object = bundle == null ? null : new Bundle(bundle);
        this.mRootHints = object;
    }

    private static String getStateLabel(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UNKNOWN/");
                stringBuilder.append(n);
                return stringBuilder.toString();
            }
            case 4: {
                return "CONNECT_STATE_SUSPENDED";
            }
            case 3: {
                return "CONNECT_STATE_CONNECTED";
            }
            case 2: {
                return "CONNECT_STATE_CONNECTING";
            }
            case 1: {
                return "CONNECT_STATE_DISCONNECTED";
            }
            case 0: 
        }
        return "CONNECT_STATE_DISCONNECTING";
    }

    private boolean isCurrent(Messenger object, String string) {
        if (this.mCallbacksMessenger == object && this.mState != 0 && this.mState != 1) {
            return true;
        }
        if (this.mState != 0 && this.mState != 1) {
            object = new StringBuilder();
            object.append(string);
            object.append(" for ");
            object.append((Object)this.mServiceComponent);
            object.append(" with mCallbacksMessenger=");
            object.append((Object)this.mCallbacksMessenger);
            object.append(" this=");
            object.append(this);
            Log.i((String)MediaBrowserCompat.TAG, (String)object.toString());
        }
        return false;
    }

    @Override
    public void connect() {
        if (this.mState != 0 && this.mState != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("connect() called while neigther disconnecting nor disconnected (state=");
            stringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(this.mState));
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.mState = 2;
        this.mHandler.post(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                boolean bl;
                block8 : {
                    if (MediaBrowserImplBase.this.mState == 0) {
                        return;
                    }
                    MediaBrowserImplBase.this.mState = 2;
                    if (MediaBrowserCompat.DEBUG && MediaBrowserImplBase.this.mServiceConnection != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("mServiceConnection should be null. Instead it is ");
                        stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    if (MediaBrowserImplBase.this.mServiceBinderWrapper != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("mServiceBinderWrapper should be null. Instead it is ");
                        stringBuilder.append(MediaBrowserImplBase.this.mServiceBinderWrapper);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("mCallbacksMessenger should be null. Instead it is ");
                        stringBuilder.append((Object)MediaBrowserImplBase.this.mCallbacksMessenger);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                    Intent intent = new Intent("android.media.browse.MediaBrowserService");
                    intent.setComponent(MediaBrowserImplBase.this.mServiceComponent);
                    MediaBrowserImplBase.this.mServiceConnection = new MediaServiceConnection();
                    try {
                        bl = MediaBrowserImplBase.this.mContext.bindService(intent, (ServiceConnection)MediaBrowserImplBase.this.mServiceConnection, 1);
                        break block8;
                    }
                    catch (Exception exception) {}
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed binding to service ");
                    stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                    Log.e((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                    bl = false;
                }
                if (!bl) {
                    MediaBrowserImplBase.this.forceCloseConnection();
                    MediaBrowserImplBase.this.mCallback.onConnectionFailed();
                }
                if (MediaBrowserCompat.DEBUG) {
                    Log.d((String)MediaBrowserCompat.TAG, (String)"connect...");
                    MediaBrowserImplBase.this.dump();
                }
            }
        });
    }

    @Override
    public void disconnect() {
        this.mState = 0;
        this.mHandler.post(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                block5 : {
                    if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                        try {
                            MediaBrowserImplBase.this.mServiceBinderWrapper.disconnect(MediaBrowserImplBase.this.mCallbacksMessenger);
                            break block5;
                        }
                        catch (RemoteException remoteException) {}
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("RemoteException during connect for ");
                        stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                        Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                    }
                }
                int n = MediaBrowserImplBase.this.mState;
                MediaBrowserImplBase.this.forceCloseConnection();
                if (n != 0) {
                    MediaBrowserImplBase.this.mState = n;
                }
                if (MediaBrowserCompat.DEBUG) {
                    Log.d((String)MediaBrowserCompat.TAG, (String)"disconnect...");
                    MediaBrowserImplBase.this.dump();
                }
            }
        });
    }

    void dump() {
        Log.d((String)MediaBrowserCompat.TAG, (String)"MediaBrowserCompat...");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  mServiceComponent=");
        stringBuilder.append((Object)this.mServiceComponent);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mCallback=");
        stringBuilder.append(this.mCallback);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mRootHints=");
        stringBuilder.append((Object)this.mRootHints);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mState=");
        stringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(this.mState));
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mServiceConnection=");
        stringBuilder.append(this.mServiceConnection);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mServiceBinderWrapper=");
        stringBuilder.append(this.mServiceBinderWrapper);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mCallbacksMessenger=");
        stringBuilder.append((Object)this.mCallbacksMessenger);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mRootId=");
        stringBuilder.append(this.mRootId);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  mMediaSessionToken=");
        stringBuilder.append(this.mMediaSessionToken);
        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
    }

    void forceCloseConnection() {
        if (this.mServiceConnection != null) {
            this.mContext.unbindService((ServiceConnection)this.mServiceConnection);
        }
        this.mState = 1;
        this.mServiceConnection = null;
        this.mServiceBinderWrapper = null;
        this.mCallbacksMessenger = null;
        this.mHandler.setCallbacksMessenger(null);
        this.mRootId = null;
        this.mMediaSessionToken = null;
    }

    @Nullable
    @Override
    public Bundle getExtras() {
        if (!this.isConnected()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getExtras() called while not connected (state=");
            stringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(this.mState));
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }
        return this.mExtras;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void getItem(final @NonNull String string, final @NonNull MediaBrowserCompat.ItemCallback itemCallback) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            throw new IllegalArgumentException("mediaId is empty");
        }
        if (itemCallback == null) {
            throw new IllegalArgumentException("cb is null");
        }
        if (!this.isConnected()) {
            Log.i((String)MediaBrowserCompat.TAG, (String)"Not connected, unable to retrieve the MediaItem.");
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    itemCallback.onError(string);
                }
            });
            return;
        }
        MediaBrowserCompat.ItemReceiver itemReceiver = new MediaBrowserCompat.ItemReceiver(string, itemCallback, this.mHandler);
        try {
            this.mServiceBinderWrapper.getMediaItem(string, itemReceiver, this.mCallbacksMessenger);
            return;
        }
        catch (RemoteException remoteException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Remote error getting media item: ");
        stringBuilder.append(string);
        Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                itemCallback.onError(string);
            }
        });
    }

    @NonNull
    @Override
    public String getRoot() {
        if (!this.isConnected()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getRoot() called while not connected(state=");
            stringBuilder.append(MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(this.mState));
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }
        return this.mRootId;
    }

    @NonNull
    @Override
    public ComponentName getServiceComponent() {
        if (!this.isConnected()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getServiceComponent() called while not connected (state=");
            stringBuilder.append(this.mState);
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }
        return this.mServiceComponent;
    }

    @NonNull
    @Override
    public MediaSessionCompat.Token getSessionToken() {
        if (!this.isConnected()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getSessionToken() called while not connected(state=");
            stringBuilder.append(this.mState);
            stringBuilder.append(")");
            throw new IllegalStateException(stringBuilder.toString());
        }
        return this.mMediaSessionToken;
    }

    @Override
    public boolean isConnected() {
        if (this.mState == 3) {
            return true;
        }
        return false;
    }

    @Override
    public void onConnectionFailed(Messenger object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onConnectFailed for ");
        stringBuilder.append((Object)this.mServiceComponent);
        Log.e((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        if (!this.isCurrent((Messenger)object, "onConnectFailed")) {
            return;
        }
        if (this.mState != 2) {
            object = new StringBuilder();
            object.append("onConnect from service while mState=");
            object.append(MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(this.mState));
            object.append("... ignoring");
            Log.w((String)MediaBrowserCompat.TAG, (String)object.toString());
            return;
        }
        this.forceCloseConnection();
        this.mCallback.onConnectionFailed();
    }

    @Override
    public void onLoadChildren(Messenger object, String string, List list, Bundle bundle) {
        if (!this.isCurrent((Messenger)object, "onLoadChildren")) {
            return;
        }
        if (MediaBrowserCompat.DEBUG) {
            object = new StringBuilder();
            object.append("onLoadChildren for ");
            object.append((Object)this.mServiceComponent);
            object.append(" id=");
            object.append(string);
            Log.d((String)MediaBrowserCompat.TAG, (String)object.toString());
        }
        if ((object = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(string)) == null) {
            if (MediaBrowserCompat.DEBUG) {
                object = new StringBuilder();
                object.append("onLoadChildren for id that isn't subscribed id=");
                object.append(string);
                Log.d((String)MediaBrowserCompat.TAG, (String)object.toString());
            }
            return;
        }
        if ((object = object.getCallback(this.mContext, bundle)) != null) {
            if (bundle == null) {
                if (list == null) {
                    object.onError(string);
                    return;
                }
                object.onChildrenLoaded(string, list);
                return;
            }
            if (list == null) {
                object.onError(string, bundle);
                return;
            }
            object.onChildrenLoaded(string, list, bundle);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onServiceConnected(Messenger object, String string, MediaSessionCompat.Token list, Bundle object2) {
        Object object3;
        if (!this.isCurrent((Messenger)object, "onConnect")) {
            return;
        }
        if (this.mState != 2) {
            object = new StringBuilder();
            object.append("onConnect from service while mState=");
            object.append(MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(this.mState));
            object.append("... ignoring");
            Log.w((String)MediaBrowserCompat.TAG, (String)object.toString());
            return;
        }
        this.mRootId = string;
        this.mMediaSessionToken = list;
        this.mExtras = object3;
        this.mState = 3;
        if (MediaBrowserCompat.DEBUG) {
            Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
            this.dump();
        }
        this.mCallback.onConnected();
        try {
            for (Map.Entry entry : this.mSubscriptions.entrySet()) {
                string = (String)entry.getKey();
                object3 = (MediaBrowserCompat.Subscription)entry.getValue();
                List<MediaBrowserCompat.SubscriptionCallback> list2 = object3.getCallbacks();
                object3 = object3.getOptionsList();
                for (int i = 0; i < list2.size(); ++i) {
                    this.mServiceBinderWrapper.addSubscription(string, list2.get(i).mToken, (Bundle)object3.get(i), this.mCallbacksMessenger);
                }
            }
            return;
        }
        catch (RemoteException remoteException) {}
        Log.d((String)MediaBrowserCompat.TAG, (String)"addSubscription failed with RemoteException.");
    }

    @Override
    public void search(@NonNull String charSequence, Bundle bundle, final @NonNull MediaBrowserCompat.SearchCallback searchCallback) {
        if (!this.isConnected()) {
            charSequence = new StringBuilder();
            charSequence.append("search() called while not connected (state=");
            charSequence.append(MediaBrowserCompat.MediaBrowserImplBase.getStateLabel(this.mState));
            charSequence.append(")");
            throw new IllegalStateException(charSequence.toString());
        }
        MediaBrowserCompat.SearchResultReceiver searchResultReceiver = new MediaBrowserCompat.SearchResultReceiver((String)charSequence, bundle, searchCallback, this.mHandler);
        try {
            this.mServiceBinderWrapper.search((String)charSequence, bundle, searchResultReceiver, this.mCallbacksMessenger);
            return;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Remote error searching items with query: ");
            stringBuilder.append((String)charSequence);
            Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
            this.mHandler.post(new Runnable((String)charSequence, bundle){
                final /* synthetic */ Bundle val$extras;
                final /* synthetic */ String val$query;
                {
                    this.val$query = string;
                    this.val$extras = bundle;
                }

                @Override
                public void run() {
                    searchCallback.onError(this.val$query, this.val$extras);
                }
            });
            return;
        }
    }

    @Override
    public void sendCustomAction(@NonNull String string, Bundle bundle, @Nullable MediaBrowserCompat.CustomActionCallback object) {
        if (!this.isConnected()) {
            object = new StringBuilder();
            object.append("Cannot send a custom action (");
            object.append(string);
            object.append(") with ");
            object.append("extras ");
            object.append((Object)bundle);
            object.append(" because the browser is not connected to the ");
            object.append("service.");
            throw new IllegalStateException(object.toString());
        }
        MediaBrowserCompat.CustomActionResultReceiver customActionResultReceiver = new MediaBrowserCompat.CustomActionResultReceiver(string, bundle, (MediaBrowserCompat.CustomActionCallback)object, this.mHandler);
        try {
            this.mServiceBinderWrapper.sendCustomAction(string, bundle, customActionResultReceiver, this.mCallbacksMessenger);
            return;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Remote error sending a custom action: action=");
            stringBuilder.append(string);
            stringBuilder.append(", extras=");
            stringBuilder.append((Object)bundle);
            Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
            if (object != null) {
                this.mHandler.post(new Runnable((MediaBrowserCompat.CustomActionCallback)object, string, bundle){
                    final /* synthetic */ String val$action;
                    final /* synthetic */ MediaBrowserCompat.CustomActionCallback val$callback;
                    final /* synthetic */ Bundle val$extras;
                    {
                        this.val$callback = customActionCallback;
                        this.val$action = string;
                        this.val$extras = bundle;
                    }

                    @Override
                    public void run() {
                        this.val$callback.onError(this.val$action, this.val$extras, null);
                    }
                });
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void subscribe(@NonNull String string, Bundle object, @NonNull MediaBrowserCompat.SubscriptionCallback subscriptionCallback) {
        void var2_5;
        void var3_8;
        MediaBrowserCompat.Subscription subscription;
        MediaBrowserCompat.Subscription subscription2 = subscription = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(string);
        if (subscription == null) {
            subscription2 = new MediaBrowserCompat.Subscription();
            this.mSubscriptions.put(string, subscription2);
        }
        if (object == null) {
            Object var2_3 = null;
        } else {
            Bundle bundle = new Bundle(object);
        }
        subscription2.putCallback(this.mContext, (Bundle)var2_5, (MediaBrowserCompat.SubscriptionCallback)var3_8);
        if (this.isConnected()) {
            try {
                this.mServiceBinderWrapper.addSubscription(string, ((MediaBrowserCompat.SubscriptionCallback)var3_8).mToken, (Bundle)var2_5, this.mCallbacksMessenger);
                return;
            }
            catch (RemoteException remoteException) {}
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("addSubscription failed with RemoteException parentId=");
            stringBuilder.append(string);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void unsubscribe(@NonNull String var1_1, MediaBrowserCompat.SubscriptionCallback var2_2) {
        block7 : {
            var4_3 = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(var1_1);
            if (var4_3 == null) {
                return;
            }
            if (var2_2 != null) ** GOTO lbl9
            try {
                if (this.isConnected()) {
                    this.mServiceBinderWrapper.removeSubscription(var1_1, null, this.mCallbacksMessenger);
                }
                break block7;
lbl9: // 1 sources:
                var5_4 = var4_3.getCallbacks();
                var6_7 = var4_3.getOptionsList();
                for (var3_8 = var5_4.size() - 1; var3_8 >= 0; --var3_8) {
                    if (var5_4.get(var3_8) != var2_2) continue;
                    if (this.isConnected()) {
                        this.mServiceBinderWrapper.removeSubscription(var1_1, MediaBrowserCompat.SubscriptionCallback.access$000(var2_2), this.mCallbacksMessenger);
                    }
                    var5_4.remove(var3_8);
                    var6_7.remove(var3_8);
                }
                break block7;
            }
            catch (RemoteException var5_5) {}
            var5_6 = new StringBuilder();
            var5_6.append("removeSubscription failed with RemoteException parentId=");
            var5_6.append(var1_1);
            Log.d((String)"MediaBrowserCompat", (String)var5_6.toString());
        }
        if (!var4_3.isEmpty()) {
            if (var2_2 != null) return;
        }
        this.mSubscriptions.remove(var1_1);
    }

    private class MediaServiceConnection
    implements ServiceConnection {
        MediaServiceConnection() {
        }

        private void postOrRun(Runnable runnable) {
            if (Thread.currentThread() == MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
                runnable.run();
                return;
            }
            MediaBrowserImplBase.this.mHandler.post(runnable);
        }

        boolean isCurrent(String string) {
            if (MediaBrowserImplBase.this.mServiceConnection == this && MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
                return true;
            }
            if (MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(" for ");
                stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                stringBuilder.append(" with mServiceConnection=");
                stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                stringBuilder.append(" this=");
                stringBuilder.append(this);
                Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            }
            return false;
        }

        public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
            this.postOrRun(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void run() {
                    if (MediaBrowserCompat.DEBUG) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("MediaServiceConnection.onServiceConnected name=");
                        stringBuilder.append((Object)componentName);
                        stringBuilder.append(" binder=");
                        stringBuilder.append((Object)iBinder);
                        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                        MediaBrowserImplBase.this.dump();
                    }
                    if (!MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                        return;
                    }
                    MediaBrowserImplBase.this.mServiceBinderWrapper = new MediaBrowserCompat.ServiceBinderWrapper(iBinder, MediaBrowserImplBase.this.mRootHints);
                    MediaBrowserImplBase.this.mCallbacksMessenger = new Messenger((Handler)MediaBrowserImplBase.this.mHandler);
                    MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserImplBase.this.mCallbacksMessenger);
                    MediaBrowserImplBase.this.mState = 2;
                    try {
                        if (MediaBrowserCompat.DEBUG) {
                            Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                            MediaBrowserImplBase.this.dump();
                        }
                        MediaBrowserImplBase.this.mServiceBinderWrapper.connect(MediaBrowserImplBase.this.mContext, MediaBrowserImplBase.this.mCallbacksMessenger);
                        return;
                    }
                    catch (RemoteException remoteException) {}
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("RemoteException during connect for ");
                    stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                    Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                    if (MediaBrowserCompat.DEBUG) {
                        Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                        MediaBrowserImplBase.this.dump();
                    }
                }
            });
        }

        public void onServiceDisconnected(final ComponentName componentName) {
            this.postOrRun(new Runnable(){

                @Override
                public void run() {
                    if (MediaBrowserCompat.DEBUG) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("MediaServiceConnection.onServiceDisconnected name=");
                        stringBuilder.append((Object)componentName);
                        stringBuilder.append(" this=");
                        stringBuilder.append(this);
                        stringBuilder.append(" mServiceConnection=");
                        stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                        Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                        MediaBrowserImplBase.this.dump();
                    }
                    if (!MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                        return;
                    }
                    MediaBrowserImplBase.this.mServiceBinderWrapper = null;
                    MediaBrowserImplBase.this.mCallbacksMessenger = null;
                    MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null);
                    MediaBrowserImplBase.this.mState = 4;
                    MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
                }
            });
        }

    }

}
