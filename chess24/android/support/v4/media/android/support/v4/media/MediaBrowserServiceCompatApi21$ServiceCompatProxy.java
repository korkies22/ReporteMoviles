/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 */
package android.support.v4.media;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.media.MediaBrowserServiceCompatApi21;
import java.util.List;

public static interface MediaBrowserServiceCompatApi21.ServiceCompatProxy {
    public MediaBrowserServiceCompatApi21.BrowserRoot onGetRoot(String var1, int var2, Bundle var3);

    public void onLoadChildren(String var1, MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>> var2);
}
