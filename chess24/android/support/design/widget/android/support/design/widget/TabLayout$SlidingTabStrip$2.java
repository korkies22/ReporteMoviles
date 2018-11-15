/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.TabLayout;

class TabLayout.SlidingTabStrip
extends AnimatorListenerAdapter {
    final /* synthetic */ int val$position;

    TabLayout.SlidingTabStrip(int n) {
        this.val$position = n;
    }

    public void onAnimationEnd(Animator animator) {
        SlidingTabStrip.this.mSelectedPosition = this.val$position;
        SlidingTabStrip.this.mSelectionOffset = 0.0f;
    }
}
