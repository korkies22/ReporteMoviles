/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.BitmapCompat;

@RequiresApi(value=19)
static class BitmapCompat.BitmapCompatApi19Impl
extends BitmapCompat.BitmapCompatApi18Impl {
    BitmapCompat.BitmapCompatApi19Impl() {
    }

    @Override
    public int getAllocationByteCount(Bitmap bitmap) {
        return bitmap.getAllocationByteCount();
    }
}
