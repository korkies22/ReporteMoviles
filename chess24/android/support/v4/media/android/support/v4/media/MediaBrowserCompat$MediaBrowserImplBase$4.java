/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.media;

import android.support.v4.media.MediaBrowserCompat;

class MediaBrowserCompat.MediaBrowserImplBase
implements Runnable {
    final /* synthetic */ MediaBrowserCompat.ItemCallback val$cb;
    final /* synthetic */ String val$mediaId;

    MediaBrowserCompat.MediaBrowserImplBase(MediaBrowserCompat.ItemCallback itemCallback, String string) {
        this.val$cb = itemCallback;
        this.val$mediaId = string;
    }

    @Override
    public void run() {
        this.val$cb.onError(this.val$mediaId);
    }
}
