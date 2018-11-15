/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatUtils;
import java.util.ArrayList;
import java.util.List;

private static class MediaBrowserCompat.Subscription {
    private final List<MediaBrowserCompat.SubscriptionCallback> mCallbacks = new ArrayList<MediaBrowserCompat.SubscriptionCallback>();
    private final List<Bundle> mOptionsList = new ArrayList<Bundle>();

    public MediaBrowserCompat.SubscriptionCallback getCallback(Context context, Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(context.getClassLoader());
        }
        for (int i = 0; i < this.mOptionsList.size(); ++i) {
            if (!MediaBrowserCompatUtils.areSameOptions(this.mOptionsList.get(i), bundle)) continue;
            return this.mCallbacks.get(i);
        }
        return null;
    }

    public List<MediaBrowserCompat.SubscriptionCallback> getCallbacks() {
        return this.mCallbacks;
    }

    public List<Bundle> getOptionsList() {
        return this.mOptionsList;
    }

    public boolean isEmpty() {
        return this.mCallbacks.isEmpty();
    }

    public void putCallback(Context context, Bundle bundle, MediaBrowserCompat.SubscriptionCallback subscriptionCallback) {
        if (bundle != null) {
            bundle.setClassLoader(context.getClassLoader());
        }
        for (int i = 0; i < this.mOptionsList.size(); ++i) {
            if (!MediaBrowserCompatUtils.areSameOptions(this.mOptionsList.get(i), bundle)) continue;
            this.mCallbacks.set(i, subscriptionCallback);
            return;
        }
        this.mCallbacks.add(subscriptionCallback);
        this.mOptionsList.add(bundle);
    }
}
