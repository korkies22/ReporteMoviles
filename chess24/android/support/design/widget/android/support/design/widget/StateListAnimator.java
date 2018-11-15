/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.util.StateSet
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.StateSet;
import java.util.ArrayList;

final class StateListAnimator {
    private final Animator.AnimatorListener mAnimationListener = new AnimatorListenerAdapter(){

        public void onAnimationEnd(Animator animator) {
            if (StateListAnimator.this.mRunningAnimator == animator) {
                StateListAnimator.this.mRunningAnimator = null;
            }
        }
    };
    private Tuple mLastMatch = null;
    ValueAnimator mRunningAnimator = null;
    private final ArrayList<Tuple> mTuples = new ArrayList();

    StateListAnimator() {
    }

    private void cancel() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.cancel();
            this.mRunningAnimator = null;
        }
    }

    private void start(Tuple tuple) {
        this.mRunningAnimator = tuple.mAnimator;
        this.mRunningAnimator.start();
    }

    public void addState(int[] object, ValueAnimator valueAnimator) {
        object = new Tuple((int[])object, valueAnimator);
        valueAnimator.addListener(this.mAnimationListener);
        this.mTuples.add((Tuple)object);
    }

    public void jumpToCurrentState() {
        if (this.mRunningAnimator != null) {
            this.mRunningAnimator.end();
            this.mRunningAnimator = null;
        }
    }

    void setState(int[] object) {
        block4 : {
            int n = this.mTuples.size();
            for (int i = 0; i < n; ++i) {
                Tuple tuple = this.mTuples.get(i);
                if (!StateSet.stateSetMatches((int[])tuple.mSpecs, (int[])object)) continue;
                object = tuple;
                break block4;
            }
            object = null;
        }
        if (object == this.mLastMatch) {
            return;
        }
        if (this.mLastMatch != null) {
            this.cancel();
        }
        this.mLastMatch = object;
        if (object != null) {
            this.start((Tuple)object);
        }
    }

    static class Tuple {
        final ValueAnimator mAnimator;
        final int[] mSpecs;

        Tuple(int[] arrn, ValueAnimator valueAnimator) {
            this.mSpecs = arrn;
            this.mAnimator = valueAnimator;
        }
    }

}
