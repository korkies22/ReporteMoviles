/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.ViewGroupUtils;
import android.view.ViewGroup;

class ChangeBounds
extends TransitionListenerAdapter {
    boolean mCanceled = false;
    final /* synthetic */ ViewGroup val$parent;

    ChangeBounds(ViewGroup viewGroup) {
        this.val$parent = viewGroup;
    }

    @Override
    public void onTransitionCancel(@NonNull Transition transition) {
        ViewGroupUtils.suppressLayout(this.val$parent, false);
        this.mCanceled = true;
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        if (!this.mCanceled) {
            ViewGroupUtils.suppressLayout(this.val$parent, false);
        }
        transition.removeListener(this);
    }

    @Override
    public void onTransitionPause(@NonNull Transition transition) {
        ViewGroupUtils.suppressLayout(this.val$parent, false);
    }

    @Override
    public void onTransitionResume(@NonNull Transition transition) {
        ViewGroupUtils.suppressLayout(this.val$parent, true);
    }
}
