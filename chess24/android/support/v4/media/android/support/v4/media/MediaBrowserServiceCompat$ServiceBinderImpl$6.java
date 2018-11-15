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
import android.support.v4.util.ArrayMap;
import android.util.Log;

class MediaBrowserServiceCompat.ServiceBinderImpl
implements Runnable {
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;
    final /* synthetic */ Bundle val$rootHints;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, Bundle bundle) {
        this.val$callbacks = serviceCallbacks;
        this.val$rootHints = bundle;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        IBinder iBinder = this.val$callbacks.asBinder();
        ServiceBinderImpl.this.this$0.mConnections.remove((Object)iBinder);
        MediaBrowserServiceCompat.ConnectionRecord connectionRecord = new MediaBrowserServiceCompat.ConnectionRecord(ServiceBinderImpl.this.this$0);
        connectionRecord.callbacks = this.val$callbacks;
        connectionRecord.rootHints = this.val$rootHints;
        ServiceBinderImpl.this.this$0.mConnections.put(iBinder, connectionRecord);
        try {
            iBinder.linkToDeath((IBinder.DeathRecipient)connectionRecord, 0);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.w((String)MediaBrowserServiceCompat.TAG, (String)"IBinder is already dead.");
    }
}
