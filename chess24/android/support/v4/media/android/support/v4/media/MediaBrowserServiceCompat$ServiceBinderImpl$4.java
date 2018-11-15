/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;

class MediaBrowserServiceCompat.ServiceBinderImpl
implements Runnable {
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;
    final /* synthetic */ String val$id;
    final /* synthetic */ IBinder val$token;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, String string, IBinder iBinder) {
        this.val$callbacks = serviceCallbacks;
        this.val$id = string;
        this.val$token = iBinder;
    }

    @Override
    public void run() {
        Object object = this.val$callbacks.asBinder();
        if ((object = (MediaBrowserServiceCompat.ConnectionRecord)ServiceBinderImpl.this.this$0.mConnections.get(object)) == null) {
            object = new StringBuilder();
            object.append("removeSubscription for callback that isn't registered id=");
            object.append(this.val$id);
            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
            return;
        }
        if (!ServiceBinderImpl.this.this$0.removeSubscription(this.val$id, (MediaBrowserServiceCompat.ConnectionRecord)object, this.val$token)) {
            object = new StringBuilder();
            object.append("removeSubscription called for ");
            object.append(this.val$id);
            object.append(" which is not subscribed");
            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
        }
    }
}
