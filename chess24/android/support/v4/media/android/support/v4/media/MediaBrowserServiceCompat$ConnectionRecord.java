/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import java.util.HashMap;
import java.util.List;

private class MediaBrowserServiceCompat.ConnectionRecord
implements IBinder.DeathRecipient {
    MediaBrowserServiceCompat.ServiceCallbacks callbacks;
    String pkg;
    MediaBrowserServiceCompat.BrowserRoot root;
    Bundle rootHints;
    HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap();

    MediaBrowserServiceCompat.ConnectionRecord() {
    }

    public void binderDied() {
        MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                MediaBrowserServiceCompat.this.mConnections.remove((Object)ConnectionRecord.this.callbacks.asBinder());
            }
        });
    }

}
