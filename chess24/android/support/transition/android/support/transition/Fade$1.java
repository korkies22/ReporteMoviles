/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.ViewUtils;
import android.view.View;

class Fade
extends TransitionListenerAdapter {
    final /* synthetic */ View val$view;

    Fade(View view) {
        this.val$view = view;
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        ViewUtils.setTransitionAlpha(this.val$view, 1.0f);
        ViewUtils.clearNonTransitionAlpha(this.val$view);
        transition.removeListener(this);
    }
}
