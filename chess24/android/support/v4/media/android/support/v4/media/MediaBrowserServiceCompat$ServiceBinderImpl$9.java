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
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.util.Log;

class MediaBrowserServiceCompat.ServiceBinderImpl
implements Runnable {
    final /* synthetic */ String val$action;
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;
    final /* synthetic */ Bundle val$extras;
    final /* synthetic */ ResultReceiver val$receiver;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, String string, Bundle bundle, ResultReceiver resultReceiver) {
        this.val$callbacks = serviceCallbacks;
        this.val$action = string;
        this.val$extras = bundle;
        this.val$receiver = resultReceiver;
    }

    @Override
    public void run() {
        Object object = this.val$callbacks.asBinder();
        if ((object = (MediaBrowserServiceCompat.ConnectionRecord)ServiceBinderImpl.this.this$0.mConnections.get(object)) == null) {
            object = new StringBuilder();
            object.append("sendCustomAction for callback that isn't registered action=");
            object.append(this.val$action);
            object.append(", extras=");
            object.append((Object)this.val$extras);
            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
            return;
        }
        ServiceBinderImpl.this.this$0.performCustomAction(this.val$action, this.val$extras, (MediaBrowserServiceCompat.ConnectionRecord)object, this.val$receiver);
    }
}
