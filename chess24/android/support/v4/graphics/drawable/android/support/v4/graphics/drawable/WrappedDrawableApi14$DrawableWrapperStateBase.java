/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 */
package android.support.v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.WrappedDrawableApi14;

private static class WrappedDrawableApi14.DrawableWrapperStateBase
extends WrappedDrawableApi14.DrawableWrapperState {
    WrappedDrawableApi14.DrawableWrapperStateBase(@Nullable WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
        super(drawableWrapperState, resources);
    }

    @NonNull
    @Override
    public Drawable newDrawable(@Nullable Resources resources) {
        return new WrappedDrawableApi14(this, resources);
    }
}
