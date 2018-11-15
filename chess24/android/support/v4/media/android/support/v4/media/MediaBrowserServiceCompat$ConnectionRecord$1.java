/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package android.support.v4.media;

import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.ArrayMap;

class MediaBrowserServiceCompat.ConnectionRecord
implements Runnable {
    MediaBrowserServiceCompat.ConnectionRecord() {
    }

    @Override
    public void run() {
        ConnectionRecord.this.this$0.mConnections.remove((Object)ConnectionRecord.this.callbacks.asBinder());
    }
}
