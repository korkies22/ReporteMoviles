/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.view.View;

class ScrollingTabContainerView
implements Runnable {
    final /* synthetic */ View val$tabView;

    ScrollingTabContainerView(View view) {
        this.val$tabView = view;
    }

    @Override
    public void run() {
        int n = this.val$tabView.getLeft();
        int n2 = (ScrollingTabContainerView.this.getWidth() - this.val$tabView.getWidth()) / 2;
        ScrollingTabContainerView.this.smoothScrollTo(n - n2, 0);
        ScrollingTabContainerView.this.mTabSelector = null;
    }
}
