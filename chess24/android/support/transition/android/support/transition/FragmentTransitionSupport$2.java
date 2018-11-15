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
    final /* synthetic */ ArrayList val$exitingViews;
    final /* synthetic */ View val$fragmentView;

    FragmentTransitionSupport(View view, ArrayList arrayList) {
        this.val$fragmentView = view;
        this.val$exitingViews = arrayList;
    }

    @Override
    public void onTransitionCancel(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionEnd(@NonNull Transition transition) {
        transition.removeListener(this);
        this.val$fragmentView.setVisibility(8);
        int n = this.val$exitingViews.size();
        for (int i = 0; i < n; ++i) {
            ((View)this.val$exitingViews.get(i)).setVisibility(0);
        }
    }

    @Override
    public void onTransitionPause(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionResume(@NonNull Transition transition) {
    }

    @Override
    public void onTransitionStart(@NonNull Transition transition) {
    }
}
