/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.Display
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.WindowManager
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.LinearLayout
 */
package uk.co.jasonfry.android.tools.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import uk.co.jasonfry.android.tools.ui.PageControl;

public class SwipeView
extends HorizontalScrollView {
    private static int DEFAULT_SWIPE_THRESHOLD = 60;
    private int SCREEN_WIDTH;
    protected boolean mCallScrollToPageInOnLayout = false;
    private Context mContext;
    private int mCurrentPage = 0;
    private boolean mJustInterceptedAndIgnored = false;
    private LinearLayout mLinearLayout;
    private boolean mMostlyScrollingInX = false;
    private boolean mMostlyScrollingInY = false;
    private int mMotionStartX;
    private int mMotionStartY;
    private OnPageChangedListener mOnPageChangedListener = null;
    private View.OnTouchListener mOnTouchListener;
    private PageControl mPageControl = null;
    private int mPageWidth = 1;
    private SwipeOnTouchListener mSwipeOnTouchListener;

    public SwipeView(Context context) {
        super(context);
        this.mContext = context;
        this.initSwipeView();
    }

    public SwipeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.initSwipeView();
    }

    public SwipeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mContext = context;
        this.initSwipeView();
    }

    private void detectMostlyScrollingDirection(MotionEvent motionEvent) {
        if (!this.mMostlyScrollingInX && !this.mMostlyScrollingInY) {
            float f = Math.abs((float)this.mMotionStartX - motionEvent.getX());
            float f2 = Math.abs((float)this.mMotionStartY - motionEvent.getY());
            if (f2 > f + 5.0f) {
                this.mMostlyScrollingInY = true;
                return;
            }
            if (f > f2 + 5.0f) {
                this.mMostlyScrollingInX = true;
            }
        }
    }

    private void initSwipeView() {
        Log.i((String)"uk.co.jasonfry.android.tools.ui.SwipeView", (String)"Initialising SwipeView");
        this.mLinearLayout = new LinearLayout(this.mContext);
        this.mLinearLayout.setOrientation(0);
        super.addView((View)this.mLinearLayout, -1, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        this.setSmoothScrollingEnabled(true);
        this.setHorizontalFadingEdgeEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.mPageWidth = this.SCREEN_WIDTH = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
        this.mCurrentPage = 0;
        this.mSwipeOnTouchListener = new SwipeOnTouchListener();
        super.setOnTouchListener((View.OnTouchListener)this.mSwipeOnTouchListener);
    }

    private void scrollToPage(int n, boolean bl) {
        int n2;
        int n3 = this.mCurrentPage;
        if (n >= this.getPageCount() && this.getPageCount() > 0) {
            n2 = this.getPageCount() - 1;
        } else {
            n2 = n;
            if (n < 0) {
                n2 = 0;
            }
        }
        if (bl) {
            this.smoothScrollTo(this.mPageWidth * n2, 0);
        } else {
            this.scrollTo(this.mPageWidth * n2, 0);
        }
        this.mCurrentPage = n2;
        if (this.mOnPageChangedListener != null && n3 != n2) {
            this.mOnPageChangedListener.onPageChanged(n3, n2);
        }
        if (this.mPageControl != null && n3 != n2) {
            this.mPageControl.setCurrentPage(n2);
        }
        this.mCallScrollToPageInOnLayout ^= true;
    }

    public void addView(View view) {
        this.addView(view, -1);
    }

    public void addView(View view, int n) {
        ViewGroup.LayoutParams layoutParams;
        if (view.getLayoutParams() == null) {
            layoutParams = new FrameLayout.LayoutParams(this.mPageWidth, -1);
        } else {
            layoutParams = view.getLayoutParams();
            layoutParams.width = this.mPageWidth;
        }
        this.addView(view, n, layoutParams);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        this.requestLayout();
        this.invalidate();
        this.mLinearLayout.addView(view, n, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        layoutParams.width = this.mPageWidth;
        this.addView(view, -1, layoutParams);
    }

    public int calculatePageSize(ViewGroup.MarginLayoutParams marginLayoutParams) {
        return this.setPageWidth(marginLayoutParams.leftMargin + marginLayoutParams.width + marginLayoutParams.rightMargin);
    }

    public LinearLayout getChildContainer() {
        return this.mLinearLayout;
    }

    public int getCurrentPage() {
        return this.mCurrentPage;
    }

    public OnPageChangedListener getOnPageChangedListener() {
        return this.mOnPageChangedListener;
    }

    public PageControl getPageControl() {
        return this.mPageControl;
    }

    public int getPageCount() {
        return this.mLinearLayout.getChildCount();
    }

    public int getPageWidth() {
        return this.mPageWidth;
    }

    public int getSwipeThreshold() {
        return DEFAULT_SWIPE_THRESHOLD;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl = super.onInterceptTouchEvent(motionEvent);
        if (motionEvent.getAction() == 0) {
            this.mMotionStartX = (int)motionEvent.getX();
            this.mMotionStartY = (int)motionEvent.getY();
            if (!this.mJustInterceptedAndIgnored) {
                this.mMostlyScrollingInX = false;
                this.mMostlyScrollingInY = false;
            }
        } else if (motionEvent.getAction() == 2) {
            this.detectMostlyScrollingDirection(motionEvent);
        }
        if (this.mMostlyScrollingInY) {
            return false;
        }
        if (this.mMostlyScrollingInX) {
            this.mJustInterceptedAndIgnored = true;
            return true;
        }
        return bl;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (this.mCallScrollToPageInOnLayout) {
            this.scrollToPage(this.mCurrentPage);
            this.mCallScrollToPageInOnLayout = false;
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        return false;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return true;
    }

    public void removeAllViews() {
        super.removeAllViews();
        this.mLinearLayout.removeAllViews();
    }

    public void removeView(View view) {
        this.mLinearLayout.removeView(view);
    }

    public void removeViewAt(int n) {
        this.mLinearLayout.removeViewAt(n);
    }

    public void requestChildFocus(View view, View view2) {
        this.requestFocus();
    }

    public void scrollToPage(int n) {
        this.scrollToPage(n, false);
    }

    public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
        this.mOnPageChangedListener = onPageChangedListener;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    public void setPageControl(PageControl pageControl) {
        this.mPageControl = pageControl;
        pageControl.setPageCount(this.getPageCount());
        pageControl.setCurrentPage(this.mCurrentPage);
        pageControl.setOnPageControlClickListener(new PageControl.OnPageControlClickListener(){

            @Override
            public void goBackwards() {
                SwipeView.this.smoothScrollToPage(SwipeView.this.mCurrentPage - 1);
            }

            @Override
            public void goForwards() {
                SwipeView.this.smoothScrollToPage(SwipeView.this.mCurrentPage + 1);
            }

            @Override
            public void goToPage(int n) {
                SwipeView.this.smoothScrollToPage(n);
            }
        });
    }

    public int setPageWidth(int n) {
        this.mPageWidth = n;
        return (this.SCREEN_WIDTH - this.mPageWidth) / 2;
    }

    public void setSwipeThreshold(int n) {
        DEFAULT_SWIPE_THRESHOLD = n;
    }

    public void smoothScrollToPage(int n) {
        this.scrollToPage(n, true);
    }

    public static interface OnPageChangedListener {
        public void onPageChanged(int var1, int var2);
    }

    private class SwipeOnTouchListener
    implements View.OnTouchListener {
        private int mDistanceX;
        private boolean mFirstMotionEvent = true;
        private int mPreviousDirection;
        private boolean mSendingDummyMotionEvent = false;

        private SwipeOnTouchListener() {
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

}
