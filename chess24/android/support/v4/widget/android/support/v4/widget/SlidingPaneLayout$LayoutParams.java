/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class SlidingPaneLayout.LayoutParams
extends ViewGroup.MarginLayoutParams {
    private static final int[] ATTRS = new int[]{16843137};
    Paint dimPaint;
    boolean dimWhenOffset;
    boolean slideable;
    public float weight = 0.0f;

    public SlidingPaneLayout.LayoutParams() {
        super(-1, -1);
    }

    public SlidingPaneLayout.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public SlidingPaneLayout.LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, ATTRS);
        this.weight = context.getFloat(0, 0.0f);
        context.recycle();
    }

    public SlidingPaneLayout.LayoutParams(@NonNull SlidingPaneLayout.LayoutParams layoutParams) {
        super((ViewGroup.MarginLayoutParams)layoutParams);
        this.weight = layoutParams.weight;
    }

    public SlidingPaneLayout.LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public SlidingPaneLayout.LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }
}
