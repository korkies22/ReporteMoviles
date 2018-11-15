/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

private class MediaBrowserServiceCompat.ServiceBinderImpl {
    MediaBrowserServiceCompat.ServiceBinderImpl() {
    }

    public void addSubscription(final String string, final IBinder iBinder, final Bundle bundle, final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

            @Override
            public void run() {
                Object object = serviceCallbacks.asBinder();
                if ((object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                    object = new StringBuilder();
                    object.append("addSubscription for callback that isn't registered id=");
                    object.append(string);
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                    return;
                }
                MediaBrowserServiceCompat.this.addSubscription(string, (MediaBrowserServiceCompat.ConnectionRecord)object, iBinder, bundle);
            }
        });
    }

    public void connect(final String string, int n, Bundle object, final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
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
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord(MediaBrowserServiceCompat.this);
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

    public void disconnect(final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

            @Override
            public void run() {
                Object object = serviceCallbacks.asBinder();
                if ((object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove(object)) != null) {
                    object.callbacks.asBinder().unlinkToDeath((IBinder.DeathRecipient)object, 0);
                }
            }
        });
    }

    public void getMediaItem(final String string, final ResultReceiver resultReceiver, final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        if (!TextUtils.isEmpty((CharSequence)string)) {
            if (resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        object = new StringBuilder();
                        object.append("getMediaItem for callback that isn't registered id=");
                        object.append(string);
                        Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                        return;
                    }
                    MediaBrowserServiceCompat.this.performLoadItem(string, (MediaBrowserServiceCompat.ConnectionRecord)object, resultReceiver);
                }
            });
            return;
        }
    }

    public void registerCallbacks(final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, final Bundle bundle) {
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
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord(MediaBrowserServiceCompat.this);
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

    public void removeSubscription(final String string, final IBinder iBinder, final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

            @Override
            public void run() {
                Object object = serviceCallbacks.asBinder();
                if ((object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                    object = new StringBuilder();
                    object.append("removeSubscription for callback that isn't registered id=");
                    object.append(string);
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                    return;
                }
                if (!MediaBrowserServiceCompat.this.removeSubscription(string, (MediaBrowserServiceCompat.ConnectionRecord)object, iBinder)) {
                    object = new StringBuilder();
                    object.append("removeSubscription called for ");
                    object.append(string);
                    object.append(" which is not subscribed");
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                }
            }
        });
    }

    public void search(final String string, final Bundle bundle, final ResultReceiver resultReceiver, final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        if (!TextUtils.isEmpty((CharSequence)string)) {
            if (resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        object = new StringBuilder();
                        object.append("search for callback that isn't registered query=");
                        object.append(string);
                        Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                        return;
                    }
                    MediaBrowserServiceCompat.this.performSearch(string, bundle, (MediaBrowserServiceCompat.ConnectionRecord)object, resultReceiver);
                }
            });
            return;
        }
    }

    public void sendCustomAction(final String string, final Bundle bundle, final ResultReceiver resultReceiver, final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        if (!TextUtils.isEmpty((CharSequence)string)) {
            if (resultReceiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

                @Override
                public void run() {
                    Object object = serviceCallbacks.asBinder();
                    if ((object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object)) == null) {
                        object = new StringBuilder();
                        object.append("sendCustomAction for callback that isn't registered action=");
                        object.append(string);
                        object.append(", extras=");
                        object.append((Object)bundle);
                        Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
                        return;
                    }
                    MediaBrowserServiceCompat.this.performCustomAction(string, bundle, (MediaBrowserServiceCompat.ConnectionRecord)object, resultReceiver);
                }
            });
            return;
        }
    }

    public void unregisterCallbacks(final MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable(){

            @Override
            public void run() {
                IBinder iBinder = serviceCallbacks.asBinder();
                MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.remove((Object)iBinder);
                if (connectionRecord != null) {
                    iBinder.unlinkToDeath((IBinder.DeathRecipient)connectionRecord, 0);
                }
            }
        });
    }

}
