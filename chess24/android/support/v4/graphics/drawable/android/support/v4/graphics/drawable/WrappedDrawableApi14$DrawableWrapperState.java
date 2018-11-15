/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 */
package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.WrappedDrawableApi14;

protected static abstract class WrappedDrawableApi14.DrawableWrapperState
extends Drawable.ConstantState {
    int mChangingConfigurations;
    Drawable.ConstantState mDrawableState;
    ColorStateList mTint = null;
    PorterDuff.Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;

    WrappedDrawableApi14.DrawableWrapperState(@Nullable WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
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
