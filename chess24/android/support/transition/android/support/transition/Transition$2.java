/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.util.ArrayMap;
import java.util.ArrayList;

class Transition
extends AnimatorListenerAdapter {
    final /* synthetic */ ArrayMap val$runningAnimators;

    Transition(ArrayMap arrayMap) {
        this.val$runningAnimators = arrayMap;
    }

    public void onAnimationEnd(Animator animator) {
        this.val$runningAnimators.remove((Object)animator);
        Transition.this.mCurrentAnimators.remove((Object)animator);
    }

    public void onAnimationStart(Animator animator) {
        Transition.this.mCurrentAnimators.add(animator);
    }
}
