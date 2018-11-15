/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.Adapter
 */
package uk.co.jasonfry.android.tools.widget;

import android.widget.Adapter;

class PageView
implements Runnable {
    final /* synthetic */ int val$startPosition;

    PageView(int n) {
        this.val$startPosition = n;
    }

    @Override
    public void run() {
        if (!PageView.this.mCarouselMode && this.val$startPosition == 0) {
            uk.co.jasonfry.android.tools.widget.PageView.super.scrollToPage(0);
            return;
        }
        if (!PageView.this.mCarouselMode && this.val$startPosition == PageView.this.mAdapter.getCount() - 1) {
            uk.co.jasonfry.android.tools.widget.PageView.super.scrollToPage(2);
            return;
        }
        uk.co.jasonfry.android.tools.widget.PageView.super.scrollToPage(1);
    }
}
