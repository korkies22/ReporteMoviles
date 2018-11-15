/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v4.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransitionImpl;
import android.view.View;
import java.util.ArrayList;
import java.util.Collection;

static final class FragmentTransition
implements Runnable {
    final /* synthetic */ Object val$enterTransition;
    final /* synthetic */ ArrayList val$enteringViews;
    final /* synthetic */ Object val$exitTransition;
    final /* synthetic */ ArrayList val$exitingViews;
    final /* synthetic */ FragmentTransitionImpl val$impl;
    final /* synthetic */ Fragment val$inFragment;
    final /* synthetic */ View val$nonExistentView;
    final /* synthetic */ ArrayList val$sharedElementsIn;

    FragmentTransition(Object object, FragmentTransitionImpl fragmentTransitionImpl, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Object object2) {
        this.val$enterTransition = object;
        this.val$impl = fragmentTransitionImpl;
        this.val$nonExistentView = view;
        this.val$inFragment = fragment;
        this.val$sharedElementsIn = arrayList;
        this.val$enteringViews = arrayList2;
        this.val$exitingViews = arrayList3;
        this.val$exitTransition = object2;
    }

    @Override
    public void run() {
        ArrayList arrayList;
        if (this.val$enterTransition != null) {
            this.val$impl.removeTarget(this.val$enterTransition, this.val$nonExistentView);
            arrayList = android.support.v4.app.FragmentTransition.configureEnteringExitingViews(this.val$impl, this.val$enterTransition, this.val$inFragment, this.val$sharedElementsIn, this.val$nonExistentView);
            this.val$enteringViews.addAll(arrayList);
        }
        if (this.val$exitingViews != null) {
            if (this.val$exitTransition != null) {
                arrayList = new ArrayList();
                arrayList.add(this.val$nonExistentView);
                this.val$impl.replaceTargets(this.val$exitTransition, this.val$exitingViews, arrayList);
            }
            this.val$exitingViews.clear();
            this.val$exitingViews.add(this.val$nonExistentView);
        }
    }
}
