/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.MediaBrowserCompatApi26;

@RequiresApi(value=26)
static class MediaBrowserCompat.MediaBrowserImplApi26
extends MediaBrowserCompat.MediaBrowserImplApi23 {
    MediaBrowserCompat.MediaBrowserImplApi26(Context context, ComponentName componentName, MediaBrowserCompat.ConnectionCallback connectionCallback, Bundle bundle) {
        super(context, componentName, connectionCallback, bundle);
    }

    @Override
    public void subscribe(@NonNull String string, @Nullable Bundle bundle, @NonNull MediaBrowserCompat.SubscriptionCallback subscriptionCallback) {
        if (this.mServiceBinderWrapper != null && this.mServiceVersion >= 2) {
            super.subscribe(string, bundle, subscriptionCallback);
            return;
        }
        if (bundle == null) {
            MediaBrowserCompatApi21.subscribe(this.mBrowserObj, string, subscriptionCallback.mSubscriptionCallbackObj);
            return;
        }
        MediaBrowserCompatApi26.subscribe(this.mBrowserObj, string, bundle, subscriptionCallback.mSubscriptionCallbackObj);
    }

    @Override
    public void unsubscribe(@NonNull String string, MediaBrowserCompat.SubscriptionCallback subscriptionCallback) {
        if (this.mServiceBinderWrapper != null && this.mServiceVersion >= 2) {
            super.unsubscribe(string, subscriptionCallback);
            return;
        }
        if (subscriptionCallback == null) {
            MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, string);
            return;
        }
        MediaBrowserCompatApi26.unsubscribe(this.mBrowserObj, string, subscriptionCallback.mSubscriptionCallbackObj);
    }
}
