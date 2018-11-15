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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class GridLayoutManager.LayoutParams
extends RecyclerView.LayoutParams {
    public static final int INVALID_SPAN_ID = -1;
    int mSpanIndex = -1;
    int mSpanSize = 0;

    public GridLayoutManager.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public GridLayoutManager.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GridLayoutManager.LayoutParams(RecyclerView.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public GridLayoutManager.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public GridLayoutManager.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }

    public int getSpanIndex() {
        return this.mSpanIndex;
    }

    public int getSpanSize() {
        return this.mSpanSize;
    }
}
