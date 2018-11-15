/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;

class MediaBrowserServiceCompat.ServiceBinderImpl
implements Runnable {
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;
    final /* synthetic */ String val$id;
    final /* synthetic */ Bundle val$options;
    final /* synthetic */ IBinder val$token;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, String string, IBinder iBinder, Bundle bundle) {
        this.val$callbacks = serviceCallbacks;
        this.val$id = string;
        this.val$token = iBinder;
        this.val$options = bundle;
    }

    @Override
    public void run() {
        Object object = this.val$callbacks.asBinder();
        if ((object = (MediaBrowserServiceCompat.ConnectionRecord)ServiceBinderImpl.this.this$0.mConnections.get(object)) == null) {
            object = new StringBuilder();
            object.append("addSubscription for callback that isn't registered id=");
            object.append(this.val$id);
            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
            return;
        }
        ServiceBinderImpl.this.this$0.addSubscription(this.val$id, (MediaBrowserServiceCompat.ConnectionRecord)object, this.val$token, this.val$options);
    }
}
