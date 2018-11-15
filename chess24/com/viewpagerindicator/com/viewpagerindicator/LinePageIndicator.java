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

public class LinePageIndicator
extends View
implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = -1;
    private boolean mCentered;
    private int mCurrentPage;
    private float mGapWidth;
    private boolean mIsDragging;
    private float mLastMotionX = -1.0f;
    private float mLineWidth;
    private ViewPager.OnPageChangeListener mListener;
    private final Paint mPaintSelected = new Paint(1);
    private final Paint mPaintUnselected = new Paint(1);
    private int mTouchSlop;
    private ViewPager mViewPager;

    public LinePageIndicator(Context context) {
        this(context, null);
    }

    public LinePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiLinePageIndicatorStyle);
    }

    public LinePageIndicator(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        if (this.isInEditMode()) {
            return;
        }
        Resources resources = this.getResources();
        int n2 = resources.getColor(R.color.default_line_indicator_selected_color);
        int n3 = resources.getColor(R.color.default_line_indicator_unselected_color);
        float f = resources.getDimension(R.dimen.default_line_indicator_line_width);
        float f2 = resources.getDimension(R.dimen.default_line_indicator_gap_width);
        float f3 = resources.getDimension(R.dimen.default_line_indicator_stroke_width);
        boolean bl = resources.getBoolean(R.bool.default_line_indicator_centered);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.LinePageIndicator, n, 0);
        this.mCentered = attributeSet.getBoolean(R.styleable.LinePageIndicator_centered, bl);
        this.mLineWidth = attributeSet.getDimension(R.styleable.LinePageIndicator_lineWidth, f);
        this.mGapWidth = attributeSet.getDimension(R.styleable.LinePageIndicator_gapWidth, f2);
        this.setStrokeWidth(attributeSet.getDimension(R.styleable.LinePageIndicator_strokeWidth, f3));
        this.mPaintUnselected.setColor(attributeSet.getColor(R.styleable.LinePageIndicator_unselectedColor, n3));
        this.mPaintSelected.setColor(attributeSet.getColor(R.styleable.LinePageIndicator_selectedColor, n2));
        resources = attributeSet.getDrawable(R.styleable.LinePageIndicator_android_background);
        if (resources != null) {
            this.setBackgroundDrawable((Drawable)resources);
        }
        attributeSet.recycle();
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get((Context)context));
    }

    private int measureHeight(int n) {
        float f;
        block1 : {
            int n2;
            block0 : {
                n2 = View.MeasureSpec.getMode((int)n);
                n = View.MeasureSpec.getSize((int)n);
                if (n2 != 1073741824) break block0;
                f = n;
                break block1;
            }
            f = this.mPaintSelected.getStrokeWidth() + (float)this.getPaddingTop() + (float)this.getPaddingBottom();
            if (n2 != Integer.MIN_VALUE) break block1;
            f = Math.min(f, (float)n);
        }
        return (int)Math.ceil(f);
    }

    private int measureWidth(int n) {
        float f;
        int n2 = View.MeasureSpec.getMode((int)n);
        n = View.MeasureSpec.getSize((int)n);
        if (n2 != 1073741824 && this.mViewPager != null) {
            float f2;
            int n3 = this.mViewPager.getAdapter().getCount();
            f = f2 = (float)(this.getPaddingLeft() + this.getPaddingRight()) + (float)n3 * this.mLineWidth + (float)(n3 - 1) * this.mGapWidth;
            if (n2 == Integer.MIN_VALUE) {
                f = Math.min(f2, (float)n);
            }
        } else {
            f = n;
        }
        return (int)Math.ceil(f);
    }

    public float getGapWidth() {
        return this.mGapWidth;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public int getSelectedColor() {
        return this.mPaintSelected.getColor();
    }

    public float getStrokeWidth() {
        return this.mPaintSelected.getStrokeWidth();
    }

    public int getUnselectedColor() {
        return this.mPaintUnselected.getColor();
    }

    public boolean isCentered() {
        return this.mCentered;
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
        float f = this.mLineWidth + this.mGapWidth;
        float f2 = n;
        float f3 = this.mGapWidth;
        float f4 = this.getPaddingTop();
        float f5 = this.getPaddingLeft();
        float f6 = this.getPaddingRight();
        float f7 = f4 + ((float)this.getHeight() - f4 - (float)this.getPaddingBottom()) / 2.0f;
        f4 = f5;
        if (this.mCentered) {
            f4 = f5 + (((float)this.getWidth() - f5 - f6) / 2.0f - (f2 * f - f3) / 2.0f);
        }
        for (int i = 0; i < n; ++i) {
            f5 = f4 + (float)i * f;
            f2 = this.mLineWidth;
            Paint paint = i == this.mCurrentPage ? this.mPaintSelected : this.mPaintUnselected;
            canvas.drawLine(f5, f7, f5 + f2, f7, paint);
        }
    }

    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(this.measureWidth(n), this.measureHeight(n2));
    }

    @Override
    public void onPageScrollStateChanged(int n) {
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(n);
        }
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
        if (this.mListener != null) {
            this.mListener.onPageScrolled(n, f, n2);
        }
    }

    @Override
    public void onPageSelected(int n) {
        this.mCurrentPage = n;
        this.invalidate();
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

    public void setGapWidth(float f) {
        this.mGapWidth = f;
        this.invalidate();
    }

    public void setLineWidth(float f) {
        this.mLineWidth = f;
        this.invalidate();
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setSelectedColor(int n) {
        this.mPaintSelected.setColor(n);
        this.invalidate();
    }

    public void setStrokeWidth(float f) {
        this.mPaintSelected.setStrokeWidth(f);
        this.mPaintUnselected.setStrokeWidth(f);
        this.invalidate();
    }

    public void setUnselectedColor(int n) {
        this.mPaintUnselected.setColor(n);
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
