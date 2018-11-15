/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package de.cisha.android.board.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import de.cisha.android.board.view.ScaleableGridLayout;

public static class ScaleableGridLayout.LayoutParams
extends ViewGroup.LayoutParams {
    public int col = -1;
    public int colspan = 1;
    public int row = -1;
    public int rowspan = 1;

    public ScaleableGridLayout.LayoutParams(int n, int n2, int n3, int n4) {
        super(0, 0);
        this.col = n;
        this.row = n2;
        this.colspan = n3;
        this.rowspan = n4;
    }

    public ScaleableGridLayout.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScaleableGridLayout.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }
}
