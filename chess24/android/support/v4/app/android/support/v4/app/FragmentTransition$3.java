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
import android.support.v4.app.FragmentTransitionImpl;
import android.support.v4.util.ArrayMap;
import android.view.View;

static final class FragmentTransition
implements Runnable {
    final /* synthetic */ Rect val$epicenter;
    final /* synthetic */ View val$epicenterView;
    final /* synthetic */ FragmentTransitionImpl val$impl;
    final /* synthetic */ Fragment val$inFragment;
    final /* synthetic */ boolean val$inIsPop;
    final /* synthetic */ ArrayMap val$inSharedElements;
    final /* synthetic */ Fragment val$outFragment;

    FragmentTransition(Fragment fragment, Fragment fragment2, boolean bl, ArrayMap arrayMap, View view, FragmentTransitionImpl fragmentTransitionImpl, Rect rect) {
        this.val$inFragment = fragment;
        this.val$outFragment = fragment2;
        this.val$inIsPop = bl;
        this.val$inSharedElements = arrayMap;
        this.val$epicenterView = view;
        this.val$impl = fragmentTransitionImpl;
        this.val$epicenter = rect;
    }

    @Override
    public void run() {
        android.support.v4.app.FragmentTransition.callSharedElementStartEnd(this.val$inFragment, this.val$outFragment, this.val$inIsPop, this.val$inSharedElements, false);
        if (this.val$epicenterView != null) {
            this.val$impl.getBoundsOnScreen(this.val$epicenterView, this.val$epicenter);
        }
    }
}
