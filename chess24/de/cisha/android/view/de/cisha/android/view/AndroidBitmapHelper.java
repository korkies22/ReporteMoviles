/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package de.cisha.android.view;

import android.content.Context;
import android.graphics.Bitmap;

public class AndroidBitmapHelper {
    static {
        System.loadLibrary("AndroidBitmapHelper");
    }

    public static Bitmap blurImage(Context context, Bitmap bitmap, int n) {
        context = bitmap;
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            context = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        bitmap = context.copy(context.getConfig(), true);
        AndroidBitmapHelper.blurNative((Bitmap)context, bitmap, n);
        return bitmap;
    }

    private static native void blurNative(Bitmap var0, Bitmap var1, int var2);
}
