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

private static class MediaBrowserCompat.ItemReceiver
extends ResultReceiver {
    private final MediaBrowserCompat.ItemCallback mCallback;
    private final String mMediaId;

    MediaBrowserCompat.ItemReceiver(String string, MediaBrowserCompat.ItemCallback itemCallback, Handler handler) {
        super(handler);
        this.mMediaId = string;
        this.mCallback = itemCallback;
    }

    @Override
    protected void onReceiveResult(int n, Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
        }
        if (n == 0 && bundle != null && bundle.containsKey("media_item")) {
            if ((bundle = bundle.getParcelable("media_item")) != null && !(bundle instanceof MediaBrowserCompat.MediaItem)) {
                this.mCallback.onError(this.mMediaId);
                return;
            }
            this.mCallback.onItemLoaded((MediaBrowserCompat.MediaItem)bundle);
            return;
        }
        this.mCallback.onError(this.mMediaId);
    }
}
