/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.Region
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.support.v4.graphics.drawable.WrappedDrawable;

class WrappedDrawableApi14
extends Drawable
implements Drawable.Callback,
WrappedDrawable,
TintAwareDrawable {
    static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
    private boolean mColorFilterSet;
    private int mCurrentColor;
    private PorterDuff.Mode mCurrentMode;
    Drawable mDrawable;
    private boolean mMutated;
    DrawableWrapperState mState;

    WrappedDrawableApi14(@Nullable Drawable drawable) {
        this.mState = this.mutateConstantState();
        this.setWrappedDrawable(drawable);
    }

    WrappedDrawableApi14(@NonNull DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
        this.mState = drawableWrapperState;
        this.updateLocalState(resources);
    }

    private void updateLocalState(@Nullable Resources resources) {
        if (this.mState != null && this.mState.mDrawableState != null) {
            this.setWrappedDrawable(this.mState.mDrawableState.newDrawable(resources));
        }
    }

    private boolean updateTint(int[] arrn) {
        if (!this.isCompatTintEnabled()) {
            return false;
        }
        ColorStateList colorStateList = this.mState.mTint;
        PorterDuff.Mode mode = this.mState.mTintMode;
        if (colorStateList != null && mode != null) {
            int n = colorStateList.getColorForState(arrn, colorStateList.getDefaultColor());
            if (!this.mColorFilterSet || n != this.mCurrentColor || mode != this.mCurrentMode) {
                this.setColorFilter(n, mode);
                this.mCurrentColor = n;
                this.mCurrentMode = mode;
                this.mColorFilterSet = true;
                return true;
            }
        } else {
            this.mColorFilterSet = false;
            this.clearColorFilter();
        }
        return false;
    }

    public void draw(@NonNull Canvas canvas) {
        this.mDrawable.draw(canvas);
    }

    public int getChangingConfigurations() {
        int n = super.getChangingConfigurations();
        int n2 = this.mState != null ? this.mState.getChangingConfigurations() : 0;
        return n | n2 | this.mDrawable.getChangingConfigurations();
    }

    @Nullable
    public Drawable.ConstantState getConstantState() {
        if (this.mState != null && this.mState.canConstantState()) {
            this.mState.mChangingConfigurations = this.getChangingConfigurations();
            return this.mState;
        }
        return null;
    }

    @NonNull
    public Drawable getCurrent() {
        return this.mDrawable.getCurrent();
    }

    public int getIntrinsicHeight() {
        return this.mDrawable.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return this.mDrawable.getIntrinsicWidth();
    }

    public int getMinimumHeight() {
        return this.mDrawable.getMinimumHeight();
    }

    public int getMinimumWidth() {
        return this.mDrawable.getMinimumWidth();
    }

    public int getOpacity() {
        return this.mDrawable.getOpacity();
    }

    public boolean getPadding(@NonNull Rect rect) {
        return this.mDrawable.getPadding(rect);
    }

    @NonNull
    public int[] getState() {
        return this.mDrawable.getState();
    }

    public Region getTransparentRegion() {
        return this.mDrawable.getTransparentRegion();
    }

    @Override
    public final Drawable getWrappedDrawable() {
        return this.mDrawable;
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        this.invalidateSelf();
    }

    protected boolean isCompatTintEnabled() {
        return true;
    }

    public boolean isStateful() {
        ColorStateList colorStateList = this.isCompatTintEnabled() && this.mState != null ? this.mState.mTint : null;
        if (colorStateList != null && colorStateList.isStateful() || this.mDrawable.isStateful()) {
            return true;
        }
        return false;
    }

    public void jumpToCurrentState() {
        this.mDrawable.jumpToCurrentState();
    }

    @NonNull
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mState = this.mutateConstantState();
            if (this.mDrawable != null) {
                this.mDrawable.mutate();
            }
            if (this.mState != null) {
                DrawableWrapperState drawableWrapperState = this.mState;
                Drawable.ConstantState constantState = this.mDrawable != null ? this.mDrawable.getConstantState() : null;
                drawableWrapperState.mDrawableState = constantState;
            }
            this.mMutated = true;
        }
        return this;
    }

    @NonNull
    DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateBase(this.mState, null);
    }

    protected void onBoundsChange(Rect rect) {
        if (this.mDrawable != null) {
            this.mDrawable.setBounds(rect);
        }
    }

    protected boolean onLevelChange(int n) {
        return this.mDrawable.setLevel(n);
    }

    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long l) {
        this.scheduleSelf(runnable, l);
    }

    public void setAlpha(int n) {
        this.mDrawable.setAlpha(n);
    }

    public void setChangingConfigurations(int n) {
        this.mDrawable.setChangingConfigurations(n);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
    }

    public void setDither(boolean bl) {
        this.mDrawable.setDither(bl);
    }

    public void setFilterBitmap(boolean bl) {
        this.mDrawable.setFilterBitmap(bl);
    }

    public boolean setState(@NonNull int[] arrn) {
        boolean bl = this.mDrawable.setState(arrn);
        if (!this.updateTint(arrn) && !bl) {
            return false;
        }
        return true;
    }

    @Override
    public void setTint(int n) {
        this.setTintList(ColorStateList.valueOf((int)n));
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        this.mState.mTint = colorStateList;
        this.updateTint(this.getState());
    }

    @Override
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        this.mState.mTintMode = mode;
        this.updateTint(this.getState());
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        if (!super.setVisible(bl, bl2) && !this.mDrawable.setVisible(bl, bl2)) {
            return false;
        }
        return true;
    }

    @Override
    public final void setWrappedDrawable(Drawable drawable) {
        if (this.mDrawable != null) {
            this.mDrawable.setCallback(null);
        }
        this.mDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback)this);
            this.setVisible(drawable.isVisible(), true);
            this.setState(drawable.getState());
            this.setLevel(drawable.getLevel());
            this.setBounds(drawable.getBounds());
            if (this.mState != null) {
                this.mState.mDrawableState = drawable.getConstantState();
            }
        }
        this.invalidateSelf();
    }

    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        this.unscheduleSelf(runnable);
    }

    protected static abstract class DrawableWrapperState
    extends Drawable.ConstantState {
        int mChangingConfigurations;
        Drawable.ConstantState mDrawableState;
        ColorStateList mTint = null;
        PorterDuff.Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;

        DrawableWrapperState(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            if (drawableWrapperState != null) {
                this.mChangingConfigurations = drawableWrapperState.mChangingConfigurations;
                this.mDrawableState = drawableWrapperState.mDrawableState;
                this.mTint = drawableWrapperState.mTint;
                this.mTintMode = drawableWrapperState.mTintMode;
            }
        }

        boolean canConstantState() {
            if (this.mDrawableState != null) {
                return true;
            }
            return false;
        }

        public int getChangingConfigurations() {
            int n = this.mChangingConfigurations;
            int n2 = this.mDrawableState != null ? this.mDrawableState.getChangingConfigurations() : 0;
            return n | n2;
        }

        @NonNull
        public Drawable newDrawable() {
            return this.newDrawable(null);
        }

        @NonNull
        public abstract Drawable newDrawable(@Nullable Resources var1);
    }

    private static class DrawableWrapperStateBase
    extends DrawableWrapperState {
        DrawableWrapperStateBase(@Nullable DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources);
        }

        @NonNull
        @Override
        public Drawable newDrawable(@Nullable Resources resources) {
            return new WrappedDrawableApi14(this, resources);
        }
    }

}
