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
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;
    final /* synthetic */ Bundle val$extras;
    final /* synthetic */ String val$query;
    final /* synthetic */ ResultReceiver val$receiver;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, String string, Bundle bundle, ResultReceiver resultReceiver) {
        this.val$callbacks = serviceCallbacks;
        this.val$query = string;
        this.val$extras = bundle;
        this.val$receiver = resultReceiver;
    }

    @Override
    public void run() {
        Object object = this.val$callbacks.asBinder();
        if ((object = (MediaBrowserServiceCompat.ConnectionRecord)ServiceBinderImpl.this.this$0.mConnections.get(object)) == null) {
            object = new StringBuilder();
            object.append("search for callback that isn't registered query=");
            object.append(this.val$query);
            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
            return;
        }
        ServiceBinderImpl.this.this$0.performSearch(this.val$query, this.val$extras, (MediaBrowserServiceCompat.ConnectionRecord)object, this.val$receiver);
    }
}
