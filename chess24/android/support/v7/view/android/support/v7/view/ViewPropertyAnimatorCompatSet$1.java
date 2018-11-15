/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.view;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.View;
import java.util.ArrayList;

class ViewPropertyAnimatorCompatSet
extends ViewPropertyAnimatorListenerAdapter {
    private int mProxyEndCount = 0;
    private boolean mProxyStarted = false;

    ViewPropertyAnimatorCompatSet() {
    }

    @Override
    public void onAnimationEnd(View view) {
        int n;
        this.mProxyEndCount = n = this.mProxyEndCount + 1;
        if (n == ViewPropertyAnimatorCompatSet.this.mAnimators.size()) {
            if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
                ViewPropertyAnimatorCompatSet.this.mListener.onAnimationEnd(null);
            }
            this.onEnd();
        }
    }

    @Override
    public void onAnimationStart(View view) {
        if (this.mProxyStarted) {
            return;
        }
        this.mProxyStarted = true;
        if (ViewPropertyAnimatorCompatSet.this.mListener != null) {
            ViewPropertyAnimatorCompatSet.this.mListener.onAnimationStart(null);
        }
    }

    void onEnd() {
        this.mProxyEndCount = 0;
        this.mProxyStarted = false;
        ViewPropertyAnimatorCompatSet.this.onAnimationsEnded();
    }
}
