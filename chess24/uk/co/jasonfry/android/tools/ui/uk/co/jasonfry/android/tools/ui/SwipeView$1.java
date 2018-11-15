/*
 * Decompiled with CFR 0_134.
 */
package uk.co.jasonfry.android.tools.ui;

import uk.co.jasonfry.android.tools.ui.PageControl;

class SwipeView
implements PageControl.OnPageControlClickListener {
    SwipeView() {
    }

    @Override
    public void goBackwards() {
        SwipeView.this.smoothScrollToPage(SwipeView.this.mCurrentPage - 1);
    }

    @Override
    public void goForwards() {
        SwipeView.this.smoothScrollToPage(SwipeView.this.mCurrentPage + 1);
    }

    @Override
    public void goToPage(int n) {
        SwipeView.this.smoothScrollToPage(n);
    }
}
