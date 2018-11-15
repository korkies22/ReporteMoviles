/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi26;
import java.util.List;

private class MediaBrowserCompat.SubscriptionCallback.StubApi26
extends MediaBrowserCompat.SubscriptionCallback.StubApi21
implements MediaBrowserCompatApi26.SubscriptionCallback {
    MediaBrowserCompat.SubscriptionCallback.StubApi26() {
        super(SubscriptionCallback.this);
    }

    @Override
    public void onChildrenLoaded(@NonNull String string, List<?> list, @NonNull Bundle bundle) {
        SubscriptionCallback.this.onChildrenLoaded(string, MediaBrowserCompat.MediaItem.fromMediaItemList(list), bundle);
    }

    @Override
    public void onError(@NonNull String string, @NonNull Bundle bundle) {
        SubscriptionCallback.this.onError(string, bundle);
    }
}
