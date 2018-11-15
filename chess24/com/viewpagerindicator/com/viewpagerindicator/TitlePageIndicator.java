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
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.Typeface
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
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import java.util.ArrayList;

public class TitlePageIndicator
extends View
implements PageIndicator {
    private static final float BOLD_FADE_PERCENTAGE = 0.05f;
    private static final String EMPTY_TITLE = "";
    private static final int INVALID_POINTER = -1;
    private static final float SELECTION_FADE_PERCENTAGE = 0.25f;
    private int mActivePointerId = -1;
    private boolean mBoldText;
    private final Rect mBounds = new Rect();
    private OnCenterItemClickListener mCenterItemClickListener;
    private float mClipPadding;
    private int mColorSelected;
    private int mColorText;
    private int mCurrentPage = -1;
    private float mFooterIndicatorHeight;
    private IndicatorStyle mFooterIndicatorStyle;
    private float mFooterIndicatorUnderlinePadding;
    private float mFooterLineHeight;
    private float mFooterPadding;
    private boolean mIsDragging;
    private float mLastMotionX = -1.0f;
    private LinePosition mLinePosition;
    private ViewPager.OnPageChangeListener mListener;
    private float mPageOffset;
    private final Paint mPaintFooterIndicator = new Paint();
    private final Paint mPaintFooterLine = new Paint();
    private final Paint mPaintText = new Paint();
    private Path mPath = new Path();
    private int mScrollState;
    private float mTitlePadding;
    private float mTopPadding;
    private int mTouchSlop;
    private ViewPager mViewPager;

    public TitlePageIndicator(Context context) {
        this(context, null);
    }

    public TitlePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiTitlePageIndicatorStyle);
    }

    public TitlePageIndicator(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        if (this.isInEditMode()) {
            return;
        }
        Resources resources = this.getResources();
        int n2 = resources.getColor(R.color.default_title_indicator_footer_color);
        float f = resources.getDimension(R.dimen.default_title_indicator_footer_line_height);
        int n3 = resources.getInteger(R.integer.default_title_indicator_footer_indicator_style);
        float f2 = resources.getDimension(R.dimen.default_title_indicator_footer_indicator_height);
        float f3 = resources.getDimension(R.dimen.default_title_indicator_footer_indicator_underline_padding);
        float f4 = resources.getDimension(R.dimen.default_title_indicator_footer_padding);
        int n4 = resources.getInteger(R.integer.default_title_indicator_line_position);
        int n5 = resources.getColor(R.color.default_title_indicator_selected_color);
        boolean bl = resources.getBoolean(R.bool.default_title_indicator_selected_bold);
        int n6 = resources.getColor(R.color.default_title_indicator_text_color);
        float f5 = resources.getDimension(R.dimen.default_title_indicator_text_size);
        float f6 = resources.getDimension(R.dimen.default_title_indicator_title_padding);
        float f7 = resources.getDimension(R.dimen.default_title_indicator_clip_padding);
        float f8 = resources.getDimension(R.dimen.default_title_indicator_top_padding);
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.TitlePageIndicator, n, 0);
        this.mFooterLineHeight = attributeSet.getDimension(R.styleable.TitlePageIndicator_footerLineHeight, f);
        this.mFooterIndicatorStyle = IndicatorStyle.fromValue(attributeSet.getInteger(R.styleable.TitlePageIndicator_footerIndicatorStyle, n3));
        this.mFooterIndicatorHeight = attributeSet.getDimension(R.styleable.TitlePageIndicator_footerIndicatorHeight, f2);
        this.mFooterIndicatorUnderlinePadding = attributeSet.getDimension(R.styleable.TitlePageIndicator_footerIndicatorUnderlinePadding, f3);
        this.mFooterPadding = attributeSet.getDimension(R.styleable.TitlePageIndicator_footerPadding, f4);
        this.mLinePosition = LinePosition.fromValue(attributeSet.getInteger(R.styleable.TitlePageIndicator_linePosition, n4));
        this.mTopPadding = attributeSet.getDimension(R.styleable.TitlePageIndicator_topPadding, f8);
        this.mTitlePadding = attributeSet.getDimension(R.styleable.TitlePageIndicator_titlePadding, f6);
        this.mClipPadding = attributeSet.getDimension(R.styleable.TitlePageIndicator_clipPadding, f7);
        this.mColorSelected = attributeSet.getColor(R.styleable.TitlePageIndicator_selectedColor, n5);
        this.mColorText = attributeSet.getColor(R.styleable.TitlePageIndicator_android_textColor, n6);
        this.mBoldText = attributeSet.getBoolean(R.styleable.TitlePageIndicator_selectedBold, bl);
        f = attributeSet.getDimension(R.styleable.TitlePageIndicator_android_textSize, f5);
        n = attributeSet.getColor(R.styleable.TitlePageIndicator_footerColor, n2);
        this.mPaintText.setTextSize(f);
        this.mPaintText.setAntiAlias(true);
        this.mPaintFooterLine.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
        this.mPaintFooterLine.setColor(n);
        this.mPaintFooterIndicator.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaintFooterIndicator.setColor(n);
        resources = attributeSet.getDrawable(R.styleable.TitlePageIndicator_android_background);
        if (resources != null) {
            this.setBackgroundDrawable((Drawable)resources);
        }
        attributeSet.recycle();
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get((Context)context));
    }

    private Rect calcBounds(int n, Paint paint) {
        Rect rect = new Rect();
        CharSequence charSequence = this.getTitle(n);
        rect.right = (int)paint.measureText(charSequence, 0, charSequence.length());
        rect.bottom = (int)(paint.descent() - paint.ascent());
        return rect;
    }

    private ArrayList<Rect> calculateAllBounds(Paint paint) {
        ArrayList<Rect> arrayList = new ArrayList<Rect>();
        int n = this.mViewPager.getAdapter().getCount();
        int n2 = this.getWidth();
        int n3 = n2 / 2;
        for (int i = 0; i < n; ++i) {
            Rect rect = this.calcBounds(i, paint);
            int n4 = rect.right - rect.left;
            int n5 = rect.bottom;
            int n6 = rect.top;
            rect.left = (int)((float)n3 - (float)n4 / 2.0f + ((float)(i - this.mCurrentPage) - this.mPageOffset) * (float)n2);
            rect.right = rect.left + n4;
            rect.top = 0;
            rect.bottom = n5 - n6;
            arrayList.add(rect);
        }
        return arrayList;
    }

    private void clipViewOnTheLeft(Rect rect, float f, int n) {
        rect.left = (int)((float)n + this.mClipPadding);
        rect.right = (int)(this.mClipPadding + f);
    }

    private void clipViewOnTheRight(Rect rect, float f, int n) {
        rect.right = (int)((float)n - this.mClipPadding);
        rect.left = (int)((float)rect.right - f);
    }

    private CharSequence getTitle(int n) {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = this.mViewPager.getAdapter().getPageTitle(n);
        if (charSequence == null) {
            charSequence2 = EMPTY_TITLE;
        }
        return charSequence2;
    }

    public float getClipPadding() {
        return this.mClipPadding;
    }

    public int getFooterColor() {
        return this.mPaintFooterLine.getColor();
    }

    public float getFooterIndicatorHeight() {
        return this.mFooterIndicatorHeight;
    }

    public float getFooterIndicatorPadding() {
        return this.mFooterPadding;
    }

    public IndicatorStyle getFooterIndicatorStyle() {
        return this.mFooterIndicatorStyle;
    }

    public float getFooterLineHeight() {
        return this.mFooterLineHeight;
    }

    public LinePosition getLinePosition() {
        return this.mLinePosition;
    }

    public int getSelectedColor() {
        return this.mColorSelected;
    }

    public int getTextColor() {
        return this.mColorText;
    }

    public float getTextSize() {
        return this.mPaintText.getTextSize();
    }

    public float getTitlePadding() {
        return this.mTitlePadding;
    }

    public float getTopPadding() {
        return this.mTopPadding;
    }

    public Typeface getTypeface() {
        return this.mPaintText.getTypeface();
    }

    public boolean isSelectedBold() {
        return this.mBoldText;
    }

    @Override
    public void notifyDataSetChanged() {
        this.invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int n;
        int n2;
        Object object;
        float f;
        Rect rect;
        super.onDraw(canvas);
        if (this.mViewPager == null) {
            return;
        }
        int n3 = this.mViewPager.getAdapter().getCount();
        if (n3 == 0) {
            return;
        }
        if (this.mCurrentPage == -1 && this.mViewPager != null) {
            this.mCurrentPage = this.mViewPager.getCurrentItem();
        }
        if (this.mCurrentPage >= (n2 = (rect = this.calculateAllBounds(this.mPaintText)).size())) {
            this.setCurrentItem(n2 - 1);
            return;
        }
        float f2 = (float)this.getWidth() / 2.0f;
        int n4 = this.getLeft();
        float f3 = (float)n4 + this.mClipPadding;
        int n5 = this.getWidth();
        int n6 = this.getHeight();
        int n7 = n4 + n5;
        float f4 = (float)n7 - this.mClipPadding;
        int n8 = this.mCurrentPage;
        if ((double)this.mPageOffset <= 0.5) {
            f = this.mPageOffset;
        } else {
            ++n8;
            f = 1.0f - this.mPageOffset;
        }
        boolean bl = f <= 0.25f;
        boolean bl2 = f <= 0.05f;
        float f5 = (0.25f - f) / 0.25f;
        Rect rect2 = rect.get(this.mCurrentPage);
        f = rect2.right - rect2.left;
        if ((float)rect2.left < f3) {
            this.clipViewOnTheLeft(rect2, f, n4);
        }
        if ((float)rect2.right > f4) {
            this.clipViewOnTheRight(rect2, f, n7);
        }
        int n9 = n5;
        if (this.mCurrentPage > 0) {
            n = this.mCurrentPage - 1;
            f = f3;
            do {
                n9 = n5;
                if (n < 0) break;
                rect2 = rect.get(n);
                if ((float)rect2.left < f) {
                    n9 = rect2.right - rect2.left;
                    this.clipViewOnTheLeft(rect2, n9, n4);
                    object = rect.get(n + 1);
                    if ((float)rect2.right + this.mTitlePadding > (float)object.left) {
                        rect2.left = (int)((float)(object.left - n9) - this.mTitlePadding);
                        rect2.right = rect2.left + n9;
                    }
                }
                --n;
            } while (true);
        }
        n5 = n9;
        if (this.mCurrentPage < n3 - 1) {
            for (n9 = this.mCurrentPage + 1; n9 < n3; ++n9) {
                rect2 = rect.get(n9);
                if ((float)rect2.right <= f4) continue;
                n = rect2.right - rect2.left;
                this.clipViewOnTheRight(rect2, n, n7);
                object = rect.get(n9 - 1);
                if ((float)rect2.left - this.mTitlePadding >= (float)object.right) continue;
                rect2.left = (int)((float)object.right + this.mTitlePadding);
                rect2.right = rect2.left + n;
            }
        }
        int n10 = this.mColorText >>> 24;
        n9 = n7;
        for (n = 0; n < n3; ++n) {
            int n11;
            rect2 = rect.get(n);
            if ((rect2.left <= n4 || rect2.left >= n9) && (rect2.right <= n4 || rect2.right >= n9)) continue;
            n7 = n == n8 ? 1 : 0;
            object = this.getTitle(n);
            Paint paint = this.mPaintText;
            boolean bl3 = n7 != 0 && bl2 && this.mBoldText;
            paint.setFakeBoldText(bl3);
            this.mPaintText.setColor(this.mColorText);
            if (n7 != 0 && bl) {
                this.mPaintText.setAlpha(n10 - (int)((float)n10 * f5));
            }
            if (n < n2 - 1) {
                paint = rect.get(n + 1);
                if ((float)rect2.right + this.mTitlePadding > (float)paint.left) {
                    n11 = rect2.right - rect2.left;
                    rect2.left = (int)((float)(paint.left - n11) - this.mTitlePadding);
                    rect2.right = rect2.left + n11;
                }
            }
            n11 = object.length();
            f = rect2.left;
            f3 = rect2.bottom;
            canvas.drawText((CharSequence)object, 0, n11, f, this.mTopPadding + f3, this.mPaintText);
            if (n7 == 0 || !bl) continue;
            this.mPaintText.setColor(this.mColorSelected);
            this.mPaintText.setAlpha((int)((float)(this.mColorSelected >>> 24) * f5));
            n7 = object.length();
            f = rect2.left;
            f3 = rect2.bottom;
            canvas.drawText((CharSequence)object, 0, n7, f, this.mTopPadding + f3, this.mPaintText);
        }
        f3 = this.mFooterLineHeight;
        f = this.mFooterIndicatorHeight;
        if (this.mLinePosition == LinePosition.Top) {
            f3 = - f3;
            f = - f;
            n9 = 0;
        } else {
            n9 = n6;
        }
        this.mPath.reset();
        rect2 = this.mPath;
        f4 = n9;
        float f6 = f4 - f3 / 2.0f;
        rect2.moveTo(0.0f, f6);
        this.mPath.lineTo((float)n5, f6);
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mPaintFooterLine);
        f3 = f4 - f3;
        switch (.$SwitchMap$com$viewpagerindicator$TitlePageIndicator$IndicatorStyle[this.mFooterIndicatorStyle.ordinal()]) {
            default: {
                return;
            }
            case 2: {
                if (!bl) break;
                if (n8 >= n2) {
                    return;
                }
                rect = rect.get(n8);
                f2 = (float)rect.right + this.mFooterIndicatorUnderlinePadding;
                f4 = (float)rect.left - this.mFooterIndicatorUnderlinePadding;
                f = f3 - f;
                this.mPath.reset();
                this.mPath.moveTo(f4, f3);
                this.mPath.lineTo(f2, f3);
                this.mPath.lineTo(f2, f);
                this.mPath.lineTo(f4, f);
                this.mPath.close();
                this.mPaintFooterIndicator.setAlpha((int)(255.0f * f5));
                canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
                this.mPaintFooterIndicator.setAlpha(255);
                return;
            }
            case 1: {
                this.mPath.reset();
                this.mPath.moveTo(f2, f3 - f);
                this.mPath.lineTo(f2 + f, f3);
                this.mPath.lineTo(f2 - f, f3);
                this.mPath.close();
                canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
            }
        }
    }

    protected void onMeasure(int n, int n2) {
        float f;
        n = View.MeasureSpec.getSize((int)n);
        if (View.MeasureSpec.getMode((int)n2) == 1073741824) {
            f = View.MeasureSpec.getSize((int)n2);
        } else {
            float f2;
            this.mBounds.setEmpty();
            this.mBounds.bottom = (int)(this.mPaintText.descent() - this.mPaintText.ascent());
            f = f2 = (float)(this.mBounds.bottom - this.mBounds.top) + this.mFooterLineHeight + this.mFooterPadding + this.mTopPadding;
            if (this.mFooterIndicatorStyle != IndicatorStyle.None) {
                f = f2 + this.mFooterIndicatorHeight;
            }
        }
        this.setMeasuredDimension(n, (int)f);
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
        if (this.mScrollState == 0) {
            this.mCurrentPage = n;
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
                        float f4 = motionEvent.getX();
                        if (f4 < f3 - (f /= 6.0f)) {
                            if (this.mCurrentPage > 0) {
                                if (n2 != 3) {
                                    this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                                }
                                return true;
                            }
                        } else if (f4 > f3 + f) {
                            if (this.mCurrentPage < n - 1) {
                                if (n2 != 3) {
                                    this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                                }
                                return true;
                            }
                        } else if (this.mCenterItemClickListener != null && n2 != 3) {
                            this.mCenterItemClickListener.onCenterItemClick(this.mCurrentPage);
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

    public void setClipPadding(float f) {
        this.mClipPadding = f;
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

    public void setFooterColor(int n) {
        this.mPaintFooterLine.setColor(n);
        this.mPaintFooterIndicator.setColor(n);
        this.invalidate();
    }

    public void setFooterIndicatorHeight(float f) {
        this.mFooterIndicatorHeight = f;
        this.invalidate();
    }

    public void setFooterIndicatorPadding(float f) {
        this.mFooterPadding = f;
        this.invalidate();
    }

    public void setFooterIndicatorStyle(IndicatorStyle indicatorStyle) {
        this.mFooterIndicatorStyle = indicatorStyle;
        this.invalidate();
    }

    public void setFooterLineHeight(float f) {
        this.mFooterLineHeight = f;
        this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
        this.invalidate();
    }

    public void setLinePosition(LinePosition linePosition) {
        this.mLinePosition = linePosition;
        this.invalidate();
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener onCenterItemClickListener) {
        this.mCenterItemClickListener = onCenterItemClickListener;
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setSelectedBold(boolean bl) {
        this.mBoldText = bl;
        this.invalidate();
    }

    public void setSelectedColor(int n) {
        this.mColorSelected = n;
        this.invalidate();
    }

    public void setTextColor(int n) {
        this.mPaintText.setColor(n);
        this.mColorText = n;
        this.invalidate();
    }

    public void setTextSize(float f) {
        this.mPaintText.setTextSize(f);
        this.invalidate();
    }

    public void setTitlePadding(float f) {
        this.mTitlePadding = f;
        this.invalidate();
    }

    public void setTopPadding(float f) {
        this.mTopPadding = f;
        this.invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.mPaintText.setTypeface(typeface);
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

    public static enum IndicatorStyle {
        None(0),
        Triangle(1),
        Underline(2);
        
        public final int value;

        private IndicatorStyle(int n2) {
            this.value = n2;
        }

        public static IndicatorStyle fromValue(int n) {
            for (IndicatorStyle indicatorStyle : IndicatorStyle.values()) {
                if (indicatorStyle.value != n) continue;
                return indicatorStyle;
            }
            return null;
        }
    }

    public static enum LinePosition {
        Bottom(0),
        Top(1);
        
        public final int value;

        private LinePosition(int n2) {
            this.value = n2;
        }

        public static LinePosition fromValue(int n) {
            for (LinePosition linePosition : LinePosition.values()) {
                if (linePosition.value != n) continue;
                return linePosition;
            }
            return null;
        }
    }

    public static interface OnCenterItemClickListener {
        public void onCenterItemClick(int var1);
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
