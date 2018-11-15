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
package android.support.v4.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;

public static class DrawerLayout.LayoutParams
extends ViewGroup.MarginLayoutParams {
    private static final int FLAG_IS_CLOSING = 4;
    private static final int FLAG_IS_OPENED = 1;
    private static final int FLAG_IS_OPENING = 2;
    public int gravity = 0;
    boolean isPeeking;
    float onScreen;
    int openState;

    public DrawerLayout.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public DrawerLayout.LayoutParams(int n, int n2, int n3) {
        this(n, n2);
        this.gravity = n3;
    }

    public DrawerLayout.LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, DrawerLayout.LAYOUT_ATTRS);
        this.gravity = context.getInt(0, 0);
        context.recycle();
    }

    public DrawerLayout.LayoutParams(@NonNull DrawerLayout.LayoutParams layoutParams) {
        super((ViewGroup.MarginLayoutParams)layoutParams);
        this.gravity = layoutParams.gravity;
    }

    public DrawerLayout.LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public DrawerLayout.LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }
}
