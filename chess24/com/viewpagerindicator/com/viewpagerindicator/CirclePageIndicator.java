/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
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

public class CirclePageIndicator
extends View
implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private boolean mCentered;
    private int mCurrentPage;
    private boolean mIsDragging;
    private float mLastMotionX = -1.0f;
    private ViewPager.OnPageChangeListener mListener;
    private int mOrientation;
    private float mPageOffset;
    private final Paint mPaintFill = new Paint(1);
    private final Paint mPaintPageFill = new Paint(1);
    private final Paint mPaintStroke = new Paint(1);
    private float mRadius;
    private int mScrollState;
    private boolean mSnap;
    private int mSnapPage;
    private int mTouchSlop;
    private ViewPager mViewPager;

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiCirclePageIndicatorStyle);
    }

    public CirclePageIndicator(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        if (this.isInEditMode()) {
            return;
        }
        Resources resources = this.getResources();
        int n2 = resources.getColor(R.color.default_circle_indicator_page_color);
        int n3 = resources.getColor(R.color.default_circle_indicator_fill_color);
        int n4 = resources.getInteger(R.integer.default_circle_indicator_orientation);
        int n5 = resources.getColor(R.color.default_circle_indicator_stroke_color);
        float f = resources.getDimension(R.dimen.default_circle_indicator_stroke_width);
        float f2 = resources.getDimension(R.dimen.default_circle_indicator_radius);
        boolean bl = resources.getBoolean(R.bool.default_circle_indicator_centered);
        boolean bl2 = resources.getBoolean(R.bool.default_circle_indicator_snap);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.CirclePageIndicator, n, 0);
        this.mCentered = attributeSet.getBoolean(R.styleable.CirclePageIndicator_centered, bl);
        this.mOrientation = attributeSet.getInt(R.styleable.CirclePageIndicator_android_orientation, n4);
        this.mPaintPageFill.setStyle(Paint.Style.FILL);
        this.mPaintPageFill.setColor(attributeSet.getColor(R.styleable.CirclePageIndicator_pageColor, n2));
        this.mPaintStroke.setStyle(Paint.Style.STROKE);
        this.mPaintStroke.setColor(attributeSet.getColor(R.styleable.CirclePageIndicator_strokeColor, n5));
        this.mPaintStroke.setStrokeWidth(attributeSet.getDimension(R.styleable.CirclePageIndicator_strokeWidth, f));
        this.mPaintFill.setStyle(Paint.Style.FILL);
        this.mPaintFill.setColor(attributeSet.getColor(R.styleable.CirclePageIndicator_fillColor, n3));
        this.mRadius = attributeSet.getDimension(R.styleable.CirclePageIndicator_radius, f2);
        this.mSnap = attributeSet.getBoolean(R.styleable.CirclePageIndicator_snap, bl2);
        resources = attributeSet.getDrawable(R.styleable.CirclePageIndicator_android_background);
        if (resources != null) {
            this.setBackgroundDrawable((Drawable)resources);
        }
        attributeSet.recycle();
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get((Context)context));
    }

    private int measureLong(int n) {
        int n2;
        int n3 = View.MeasureSpec.getMode((int)n);
        n = n2 = View.MeasureSpec.getSize((int)n);
        if (n3 != 1073741824) {
            if (this.mViewPager == null) {
                return n2;
            }
            n = this.mViewPager.getAdapter().getCount();
            n = (int)((float)(this.getPaddingLeft() + this.getPaddingRight()) + (float)(n * 2) * this.mRadius + (float)(n - 1) * this.mRadius + 1.0f);
            if (n3 == Integer.MIN_VALUE) {
                return Math.min(n, n2);
            }
        }
        return n;
    }

    private int measureShort(int n) {
        int n2 = View.MeasureSpec.getMode((int)n);
        n = View.MeasureSpec.getSize((int)n);
        if (n2 == 1073741824) {
            return n;
        }
        int n3 = (int)(2.0f * this.mRadius + (float)this.getPaddingTop() + (float)this.getPaddingBottom() + 1.0f);
        if (n2 == Integer.MIN_VALUE) {
            return Math.min(n3, n);
        }
        return n3;
    }

    public int getFillColor() {
        return this.mPaintFill.getColor();
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getPageColor() {
        return this.mPaintPageFill.getColor();
    }

    public float getRadius() {
        return this.mRadius;
    }

    public int getStrokeColor() {
        return this.mPaintStroke.getColor();
    }

    public float getStrokeWidth() {
        return this.mPaintStroke.getStrokeWidth();
    }

    public boolean isCentered() {
        return this.mCentered;
    }

    public boolean isSnap() {
        return this.mSnap;
    }

    @Override
    public void notifyDataSetChanged() {
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int n;
        int n2;
        float f;
        int n3;
        int n4;
        float f2;
        super.onDraw(canvas);
        if (this.mViewPager == null) {
            return;
        }
        int n5 = this.mViewPager.getAdapter().getCount();
        if (n5 == 0) {
            return;
        }
        if (this.mCurrentPage >= n5) {
            this.setCurrentItem(n5 - 1);
            return;
        }
        if (this.mOrientation == 0) {
            n = this.getWidth();
            n2 = this.getPaddingLeft();
            n4 = this.getPaddingRight();
            n3 = this.getPaddingTop();
        } else {
            n = this.getHeight();
            n2 = this.getPaddingTop();
            n4 = this.getPaddingBottom();
            n3 = this.getPaddingLeft();
        }
        float f3 = this.mRadius * 3.0f;
        float f4 = (float)n3 + this.mRadius;
        float f5 = f2 = (float)n2 + this.mRadius;
        if (this.mCentered) {
            f5 = f2 + ((float)(n - n2 - n4) / 2.0f - (float)n5 * f3 / 2.0f);
        }
        f2 = f = this.mRadius;
        if (this.mPaintStroke.getStrokeWidth() > 0.0f) {
            f2 = f - this.mPaintStroke.getStrokeWidth() / 2.0f;
        }
        for (n = 0; n < n5; ++n) {
            float f6;
            float f7 = (float)n * f3 + f5;
            if (this.mOrientation == 0) {
                f6 = f = f4;
            } else {
                f = f4;
                f6 = f7;
                f7 = f;
            }
            if (this.mPaintPageFill.getAlpha() > 0) {
                canvas.drawCircle(f7, f6, f2, this.mPaintPageFill);
            }
            if (f2 == this.mRadius) continue;
            canvas.drawCircle(f7, f6, this.mRadius, this.mPaintStroke);
        }
        n = this.mSnap ? this.mSnapPage : this.mCurrentPage;
        f2 = f = (float)n * f3;
        if (!this.mSnap) {
            f2 = f + this.mPageOffset * f3;
        }
        if (this.mOrientation == 0) {
            f5 = f2 + f5;
            f2 = f4;
        } else {
            f2 += f5;
            f5 = f4;
        }
        canvas.drawCircle(f5, f2, this.mRadius, this.mPaintFill);
    }

    protected void onMeasure(int n, int n2) {
        if (this.mOrientation == 0) {
            this.setMeasuredDimension(this.measureLong(n), this.measureShort(n2));
            return;
        }
        this.setMeasuredDimension(this.measureShort(n), this.measureLong(n2));
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
        this.mPageOffset = f;
        this.invalidate();
        if (this.mListener != null) {
            this.mListener.onPageScrolled(n, f, n2);
        }
    }

    @Override
    public void onPageSelected(int n) {
        if (this.mSnap || this.mScrollState == 0) {
            this.mCurrentPage = n;
            this.mSnapPage = n;
            this.invalidate();
        }
        if (this.mListener != null) {
            this.mListener.onPageSelected(n);
        }
    }

    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mCurrentPage = object.currentPage;
        this.mSnapPage = object.currentPage;
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

    public void setCentered(boolean bl) {
        this.mCentered = bl;
        this.invalidate();
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

    public void setFillColor(int n) {
        this.mPaintFill.setColor(n);
        this.invalidate();
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setOrientation(int n) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
            }
            case 0: 
            case 1: 
        }
        this.mOrientation = n;
        this.requestLayout();
    }

    public void setPageColor(int n) {
        this.mPaintPageFill.setColor(n);
        this.invalidate();
    }

    public void setRadius(float f) {
        this.mRadius = f;
        this.invalidate();
    }

    public void setSnap(boolean bl) {
        this.mSnap = bl;
        this.invalidate();
    }

    public void setStrokeColor(int n) {
        this.mPaintStroke.setColor(n);
        this.invalidate();
    }

    public void setStrokeWidth(float f) {
        this.mPaintStroke.setStrokeWidth(f);
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
