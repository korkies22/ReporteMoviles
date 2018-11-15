/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.view.animation.Animation
 */
package android.support.v4.app;

import android.animation.Animator;
import android.support.v4.app.FragmentManagerImpl;
import android.view.animation.Animation;

private static class FragmentManagerImpl.AnimationOrAnimator {
    public final Animation animation;
    public final Animator animator;

    private FragmentManagerImpl.AnimationOrAnimator(Animator animator) {
        this.animation = null;
        this.animator = animator;
        if (animator == null) {
            throw new IllegalStateException("Animator cannot be null");
        }
    }

    private FragmentManagerImpl.AnimationOrAnimator(Animation animation) {
        this.animation = animation;
        this.animator = null;
        if (animation == null) {
            throw new IllegalStateException("Animation cannot be null");
        }
    }
}
