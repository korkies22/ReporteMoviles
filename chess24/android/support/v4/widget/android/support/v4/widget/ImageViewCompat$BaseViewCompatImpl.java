/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.widget.ImageView
 */
package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.widget.ImageViewCompat;
import android.support.v4.widget.TintableImageSourceView;
import android.widget.ImageView;

static class ImageViewCompat.BaseViewCompatImpl
implements ImageViewCompat.ImageViewCompatImpl {
    ImageViewCompat.BaseViewCompatImpl() {
    }

    @Override
    public ColorStateList getImageTintList(ImageView imageView) {
        if (imageView instanceof TintableImageSourceView) {
            return ((TintableImageSourceView)imageView).getSupportImageTintList();
        }
        return null;
    }

    @Override
    public PorterDuff.Mode getImageTintMode(ImageView imageView) {
        if (imageView instanceof TintableImageSourceView) {
            return ((TintableImageSourceView)imageView).getSupportImageTintMode();
        }
        return null;
    }

    @Override
    public void setImageTintList(ImageView imageView, ColorStateList colorStateList) {
        if (imageView instanceof TintableImageSourceView) {
            ((TintableImageSourceView)imageView).setSupportImageTintList(colorStateList);
        }
    }

    @Override
    public void setImageTintMode(ImageView imageView, PorterDuff.Mode mode) {
        if (imageView instanceof TintableImageSourceView) {
            ((TintableImageSourceView)imageView).setSupportImageTintMode(mode);
        }
    }
}
