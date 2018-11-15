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
import android.support.annotation.RequiresApi;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserCompatApi23;

@RequiresApi(value=23)
static class MediaBrowserCompat.MediaBrowserImplApi23
extends MediaBrowserCompat.MediaBrowserImplApi21 {
    MediaBrowserCompat.MediaBrowserImplApi23(Context context, ComponentName componentName, MediaBrowserCompat.ConnectionCallback connectionCallback, Bundle bundle) {
        super(context, componentName, connectionCallback, bundle);
    }

    @Override
    public void getItem(@NonNull String string, @NonNull MediaBrowserCompat.ItemCallback itemCallback) {
        if (this.mServiceBinderWrapper == null) {
            MediaBrowserCompatApi23.getItem(this.mBrowserObj, string, itemCallback.mItemCallbackObj);
            return;
        }
        super.getItem(string, itemCallback);
    }
}
