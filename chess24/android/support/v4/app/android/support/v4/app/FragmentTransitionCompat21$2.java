/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.transition.Transition
 *  android.transition.Transition$TransitionListener
 *  android.view.View
 */
package android.support.v4.app;

import android.transition.Transition;
import android.view.View;
import java.util.ArrayList;

class FragmentTransitionCompat21
implements Transition.TransitionListener {
    final /* synthetic */ ArrayList val$exitingViews;
    final /* synthetic */ View val$fragmentView;

    FragmentTransitionCompat21(View view, ArrayList arrayList) {
        this.val$fragmentView = view;
        this.val$exitingViews = arrayList;
    }

    public void onTransitionCancel(Transition transition) {
    }

    public void onTransitionEnd(Transition transition) {
        transition.removeListener((Transition.TransitionListener)this);
        this.val$fragmentView.setVisibility(8);
        int n = this.val$exitingViews.size();
        for (int i = 0; i < n; ++i) {
            ((View)this.val$exitingViews.get(i)).setVisibility(0);
        }
    }

    public void onTransitionPause(Transition transition) {
    }

    public void onTransitionResume(Transition transition) {
    }

    public void onTransitionStart(Transition transition) {
    }
}
