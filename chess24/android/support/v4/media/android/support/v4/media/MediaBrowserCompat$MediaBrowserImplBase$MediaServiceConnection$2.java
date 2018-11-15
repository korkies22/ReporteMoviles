/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.os.Messenger
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.os.Messenger;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

class MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection
implements Runnable {
    final /* synthetic */ ComponentName val$name;

    MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection(ComponentName componentName) {
        this.val$name = componentName;
    }

    @Override
    public void run() {
        if (MediaBrowserCompat.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MediaServiceConnection.onServiceDisconnected name=");
            stringBuilder.append((Object)this.val$name);
            stringBuilder.append(" this=");
            stringBuilder.append(this);
            stringBuilder.append(" mServiceConnection=");
            stringBuilder.append(MediaServiceConnection.this.this$0.mServiceConnection);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            MediaServiceConnection.this.this$0.dump();
        }
        if (!MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
            return;
        }
        MediaServiceConnection.this.this$0.mServiceBinderWrapper = null;
        MediaServiceConnection.this.this$0.mCallbacksMessenger = null;
        MediaServiceConnection.this.this$0.mHandler.setCallbacksMessenger(null);
        MediaServiceConnection.this.this$0.mState = 4;
        MediaServiceConnection.this.this$0.mCallback.onConnectionSuspended();
    }
}
