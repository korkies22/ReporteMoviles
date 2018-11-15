/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 */
package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
static class BaseTransientBottomBar.SnackbarBaseLayout
extends FrameLayout {
    private BaseTransientBottomBar.OnAttachStateChangeListener mOnAttachStateChangeListener;
    private BaseTransientBottomBar.OnLayoutChangeListener mOnLayoutChangeListener;

    BaseTransientBottomBar.SnackbarBaseLayout(Context context) {
        this(context, null);
    }

    BaseTransientBottomBar.SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.SnackbarLayout);
        if (context.hasValue(R.styleable.SnackbarLayout_elevation)) {
            ViewCompat.setElevation((View)this, context.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
        }
        context.recycle();
        this.setClickable(true);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mOnAttachStateChangeListener != null) {
            this.mOnAttachStateChangeListener.onViewAttachedToWindow((View)this);
        }
        ViewCompat.requestApplyInsets((View)this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mOnAttachStateChangeListener != null) {
            this.mOnAttachStateChangeListener.onViewDetachedFromWindow((View)this);
        }
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mOnLayoutChangeListener != null) {
            this.mOnLayoutChangeListener.onLayoutChange((View)this, n, n2, n3, n4);
        }
    }

    void setOnAttachStateChangeListener(BaseTransientBottomBar.OnAttachStateChangeListener onAttachStateChangeListener) {
        this.mOnAttachStateChangeListener = onAttachStateChangeListener;
    }

    void setOnLayoutChangeListener(BaseTransientBottomBar.OnLayoutChangeListener onLayoutChangeListener) {
        this.mOnLayoutChangeListener = onLayoutChangeListener;
    }
}
