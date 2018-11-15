/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$ItemCallback
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompatApi23;

static class MediaBrowserCompatApi23.ItemCallbackProxy<T extends MediaBrowserCompatApi23.ItemCallback>
extends MediaBrowser.ItemCallback {
    protected final T mItemCallback;

    public MediaBrowserCompatApi23.ItemCallbackProxy(T t) {
        this.mItemCallback = t;
    }

    public void onError(@NonNull String string) {
        this.mItemCallback.onError(string);
    }

    public void onItemLoaded(MediaBrowser.MediaItem mediaItem) {
        if (mediaItem == null) {
            this.mItemCallback.onItemLoaded(null);
            return;
        }
        Parcel parcel = Parcel.obtain();
        mediaItem.writeToParcel(parcel, 0);
        this.mItemCallback.onItemLoaded(parcel);
    }
}
