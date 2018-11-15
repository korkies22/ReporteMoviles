/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 */
package android.support.v4.app;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransition;
import android.support.v4.app.FragmentTransitionImpl;
import android.support.v4.util.ArrayMap;
import android.view.View;
import java.util.ArrayList;
import java.util.Collection;

static final class FragmentTransition
implements Runnable {
    final /* synthetic */ Object val$enterTransition;
    final /* synthetic */ Object val$finalSharedElementTransition;
    final /* synthetic */ FragmentTransition.FragmentContainerTransition val$fragments;
    final /* synthetic */ FragmentTransitionImpl val$impl;
    final /* synthetic */ Rect val$inEpicenter;
    final /* synthetic */ Fragment val$inFragment;
    final /* synthetic */ boolean val$inIsPop;
    final /* synthetic */ ArrayMap val$nameOverrides;
    final /* synthetic */ View val$nonExistentView;
    final /* synthetic */ Fragment val$outFragment;
    final /* synthetic */ ArrayList val$sharedElementsIn;
    final /* synthetic */ ArrayList val$sharedElementsOut;

    FragmentTransition(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap arrayMap, Object object, FragmentTransition.FragmentContainerTransition fragmentContainerTransition, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean bl, ArrayList arrayList2, Object object2, Rect rect) {
        this.val$impl = fragmentTransitionImpl;
        this.val$nameOverrides = arrayMap;
        this.val$finalSharedElementTransition = object;
        this.val$fragments = fragmentContainerTransition;
        this.val$sharedElementsIn = arrayList;
        this.val$nonExistentView = view;
        this.val$inFragment = fragment;
        this.val$outFragment = fragment2;
        this.val$inIsPop = bl;
        this.val$sharedElementsOut = arrayList2;
        this.val$enterTransition = object2;
        this.val$inEpicenter = rect;
    }

    @Override
    public void run() {
        ArrayMap arrayMap = android.support.v4.app.FragmentTransition.captureInSharedElements(this.val$impl, this.val$nameOverrides, this.val$finalSharedElementTransition, this.val$fragments);
        if (arrayMap != null) {
            this.val$sharedElementsIn.addAll(arrayMap.values());
            this.val$sharedElementsIn.add(this.val$nonExistentView);
        }
        android.support.v4.app.FragmentTransition.callSharedElementStartEnd(this.val$inFragment, this.val$outFragment, this.val$inIsPop, arrayMap, false);
        if (this.val$finalSharedElementTransition != null) {
            this.val$impl.swapSharedElementTargets(this.val$finalSharedElementTransition, this.val$sharedElementsOut, this.val$sharedElementsIn);
            arrayMap = android.support.v4.app.FragmentTransition.getInEpicenterView(arrayMap, this.val$fragments, this.val$enterTransition, this.val$inIsPop);
            if (arrayMap != null) {
                this.val$impl.getBoundsOnScreen((View)arrayMap, this.val$inEpicenter);
            }
        }
    }
}
