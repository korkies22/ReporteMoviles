/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ImageView
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.widget.TintableImageSourceView;
import android.support.v7.widget.AppCompatBackgroundHelper;
import android.support.v7.widget.AppCompatImageHelper;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class AppCompatImageView
extends ImageView
implements TintableBackgroundView,
TintableImageSourceView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this);
    private final AppCompatImageHelper mImageHelper;

    public AppCompatImageView(Context context) {
        this(context, null);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppCompatImageView(Context context, AttributeSet attributeSet, int n) {
        super(TintContextWrapper.wrap(context), attributeSet, n);
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, n);
        this.mImageHelper = new AppCompatImageHelper(this);
        this.mImageHelper.loadFromAttributes(attributeSet, n);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public ColorStateList getSupportImageTintList() {
        if (this.mImageHelper != null) {
            return this.mImageHelper.getSupportImageTintList();
        }
        return null;
    }

    @Nullable
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public PorterDuff.Mode getSupportImageTintMode() {
        if (this.mImageHelper != null) {
            return this.mImageHelper.getSupportImageTintMode();
        }
        return null;
    }

    public boolean hasOverlappingRendering() {
        if (this.mImageHelper.hasOverlappingRendering() && super.hasOverlappingRendering()) {
            return true;
        }
        return false;
    }

    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(drawable);
        }
    }

    public void setBackgroundResource(@DrawableRes int n) {
        super.setBackgroundResource(n);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(n);
        }
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    public void setImageResource(@DrawableRes int n) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setImageResource(n);
        }
    }

    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        if (this.mImageHelper != null) {
            this.mImageHelper.applySupportImageTint();
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(colorStateList);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(mode);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportImageTintList(@Nullable ColorStateList colorStateList) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setSupportImageTintList(colorStateList);
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void setSupportImageTintMode(@Nullable PorterDuff.Mode mode) {
        if (this.mImageHelper != null) {
            this.mImageHelper.setSupportImageTintMode(mode);
        }
    }
}
