/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package uk.co.jasonfry.android.tools.widget;

import android.view.MotionEvent;
import android.view.View;
import uk.co.jasonfry.android.tools.widget.BounceSwipeView;

private class BounceSwipeView.BounceViewOnTouchListener
implements View.OnTouchListener {
    private BounceSwipeView.BounceViewOnTouchListener() {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (BounceSwipeView.this.mOnTouchListener != null && BounceSwipeView.this.mOnTouchListener.onTouch(view, motionEvent)) {
            return true;
        }
        if (BounceSwipeView.this.mBounceEnabled) {
            switch (motionEvent.getAction()) {
                default: {
                    return false;
                }
                case 2: {
                    int n = (BounceSwipeView.this.getPageCount() - 1) * BounceSwipeView.this.getPageWidth() - BounceSwipeView.this.getPageWidth() % 2;
                    if (BounceSwipeView.this.getScrollX() == 0 && !BounceSwipeView.this.mAtEdge || BounceSwipeView.this.getScrollX() == n && !BounceSwipeView.this.mAtEdge) {
                        BounceSwipeView.this.mAtEdge = true;
                        BounceSwipeView.this.mAtEdgeStartPosition = motionEvent.getX();
                        BounceSwipeView.this.mAtEdgePreviousPosition = motionEvent.getX();
                        return false;
                    }
                    if (BounceSwipeView.this.getScrollX() == 0) {
                        BounceSwipeView.this.mAtEdgePreviousPosition = motionEvent.getX();
                        BounceSwipeView.this.mBouncingSide = true;
                        BounceSwipeView.super.setPadding((int)(BounceSwipeView.this.mAtEdgePreviousPosition - BounceSwipeView.this.mAtEdgeStartPosition) / 2, BounceSwipeView.this.getPaddingTop(), BounceSwipeView.this.getPaddingRight(), BounceSwipeView.this.getPaddingBottom());
                        return true;
                    }
                    if (BounceSwipeView.this.getScrollX() >= n) {
                        BounceSwipeView.this.mAtEdgePreviousPosition = motionEvent.getX();
                        BounceSwipeView.this.mBouncingSide = false;
                        int n2 = (int)(BounceSwipeView.this.mAtEdgeStartPosition - BounceSwipeView.this.mAtEdgePreviousPosition) / 2;
                        if (n2 >= BounceSwipeView.this.mPaddingRight) {
                            BounceSwipeView.super.setPadding(BounceSwipeView.this.getPaddingLeft(), BounceSwipeView.this.getPaddingTop(), n2, BounceSwipeView.this.getPaddingBottom());
                        } else {
                            BounceSwipeView.super.setPadding(BounceSwipeView.this.getPaddingLeft(), BounceSwipeView.this.getPaddingTop(), BounceSwipeView.this.mPaddingRight, BounceSwipeView.this.getPaddingBottom());
                        }
                        BounceSwipeView.this.scrollTo((int)((float)n + (BounceSwipeView.this.mAtEdgeStartPosition - BounceSwipeView.this.mAtEdgePreviousPosition) / 2.0f), BounceSwipeView.this.getScrollY());
                        return true;
                    }
                    BounceSwipeView.this.mAtEdge = false;
                    return false;
                }
                case 1: 
            }
            if (BounceSwipeView.this.mAtEdge) {
                BounceSwipeView.this.mAtEdge = false;
                BounceSwipeView.this.mAtEdgePreviousPosition = 0.0f;
                BounceSwipeView.this.mAtEdgeStartPosition = 0.0f;
                BounceSwipeView.this.doBounceBackEaseAnimation();
                return true;
            }
        }
        return false;
    }
}
