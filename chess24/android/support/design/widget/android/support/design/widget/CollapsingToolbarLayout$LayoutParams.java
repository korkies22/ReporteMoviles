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
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public static class CollapsingToolbarLayout.LayoutParams
extends FrameLayout.LayoutParams {
    public static final int COLLAPSE_MODE_OFF = 0;
    public static final int COLLAPSE_MODE_PARALLAX = 2;
    public static final int COLLAPSE_MODE_PIN = 1;
    private static final float DEFAULT_PARALLAX_MULTIPLIER = 0.5f;
    int mCollapseMode = 0;
    float mParallaxMult = 0.5f;

    public CollapsingToolbarLayout.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public CollapsingToolbarLayout.LayoutParams(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    public CollapsingToolbarLayout.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.CollapsingToolbarLayout_Layout);
        this.mCollapseMode = context.getInt(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
        this.setParallaxMultiplier(context.getFloat(R.styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
        context.recycle();
    }

    public CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public CollapsingToolbarLayout.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }

    @RequiresApi(value=19)
    public CollapsingToolbarLayout.LayoutParams(FrameLayout.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public int getCollapseMode() {
        return this.mCollapseMode;
    }

    public float getParallaxMultiplier() {
        return this.mParallaxMult;
    }

    public void setCollapseMode(int n) {
        this.mCollapseMode = n;
    }

    public void setParallaxMultiplier(float f) {
        this.mParallaxMult = f;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    static @interface CollapseMode {
    }

}
