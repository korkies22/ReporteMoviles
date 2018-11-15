/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.widget.OverScroller
 */
package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.ViewOffsetBehavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

abstract class HeaderBehavior<V extends View>
extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private Runnable mFlingRunnable;
    private boolean mIsBeingDragged;
    private int mLastMotionY;
    OverScroller mScroller;
    private int mTouchSlop = -1;
    private VelocityTracker mVelocityTracker;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void ensureVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    boolean canDragView(V v) {
        return false;
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V v, int n, int n2, float f) {
        if (this.mFlingRunnable != null) {
            v.removeCallbacks(this.mFlingRunnable);
            this.mFlingRunnable = null;
        }
        if (this.mScroller == null) {
            this.mScroller = new OverScroller(v.getContext());
        }
        this.mScroller.fling(0, this.getTopAndBottomOffset(), 0, Math.round(f), 0, 0, n, n2);
        if (this.mScroller.computeScrollOffset()) {
            this.mFlingRunnable = new FlingRunnable(this, coordinatorLayout, v);
            ViewCompat.postOnAnimation(v, this.mFlingRunnable);
            return true;
        }
        this.onFlingFinished(coordinatorLayout, v);
        return false;
    }

    int getMaxDragOffset(V v) {
        return - v.getHeight();
    }

    int getScrollRangeForDragFling(V v) {
        return v.getHeight();
    }

    int getTopBottomOffsetForScrollingSibling() {
        return this.getTopAndBottomOffset();
    }

    void onFlingFinished(CoordinatorLayout coordinatorLayout, V v) {
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get((Context)coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getAction() == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (motionEvent.getActionMasked()) {
            default: {
                break;
            }
            case 2: {
                int n = this.mActivePointerId;
                if (n == -1 || (n = motionEvent.findPointerIndex(n)) == -1 || Math.abs((n = (int)motionEvent.getY(n)) - this.mLastMotionY) <= this.mTouchSlop) break;
                this.mIsBeingDragged = true;
                this.mLastMotionY = n;
                break;
            }
            case 1: 
            case 3: {
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mVelocityTracker == null) break;
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            }
            case 0: {
                this.mIsBeingDragged = false;
                int n = (int)motionEvent.getX();
                int n2 = (int)motionEvent.getY();
                if (!this.canDragView(v) || !coordinatorLayout.isPointInChildBounds((View)v, n, n2)) break;
                this.mLastMotionY = n2;
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.ensureVelocityTracker();
            }
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return this.mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get((Context)coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        switch (motionEvent.getActionMasked()) {
            default: {
                break;
            }
            case 2: {
                int n;
                int n2 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (n2 == -1) {
                    return false;
                }
                int n3 = (int)motionEvent.getY(n2);
                n2 = n = this.mLastMotionY - n3;
                if (!this.mIsBeingDragged) {
                    n2 = n;
                    if (Math.abs(n) > this.mTouchSlop) {
                        this.mIsBeingDragged = true;
                        n2 = n > 0 ? n - this.mTouchSlop : n + this.mTouchSlop;
                    }
                }
                if (!this.mIsBeingDragged) break;
                this.mLastMotionY = n3;
                this.scroll(coordinatorLayout, v, n2, this.getMaxDragOffset(v), 0);
                break;
            }
            case 1: {
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                    this.fling(coordinatorLayout, v, - this.getScrollRangeForDragFling(v), 0, f);
                }
            }
            case 3: {
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mVelocityTracker == null) break;
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            }
            case 0: {
                int n = (int)motionEvent.getX();
                int n4 = (int)motionEvent.getY();
                if (coordinatorLayout.isPointInChildBounds((View)v, n, n4) && this.canDragView(v)) {
                    this.mLastMotionY = n4;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    this.ensureVelocityTracker();
                    break;
                }
                return false;
            }
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return true;
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v, this.getTopBottomOffsetForScrollingSibling() - n, n2, n3);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int n) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v, n, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3) {
        int n4 = this.getTopAndBottomOffset();
        if (n2 != 0 && n4 >= n2 && n4 <= n3 && n4 != (n = MathUtils.clamp(n, n2, n3))) {
            this.setTopAndBottomOffset(n);
            return n4 - n;
        }
        return 0;
    }

    private class FlingRunnable
    implements Runnable {
        private final V mLayout;
        private final CoordinatorLayout mParent;

        FlingRunnable(CoordinatorLayout coordinatorLayout, V v) {
            this.mParent = coordinatorLayout;
            this.mLayout = v;
        }

        @Override
        public void run() {
            if (this.mLayout != null && this$0.mScroller != null) {
                if (this$0.mScroller.computeScrollOffset()) {
                    this$0.setHeaderTopBottomOffset(this.mParent, this.mLayout, this$0.mScroller.getCurrY());
                    ViewCompat.postOnAnimation(this.mLayout, this);
                    return;
                }
                this$0.onFlingFinished(this.mParent, this.mLayout);
            }
        }
    }

}
