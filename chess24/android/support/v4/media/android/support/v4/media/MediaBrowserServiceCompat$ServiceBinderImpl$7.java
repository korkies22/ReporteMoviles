/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 */
package android.support.v4.media;

import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.ArrayMap;

class MediaBrowserServiceCompat.ServiceBinderImpl
implements Runnable {
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks) {
        this.val$callbacks = serviceCallbacks;
    }

    @Override
    public void run() {
        IBinder iBinder = this.val$callbacks.asBinder();
        MediaBrowserServiceCompat.ConnectionRecord connectionRecord = (MediaBrowserServiceCompat.ConnectionRecord)ServiceBinderImpl.this.this$0.mConnections.remove((Object)iBinder);
        if (connectionRecord != null) {
            iBinder.unlinkToDeath((IBinder.DeathRecipient)connectionRecord, 0);
        }
    }
}
