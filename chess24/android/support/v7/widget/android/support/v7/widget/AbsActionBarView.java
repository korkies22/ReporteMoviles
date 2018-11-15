/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.ContextThemeWrapper
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.appcompat.R;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.ActionMenuView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

abstract class AbsActionBarView
extends ViewGroup {
    private static final int FADE_DURATION = 200;
    protected ActionMenuPresenter mActionMenuPresenter;
    protected int mContentHeight;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    protected ActionMenuView mMenuView;
    protected final Context mPopupContext;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    protected ViewPropertyAnimatorCompat mVisibilityAnim;

    AbsActionBarView(Context context) {
        this(context, null);
    }

    AbsActionBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    AbsActionBarView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        attributeSet = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.actionBarPopupTheme, (TypedValue)attributeSet, true) && attributeSet.resourceId != 0) {
            this.mPopupContext = new ContextThemeWrapper(context, attributeSet.resourceId);
            return;
        }
        this.mPopupContext = context;
    }

    protected static int next(int n, int n2, boolean bl) {
        if (bl) {
            return n - n2;
        }
        return n + n2;
    }

    public void animateToVisibility(int n) {
        this.setupAnimatorToVisibility(n, 200L).start();
    }

    public boolean canShowOverflowMenu() {
        if (this.isOverflowReserved() && this.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public void dismissPopupMenus() {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.dismissPopupMenus();
        }
    }

    public int getAnimatedVisibility() {
        if (this.mVisibilityAnim != null) {
            return this.mVisAnimListener.mFinalVisibility;
        }
        return this.getVisibility();
    }

    public int getContentHeight() {
        return this.mContentHeight;
    }

    public boolean hideOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.hideOverflowMenu();
        }
        return false;
    }

    public boolean isOverflowMenuShowPending() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowPending();
        }
        return false;
    }

    public boolean isOverflowMenuShowing() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.isOverflowMenuShowing();
        }
        return false;
    }

    public boolean isOverflowReserved() {
        if (this.mActionMenuPresenter != null && this.mActionMenuPresenter.isOverflowReserved()) {
            return true;
        }
        return false;
    }

    protected int measureChildView(View view, int n, int n2, int n3) {
        view.measure(View.MeasureSpec.makeMeasureSpec((int)n, (int)Integer.MIN_VALUE), n2);
        return Math.max(0, n - view.getMeasuredWidth() - n3);
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArray = this.getContext().obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
        this.setContentHeight(typedArray.getLayoutDimension(R.styleable.ActionBar_height, 0));
        typedArray.recycle();
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.onConfigurationChanged(configuration);
        }
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean bl = super.onHoverEvent(motionEvent);
            if (n == 9 && !bl) {
                this.mEatingHover = true;
            }
        }
        if (n == 10 || n == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getActionMasked();
        if (n == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean bl = super.onTouchEvent(motionEvent);
            if (n == 0 && !bl) {
                this.mEatingTouch = true;
            }
        }
        if (n == 1 || n == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    protected int positionChild(View view, int n, int n2, int n3, boolean bl) {
        int n4 = view.getMeasuredWidth();
        int n5 = view.getMeasuredHeight();
        n2 += (n3 - n5) / 2;
        if (bl) {
            view.layout(n - n4, n2, n, n5 + n2);
        } else {
            view.layout(n, n2, n + n4, n5 + n2);
        }
        n = n4;
        if (bl) {
            n = - n4;
        }
        return n;
    }

    public void postShowOverflowMenu() {
        this.post(new Runnable(){

            @Override
            public void run() {
                AbsActionBarView.this.showOverflowMenu();
            }
        });
    }

    public void setContentHeight(int n) {
        this.mContentHeight = n;
        this.requestLayout();
    }

    public void setVisibility(int n) {
        if (n != this.getVisibility()) {
            if (this.mVisibilityAnim != null) {
                this.mVisibilityAnim.cancel();
            }
            super.setVisibility(n);
        }
    }

    public ViewPropertyAnimatorCompat setupAnimatorToVisibility(int n, long l) {
        if (this.mVisibilityAnim != null) {
            this.mVisibilityAnim.cancel();
        }
        if (n == 0) {
            if (this.getVisibility() != 0) {
                this.setAlpha(0.0f);
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this).alpha(1.0f);
            viewPropertyAnimatorCompat.setDuration(l);
            viewPropertyAnimatorCompat.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorCompat, n));
            return viewPropertyAnimatorCompat;
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this).alpha(0.0f);
        viewPropertyAnimatorCompat.setDuration(l);
        viewPropertyAnimatorCompat.setListener(this.mVisAnimListener.withFinalVisibility(viewPropertyAnimatorCompat, n));
        return viewPropertyAnimatorCompat;
    }

    public boolean showOverflowMenu() {
        if (this.mActionMenuPresenter != null) {
            return this.mActionMenuPresenter.showOverflowMenu();
        }
        return false;
    }

    protected class VisibilityAnimListener
    implements ViewPropertyAnimatorListener {
        private boolean mCanceled = false;
        int mFinalVisibility;

        protected VisibilityAnimListener() {
        }

        @Override
        public void onAnimationCancel(View view) {
            this.mCanceled = true;
        }

        @Override
        public void onAnimationEnd(View view) {
            if (this.mCanceled) {
                return;
            }
            AbsActionBarView.this.mVisibilityAnim = null;
            AbsActionBarView.super.setVisibility(this.mFinalVisibility);
        }

        @Override
        public void onAnimationStart(View view) {
            AbsActionBarView.super.setVisibility(0);
            this.mCanceled = false;
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, int n) {
            AbsActionBarView.this.mVisibilityAnim = viewPropertyAnimatorCompat;
            this.mFinalVisibility = n;
            return this;
        }
    }

}
