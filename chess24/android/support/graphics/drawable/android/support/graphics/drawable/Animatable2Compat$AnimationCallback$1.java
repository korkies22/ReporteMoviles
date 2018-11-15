/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.Animatable2
 *  android.graphics.drawable.Animatable2$AnimationCallback
 *  android.graphics.drawable.Drawable
 */
package android.support.graphics.drawable;

import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.Animatable2Compat;

class Animatable2Compat.AnimationCallback
extends Animatable2.AnimationCallback {
    Animatable2Compat.AnimationCallback() {
    }

    public void onAnimationEnd(Drawable drawable) {
        AnimationCallback.this.onAnimationEnd(drawable);
    }

    public void onAnimationStart(Drawable drawable) {
        AnimationCallback.this.onAnimationStart(drawable);
    }
}
