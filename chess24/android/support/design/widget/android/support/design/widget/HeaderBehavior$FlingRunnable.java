/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.OverScroller
 */
package android.support.design.widget;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.HeaderBehavior;
import android.support.v4.view.ViewCompat;
import android.widget.OverScroller;

private class HeaderBehavior.FlingRunnable
implements Runnable {
    private final V mLayout;
    private final CoordinatorLayout mParent;

    HeaderBehavior.FlingRunnable(CoordinatorLayout coordinatorLayout, V v) {
        this.mParent = coordinatorLayout;
        this.mLayout = v;
    }

    @Override
    public void run() {
        if (this.mLayout != null && this$0.mScroller != null) {
            if (this$0.mScroller.computeScrollOffset()) {
                this$0.setHeaderTopBottomOffset(this.mParent, this.mLayout, this$0.mScroller.getCurrY());
                ViewCompat.postOnAnimation(this.mLayout, this);
                return;
            }
            this$0.onFlingFinished(this.mParent, this.mLayout);
        }
    }
}
