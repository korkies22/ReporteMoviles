/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;

private class MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection
implements ServiceConnection {
    MediaBrowserCompat.MediaBrowserImplBase.MediaServiceConnection() {
    }

    private void postOrRun(Runnable runnable) {
        if (Thread.currentThread() == MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
            runnable.run();
            return;
        }
        MediaBrowserImplBase.this.mHandler.post(runnable);
    }

    boolean isCurrent(String string) {
        if (MediaBrowserImplBase.this.mServiceConnection == this && MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
            return true;
        }
        if (MediaBrowserImplBase.this.mState != 0 && MediaBrowserImplBase.this.mState != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" for ");
            stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
            stringBuilder.append(" with mServiceConnection=");
            stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
            stringBuilder.append(" this=");
            stringBuilder.append(this);
            Log.i((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
        }
        return false;
    }

    public void onServiceConnected(final ComponentName componentName, final IBinder iBinder) {
        this.postOrRun(new Runnable(){

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
                    stringBuilder.append((Object)componentName);
                    stringBuilder.append(" binder=");
                    stringBuilder.append((Object)iBinder);
                    Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                    MediaBrowserImplBase.this.dump();
                }
                if (!MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                    return;
                }
                MediaBrowserImplBase.this.mServiceBinderWrapper = new MediaBrowserCompat.ServiceBinderWrapper(iBinder, MediaBrowserImplBase.this.mRootHints);
                MediaBrowserImplBase.this.mCallbacksMessenger = new Messenger((Handler)MediaBrowserImplBase.this.mHandler);
                MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserImplBase.this.mCallbacksMessenger);
                MediaBrowserImplBase.this.mState = 2;
                try {
                    if (MediaBrowserCompat.DEBUG) {
                        Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                        MediaBrowserImplBase.this.dump();
                    }
                    MediaBrowserImplBase.this.mServiceBinderWrapper.connect(MediaBrowserImplBase.this.mContext, MediaBrowserImplBase.this.mCallbacksMessenger);
                    return;
                }
                catch (RemoteException remoteException) {}
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RemoteException during connect for ");
                stringBuilder.append((Object)MediaBrowserImplBase.this.mServiceComponent);
                Log.w((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                if (MediaBrowserCompat.DEBUG) {
                    Log.d((String)MediaBrowserCompat.TAG, (String)"ServiceCallbacks.onConnect...");
                    MediaBrowserImplBase.this.dump();
                }
            }
        });
    }

    public void onServiceDisconnected(final ComponentName componentName) {
        this.postOrRun(new Runnable(){

            @Override
            public void run() {
                if (MediaBrowserCompat.DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("MediaServiceConnection.onServiceDisconnected name=");
                    stringBuilder.append((Object)componentName);
                    stringBuilder.append(" this=");
                    stringBuilder.append(this);
                    stringBuilder.append(" mServiceConnection=");
                    stringBuilder.append(MediaBrowserImplBase.this.mServiceConnection);
                    Log.d((String)MediaBrowserCompat.TAG, (String)stringBuilder.toString());
                    MediaBrowserImplBase.this.dump();
                }
                if (!MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                    return;
                }
                MediaBrowserImplBase.this.mServiceBinderWrapper = null;
                MediaBrowserImplBase.this.mCallbacksMessenger = null;
                MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null);
                MediaBrowserImplBase.this.mState = 4;
                MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
            }
        });
    }

}
