/*
 * Decompiled with CFR 0_134.
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.TransitionSet;

static class TransitionSet.TransitionSetListener
extends TransitionListenerAdapter {
    TransitionSet mTransitionSet;

    TransitionSet.TransitionSetListener(TransitionSet transitionSet) {
        this.mTransitionSet = transitionSet;
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        TransitionSet.access$106(this.mTransitionSet);
        if (this.mTransitionSet.mCurrentListeners == 0) {
            this.mTransitionSet.mStarted = false;
            this.mTransitionSet.end();
        }
        transition.removeListener(this);
    }

    @Override
    public void onTransitionStart(@NonNull Transition transition) {
        if (!this.mTransitionSet.mStarted) {
            this.mTransitionSet.start();
            this.mTransitionSet.mStarted = true;
        }
    }
}
