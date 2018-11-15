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
import android.support.annotation.RequiresApi;

@RequiresApi(value=23)
class MediaBrowserCompatApi23 {
    MediaBrowserCompatApi23() {
    }

    public static Object createItemCallback(ItemCallback itemCallback) {
        return new ItemCallbackProxy<ItemCallback>(itemCallback);
    }

    public static void getItem(Object object, String string, Object object2) {
        ((MediaBrowser)object).getItem(string, (MediaBrowser.ItemCallback)object2);
    }

    static interface ItemCallback {
        public void onError(@NonNull String var1);

        public void onItemLoaded(Parcel var1);
    }

    static class ItemCallbackProxy<T extends ItemCallback>
    extends MediaBrowser.ItemCallback {
        protected final T mItemCallback;

        public ItemCallbackProxy(T t) {
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

}
