/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

class MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection
implements Runnable {
    final /* synthetic */ IBinder val$binder;
    final /* synthetic */ ComponentName val$name;

    MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection(ComponentName componentName, IBinder iBinder) {
        this.val$name = componentName;
        this.val$binder = iBinder;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        if (MediaBrowserCompat.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MediaServiceConnection.onServiceConnected name=");
            stringBuilder.append((Object)this.val$name);
            stringBuilder.append(" binder=");
            stringBuilder.append((Object)this.val$binder);
            Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
            MediaServiceConnection.this.this$0.dump();
        }
        if (!MediaServiceConnection.this.isCurrent("onServiceConnected")) {
            return;
        }
        MediaServiceConnection.this.this$0.mServiceBinderWrapper = new MediaBrowserCompat.ServiceBinderWrapper(this.val$binder, MediaServiceConnection.this.this$0.mRootHints);
        MediaServiceConnection.this.this$0.mCallbacksMessenger = new Messenger((Handler)MediaServiceConnection.this.this$0.mHandler);
        MediaServiceConnection.this.this$0.mHandler.setCallbacksMessenger(MediaServiceConnection.this.this$0.mCallbacksMessenger);
        MediaServiceConnection.this.this$0.mState = 2;
        try {
            if (MediaBrowserCompat.DEBUG) {
                Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                MediaServiceConnection.this.this$0.dump();
            }
            MediaServiceConnection.this.this$0.mServiceBinderWrapper.connect(MediaServiceConnection.this.this$0.mContext, MediaServiceConnection.this.this$0.mCallbacksMessenger);
            return;
        }
        catch (RemoteException remoteException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RemoteException during connect for ");
        stringBuilder.append((Object)MediaServiceConnection.this.this$0.mServiceComponent);
        Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        if (MediaBrowserCompat.DEBUG) {
            Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
            MediaServiceConnection.this.this$0.dump();
        }
    }
}
