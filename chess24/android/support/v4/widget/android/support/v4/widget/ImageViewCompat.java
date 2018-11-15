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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TintableImageSourceView;
import android.widget.ImageView;

public class ImageViewCompat {
    static final ImageViewCompatImpl IMPL = Build.VERSION.SDK_INT >= 21 ? new LollipopViewCompatImpl() : new BaseViewCompatImpl();

    private ImageViewCompat() {
    }

    @Nullable
    public static ColorStateList getImageTintList(@NonNull ImageView imageView) {
        return IMPL.getImageTintList(imageView);
    }

    @Nullable
    public static PorterDuff.Mode getImageTintMode(@NonNull ImageView imageView) {
        return IMPL.getImageTintMode(imageView);
    }

    public static void setImageTintList(@NonNull ImageView imageView, @Nullable ColorStateList colorStateList) {
        IMPL.setImageTintList(imageView, colorStateList);
    }

    public static void setImageTintMode(@NonNull ImageView imageView, @Nullable PorterDuff.Mode mode) {
        IMPL.setImageTintMode(imageView, mode);
    }

    static class BaseViewCompatImpl
    implements ImageViewCompatImpl {
        BaseViewCompatImpl() {
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

    static interface ImageViewCompatImpl {
        public ColorStateList getImageTintList(ImageView var1);

        public PorterDuff.Mode getImageTintMode(ImageView var1);

        public void setImageTintList(ImageView var1, ColorStateList var2);

        public void setImageTintMode(ImageView var1, PorterDuff.Mode var2);
    }

    @RequiresApi(value=21)
    static class LollipopViewCompatImpl
    extends BaseViewCompatImpl {
        LollipopViewCompatImpl() {
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

}
