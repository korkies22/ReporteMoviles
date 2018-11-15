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
import android.support.transition.TransitionManager;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;
import java.util.ArrayList;

class TransitionManager.MultiListener
extends TransitionListenerAdapter {
    final /* synthetic */ ArrayMap val$runningTransitions;

    TransitionManager.MultiListener(ArrayMap arrayMap) {
        this.val$runningTransitions = arrayMap;
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        ((ArrayList)this.val$runningTransitions.get((Object)MultiListener.this.mSceneRoot)).remove(transition);
    }
}
