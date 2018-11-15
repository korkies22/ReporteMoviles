/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.annotation.NonNull;
import android.support.transition.Transition;
import android.view.View;
import java.util.ArrayList;

class FragmentTransitionSupport
implements Transition.TransitionListener {
    final /* synthetic */ Object val$enterTransition;
    final /* synthetic */ ArrayList val$enteringViews;
    final /* synthetic */ Object val$exitTransition;
    final /* synthetic */ ArrayList val$exitingViews;
    final /* synthetic */ Object val$sharedElementTransition;
    final /* synthetic */ ArrayList val$sharedElementsIn;

    FragmentTransitionSupport(Object object, ArrayList arrayList, Object object2, ArrayList arrayList2, Object object3, ArrayList arrayList3) {
        this.val$enterTransition = object;
        this.val$enteringViews = arrayList;
        this.val$exitTransition = object2;
        this.val$exitingViews = arrayList2;
        this.val$sharedElementTransition = object3;
        this.val$sharedElementsIn = arrayList3;
    }

    @Override
    public void onTransitionCancel(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionPause(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionResume(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionStart(@NonNull Transition transition) {
        if (this.val$enterTransition != null) {
            FragmentTransitionSupport.this.replaceTargets(this.val$enterTransition, this.val$enteringViews, null);
        }
        if (this.val$exitTransition != null) {
            FragmentTransitionSupport.this.replaceTargets(this.val$exitTransition, this.val$exitingViews, null);
        }
        if (this.val$sharedElementTransition != null) {
            FragmentTransitionSupport.this.replaceTargets(this.val$sharedElementTransition, this.val$sharedElementsIn, null);
        }
    }
}
