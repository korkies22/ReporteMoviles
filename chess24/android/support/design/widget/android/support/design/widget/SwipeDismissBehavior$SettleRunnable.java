/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;

private class SwipeDismissBehavior.SettleRunnable
implements Runnable {
    private final boolean mDismiss;
    private final View mView;

    SwipeDismissBehavior.SettleRunnable(View view, boolean bl) {
        this.mView = view;
        this.mDismiss = bl;
    }

    @Override
    public void run() {
        if (SwipeDismissBehavior.this.mViewDragHelper != null && SwipeDismissBehavior.this.mViewDragHelper.continueSettling(true)) {
            ViewCompat.postOnAnimation(this.mView, this);
            return;
        }
        if (this.mDismiss && SwipeDismissBehavior.this.mListener != null) {
            SwipeDismissBehavior.this.mListener.onDismiss(this.mView);
        }
    }
}
