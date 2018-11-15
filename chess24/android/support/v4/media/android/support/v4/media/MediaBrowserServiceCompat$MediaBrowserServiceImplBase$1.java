/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import java.util.Collection;
import java.util.Iterator;

class MediaBrowserServiceCompat.MediaBrowserServiceImplBase
implements Runnable {
    final /* synthetic */ MediaSessionCompat.Token val$token;

    MediaBrowserServiceCompat.MediaBrowserServiceImplBase(MediaSessionCompat.Token token) {
        this.val$token = token;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void run() {
        Iterator<MediaBrowserServiceCompat.ConnectionRecord> iterator = MediaBrowserServiceImplBase.this.this$0.mConnections.values().iterator();
        while (iterator.hasNext()) {
            MediaBrowserServiceCompat.ConnectionRecord connectionRecord = iterator.next();
            try {
                connectionRecord.callbacks.onConnect(connectionRecord.root.getRootId(), this.val$token, connectionRecord.root.getExtras());
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
}
