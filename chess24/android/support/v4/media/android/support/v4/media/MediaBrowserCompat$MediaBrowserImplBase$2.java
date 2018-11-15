/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

class MediaBrowserCompat.MediaBrowserImplBase
implements Runnable {
    MediaBrowserCompat.MediaBrowserImplBase() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        block5 : {
            if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                try {
                    MediaBrowserImplBase.this.mServiceBinderWrapper.disconnect(MediaBrowserImplBase.this.mCallbacksMessenger);
                    break block5;
                }
                catch (RemoteException remoteException) {}
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RemoteException during connect for ");
                stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            }
        }
        int n = MediaBrowserImplBase.this.mState;
        MediaBrowserImplBase.this.forceCloseConnection();
        if (n != 0) {
            MediaBrowserImplBase.this.mState = n;
        }
        if (MediaBrowserCompat.DEBUG) {
            Log.d((String)MediaBrowserCompat.TAG, (String)"disconnect...");
            MediaBrowserImplBase.this.dump();
        }
    }
}
