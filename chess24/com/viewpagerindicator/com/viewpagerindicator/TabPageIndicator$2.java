/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.viewpagerindicator;

import android.view.View;

class TabPageIndicator
implements Runnable {
    final /* synthetic */ View val$tabView;

    TabPageIndicator(View view) {
        this.val$tabView = view;
    }

    @Override
    public void run() {
        int n = this.val$tabView.getLeft();
        int n2 = (TabPageIndicator.this.getWidth() - this.val$tabView.getWidth()) / 2;
        TabPageIndicator.this.smoothScrollTo(n - n2, 0);
        TabPageIndicator.this.mTabSelector = null;
    }
}
