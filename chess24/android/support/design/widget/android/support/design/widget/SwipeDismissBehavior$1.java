/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewParent
 */
package android.support.design.widget;

import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import android.view.ViewParent;

class SwipeDismissBehavior
extends ViewDragHelper.Callback {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = -1;
    private int mOriginalCapturedViewLeft;

    SwipeDismissBehavior() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean shouldDismiss(View view, float f) {
        boolean bl = false;
        if (f != 0.0f) {
            boolean bl2 = ViewCompat.getLayoutDirection(view) == 1;
            if (SwipeDismissBehavior.this.mSwipeDirection == 2) {
                return true;
            }
            if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
                if (bl2) {
                    if (f >= 0.0f) return false;
                    do {
                        return true;
                        break;
                    } while (true);
                }
                if (f <= 0.0f) return false;
                return true;
            }
            if (SwipeDismissBehavior.this.mSwipeDirection != 1) return false;
            if (bl2) {
                if (f <= 0.0f) return false;
                do {
                    return true;
                    break;
                } while (true);
            }
            if (f >= 0.0f) return false;
            return true;
        }
        int n = view.getLeft();
        int n2 = this.mOriginalCapturedViewLeft;
        int n3 = Math.round((float)view.getWidth() * SwipeDismissBehavior.this.mDragDismissThreshold);
        if (Math.abs(n - n2) < n3) return bl;
        return true;
    }

    @Override
    public int clampViewPositionHorizontal(View view, int n, int n2) {
        int n3;
        n2 = ViewCompat.getLayoutDirection(view) == 1 ? 1 : 0;
        if (SwipeDismissBehavior.this.mSwipeDirection == 0) {
            if (n2 != 0) {
                n3 = this.mOriginalCapturedViewLeft - view.getWidth();
                n2 = this.mOriginalCapturedViewLeft;
            } else {
                n3 = this.mOriginalCapturedViewLeft;
                n2 = this.mOriginalCapturedViewLeft;
                n2 = view.getWidth() + n2;
            }
        } else if (SwipeDismissBehavior.this.mSwipeDirection == 1) {
            if (n2 != 0) {
                n3 = this.mOriginalCapturedViewLeft;
                n2 = this.mOriginalCapturedViewLeft;
                n2 = view.getWidth() + n2;
            } else {
                n3 = this.mOriginalCapturedViewLeft - view.getWidth();
                n2 = this.mOriginalCapturedViewLeft;
            }
        } else {
            n3 = this.mOriginalCapturedViewLeft - view.getWidth();
            n2 = this.mOriginalCapturedViewLeft;
            n2 = view.getWidth() + n2;
        }
        return android.support.design.widget.SwipeDismissBehavior.clamp(n3, n, n2);
    }

    @Override
    public int clampViewPositionVertical(View view, int n, int n2) {
        return view.getTop();
    }

    @Override
    public int getViewHorizontalDragRange(View view) {
        return view.getWidth();
    }

    @Override
    public void onViewCaptured(View view, int n) {
        this.mActivePointerId = n;
        this.mOriginalCapturedViewLeft = view.getLeft();
        if ((view = view.getParent()) != null) {
            view.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override
    public void onViewDragStateChanged(int n) {
        if (SwipeDismissBehavior.this.mListener != null) {
            SwipeDismissBehavior.this.mListener.onDragStateChanged(n);
        }
    }

    @Override
    public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
        float f = (float)this.mOriginalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.mAlphaStartSwipeDistance;
        float f2 = (float)this.mOriginalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.mAlphaEndSwipeDistance;
        float f3 = n;
        if (f3 <= f) {
            view.setAlpha(1.0f);
            return;
        }
        if (f3 >= f2) {
            view.setAlpha(0.0f);
            return;
        }
        view.setAlpha(android.support.design.widget.SwipeDismissBehavior.clamp(0.0f, 1.0f - android.support.design.widget.SwipeDismissBehavior.fraction(f, f2, f3), 1.0f));
    }

    @Override
    public void onViewReleased(View view, float f, float f2) {
        boolean bl;
        this.mActivePointerId = -1;
        int n = view.getWidth();
        if (this.shouldDismiss(view, f)) {
            n = view.getLeft() < this.mOriginalCapturedViewLeft ? this.mOriginalCapturedViewLeft - n : this.mOriginalCapturedViewLeft + n;
            bl = true;
        } else {
            n = this.mOriginalCapturedViewLeft;
            bl = false;
        }
        if (SwipeDismissBehavior.this.mViewDragHelper.settleCapturedViewAt(n, view.getTop())) {
            ViewCompat.postOnAnimation(view, new SwipeDismissBehavior.SettleRunnable(SwipeDismissBehavior.this, view, bl));
            return;
        }
        if (bl && SwipeDismissBehavior.this.mListener != null) {
            SwipeDismissBehavior.this.mListener.onDismiss(view);
        }
    }

    @Override
    public boolean tryCaptureView(View view, int n) {
        if (this.mActivePointerId == -1 && SwipeDismissBehavior.this.canSwipeDismissView(view)) {
            return true;
        }
        return false;
    }
}
