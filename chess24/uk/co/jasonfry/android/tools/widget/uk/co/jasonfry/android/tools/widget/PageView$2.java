/*
 * Decompiled with CFR 0_134.
 */
package uk.co.jasonfry.android.tools.widget;

class PageView
implements Runnable {
    final /* synthetic */ int val$pageToScrollTo;
    final /* synthetic */ boolean val$smooth;

    PageView(boolean bl, int n) {
        this.val$smooth = bl;
        this.val$pageToScrollTo = n;
    }

    @Override
    public void run() {
        if (this.val$smooth) {
            uk.co.jasonfry.android.tools.widget.PageView.super.smoothScrollToPage(this.val$pageToScrollTo);
            return;
        }
        uk.co.jasonfry.android.tools.widget.PageView.super.scrollToPage(this.val$pageToScrollTo);
    }
}
