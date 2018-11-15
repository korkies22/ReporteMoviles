/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentCallbacks2
 *  android.content.res.Configuration
 */
package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import com.google.android.gms.common.images.ImageManager;

@TargetApi(value=14)
private static final class ImageManager.zze
implements ComponentCallbacks2 {
    private final ImageManager.zzb zzaCL;

    public ImageManager.zze(ImageManager.zzb zzb2) {
        this.zzaCL = zzb2;
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onLowMemory() {
        this.zzaCL.evictAll();
    }

    public void onTrimMemory(int n) {
        if (n >= 60) {
            this.zzaCL.evictAll();
            return;
        }
        if (n >= 20) {
            this.zzaCL.trimToSize(this.zzaCL.size() / 2);
        }
    }
}
