/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompatApi23;

static interface MediaBrowserCompatApi23.ItemCallback {
    public void onError(@NonNull String var1);

    public void onItemLoaded(Parcel var1);
}
