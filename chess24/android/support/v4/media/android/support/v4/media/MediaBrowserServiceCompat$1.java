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
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import java.util.List;

class MediaBrowserServiceCompat
extends MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>> {
    final /* synthetic */ MediaBrowserServiceCompat.ConnectionRecord val$connection;
    final /* synthetic */ Bundle val$options;
    final /* synthetic */ String val$parentId;

    MediaBrowserServiceCompat(Object object, MediaBrowserServiceCompat.ConnectionRecord connectionRecord, String string, Bundle bundle) {
        this.val$connection = connectionRecord;
        this.val$parentId = string;
        this.val$options = bundle;
        super(object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    void onResultSent(List<MediaBrowserCompat.MediaItem> list) {
        if (MediaBrowserServiceCompat.this.mConnections.get((Object)this.val$connection.callbacks.asBinder()) != this.val$connection) {
            if (android.support.v4.media.MediaBrowserServiceCompat.DEBUG) {
                list = new StringBuilder();
                list.append("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
                list.append(this.val$connection.pkg);
                list.append(" id=");
                list.append(this.val$parentId);
                Log.d((String)android.support.v4.media.MediaBrowserServiceCompat.TAG, (String)list.toString());
            }
            return;
        }
        List<MediaBrowserCompat.MediaItem> list2 = list;
        if ((this.getFlags() & 1) != 0) {
            list2 = MediaBrowserServiceCompat.this.applyOptions(list, this.val$options);
        }
        try {
            this.val$connection.callbacks.onLoadChildren(this.val$parentId, list2, this.val$options);
            return;
        }
        catch (RemoteException remoteException) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calling onLoadChildren() failed for id=");
        stringBuilder.append(this.val$parentId);
        stringBuilder.append(" package=");
        stringBuilder.append(this.val$connection.pkg);
        Log.w((String)android.support.v4.media.MediaBrowserServiceCompat.TAG, (String)stringBuilder.toString());
    }
}
