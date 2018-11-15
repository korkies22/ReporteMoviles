/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.v7.app;

import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.widget.ActionBarContainer;
import android.view.View;
import android.view.ViewParent;

class WindowDecorActionBar
implements ViewPropertyAnimatorUpdateListener {
    WindowDecorActionBar() {
    }

    @Override
    public void onAnimationUpdate(View view) {
        ((View)WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
    }
}
