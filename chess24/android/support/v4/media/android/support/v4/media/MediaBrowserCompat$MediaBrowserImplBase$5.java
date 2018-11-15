/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;

class MediaBrowserCompat.MediaBrowserImplBase
implements Runnable {
    final /* synthetic */ MediaBrowserCompat.SearchCallback val$callback;
    final /* synthetic */ Bundle val$extras;
    final /* synthetic */ String val$query;

    MediaBrowserCompat.MediaBrowserImplBase(MediaBrowserCompat.SearchCallback searchCallback, String string, Bundle bundle) {
        this.val$callback = searchCallback;
        this.val$query = string;
        this.val$extras = bundle;
    }

    @Override
    public void run() {
        this.val$callback.onError(this.val$query, this.val$extras);
    }
}
