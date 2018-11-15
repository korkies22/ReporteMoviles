/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Messenger
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Messenger;
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
        boolean bl;
        block8 : {
            if (MediaBrowserImplBase.this.mState == 0) {
                return;
            }
            MediaBrowserImplBase.this.mState = 2;
            if (MediaBrowserCompat.DEBUG && MediaBrowserImplBase.this.mServiceConnection != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("mServiceConnection should be null. Instead it is ");
                stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                throw new RuntimeException(stringBuilder.toString());
            }
            if (MediaBrowserImplBase.this.mServiceBinderWrapper != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("mServiceBinderWrapper should be null. Instead it is ");
                stringBuilder.append(MediaBrowserImplBase.this.mServiceBinderWrapper);
                throw new RuntimeException(stringBuilder.toString());
            }
            if (MediaBrowserImplBase.this.mCallbacksMessenger != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("mCallbacksMessenger should be null. Instead it is ");
                stringBuilder.append((Object)MediaBrowserImplBase.this.mCallbacksMessenger);
                throw new RuntimeException(stringBuilder.toString());
            }
            Intent intent = new Intent("android.media.browse.MediaBrowserService");
            intent.setComponent(MediaBrowserImplBase.this.mServiceComponent);
            MediaBrowserImplBase.this.mServiceConnection = new MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection(MediaBrowserImplBase.this);
            try {
                bl = MediaBrowserImplBase.this.mContext.bindService(intent, (ServiceConnection)MediaBrowserImplBase.this.mServiceConnection, 1);
                break block8;
            }
            catch (Exception exception) {}
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed binding to service ");
            stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
            Log.e((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            bl = false;
        }
        if (!bl) {
            MediaBrowserImplBase.this.forceCloseConnection();
            MediaBrowserImplBase.this.mCallback.onConnectionFailed();
        }
        if (MediaBrowserCompat.DEBUG) {
            Log.d((String)MediaBrowserCompat.TAG, (String)"connect...");
            MediaBrowserImplBase.this.dump();
        }
    }
}
