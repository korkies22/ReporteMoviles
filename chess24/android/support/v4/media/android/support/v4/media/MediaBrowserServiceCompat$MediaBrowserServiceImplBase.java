/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Messenger
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.media;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.util.Log;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class MediaBrowserServiceCompat.MediaBrowserServiceImplBase
implements MediaBrowserServiceCompat.MediaBrowserServiceImpl {
    private Messenger mMessenger;

    MediaBrowserServiceCompat.MediaBrowserServiceImplBase() {
    }

    @Override
    public Bundle getBrowserRootHints() {
        if (MediaBrowserServiceCompat.this.mCurConnection == null) {
            throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
        }
        if (MediaBrowserServiceCompat.this.mCurConnection.rootHints == null) {
            return null;
        }
        return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
    }

    @Override
    public void notifyChildrenChanged(final @NonNull String string, final Bundle bundle) {
        MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                for (Object object : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                    object = (MediaBrowserServiceCompat.ConnectionRecord)MediaBrowserServiceCompat.this.mConnections.get(object);
                    List<Pair<IBinder, Bundle>> list = object.subscriptions.get(string);
                    if (list == null) continue;
                    for (Pair pair : list) {
                        if (!MediaBrowserCompatUtils.hasDuplicatedItems(bundle, (Bundle)pair.second)) continue;
                        MediaBrowserServiceCompat.this.performLoadChildren(string, (MediaBrowserServiceCompat.ConnectionRecord)object, (Bundle)pair.second);
                    }
                }
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mMessenger.getBinder();
        }
        return null;
    }

    @Override
    public void onCreate() {
        this.mMessenger = new Messenger((Handler)MediaBrowserServiceCompat.this.mHandler);
    }

    @Override
    public void setSessionToken(final MediaSessionCompat.Token token) {
        MediaBrowserServiceCompat.this.mHandler.post(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Iterator<MediaBrowserServiceCompat.ConnectionRecord> iterator = MediaBrowserServiceCompat.this.mConnections.values().iterator();
                while (iterator.hasNext()) {
                    MediaBrowserServiceCompat.ConnectionRecord connectionRecord = iterator.next();
                    try {
                        connectionRecord.callbacks.onConnect(connectionRecord.root.getRootId(), token, connectionRecord.root.getExtras());
                        continue;
                    }
                    catch (RemoteException remoteException) {}
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Connection for ");
                    stringBuilder.append(connectionRecord.pkg);
                    stringBuilder.append(" is no longer valid.");
                    Log.w((String)MediaBrowserServiceCompat.TAG, (String)stringBuilder.toString());
                    iterator.remove();
                }
                return;
            }
        });
    }

}
