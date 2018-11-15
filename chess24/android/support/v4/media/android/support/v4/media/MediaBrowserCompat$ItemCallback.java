/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package android.support.v4.media;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi23;

public static abstract class MediaBrowserCompat.ItemCallback {
    final Object mItemCallbackObj;

    public MediaBrowserCompat.ItemCallback() {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mItemCallbackObj = MediaBrowserCompatApi23.createItemCallback(new StubApi23());
            return;
        }
        this.mItemCallbackObj = null;
    }

    public void onError(@NonNull String string) {
    }

    public void onItemLoaded(MediaBrowserCompat.MediaItem mediaItem) {
    }

    private class StubApi23
    implements MediaBrowserCompatApi23.ItemCallback {
        StubApi23() {
        }

        @Override
        public void onError(@NonNull String string) {
            ItemCallback.this.onError(string);
        }

        @Override
        public void onItemLoaded(Parcel parcel) {
            if (parcel == null) {
                ItemCallback.this.onItemLoaded(null);
                return;
            }
            parcel.setDataPosition(0);
            MediaBrowserCompat.MediaItem mediaItem = (MediaBrowserCompat.MediaItem)MediaBrowserCompat.MediaItem.CREATOR.createFromParcel(parcel);
            parcel.recycle();
            ItemCallback.this.onItemLoaded(mediaItem);
        }
    }

}
