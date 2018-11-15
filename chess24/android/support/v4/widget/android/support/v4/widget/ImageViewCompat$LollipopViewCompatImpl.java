/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.widget.ImageView
 */
package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ImageViewCompat;
import android.widget.ImageView;

@RequiresApi(value=21)
static class ImageViewCompat.LollipopViewCompatImpl
extends ImageViewCompat.BaseViewCompatImpl {
    ImageViewCompat.LollipopViewCompatImpl() {
    }

    @Override
    public ColorStateList getImageTintList(ImageView imageView) {
        return imageView.getImageTintList();
    }

    @Override
    public PorterDuff.Mode getImageTintMode(ImageView imageView) {
        return imageView.getImageTintMode();
    }

    @Override
    public void setImageTintList(ImageView imageView, ColorStateList colorStateList) {
        imageView.setImageTintList(colorStateList);
        if (Build.VERSION.SDK_INT == 21) {
            colorStateList = imageView.getDrawable();
            boolean bl = imageView.getImageTintList() != null && imageView.getImageTintMode() != null;
            if (colorStateList != null && bl) {
                if (colorStateList.isStateful()) {
                    colorStateList.setState(imageView.getDrawableState());
                }
                imageView.setImageDrawable((Drawable)colorStateList);
            }
        }
    }

    @Override
    public void setImageTintMode(ImageView imageView, PorterDuff.Mode mode) {
        imageView.setImageTintMode(mode);
        if (Build.VERSION.SDK_INT == 21) {
            mode = imageView.getDrawable();
            boolean bl = imageView.getImageTintList() != null && imageView.getImageTintMode() != null;
            if (mode != null && bl) {
                if (mode.isStateful()) {
                    mode.setState(imageView.getDrawableState());
                }
                imageView.setImageDrawable((Drawable)mode);
            }
        }
    }
}
