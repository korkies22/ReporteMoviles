/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;

class MediaBrowserServiceCompat.ServiceBinderImpl
implements Runnable {
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;
    final /* synthetic */ String val$pkg;
    final /* synthetic */ Bundle val$rootHints;
    final /* synthetic */ int val$uid;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, String string, Bundle bundle, int n) {
        this.val$callbacks = serviceCallbacks;
        this.val$pkg = string;
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
        Object object = this.val$callbacks.asBinder();
        ServiceBinderImpl.this.this$0.mConnections.remove(object);
        MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord(ServiceBinderImpl.this.this$0);
        connectionRecord.pkg = this.val$pkg;
        connectionRecord.rootHints = this.val$rootHints;
        connectionRecord.callbacks = this.val$callbacks;
        connectionRecord.root = ServiceBinderImpl.this.this$0.onGetRoot(this.val$pkg, this.val$uid, this.val$rootHints);
        if (connectionRecord.root == null) {
            object = new StringBuilder();
            object.append("No root for client ");
            object.append(this.val$pkg);
            object.append(" from service ");
            object.append(this.getClass().getName());
            Log.i((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
            this.val$callbacks.onConnectFailed();
            return;
        }
        try {
            ServiceBinderImpl.this.this$0.mConnections.put((IBinder)object, connectionRecord);
            object.linkToDeath((IBinder.DeathRecipient)connectionRecord, 0);
            if (ServiceBinderImpl.this.this$0.mSession == null) return;
            this.val$callbacks.onConnect(connectionRecord.root.getRootId(), ServiceBinderImpl.this.this$0.mSession, connectionRecord.root.getExtras());
            return;
        }
        catch (RemoteException remoteException) {}
        catch (RemoteException remoteException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calling onConnectFailed() failed. Ignoring. pkg=");
        stringBuilder.append(this.val$pkg);
        Log.w((String)MediaBrowserServiceCompat.TAG, (String)stringBuilder.toString());
        return;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Calling onConnect() failed. Dropping client. pkg=");
        stringBuilder2.append(this.val$pkg);
        Log.w((String)MediaBrowserServiceCompat.TAG, (String)stringBuilder2.toString());
        ServiceBinderImpl.this.this$0.mConnections.remove(object);
    }
}
