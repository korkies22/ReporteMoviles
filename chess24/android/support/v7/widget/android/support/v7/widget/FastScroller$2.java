/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;

class FastScroller
extends RecyclerView.OnScrollListener {
    FastScroller() {
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int n, int n2) {
        FastScroller.this.updateScrollPosition(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
    }
}
