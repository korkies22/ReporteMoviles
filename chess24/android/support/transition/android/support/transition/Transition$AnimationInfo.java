/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.transition.Transition;
import android.support.transition.TransitionValues;
import android.support.transition.WindowIdImpl;
import android.view.View;

private static class Transition.AnimationInfo {
    String mName;
    Transition mTransition;
    TransitionValues mValues;
    View mView;
    WindowIdImpl mWindowId;

    Transition.AnimationInfo(View view, String string, Transition transition, WindowIdImpl windowIdImpl, TransitionValues transitionValues) {
        this.mView = view;
        this.mName = string;
        this.mValues = transitionValues;
        this.mWindowId = windowIdImpl;
        this.mTransition = transition;
    }
}
