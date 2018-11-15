/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 */
package android.support.graphics.drawable;

import android.graphics.drawable.Drawable;

class AnimatedVectorDrawableCompat
implements Drawable.Callback {
    AnimatedVectorDrawableCompat() {
    }

    public void invalidateDrawable(Drawable drawable) {
        AnimatedVectorDrawableCompat.this.invalidateSelf();
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long l) {
        AnimatedVectorDrawableCompat.this.scheduleSelf(runnable, l);
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        AnimatedVectorDrawableCompat.this.unscheduleSelf(runnable);
    }
}
