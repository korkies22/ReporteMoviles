/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Rect
 */
package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;

private static class RoundedBitmapDrawableFactory.DefaultRoundedBitmapDrawable
extends RoundedBitmapDrawable {
    RoundedBitmapDrawableFactory.DefaultRoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
    }

    @Override
    void gravityCompatApply(int n, int n2, int n3, Rect rect, Rect rect2) {
        GravityCompat.apply(n, n2, n3, rect, rect2, 0);
    }

    @Override
    public boolean hasMipMap() {
        if (this.mBitmap != null && BitmapCompat.hasMipMap(this.mBitmap)) {
            return true;
        }
        return false;
    }

    @Override
    public void setMipMap(boolean bl) {
        if (this.mBitmap != null) {
            BitmapCompat.setHasMipMap(this.mBitmap, bl);
            this.invalidateSelf();
        }
    }
}
