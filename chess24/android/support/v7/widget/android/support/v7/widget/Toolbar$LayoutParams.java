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
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class Toolbar.LayoutParams
extends ActionBar.LayoutParams {
    static final int CUSTOM = 0;
    static final int EXPANDED = 2;
    static final int SYSTEM = 1;
    int mViewType = 0;

    public Toolbar.LayoutParams(int n) {
        this(-2, -1, n);
    }

    public Toolbar.LayoutParams(int n, int n2) {
        super(n, n2);
        this.gravity = 8388627;
    }

    public Toolbar.LayoutParams(int n, int n2, int n3) {
        super(n, n2);
        this.gravity = n3;
    }

    public Toolbar.LayoutParams(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Toolbar.LayoutParams(ActionBar.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public Toolbar.LayoutParams(Toolbar.LayoutParams layoutParams) {
        super(layoutParams);
        this.mViewType = layoutParams.mViewType;
    }

    public Toolbar.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public Toolbar.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super((ViewGroup.LayoutParams)marginLayoutParams);
        this.copyMarginsFromCompat(marginLayoutParams);
    }

    void copyMarginsFromCompat(ViewGroup.MarginLayoutParams marginLayoutParams) {
        this.leftMargin = marginLayoutParams.leftMargin;
        this.topMargin = marginLayoutParams.topMargin;
        this.rightMargin = marginLayoutParams.rightMargin;
        this.bottomMargin = marginLayoutParams.bottomMargin;
    }
}
