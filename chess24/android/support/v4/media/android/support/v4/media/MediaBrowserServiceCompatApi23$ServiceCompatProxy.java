/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.os.Parcel;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import android.support.v4.media.MediaBrowserServiceCompatApi23;

public static interface MediaBrowserServiceCompatApi23.ServiceCompatProxy
extends MediaBrowserServiceCompatApi21.ServiceCompatProxy {
    public void onLoadItem(String var1, MediaBrowserServiceCompatApi21.ResultWrapper<Parcel> var2);
}
