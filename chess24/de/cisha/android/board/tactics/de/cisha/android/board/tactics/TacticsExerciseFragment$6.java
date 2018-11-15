/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 */
package de.cisha.android.board.tactics;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

class TacticsExerciseFragment
implements ViewTreeObserver.OnGlobalLayoutListener {
    final /* synthetic */ ViewGroup val$container;
    final /* synthetic */ View val$contentView;

    TacticsExerciseFragment(ViewGroup viewGroup, View view) {
        this.val$container = viewGroup;
        this.val$contentView = view;
    }

    public void onGlobalLayout() {
        TacticsExerciseFragment.this._fragmentWidth = this.val$container.getWidth();
        TacticsExerciseFragment.this._fragmentHeight = this.val$container.getHeight();
        TacticsExerciseFragment.this.checkForSmallDisplays(this.val$contentView);
        this.val$contentView.getViewTreeObserver().removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
        TacticsExerciseFragment.this.initRatingIndicatorBar();
    }
}
