/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;

static class BitmapCompat.BitmapCompatBaseImpl {
    BitmapCompat.BitmapCompatBaseImpl() {
    }

    public int getAllocationByteCount(Bitmap bitmap) {
        return bitmap.getByteCount();
    }

    public boolean hasMipMap(Bitmap bitmap) {
        return false;
    }

    public void setHasMipMap(Bitmap bitmap, boolean bl) {
    }
}
