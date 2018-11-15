/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;

class SnapHelper
extends RecyclerView.OnScrollListener {
    boolean mScrolled = false;

    SnapHelper() {
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int n) {
        super.onScrollStateChanged(recyclerView, n);
        if (n == 0 && this.mScrolled) {
            this.mScrolled = false;
            SnapHelper.this.snapToTargetExistingView();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int n, int n2) {
        if (n != 0 || n2 != 0) {
            this.mScrolled = true;
        }
    }
}
