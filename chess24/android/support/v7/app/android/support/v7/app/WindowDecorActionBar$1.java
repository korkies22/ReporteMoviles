/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.view.View;

class WindowDecorActionBar
extends ViewPropertyAnimatorListenerAdapter {
    WindowDecorActionBar() {
    }

    @Override
    public void onAnimationEnd(View view) {
        if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
            WindowDecorActionBar.this.mContentView.setTranslationY(0.0f);
            WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f);
        }
        WindowDecorActionBar.this.mContainerView.setVisibility(8);
        WindowDecorActionBar.this.mContainerView.setTransitioning(false);
        WindowDecorActionBar.this.mCurrentShowAnim = null;
        WindowDecorActionBar.this.completeDeferredDestroyActionMode();
        if (WindowDecorActionBar.this.mOverlayLayout != null) {
            ViewCompat.requestApplyInsets((View)WindowDecorActionBar.this.mOverlayLayout);
        }
    }
}
