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
import android.support.v7.widget.ActionBarOverlayLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class ActionBarOverlayLayout.LayoutParams
extends ViewGroup.MarginLayoutParams {
    public ActionBarOverlayLayout.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public ActionBarOverlayLayout.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ActionBarOverlayLayout.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public ActionBarOverlayLayout.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }
}
