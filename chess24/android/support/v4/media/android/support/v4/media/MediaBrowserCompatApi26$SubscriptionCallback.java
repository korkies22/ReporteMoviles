/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompatApi21;
import android.support.v4.media.MediaBrowserCompatApi26;
import java.util.List;

static interface MediaBrowserCompatApi26.SubscriptionCallback
extends MediaBrowserCompatApi21.SubscriptionCallback {
    public void onChildrenLoaded(@NonNull String var1, List<?> var2, @NonNull Bundle var3);

    public void onError(@NonNull String var1, @NonNull Bundle var2);
}
