/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 */
package android.support.v4.media;

import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.MediaBrowserCompatApi26;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public static abstract class MediaBrowserCompat.SubscriptionCallback {
    private final Object mSubscriptionCallbackObj;
    WeakReference<MediaBrowserCompat.Subscription> mSubscriptionRef;
    private final IBinder mToken = new Binder();

    public MediaBrowserCompat.SubscriptionCallback() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mSubscriptionCallbackObj = MediaBrowserCompatApi26.createSubscriptionCallback(new StubApi26());
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(new StubApi21());
            return;
        }
        this.mSubscriptionCallbackObj = null;
    }

    static /* synthetic */ IBinder access$000(MediaBrowserCompat.SubscriptionCallback subscriptionCallback) {
        return subscriptionCallback.mToken;
    }

    static /* synthetic */ void access$100(MediaBrowserCompat.SubscriptionCallback subscriptionCallback, MediaBrowserCompat.Subscription subscription) {
        subscriptionCallback.setSubscription(subscription);
    }

    static /* synthetic */ Object access$200(MediaBrowserCompat.SubscriptionCallback subscriptionCallback) {
        return subscriptionCallback.mSubscriptionCallbackObj;
    }

    private void setSubscription(MediaBrowserCompat.Subscription subscription) {
        this.mSubscriptionRef = new WeakReference<MediaBrowserCompat.Subscription>(subscription);
    }

    public void onChildrenLoaded(@NonNull String string, @NonNull List<MediaBrowserCompat.MediaItem> list) {
    }

    public void onChildrenLoaded(@NonNull String string, @NonNull List<MediaBrowserCompat.MediaItem> list, @NonNull Bundle bundle) {
    }

    public void onError(@NonNull String string) {
    }

    public void onError(@NonNull String string, @NonNull Bundle bundle) {
    }

    private class StubApi21
    implements MediaBrowserCompatApi21.SubscriptionCallback {
        StubApi21() {
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

    private class StubApi26
    extends StubApi21
    implements MediaBrowserCompatApi26.SubscriptionCallback {
        StubApi26() {
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

}
