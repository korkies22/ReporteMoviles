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
package android.support.v7.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class ActionBar.LayoutParams
extends ViewGroup.MarginLayoutParams {
    public int gravity = 0;

    public ActionBar.LayoutParams(int n) {
        this(-2, -1, n);
    }

    public ActionBar.LayoutParams(int n, int n2) {
        super(n, n2);
        this.gravity = 8388627;
    }

    public ActionBar.LayoutParams(int n, int n2, int n3) {
        super(n, n2);
        this.gravity = n3;
    }

    public ActionBar.LayoutParams(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBarLayout);
        this.gravity = context.getInt(R.styleable.ActionBarLayout_android_layout_gravity, 0);
        context.recycle();
    }

    public ActionBar.LayoutParams(ActionBar.LayoutParams layoutParams) {
        super((ViewGroup.MarginLayoutParams)layoutParams);
        this.gravity = layoutParams.gravity;
    }

    public ActionBar.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }
}
