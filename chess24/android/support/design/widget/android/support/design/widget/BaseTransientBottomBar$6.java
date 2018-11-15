/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.design.widget;

import android.support.design.widget.BaseTransientBottomBar;
import android.view.View;

class BaseTransientBottomBar
implements BaseTransientBottomBar.OnLayoutChangeListener {
    BaseTransientBottomBar() {
    }

    @Override
    public void onLayoutChange(View view, int n, int n2, int n3, int n4) {
        BaseTransientBottomBar.this.mView.setOnLayoutChangeListener(null);
        if (BaseTransientBottomBar.this.shouldAnimate()) {
            BaseTransientBottomBar.this.animateViewIn();
            return;
        }
        BaseTransientBottomBar.this.onViewShown();
    }
}
