/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.ActivityManager
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.graphics.Bitmap
 */
package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.common.util.zzs;

private static final class ImageManager.zzb
extends LruCache<zza.zza, Bitmap> {
    public ImageManager.zzb(Context context) {
        super(ImageManager.zzb.zzaz(context));
    }

    @TargetApi(value=11)
    private static int zzaz(Context context) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
        int n = (context.getApplicationInfo().flags & 1048576) != 0 ? 1 : 0;
        n = n != 0 && zzs.zzyx() ? ImageManager.zza.zza(activityManager) : activityManager.getMemoryClass();
        return (int)(0.33f * (float)(1048576 * n));
    }

    @Override
    protected /* synthetic */ void entryRemoved(boolean bl, Object object, Object object2, Object object3) {
        this.zza(bl, (zza.zza)object, (Bitmap)object2, (Bitmap)object3);
    }

    @Override
    protected /* synthetic */ int sizeOf(Object object, Object object2) {
        return this.zza((zza.zza)object, (Bitmap)object2);
    }

    protected int zza(zza.zza zza2, Bitmap bitmap) {
        return bitmap.getHeight() * bitmap.getRowBytes();
    }

    protected void zza(boolean bl, zza.zza zza2, Bitmap bitmap, Bitmap bitmap2) {
        super.entryRemoved(bl, zza2, bitmap, bitmap2);
    }
}
