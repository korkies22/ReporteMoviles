/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.util.List;

@RequiresApi(value=21)
static class MediaBrowserCompat.MediaBrowserImplApi21
implements MediaBrowserCompat.MediaBrowserImpl,
MediaBrowserCompat.MediaBrowserServiceCallbackImpl,
MediaBrowserCompat.ConnectionCallback.ConnectionCallbackInternal {
    protected final Object mBrowserObj;
    protected Messenger mCallbacksMessenger;
    final Context mContext;
    protected final MediaBrowserCompat.CallbackHandler mHandler = new MediaBrowserCompat.CallbackHandler(this);
    private MediaSessionCompat.Token mMediaSessionToken;
    protected final Bundle mRootHints;
    protected MediaBrowserCompat.ServiceBinderWrapper mServiceBinderWrapper;
    protected int mServiceVersion;
    private final ArrayMap<String, MediaBrowserCompat.Subscription> mSubscriptions = new ArrayMap();

    MediaBrowserCompat.MediaBrowserImplApi21(Context context, ComponentName componentName, MediaBrowserCompat.ConnectionCallback connectionCallback, Bundle bundle) {
        this.mContext = context;
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        bundle2.putInt("extra_client_version", 1);
        this.mRootHints = new Bundle(bundle2);
        connectionCallback.setInternalConnectionCallback(this);
        this.mBrowserObj = MediaBrowserCompatApi21.createBrowser(context, componentName, connectionCallback.mConnectionCallbackObj, this.mRootHints);
    }

    @Override
    public void connect() {
        MediaBrowserCompatApi21.connect(this.mBrowserObj);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        block3 : {
            if (this.mServiceBinderWrapper != null && this.mCallbacksMessenger != null) {
                try {
                    this.mServiceBinderWrapper.unregisterCallbackMessenger(this.mCallbacksMessenger);
                    break block3;
                }
                catch (RemoteException remoteException) {}
                Log.i((String)MediaBrowserCompat.TAG, (String)"Remote error unregistering client messenger.");
            }
        }
        MediaBrowserCompatApi21.disconnect(this.mBrowserObj);
    }

    @Nullable
    @Override
    public Bundle getExtras() {
        return MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
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
        if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj)) {
            Log.i((String)MediaBrowserCompat.TAG, (String)"Not connected, unable to retrieve the MediaItem.");
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    itemCallback.onError(string);
                }
            });
            return;
        }
        if (this.mServiceBinderWrapper == null) {
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
        return MediaBrowserCompatApi21.getRoot(this.mBrowserObj);
    }

    @Override
    public ComponentName getServiceComponent() {
        return MediaBrowserCompatApi21.getServiceComponent(this.mBrowserObj);
    }

    @NonNull
    @Override
    public MediaSessionCompat.Token getSessionToken() {
        if (this.mMediaSessionToken == null) {
            this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj));
        }
        return this.mMediaSessionToken;
    }

    @Override
    public boolean isConnected() {
        return MediaBrowserCompatApi21.isConnected(this.mBrowserObj);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onConnected() {
        IMediaSession iMediaSession;
        Bundle bundle;
        block5 : {
            bundle = MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
            if (bundle == null) {
                return;
            }
            this.mServiceVersion = bundle.getInt("extra_service_version", 0);
            IBinder iBinder = BundleCompat.getBinder(bundle, "extra_messenger");
            if (iBinder != null) {
                this.mServiceBinderWrapper = new MediaBrowserCompat.ServiceBinderWrapper(iBinder, this.mRootHints);
                this.mCallbacksMessenger = new Messenger((Handler)this.mHandler);
                this.mHandler.setCallbacksMessenger(this.mCallbacksMessenger);
                try {
                    this.mServiceBinderWrapper.registerCallbackMessenger(this.mCallbacksMessenger);
                    break block5;
                }
                catch (RemoteException remoteException) {}
                Log.i((String)MediaBrowserCompat.TAG, (String)"Remote error registering client messenger.");
            }
        }
        if ((iMediaSession = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "extra_session_binder"))) != null) {
            this.mMediaSessionToken = MediaSessionCompat.Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj), iMediaSession);
        }
    }

    @Override
    public void onConnectionFailed() {
    }

    @Override
    public void onConnectionFailed(Messenger messenger) {
    }

    @Override
    public void onConnectionSuspended() {
        this.mServiceBinderWrapper = null;
        this.mCallbacksMessenger = null;
        this.mMediaSessionToken = null;
        this.mHandler.setCallbacksMessenger(null);
    }

    @Override
    public void onLoadChildren(Messenger object, String string, List list, Bundle bundle) {
        if (this.mCallbacksMessenger != object) {
            return;
        }
        object = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(string);
        if (object == null) {
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

    @Override
    public void onServiceConnected(Messenger messenger, String string, MediaSessionCompat.Token token, Bundle bundle) {
    }

    @Override
    public void search(final @NonNull String string, final Bundle bundle, final @NonNull MediaBrowserCompat.SearchCallback searchCallback) {
        if (!this.isConnected()) {
            throw new IllegalStateException("search() called while not connected");
        }
        if (this.mServiceBinderWrapper == null) {
            Log.i((String)MediaBrowserCompat.TAG, (String)"The connected service doesn't support search.");
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    searchCallback.onError(string, bundle);
                }
            });
            return;
        }
        MediaBrowserCompat.SearchResultReceiver searchResultReceiver = new MediaBrowserCompat.SearchResultReceiver(string, bundle, searchCallback, this.mHandler);
        try {
            this.mServiceBinderWrapper.search(string, bundle, searchResultReceiver, this.mCallbacksMessenger);
            return;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Remote error searching items with query: ");
            stringBuilder.append(string);
            Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString(), (Throwable)remoteException);
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    searchCallback.onError(string, bundle);
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
        if (this.mServiceBinderWrapper == null) {
            Log.i((String)MediaBrowserCompat.TAG, (String)"The connected service doesn't support sendCustomAction.");
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
        MediaBrowserCompat.Subscription subscription;
        void var3_8;
        MediaBrowserCompat.Subscription subscription2 = subscription = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(string);
        if (subscription == null) {
            subscription2 = new MediaBrowserCompat.Subscription();
            this.mSubscriptions.put(string, subscription2);
        }
        ((MediaBrowserCompat.SubscriptionCallback)var3_8).setSubscription(subscription2);
        if (object == null) {
            Object var2_3 = null;
        } else {
            Bundle bundle = new Bundle(object);
        }
        subscription2.putCallback(this.mContext, (Bundle)var2_5, (MediaBrowserCompat.SubscriptionCallback)var3_8);
        if (this.mServiceBinderWrapper == null) {
            MediaBrowserCompatApi21.subscribe(this.mBrowserObj, string, ((MediaBrowserCompat.SubscriptionCallback)var3_8).mSubscriptionCallbackObj);
            return;
        }
        try {
            this.mServiceBinderWrapper.addSubscription(string, ((MediaBrowserCompat.SubscriptionCallback)var3_8).mToken, (Bundle)var2_5, this.mCallbacksMessenger);
            return;
        }
        catch (RemoteException remoteException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Remote error subscribing media item: ");
        stringBuilder.append(string);
        Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
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
        block9 : {
            block10 : {
                var4_3 = (MediaBrowserCompat.Subscription)this.mSubscriptions.get(var1_1);
                if (var4_3 == null) {
                    return;
                }
                if (this.mServiceBinderWrapper != null) break block10;
                if (var2_2 == null) {
                    MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, var1_1);
                } else {
                    var5_4 = var4_3.getCallbacks();
                    var6_8 = var4_3.getOptionsList();
                    for (var3_10 = var5_4.size() - 1; var3_10 >= 0; --var3_10) {
                        if (var5_4.get(var3_10) != var2_2) continue;
                        var5_4.remove(var3_10);
                        var6_8.remove(var3_10);
                    }
                    if (var5_4.size() == 0) {
                        MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, var1_1);
                    }
                }
                break block9;
            }
            if (var2_2 != null) ** GOTO lbl23
            try {
                this.mServiceBinderWrapper.removeSubscription(var1_1, null, this.mCallbacksMessenger);
                break block9;
lbl23: // 1 sources:
                var5_5 = var4_3.getCallbacks();
                var6_9 = var4_3.getOptionsList();
                for (var3_11 = var5_5.size() - 1; var3_11 >= 0; --var3_11) {
                    if (var5_5.get(var3_11) != var2_2) continue;
                    this.mServiceBinderWrapper.removeSubscription(var1_1, MediaBrowserCompat.SubscriptionCallback.access$000(var2_2), this.mCallbacksMessenger);
                    var5_5.remove(var3_11);
                    var6_9.remove(var3_11);
                }
                break block9;
            }
            catch (RemoteException var5_6) {}
            var5_7 = new StringBuilder();
            var5_7.append("removeSubscription failed with RemoteException parentId=");
            var5_7.append(var1_1);
            Log.d((String)"MediaBrowserCompat", (String)var5_7.toString());
        }
        if (!var4_3.isEmpty()) {
            if (var2_2 != null) return;
        }
        this.mSubscriptions.remove(var1_1);
    }

}
