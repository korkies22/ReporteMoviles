/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.media.browse.MediaBrowser$SubscriptionCallback
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompatApi21;
import java.util.List;

static class MediaBrowserCompatApi21.SubscriptionCallbackProxy<T extends MediaBrowserCompatApi21.SubscriptionCallback>
extends MediaBrowser.SubscriptionCallback {
    protected final T mSubscriptionCallback;

    public MediaBrowserCompatApi21.SubscriptionCallbackProxy(T t) {
        this.mSubscriptionCallback = t;
    }

    public void onChildrenLoaded(@NonNull String string, List<MediaBrowser.MediaItem> list) {
        this.mSubscriptionCallback.onChildrenLoaded(string, list);
    }

    public void onError(@NonNull String string) {
        this.mSubscriptionCallback.onError(string);
    }
}
