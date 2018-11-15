/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorSet
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 */
package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.util.ArrayMap;
import java.util.ArrayList;
import java.util.Collection;

private static class AnimatedVectorDrawableCompat.AnimatedVectorDrawableCompatState
extends Drawable.ConstantState {
    AnimatorSet mAnimatorSet;
    private ArrayList<Animator> mAnimators;
    int mChangingConfigurations;
    ArrayMap<Animator, String> mTargetNameMap;
    VectorDrawableCompat mVectorDrawable;

    public AnimatedVectorDrawableCompat.AnimatedVectorDrawableCompatState(Context object, AnimatedVectorDrawableCompat.AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState, Drawable.Callback object2, Resources resources) {
        if (animatedVectorDrawableCompatState != null) {
            this.mChangingConfigurations = animatedVectorDrawableCompatState.mChangingConfigurations;
            object = animatedVectorDrawableCompatState.mVectorDrawable;
            int n = 0;
            if (object != null) {
                object = animatedVectorDrawableCompatState.mVectorDrawable.getConstantState();
                this.mVectorDrawable = resources != null ? (VectorDrawableCompat)object.newDrawable(resources) : (VectorDrawableCompat)object.newDrawable();
                this.mVectorDrawable = (VectorDrawableCompat)this.mVectorDrawable.mutate();
                this.mVectorDrawable.setCallback(object2);
                this.mVectorDrawable.setBounds(animatedVectorDrawableCompatState.mVectorDrawable.getBounds());
                this.mVectorDrawable.setAllowCaching(false);
            }
            if (animatedVectorDrawableCompatState.mAnimators != null) {
                int n2 = animatedVectorDrawableCompatState.mAnimators.size();
                this.mAnimators = new ArrayList(n2);
                this.mTargetNameMap = new ArrayMap(n2);
                while (n < n2) {
                    object2 = animatedVectorDrawableCompatState.mAnimators.get(n);
                    object = object2.clone();
                    object2 = (String)animatedVectorDrawableCompatState.mTargetNameMap.get(object2);
                    object.setTarget(this.mVectorDrawable.getTargetByName((String)object2));
                    this.mAnimators.add((Animator)object);
                    this.mTargetNameMap.put((Animator)object, (String)object2);
                    ++n;
                }
                this.setupAnimatorSet();
            }
        }
    }

    static /* synthetic */ ArrayList access$000(AnimatedVectorDrawableCompat.AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState) {
        return animatedVectorDrawableCompatState.mAnimators;
    }

    static /* synthetic */ ArrayList access$002(AnimatedVectorDrawableCompat.AnimatedVectorDrawableCompatState animatedVectorDrawableCompatState, ArrayList arrayList) {
        animatedVectorDrawableCompatState.mAnimators = arrayList;
        return arrayList;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public Drawable newDrawable() {
        throw new IllegalStateException("No constant state support for SDK < 24.");
    }

    public Drawable newDrawable(Resources resources) {
        throw new IllegalStateException("No constant state support for SDK < 24.");
    }

    public void setupAnimatorSet() {
        if (this.mAnimatorSet == null) {
            this.mAnimatorSet = new AnimatorSet();
        }
        this.mAnimatorSet.playTogether(this.mAnimators);
    }
}
