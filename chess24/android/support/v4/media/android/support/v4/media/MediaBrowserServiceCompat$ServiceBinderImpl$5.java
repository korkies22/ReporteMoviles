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
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.util.Log;

class MediaBrowserServiceCompat.ServiceBinderImpl
implements Runnable {
    final /* synthetic */ MediaBrowserServiceCompat.ServiceCallbacks val$callbacks;
    final /* synthetic */ String val$mediaId;
    final /* synthetic */ ResultReceiver val$receiver;

    MediaBrowserServiceCompat.ServiceBinderImpl(MediaBrowserServiceCompat.ServiceCallbacks serviceCallbacks, String string, ResultReceiver resultReceiver) {
        this.val$callbacks = serviceCallbacks;
        this.val$mediaId = string;
        this.val$receiver = resultReceiver;
    }

    @Override
    public void run() {
        Object object = this.val$callbacks.asBinder();
        if ((object = (MediaBrowserServiceCompat.ConnectionRecord)ServiceBinderImpl.this.this$0.mConnections.get(object)) == null) {
            object = new StringBuilder();
            object.append("getMediaItem for callback that isn't registered id=");
            object.append(this.val$mediaId);
            Log.w((String)MediaBrowserServiceCompat.TAG, (String)object.toString());
            return;
        }
        ServiceBinderImpl.this.this$0.performLoadItem(this.val$mediaId, (MediaBrowserServiceCompat.ConnectionRecord)object, this.val$receiver);
    }
}
