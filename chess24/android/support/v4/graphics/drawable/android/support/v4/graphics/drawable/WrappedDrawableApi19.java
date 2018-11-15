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
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.drawable.WrappedDrawableApi14;

@RequiresApi(value=19)
class WrappedDrawableApi19
extends WrappedDrawableApi14 {
    WrappedDrawableApi19(Drawable drawable) {
        super(drawable);
    }

    WrappedDrawableApi19(WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, Resources resources) {
        super(drawableWrapperState, resources);
    }

    public boolean isAutoMirrored() {
        return this.mDrawable.isAutoMirrored();
    }

    @NonNull
    @Override
    WrappedDrawableApi14.DrawableWrapperState mutateConstantState() {
        return new DrawableWrapperStateKitKat(this.mState, null);
    }

    public void setAutoMirrored(boolean bl) {
        this.mDrawable.setAutoMirrored(bl);
    }

    private static class DrawableWrapperStateKitKat
    extends WrappedDrawableApi14.DrawableWrapperState {
        DrawableWrapperStateKitKat(@Nullable WrappedDrawableApi14.DrawableWrapperState drawableWrapperState, @Nullable Resources resources) {
            super(drawableWrapperState, resources);
        }

        @NonNull
        @Override
        public Drawable newDrawable(@Nullable Resources resources) {
            return new WrappedDrawableApi19(this, resources);
        }
    }

}
