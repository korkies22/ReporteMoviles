/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.graphics.drawable.Drawable
 */
package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.Animatable2Compat;
import java.util.ArrayList;
import java.util.Collection;

class AnimatedVectorDrawableCompat
extends AnimatorListenerAdapter {
    AnimatedVectorDrawableCompat() {
    }

    public void onAnimationEnd(Animator object) {
        object = new ArrayList(AnimatedVectorDrawableCompat.this.mAnimationCallbacks);
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            ((Animatable2Compat.AnimationCallback)object.get(i)).onAnimationEnd(AnimatedVectorDrawableCompat.this);
        }
    }

    public void onAnimationStart(Animator object) {
        object = new ArrayList(AnimatedVectorDrawableCompat.this.mAnimationCallbacks);
        int n = object.size();
        for (int i = 0; i < n; ++i) {
            ((Animatable2Compat.AnimationCallback)object.get(i)).onAnimationStart(AnimatedVectorDrawableCompat.this);
        }
    }
}
