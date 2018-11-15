/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintInfo;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;

class AppCompatBackgroundHelper {
    private int mBackgroundResId = -1;
    private TintInfo mBackgroundTint;
    private final AppCompatDrawableManager mDrawableManager;
    private TintInfo mInternalBackgroundTint;
    private TintInfo mTmpInfo;
    private final View mView;

    AppCompatBackgroundHelper(View view) {
        this.mView = view;
        this.mDrawableManager = AppCompatDrawableManager.get();
    }

    private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable drawable) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo tintInfo = this.mTmpInfo;
        tintInfo.clear();
        ColorStateList colorStateList = ViewCompat.getBackgroundTintList(this.mView);
        if (colorStateList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = colorStateList;
        }
        if ((colorStateList = ViewCompat.getBackgroundTintMode(this.mView)) != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = colorStateList;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.tintDrawable(drawable, tintInfo, this.mView.getDrawableState());
        return true;
    }

    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        int n = Build.VERSION.SDK_INT;
        boolean bl = false;
        if (n > 21) {
            if (this.mInternalBackgroundTint != null) {
                bl = true;
            }
            return bl;
        }
        if (n == 21) {
            return true;
        }
        return false;
    }

    void applySupportBackgroundTint() {
        Drawable drawable = this.mView.getBackground();
        if (drawable != null) {
            if (this.shouldApplyFrameworkTintUsingColorFilter() && this.applyFrameworkTintUsingColorFilter(drawable)) {
                return;
            }
            if (this.mBackgroundTint != null) {
                AppCompatDrawableManager.tintDrawable(drawable, this.mBackgroundTint, this.mView.getDrawableState());
                return;
            }
            if (this.mInternalBackgroundTint != null) {
                AppCompatDrawableManager.tintDrawable(drawable, this.mInternalBackgroundTint, this.mView.getDrawableState());
            }
        }
    }

    ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintList;
        }
        return null;
    }

    PorterDuff.Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintMode;
        }
        return null;
    }

    void loadFromAttributes(AttributeSet object, int n) {
        object = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), (AttributeSet)object, R.styleable.ViewBackgroundHelper, n, 0);
        try {
            if (object.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                this.mBackgroundResId = object.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList colorStateList = this.mDrawableManager.getTintList(this.mView.getContext(), this.mBackgroundResId);
                if (colorStateList != null) {
                    this.setInternalBackgroundTint(colorStateList);
                }
            }
            if (object.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.mView, object.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (object.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.mView, DrawableUtils.parseTintMode(object.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
            return;
        }
        finally {
            object.recycle();
        }
    }

    void onSetBackgroundDrawable(Drawable drawable) {
        this.mBackgroundResId = -1;
        this.setInternalBackgroundTint(null);
        this.applySupportBackgroundTint();
    }

    void onSetBackgroundResource(int n) {
        this.mBackgroundResId = n;
        ColorStateList colorStateList = this.mDrawableManager != null ? this.mDrawableManager.getTintList(this.mView.getContext(), n) : null;
        this.setInternalBackgroundTint(colorStateList);
        this.applySupportBackgroundTint();
    }

    void setInternalBackgroundTint(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            this.mInternalBackgroundTint.mTintList = colorStateList;
            this.mInternalBackgroundTint.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        this.applySupportBackgroundTint();
    }

    void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = colorStateList;
        this.mBackgroundTint.mHasTintList = true;
        this.applySupportBackgroundTint();
    }

    void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintMode = mode;
        this.mBackgroundTint.mHasTintMode = true;
        this.applySupportBackgroundTint();
    }
}
