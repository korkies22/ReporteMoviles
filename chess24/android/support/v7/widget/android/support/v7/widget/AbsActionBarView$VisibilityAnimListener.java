/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.AbsActionBarView;
import android.view.View;

protected class AbsActionBarView.VisibilityAnimListener
implements ViewPropertyAnimatorListener {
    private boolean mCanceled = false;
    int mFinalVisibility;

    protected AbsActionBarView.VisibilityAnimListener() {
    }

    @Override
    public void onAnimationCancel(View view) {
        this.mCanceled = true;
    }

    @Override
    public void onAnimationEnd(View view) {
        if (this.mCanceled) {
            return;
        }
        AbsActionBarView.this.mVisibilityAnim = null;
        AbsActionBarView.super.setVisibility(this.mFinalVisibility);
    }

    @Override
    public void onAnimationStart(View view) {
        AbsActionBarView.super.setVisibility(0);
        this.mCanceled = false;
    }

    public AbsActionBarView.VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, int n) {
        AbsActionBarView.this.mVisibilityAnim = viewPropertyAnimatorCompat;
        this.mFinalVisibility = n;
        return this;
    }
}
