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
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public static class AppBarLayout.LayoutParams
extends LinearLayout.LayoutParams {
    static final int COLLAPSIBLE_FLAGS = 10;
    static final int FLAG_QUICK_RETURN = 5;
    static final int FLAG_SNAP = 17;
    public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
    public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
    public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
    public static final int SCROLL_FLAG_SCROLL = 1;
    public static final int SCROLL_FLAG_SNAP = 16;
    int mScrollFlags = 1;
    Interpolator mScrollInterpolator;

    public AppBarLayout.LayoutParams(int n, int n2) {
        super(n, n2);
    }

    public AppBarLayout.LayoutParams(int n, int n2, float f) {
        super(n, n2, f);
    }

    public AppBarLayout.LayoutParams(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
        this.mScrollFlags = attributeSet.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
        if (attributeSet.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
            this.mScrollInterpolator = AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
        }
        attributeSet.recycle();
    }

    @RequiresApi(value=19)
    public AppBarLayout.LayoutParams(AppBarLayout.LayoutParams layoutParams) {
        super((LinearLayout.LayoutParams)layoutParams);
        this.mScrollFlags = layoutParams.mScrollFlags;
        this.mScrollInterpolator = layoutParams.mScrollInterpolator;
    }

    public AppBarLayout.LayoutParams(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public AppBarLayout.LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
    }

    @RequiresApi(value=19)
    public AppBarLayout.LayoutParams(LinearLayout.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public int getScrollFlags() {
        return this.mScrollFlags;
    }

    public Interpolator getScrollInterpolator() {
        return this.mScrollInterpolator;
    }

    boolean isCollapsible() {
        if ((this.mScrollFlags & 1) == 1 && (this.mScrollFlags & 10) != 0) {
            return true;
        }
        return false;
    }

    public void setScrollFlags(int n) {
        this.mScrollFlags = n;
    }

    public void setScrollInterpolator(Interpolator interpolator) {
        this.mScrollInterpolator = interpolator;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface ScrollFlags {
    }

}
