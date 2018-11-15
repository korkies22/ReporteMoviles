/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;

private class BottomSheetBehavior.SettleRunnable
implements Runnable {
    private final int mTargetState;
    private final View mView;

    BottomSheetBehavior.SettleRunnable(View view, int n) {
        this.mView = view;
        this.mTargetState = n;
    }

    @Override
    public void run() {
        if (BottomSheetBehavior.this.mViewDragHelper != null && BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
            ViewCompat.postOnAnimation(this.mView, this);
            return;
        }
        BottomSheetBehavior.this.setStateInternal(this.mTargetState);
    }
}
