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
    final /* synthetic */ Object val$enterTransition;
    final /* synthetic */ ArrayList val$enteringViews;
    final /* synthetic */ Object val$exitTransition;
    final /* synthetic */ ArrayList val$exitingViews;
    final /* synthetic */ Object val$sharedElementTransition;
    final /* synthetic */ ArrayList val$sharedElementsIn;

    FragmentTransitionCompat21(Object object, ArrayList arrayList, Object object2, ArrayList arrayList2, Object object3, ArrayList arrayList3) {
        this.val$enterTransition = object;
        this.val$enteringViews = arrayList;
        this.val$exitTransition = object2;
        this.val$exitingViews = arrayList2;
        this.val$sharedElementTransition = object3;
        this.val$sharedElementsIn = arrayList3;
    }

    public void onTransitionCancel(Transition transition) {
    }

    public void onTransitionEnd(Transition transition) {
    }

    public void onTransitionPause(Transition transition) {
    }

    public void onTransitionResume(Transition transition) {
    }

    public void onTransitionStart(Transition transition) {
        if (this.val$enterTransition != null) {
            FragmentTransitionCompat21.this.replaceTargets(this.val$enterTransition, this.val$enteringViews, null);
        }
        if (this.val$exitTransition != null) {
            FragmentTransitionCompat21.this.replaceTargets(this.val$exitTransition, this.val$exitingViews, null);
        }
        if (this.val$sharedElementTransition != null) {
            FragmentTransitionCompat21.this.replaceTargets(this.val$sharedElementTransition, this.val$sharedElementsIn, null);
        }
    }
}
