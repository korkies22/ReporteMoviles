/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class LinearLayoutCompat.LayoutParams
extends ViewGroup.MarginLayoutParams {
    public int gravity = -1;
    public float weight;

    public LinearLayoutCompat.LayoutParams(int n, int n2) {
        super(n, n2);
        this.weight = 0.0f;
    }

    public LinearLayoutCompat.LayoutParams(int n, int n2, float f) {
        super(n, n2);
        this.weight = f;
    }

    public LinearLayoutCompat.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout);
        this.weight = context.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
        this.gravity = context.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
        context.recycle();
    }

    public LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams layoutParams) {
        super((ViewGroup.MarginLayoutParams)layoutParams);
        this.weight = layoutParams.weight;
        this.gravity = layoutParams.gravity;
    }

    public LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public LinearLayoutCompat.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }
}
