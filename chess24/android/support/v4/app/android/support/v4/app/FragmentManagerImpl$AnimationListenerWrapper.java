/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package android.support.v4.app;

import android.support.annotation.CallSuper;
import android.support.v4.app.FragmentManagerImpl;
import android.view.animation.Animation;

private static class FragmentManagerImpl.AnimationListenerWrapper
implements Animation.AnimationListener {
    private final Animation.AnimationListener mWrapped;

    private FragmentManagerImpl.AnimationListenerWrapper(Animation.AnimationListener animationListener) {
        this.mWrapped = animationListener;
    }

    @CallSuper
    public void onAnimationEnd(Animation animation) {
        if (this.mWrapped != null) {
            this.mWrapped.onAnimationEnd(animation);
        }
    }

    @CallSuper
    public void onAnimationRepeat(Animation animation) {
        if (this.mWrapped != null) {
            this.mWrapped.onAnimationRepeat(animation);
        }
    }

    @CallSuper
    public void onAnimationStart(Animation animation) {
        if (this.mWrapped != null) {
            this.mWrapped.onAnimationStart(animation);
        }
    }
}
