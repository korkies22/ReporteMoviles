/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Parcelable
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.os.ResultReceiver;
import java.util.ArrayList;
import java.util.List;

private static class MediaBrowserCompat.SearchResultReceiver
extends ResultReceiver {
    private final MediaBrowserCompat.SearchCallback mCallback;
    private final Bundle mExtras;
    private final String mQuery;

    MediaBrowserCompat.SearchResultReceiver(String string, Bundle bundle, MediaBrowserCompat.SearchCallback searchCallback, Handler handler) {
        super(handler);
        this.mQuery = string;
        this.mExtras = bundle;
        this.mCallback = searchCallback;
    }

    @Override
    protected void onReceiveResult(int n, Bundle object) {
        if (object != null) {
            object.setClassLoader(MediaBrowserCompat.class.getClassLoader());
        }
        if (n == 0 && object != null && object.containsKey("search_results")) {
            Parcelable[] arrparcelable = object.getParcelableArray("search_results");
            object = null;
            if (arrparcelable != null) {
                ArrayList<MediaBrowserCompat.MediaItem> arrayList = new ArrayList<MediaBrowserCompat.MediaItem>();
                int n2 = arrparcelable.length;
                n = 0;
                do {
                    object = arrayList;
                    if (n >= n2) break;
                    arrayList.add((MediaBrowserCompat.MediaItem)arrparcelable[n]);
                    ++n;
                } while (true);
            }
            this.mCallback.onSearchResult(this.mQuery, this.mExtras, (List<MediaBrowserCompat.MediaItem>)object);
            return;
        }
        this.mCallback.onError(this.mQuery, this.mExtras);
    }
}
