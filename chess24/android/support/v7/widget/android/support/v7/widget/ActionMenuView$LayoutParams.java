/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewDebug
 *  android.view.ViewDebug$ExportedProperty
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.view.ViewGroup;

public static class ActionMenuView.LayoutParams
extends LinearLayoutCompat.LayoutParams {
    @ViewDebug.ExportedProperty
    public int cellsUsed;
    @ViewDebug.ExportedProperty
    public boolean expandable;
    boolean expanded;
    @ViewDebug.ExportedProperty
    public int extraPixels;
    @ViewDebug.ExportedProperty
    public boolean isOverflowButton;
    @ViewDebug.ExportedProperty
    public boolean preventEdgeOffset;

    public ActionMenuView.LayoutParams(int n, int n2) {
        super(n, n2);
        this.isOverflowButton = false;
    }

    ActionMenuView.LayoutParams(int n, int n2, boolean bl) {
        super(n, n2);
        this.isOverflowButton = bl;
    }

    public ActionMenuView.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ActionMenuView.LayoutParams(ActionMenuView.LayoutParams layoutParams) {
        super((ViewGroup.LayoutParams)layoutParams);
        this.isOverflowButton = layoutParams.isOverflowButton;
    }

    public ActionMenuView.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }
}
