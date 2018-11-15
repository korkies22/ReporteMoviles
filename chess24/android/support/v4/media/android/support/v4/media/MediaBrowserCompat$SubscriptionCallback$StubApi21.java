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
import android.support.v4.media.MediaBrowserCompatApi21;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

private class MediaBrowserCompat.SubscriptionCallback.StubApi21
implements MediaBrowserCompatApi21.SubscriptionCallback {
    MediaBrowserCompat.SubscriptionCallback.StubApi21() {
    }

    List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int n = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int n2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (n == -1 && n2 == -1) {
            return list;
        }
        int n3 = n2 * n;
        int n4 = n3 + n2;
        if (n >= 0 && n2 >= 1 && n3 < list.size()) {
            n = n4;
            if (n4 > list.size()) {
                n = list.size();
            }
            return list.subList(n3, n);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void onChildrenLoaded(@NonNull String string, List<?> list) {
        Object object = SubscriptionCallback.this.mSubscriptionRef == null ? null : (MediaBrowserCompat.Subscription)SubscriptionCallback.this.mSubscriptionRef.get();
        if (object == null) {
            SubscriptionCallback.this.onChildrenLoaded(string, MediaBrowserCompat.MediaItem.fromMediaItemList(list));
            return;
        }
        list = MediaBrowserCompat.MediaItem.fromMediaItemList(list);
        List<MediaBrowserCompat.SubscriptionCallback> list2 = object.getCallbacks();
        object = object.getOptionsList();
        for (int i = 0; i < list2.size(); ++i) {
            Bundle bundle = (Bundle)object.get(i);
            if (bundle == null) {
                SubscriptionCallback.this.onChildrenLoaded(string, list);
                continue;
            }
            SubscriptionCallback.this.onChildrenLoaded(string, this.applyOptions(list, bundle), bundle);
        }
    }

    @Override
    public void onError(@NonNull String string) {
        SubscriptionCallback.this.onError(string);
    }
}
