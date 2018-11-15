/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.media;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompatApi21;
import java.util.List;

static interface MediaBrowserCompatApi21.SubscriptionCallback {
    public void onChildrenLoaded(@NonNull String var1, List<?> var2);

    public void onError(@NonNull String var1);
}
