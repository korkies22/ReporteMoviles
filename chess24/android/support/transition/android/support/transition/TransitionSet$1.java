/*
 * Decompiled with CFR 0_134.
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;

class TransitionSet
extends TransitionListenerAdapter {
    final /* synthetic */ Transition val$nextTransition;

    TransitionSet(Transition transition) {
        this.val$nextTransition = transition;
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        this.val$nextTransition.runAnimators();
        transition.removeListener(this);
    }
}
