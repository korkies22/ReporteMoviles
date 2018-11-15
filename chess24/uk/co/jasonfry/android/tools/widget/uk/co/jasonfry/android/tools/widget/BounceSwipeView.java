/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.os.Handler
 *  android.os.Message
 *  android.preference.PreferenceManager
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package uk.co.jasonfry.android.tools.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import uk.co.jasonfry.android.tools.util.AnimationUtil;

public class BounceSwipeView
extends SwipeView {
    private static final int ANIMATION_DURATION = 120;
    private static final boolean BOUNCING_ON_LEFT = true;
    private static final boolean BOUNCING_ON_RIGHT = false;
    private static final int FRAME_DURATION = 30;
    private static final int NUMBER_OF_FRAMES = 4;
    private boolean mAtEdge = false;
    private float mAtEdgePreviousPosition;
    private float mAtEdgeStartPosition;
    private boolean mBounceEnabled = true;
    private boolean mBouncingSide;
    private Context mContext;
    private int mCurrentAnimationFrame;
    Handler mEaseAnimationFrameHandler;
    private View.OnTouchListener mOnTouchListener;
    private int mPaddingChange;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingStartValue;
    private SharedPreferences mSharedPreferences;

    public BounceSwipeView(Context context) {
        super(context);
        this.mContext = context;
        this.initBounceSwipeView();
    }

    public BounceSwipeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.initBounceSwipeView();
    }

    public BounceSwipeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mContext = context;
        this.initBounceSwipeView();
    }

    static /* synthetic */ int access$108(BounceSwipeView bounceSwipeView) {
        int n = bounceSwipeView.mCurrentAnimationFrame;
        bounceSwipeView.mCurrentAnimationFrame = n + 1;
        return n;
    }

    private void doBounceBackEaseAnimation() {
        if (this.mBouncingSide) {
            this.mPaddingChange = this.getPaddingLeft() - this.mPaddingLeft;
            this.mPaddingStartValue = this.getPaddingLeft();
        } else if (!this.mBouncingSide) {
            this.mPaddingChange = this.getPaddingRight() - this.mPaddingRight;
            this.mPaddingStartValue = this.getPaddingRight();
        }
        this.mCurrentAnimationFrame = 0;
        this.mEaseAnimationFrameHandler.removeMessages(0);
        this.mEaseAnimationFrameHandler.sendEmptyMessage(0);
    }

    private void initBounceSwipeView() {
        super.setOnTouchListener(new BounceViewOnTouchListener());
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext);
        this.mEaseAnimationFrameHandler = new Handler(){

            public void handleMessage(Message message) {
                int n = AnimationUtil.quadraticOutEase(BounceSwipeView.this.mCurrentAnimationFrame, BounceSwipeView.this.mPaddingStartValue, - BounceSwipeView.this.mPaddingChange, 4.0f);
                if (BounceSwipeView.this.mBouncingSide) {
                    BounceSwipeView.super.setPadding(n, BounceSwipeView.this.getPaddingTop(), BounceSwipeView.this.getPaddingRight(), BounceSwipeView.this.getPaddingBottom());
                } else if (!BounceSwipeView.this.mBouncingSide) {
                    BounceSwipeView.super.setPadding(BounceSwipeView.this.getPaddingLeft(), BounceSwipeView.this.getPaddingTop(), n, BounceSwipeView.this.getPaddingBottom());
                }
                BounceSwipeView.access$108(BounceSwipeView.this);
                if (BounceSwipeView.this.mCurrentAnimationFrame <= 4) {
                    BounceSwipeView.this.mEaseAnimationFrameHandler.sendEmptyMessageDelayed(0, 30L);
                }
            }
        };
    }

    public void doAtEdgeAnimation() {
        if (this.getCurrentPage() == 0) {
            this.mBouncingSide = true;
            BounceSwipeView.super.setPadding(this.getPaddingLeft() + 50, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        } else if (this.getCurrentPage() == this.getPageCount() - 1) {
            this.mBouncingSide = false;
            BounceSwipeView.super.setPadding(this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight() + 50, this.getPaddingBottom());
            this.scrollTo(this.getScrollX() + 50, this.getScrollY());
        }
        this.doBounceBackEaseAnimation();
    }

    public boolean getBounceEnabled() {
        return this.mBounceEnabled;
    }

    public void setBounceEnabled(boolean bl) {
        this.mBounceEnabled = bl;
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        this.mPaddingLeft = n;
        this.mPaddingRight = n3;
        super.setPadding(n, n2, n3, n4);
    }

    private class BounceViewOnTouchListener
    implements View.OnTouchListener {
        private BounceViewOnTouchListener() {
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

}
