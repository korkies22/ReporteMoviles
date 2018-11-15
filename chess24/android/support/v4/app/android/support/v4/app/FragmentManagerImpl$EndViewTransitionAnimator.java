/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.AnimationSet
 *  android.view.animation.Transformation
 */
package android.support.v4.app;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.OneShotPreDrawListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;

private static class FragmentManagerImpl.EndViewTransitionAnimator
extends AnimationSet
implements Runnable {
    private final View mChild;
    private boolean mEnded;
    private final ViewGroup mParent;
    private boolean mTransitionEnded;

    FragmentManagerImpl.EndViewTransitionAnimator(@NonNull Animation animation, @NonNull ViewGroup viewGroup, @NonNull View view) {
        super(false);
        this.mParent = viewGroup;
        this.mChild = view;
        this.addAnimation(animation);
    }

    public boolean getTransformation(long l, Transformation transformation) {
        if (this.mEnded) {
            return this.mTransitionEnded ^ true;
        }
        if (!super.getTransformation(l, transformation)) {
            this.mEnded = true;
            OneShotPreDrawListener.add((View)this.mParent, this);
        }
        return true;
    }

    public boolean getTransformation(long l, Transformation transformation, float f) {
        if (this.mEnded) {
            return this.mTransitionEnded ^ true;
        }
        if (!super.getTransformation(l, transformation, f)) {
            this.mEnded = true;
            OneShotPreDrawListener.add((View)this.mParent, this);
        }
        return true;
    }

    @Override
    public void run() {
        this.mParent.endViewTransition(this.mChild);
        this.mTransitionEnded = true;
    }
}
