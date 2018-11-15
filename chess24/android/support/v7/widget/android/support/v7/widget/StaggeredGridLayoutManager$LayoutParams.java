/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class StaggeredGridLayoutManager.LayoutParams
extends RecyclerView.LayoutParams {
    public static final int INVALID_SPAN_ID = -1;
    boolean mFullSpan;
    StaggeredGridLayoutManager.Span mSpan;

    public StaggeredGridLayoutManager.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public StaggeredGridLayoutManager.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StaggeredGridLayoutManager.LayoutParams(RecyclerView.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public StaggeredGridLayoutManager.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }

    public final int getSpanIndex() {
        if (this.mSpan == null) {
            return -1;
        }
        return this.mSpan.mIndex;
    }

    public boolean isFullSpan() {
        return this.mFullSpan;
    }

    public void setFullSpan(boolean bl) {
        this.mFullSpan = bl;
    }
}
