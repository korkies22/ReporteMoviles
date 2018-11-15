/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.GridLayoutManager;

public static final class GridLayoutManager.DefaultSpanSizeLookup
extends GridLayoutManager.SpanSizeLookup {
    @Override
    public int getSpanIndex(int n, int n2) {
        return n % n2;
    }

    @Override
    public int getSpanSize(int n) {
        return 1;
    }
}
