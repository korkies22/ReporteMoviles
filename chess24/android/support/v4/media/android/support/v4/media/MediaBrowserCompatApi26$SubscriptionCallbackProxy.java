/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.MediaBrowserCompatApi26;
import java.util.List;

static class MediaBrowserCompatApi26.SubscriptionCallbackProxy<T extends MediaBrowserCompatApi26.SubscriptionCallback>
extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {
    MediaBrowserCompatApi26.SubscriptionCallbackProxy(T t) {
        super(t);
    }

    public void onChildrenLoaded(@NonNull String string, List<MediaBrowser.MediaItem> list, @NonNull Bundle bundle) {
        ((MediaBrowserCompatApi26.SubscriptionCallback)this.mSubscriptionCallback).onChildrenLoaded(string, list, bundle);
    }

    public void onError(@NonNull String string, @NonNull Bundle bundle) {
        ((MediaBrowserCompatApi26.SubscriptionCallback)this.mSubscriptionCallback).onError(string, bundle);
    }
}
