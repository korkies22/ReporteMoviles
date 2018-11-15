/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.app;

import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.widget.ActionBarContainer;
import android.view.View;

class WindowDecorActionBar
extends ViewPropertyAnimatorListenerAdapter {
    WindowDecorActionBar() {
    }

    @Override
    public void onAnimationEnd(View view) {
        WindowDecorActionBar.this.mCurrentShowAnim = null;
        WindowDecorActionBar.this.mContainerView.requestLayout();
    }
}
