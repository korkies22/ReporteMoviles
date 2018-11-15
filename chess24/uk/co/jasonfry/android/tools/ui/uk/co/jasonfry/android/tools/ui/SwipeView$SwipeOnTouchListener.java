/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.widget.LinearLayout
 */
package uk.co.jasonfry.android.tools.ui;

import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import uk.co.jasonfry.android.tools.ui.SwipeView;

private class SwipeView.SwipeOnTouchListener
implements View.OnTouchListener {
    private int mDistanceX;
    private boolean mFirstMotionEvent = true;
    private int mPreviousDirection;
    private boolean mSendingDummyMotionEvent = false;

    private SwipeView.SwipeOnTouchListener() {
    }

    private boolean actionDown(MotionEvent motionEvent) {
        SwipeView.this.mMotionStartX = (int)motionEvent.getX();
        SwipeView.this.mMotionStartY = (int)motionEvent.getY();
        this.mFirstMotionEvent = false;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean actionMove(MotionEvent motionEvent) {
        int n = SwipeView.this.mMotionStartX - (int)motionEvent.getX();
        int n2 = -1;
        if (n < 0 ? this.mDistanceX + 4 <= n : this.mDistanceX - 4 <= n) {
            n2 = 1;
        }
        if (n2 != this.mPreviousDirection && !this.mFirstMotionEvent) {
            SwipeView.this.mMotionStartX = (int)motionEvent.getX();
            this.mDistanceX = SwipeView.this.mMotionStartX - (int)motionEvent.getX();
        } else {
            this.mDistanceX = n;
        }
        this.mPreviousDirection = n2;
        if (SwipeView.this.mJustInterceptedAndIgnored) {
            this.mSendingDummyMotionEvent = true;
            SwipeView.this.dispatchTouchEvent(MotionEvent.obtain((long)motionEvent.getDownTime(), (long)motionEvent.getEventTime(), (int)0, (float)SwipeView.this.mMotionStartX, (float)SwipeView.this.mMotionStartY, (float)motionEvent.getPressure(), (float)motionEvent.getSize(), (int)motionEvent.getMetaState(), (float)motionEvent.getXPrecision(), (float)motionEvent.getYPrecision(), (int)motionEvent.getDeviceId(), (int)motionEvent.getEdgeFlags()));
            SwipeView.this.mJustInterceptedAndIgnored = false;
            return true;
        }
        return false;
    }

    private boolean actionUp(MotionEvent motionEvent) {
        if (SwipeView.this.mPageWidth > 0) {
            float f = SwipeView.this.getScrollX();
            float f2 = SwipeView.this.mLinearLayout.getMeasuredWidth() / SwipeView.this.mPageWidth;
            f2 = this.mPreviousDirection == 1 ? (this.mDistanceX > DEFAULT_SWIPE_THRESHOLD ? ((float)SwipeView.this.mCurrentPage < f2 - 1.0f ? (float)((int)(f + 1.0f) * SwipeView.this.mPageWidth) : (float)(SwipeView.this.mCurrentPage * SwipeView.this.mPageWidth)) : ((float)Math.round(f) == f2 - 1.0f ? (float)((int)(f + 1.0f) * SwipeView.this.mPageWidth) : (float)(SwipeView.this.mCurrentPage * SwipeView.this.mPageWidth))) : (this.mDistanceX < - DEFAULT_SWIPE_THRESHOLD ? (float)((int)f * SwipeView.this.mPageWidth) : (Math.round(f) == 0 ? (float)((int)(f /= (float)SwipeView.this.mPageWidth) * SwipeView.this.mPageWidth) : (float)(SwipeView.this.mCurrentPage * SwipeView.this.mPageWidth)));
            SwipeView.this.smoothScrollToPage((int)f2 / SwipeView.this.mPageWidth);
            this.mFirstMotionEvent = true;
            this.mDistanceX = 0;
            SwipeView.this.mMostlyScrollingInX = false;
            SwipeView.this.mMostlyScrollingInY = false;
        }
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if ((SwipeView.this.mOnTouchListener != null && !SwipeView.this.mJustInterceptedAndIgnored || SwipeView.this.mOnTouchListener != null && this.mSendingDummyMotionEvent) && SwipeView.this.mOnTouchListener.onTouch(view, motionEvent)) {
            if (motionEvent.getAction() == 1) {
                this.actionUp(motionEvent);
            }
            return true;
        }
        if (this.mSendingDummyMotionEvent) {
            this.mSendingDummyMotionEvent = false;
            return false;
        }
        switch (motionEvent.getAction()) {
            default: {
                return false;
            }
            case 2: {
                return this.actionMove(motionEvent);
            }
            case 1: {
                return this.actionUp(motionEvent);
            }
            case 0: 
        }
        return this.actionDown(motionEvent);
    }
}
