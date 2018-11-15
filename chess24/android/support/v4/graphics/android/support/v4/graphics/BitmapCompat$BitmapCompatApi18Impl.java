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

@RequiresApi(value=18)
static class BitmapCompat.BitmapCompatApi18Impl
extends BitmapCompat.BitmapCompatBaseImpl {
    BitmapCompat.BitmapCompatApi18Impl() {
    }

    @Override
    public boolean hasMipMap(Bitmap bitmap) {
        return bitmap.hasMipMap();
    }

    @Override
    public void setHasMipMap(Bitmap bitmap, boolean bl) {
        bitmap.setHasMipMap(bl);
    }
}
