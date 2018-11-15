/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.ActivityManager
 */
package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import com.google.android.gms.common.images.ImageManager;

@TargetApi(value=11)
private static final class ImageManager.zza {
    static int zza(ActivityManager activityManager) {
        return activityManager.getLargeMemoryClass();
    }
}
