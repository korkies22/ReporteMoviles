/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.ActionBarContextView;
import android.view.View;
import android.view.ViewParent;

class AppCompatDelegateImplV9
extends ViewPropertyAnimatorListenerAdapter {
    AppCompatDelegateImplV9() {
    }

    @Override
    public void onAnimationEnd(View view) {
        AppCompatDelegateImplV9.this.mActionModeView.setAlpha(1.0f);
        AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
        AppCompatDelegateImplV9.this.mFadeAnim = null;
    }

    @Override
    public void onAnimationStart(View view) {
        AppCompatDelegateImplV9.this.mActionModeView.setVisibility(0);
        AppCompatDelegateImplV9.this.mActionModeView.sendAccessibilityEvent(32);
        if (AppCompatDelegateImplV9.this.mActionModeView.getParent() instanceof View) {
            ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV9.this.mActionModeView.getParent());
        }
    }
}
