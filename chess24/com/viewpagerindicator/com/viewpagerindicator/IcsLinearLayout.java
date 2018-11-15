/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package com.viewpagerindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

class IcsLinearLayout
extends LinearLayout {
    private static final int[] LL = new int[]{16843049, 16843561, 16843562};
    private static final int LL_DIVIDER = 0;
    private static final int LL_DIVIDER_PADDING = 2;
    private static final int LL_SHOW_DIVIDER = 1;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mShowDividers;

    public IcsLinearLayout(Context context, int n) {
        super(context);
        context = context.obtainStyledAttributes(null, LL, n, 0);
        this.setDividerDrawable(context.getDrawable(0));
        this.mDividerPadding = context.getDimensionPixelSize(2, 0);
        this.mShowDividers = context.getInteger(1, 0);
        context.recycle();
    }

    private void drawDividersHorizontal(Canvas canvas) {
        View view;
        int n;
        int n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            view = this.getChildAt(n);
            if (view == null || view.getVisibility() == 8 || !this.hasDividerBeforeChildAt(n)) continue;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            this.drawVerticalDivider(canvas, view.getLeft() - layoutParams.leftMargin);
        }
        if (this.hasDividerBeforeChildAt(n2)) {
            view = this.getChildAt(n2 - 1);
            n = view == null ? this.getWidth() - this.getPaddingRight() - this.mDividerWidth : view.getRight();
            this.drawVerticalDivider(canvas, n);
        }
    }

    private void drawDividersVertical(Canvas canvas) {
        View view;
        int n;
        int n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            view = this.getChildAt(n);
            if (view == null || view.getVisibility() == 8 || !this.hasDividerBeforeChildAt(n)) continue;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            this.drawHorizontalDivider(canvas, view.getTop() - layoutParams.topMargin);
        }
        if (this.hasDividerBeforeChildAt(n2)) {
            view = this.getChildAt(n2 - 1);
            n = view == null ? this.getHeight() - this.getPaddingBottom() - this.mDividerHeight : view.getBottom();
            this.drawHorizontalDivider(canvas, n);
        }
    }

    private void drawHorizontalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(this.getPaddingLeft() + this.mDividerPadding, n, this.getWidth() - this.getPaddingRight() - this.mDividerPadding, this.mDividerHeight + n);
        this.mDivider.draw(canvas);
    }

    private void drawVerticalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(n, this.getPaddingTop() + this.mDividerPadding, this.mDividerWidth + n, this.getHeight() - this.getPaddingBottom() - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    private boolean hasDividerBeforeChildAt(int n) {
        if (n != 0) {
            if (n == this.getChildCount()) {
                return false;
            }
            if ((this.mShowDividers & 2) != 0) {
                --n;
                while (n >= 0) {
                    if (this.getChildAt(n).getVisibility() != 8) {
                        return true;
                    }
                    --n;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        int n5;
        int n6 = this.indexOfChild(view);
        int n7 = this.getOrientation();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
        if (this.hasDividerBeforeChildAt(n6)) {
            if (n7 == 1) {
                layoutParams.topMargin = this.mDividerHeight;
            } else {
                layoutParams.leftMargin = this.mDividerWidth;
            }
        }
        if (n6 == (n5 = this.getChildCount()) - 1 && this.hasDividerBeforeChildAt(n5)) {
            if (n7 == 1) {
                layoutParams.bottomMargin = this.mDividerHeight;
            } else {
                layoutParams.rightMargin = this.mDividerWidth;
            }
        }
        super.measureChildWithMargins(view, n, n2, n3, n4);
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDivider != null) {
            if (this.getOrientation() == 1) {
                this.drawDividersVertical(canvas);
            } else {
                this.drawDividersHorizontal(canvas);
            }
        }
        super.onDraw(canvas);
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        boolean bl = false;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        if (drawable == null) {
            bl = true;
        }
        this.setWillNotDraw(bl);
        this.requestLayout();
    }
}
