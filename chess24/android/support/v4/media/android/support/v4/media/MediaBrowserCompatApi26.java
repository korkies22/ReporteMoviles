/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.media.browse.MediaBrowser
 *  android.media.browse.MediaBrowser$MediaItem
 *  android.media.browse.MediaBrowser$SubscriptionCallback
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserCompatApi21;
import java.util.List;

@RequiresApi(value=26)
class MediaBrowserCompatApi26 {
    MediaBrowserCompatApi26() {
    }

    static Object createSubscriptionCallback(SubscriptionCallback subscriptionCallback) {
        return new SubscriptionCallbackProxy<SubscriptionCallback>(subscriptionCallback);
    }

    public static void subscribe(Object object, String string, Bundle bundle, Object object2) {
        ((MediaBrowser)object).subscribe(string, bundle, (MediaBrowser.SubscriptionCallback)object2);
    }

    public static void unsubscribe(Object object, String string, Object object2) {
        ((MediaBrowser)object).unsubscribe(string, (MediaBrowser.SubscriptionCallback)object2);
    }

    static interface SubscriptionCallback
    extends MediaBrowserCompatApi21.SubscriptionCallback {
        public void onChildrenLoaded(@NonNull String var1, List<?> var2, @NonNull Bundle var3);

        public void onError(@NonNull String var1, @NonNull Bundle var2);
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback>
    extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {
        SubscriptionCallbackProxy(T t) {
            super(t);
        }

        public void onChildrenLoaded(@NonNull String string, List<MediaBrowser.MediaItem> list, @NonNull Bundle bundle) {
            ((SubscriptionCallback)this.mSubscriptionCallback).onChildrenLoaded(string, list, bundle);
        }

        public void onError(@NonNull String string, @NonNull Bundle bundle) {
            ((SubscriptionCallback)this.mSubscriptionCallback).onError(string, bundle);
        }
    }

}
