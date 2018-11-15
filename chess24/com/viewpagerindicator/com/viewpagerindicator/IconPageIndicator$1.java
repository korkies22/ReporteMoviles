/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.viewpagerindicator;

import android.view.View;

class IconPageIndicator
implements Runnable {
    final /* synthetic */ View val$iconView;

    IconPageIndicator(View view) {
        this.val$iconView = view;
    }

    @Override
    public void run() {
        int n = this.val$iconView.getLeft();
        int n2 = (IconPageIndicator.this.getWidth() - this.val$iconView.getWidth()) / 2;
        IconPageIndicator.this.smoothScrollTo(n - n2, 0);
        IconPageIndicator.this.mIconSelector = null;
    }
}
