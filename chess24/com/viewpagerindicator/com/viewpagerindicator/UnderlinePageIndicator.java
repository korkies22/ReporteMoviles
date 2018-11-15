/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.ViewConfiguration
 */
package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.R;

public class UnderlinePageIndicator
extends View
implements PageIndicator {
    private static final int FADE_FRAME_MS = 30;
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private int mCurrentPage;
    private int mFadeBy;
    private int mFadeDelay;
    private int mFadeLength;
    private final Runnable mFadeRunnable = new Runnable(){

        @Override
        public void run() {
            if (!UnderlinePageIndicator.this.mFades) {
                return;
            }
            int n = Math.max(UnderlinePageIndicator.this.mPaint.getAlpha() - UnderlinePageIndicator.this.mFadeBy, 0);
            UnderlinePageIndicator.this.mPaint.setAlpha(n);
            UnderlinePageIndicator.this.invalidate();
            if (n > 0) {
                UnderlinePageIndicator.this.postDelayed((Runnable)this, 30L);
            }
        }
    };
    private boolean mFades;
    private boolean mIsDragging;
    private float mLastMotionX = -1.0f;
    private ViewPager.OnPageChangeListener mListener;
    private final Paint mPaint = new Paint(1);
    private float mPositionOffset;
    private int mScrollState;
    private int mTouchSlop;
    private ViewPager mViewPager;

    public UnderlinePageIndicator(Context context) {
        this(context, null);
    }

    public UnderlinePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiUnderlinePageIndicatorStyle);
    }

    public UnderlinePageIndicator(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        if (this.isInEditMode()) {
            return;
        }
        Resources resources = this.getResources();
        boolean bl = resources.getBoolean(R.bool.default_underline_indicator_fades);
        int n2 = resources.getInteger(R.integer.default_underline_indicator_fade_delay);
        int n3 = resources.getInteger(R.integer.default_underline_indicator_fade_length);
        int n4 = resources.getColor(R.color.default_underline_indicator_selected_color);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.UnderlinePageIndicator, n, 0);
        this.setFades(attributeSet.getBoolean(R.styleable.UnderlinePageIndicator_fades, bl));
        this.setSelectedColor(attributeSet.getColor(R.styleable.UnderlinePageIndicator_selectedColor, n4));
        this.setFadeDelay(attributeSet.getInteger(R.styleable.UnderlinePageIndicator_fadeDelay, n2));
        this.setFadeLength(attributeSet.getInteger(R.styleable.UnderlinePageIndicator_fadeLength, n3));
        resources = attributeSet.getDrawable(R.styleable.UnderlinePageIndicator_android_background);
        if (resources != null) {
            this.setBackgroundDrawable((Drawable)resources);
        }
        attributeSet.recycle();
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get((Context)context));
    }

    public int getFadeDelay() {
        return this.mFadeDelay;
    }

    public int getFadeLength() {
        return this.mFadeLength;
    }

    public boolean getFades() {
        return this.mFades;
    }

    public int getSelectedColor() {
        return this.mPaint.getColor();
    }

    @Override
    public void notifyDataSetChanged() {
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mViewPager == null) {
            return;
        }
        int n = this.mViewPager.getAdapter().getCount();
        if (n == 0) {
            return;
        }
        if (this.mCurrentPage >= n) {
            this.setCurrentItem(n - 1);
            return;
        }
        int n2 = this.getPaddingLeft();
        float f = (float)(this.getWidth() - n2 - this.getPaddingRight()) / (1.0f * (float)n);
        float f2 = (float)n2 + ((float)this.mCurrentPage + this.mPositionOffset) * f;
        canvas.drawRect(f2, (float)this.getPaddingTop(), f2 + f, (float)(this.getHeight() - this.getPaddingBottom()), this.mPaint);
    }

    @Override
    public void onPageScrollStateChanged(int n) {
        this.mScrollState = n;
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(n);
        }
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
        this.mCurrentPage = n;
        this.mPositionOffset = f;
        if (this.mFades) {
            if (n2 > 0) {
                this.removeCallbacks(this.mFadeRunnable);
                this.mPaint.setAlpha(255);
            } else if (this.mScrollState != 1) {
                this.postDelayed(this.mFadeRunnable, (long)this.mFadeDelay);
            }
        }
        this.invalidate();
        if (this.mListener != null) {
            this.mListener.onPageScrolled(n, f, n2);
        }
    }

    @Override
    public void onPageSelected(int n) {
        if (this.mScrollState == 0) {
            this.mCurrentPage = n;
            this.mPositionOffset = 0.0f;
            this.invalidate();
            this.mFadeRunnable.run();
        }
        if (this.mListener != null) {
            this.mListener.onPageSelected(n);
        }
    }

    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mCurrentPage = object.currentPage;
        this.requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPage = this.mCurrentPage;
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (super.onTouchEvent(motionEvent)) {
            return true;
        }
        ViewPager viewPager = this.mViewPager;
        int n = 0;
        if (viewPager != null) {
            if (this.mViewPager.getAdapter().getCount() == 0) {
                return false;
            }
            int n2 = motionEvent.getAction() & 255;
            switch (n2) {
                default: {
                    return true;
                }
                case 6: {
                    n2 = MotionEventCompat.getActionIndex(motionEvent);
                    if (MotionEventCompat.getPointerId(motionEvent, n2) == this.mActivePointerId) {
                        if (n2 == 0) {
                            n = 1;
                        }
                        this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n);
                    }
                    this.mLastMotionX = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                    return true;
                }
                case 5: {
                    n = MotionEventCompat.getActionIndex(motionEvent);
                    this.mLastMotionX = MotionEventCompat.getX(motionEvent, n);
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n);
                    return true;
                }
                case 2: {
                    float f = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                    float f2 = f - this.mLastMotionX;
                    if (!this.mIsDragging && Math.abs(f2) > (float)this.mTouchSlop) {
                        this.mIsDragging = true;
                    }
                    if (!this.mIsDragging) break;
                    this.mLastMotionX = f;
                    if (!this.mViewPager.isFakeDragging() && !this.mViewPager.beginFakeDrag()) break;
                    this.mViewPager.fakeDragBy(f2);
                    return true;
                }
                case 1: 
                case 3: {
                    if (!this.mIsDragging) {
                        n = this.mViewPager.getAdapter().getCount();
                        float f = this.getWidth();
                        float f3 = f / 2.0f;
                        if (this.mCurrentPage > 0 && motionEvent.getX() < f3 - (f /= 6.0f)) {
                            if (n2 != 3) {
                                this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                            }
                            return true;
                        }
                        if (this.mCurrentPage < n - 1 && motionEvent.getX() > f3 + f) {
                            if (n2 != 3) {
                                this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                            }
                            return true;
                        }
                    }
                    this.mIsDragging = false;
                    this.mActivePointerId = -1;
                    if (!this.mViewPager.isFakeDragging()) break;
                    this.mViewPager.endFakeDrag();
                    return true;
                }
                case 0: {
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    this.mLastMotionX = motionEvent.getX();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void setCurrentItem(int n) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mViewPager.setCurrentItem(n);
        this.mCurrentPage = n;
        this.invalidate();
    }

    public void setFadeDelay(int n) {
        this.mFadeDelay = n;
    }

    public void setFadeLength(int n) {
        this.mFadeLength = n;
        this.mFadeBy = 255 / (this.mFadeLength / 30);
    }

    public void setFades(boolean bl) {
        if (bl != this.mFades) {
            this.mFades = bl;
            if (bl) {
                this.post(this.mFadeRunnable);
                return;
            }
            this.removeCallbacks(this.mFadeRunnable);
            this.mPaint.setAlpha(255);
            this.invalidate();
        }
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setSelectedColor(int n) {
        this.mPaint.setColor(n);
        this.invalidate();
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        if (this.mViewPager == viewPager) {
            return;
        }
        if (this.mViewPager != null) {
            this.mViewPager.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager = viewPager;
        this.mViewPager.setOnPageChangeListener(this);
        this.invalidate();
        this.post(new Runnable(){

            @Override
            public void run() {
                if (UnderlinePageIndicator.this.mFades) {
                    UnderlinePageIndicator.this.post(UnderlinePageIndicator.this.mFadeRunnable);
                }
            }
        });
    }

    @Override
    public void setViewPager(ViewPager viewPager, int n) {
        this.setViewPager(viewPager);
        this.setCurrentItem(n);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int currentPage;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.currentPage = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.currentPage);
        }

    }

}
