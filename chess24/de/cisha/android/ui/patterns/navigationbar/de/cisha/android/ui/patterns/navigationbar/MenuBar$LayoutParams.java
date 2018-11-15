/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package de.cisha.android.ui.patterns.navigationbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import de.cisha.android.ui.patterns.R;
import de.cisha.android.ui.patterns.navigationbar.MenuBar;

public static class MenuBar.LayoutParams
extends ViewGroup.LayoutParams {
    private int _x = 0;
    public boolean needed = false;
    public int weight = 1;

    public MenuBar.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public MenuBar.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.MenuBar_LayoutParams);
        try {
            this.weight = context.getInteger(R.styleable.MenuBar_LayoutParams_layout_weight, 1);
            this.needed = context.getBoolean(R.styleable.MenuBar_LayoutParams_layout_needed, false);
            return;
        }
        finally {
            context.recycle();
        }
    }

    public MenuBar.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        this(layoutParams.width, layoutParams.height);
    }

    static /* synthetic */ int access$300(MenuBar.LayoutParams layoutParams) {
        return layoutParams._x;
    }

    static /* synthetic */ int access$302(MenuBar.LayoutParams layoutParams, int n) {
        layoutParams._x = n;
        return n;
    }
}
