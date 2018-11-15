/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat
extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned = true;
    private int mBaselineAlignedChildIndex = -1;
    private int mBaselineChildTop = 0;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity = 8388659;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(Context object, AttributeSet attributeSet, int n) {
        boolean bl;
        super((Context)object, attributeSet, n);
        object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, R.styleable.LinearLayoutCompat, n, 0);
        n = object.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (n >= 0) {
            this.setOrientation(n);
        }
        if ((n = object.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1)) >= 0) {
            this.setGravity(n);
        }
        if (!(bl = object.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true))) {
            this.setBaselineAligned(bl);
        }
        this.mWeightSum = object.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = object.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = object.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        this.setDividerDrawable(object.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = object.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = object.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        object.recycle();
    }

    private void forceUniformHeight(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)1073741824);
        for (int i = 0; i < n; ++i) {
            View view = this.getVirtualChildAt(i);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.height != -1) continue;
            int n4 = layoutParams.width;
            layoutParams.width = view.getMeasuredWidth();
            this.measureChildWithMargins(view, n2, 0, n3, 0);
            layoutParams.width = n4;
        }
    }

    private void forceUniformWidth(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824);
        for (int i = 0; i < n; ++i) {
            View view = this.getVirtualChildAt(i);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.width != -1) continue;
            int n4 = layoutParams.height;
            layoutParams.height = view.getMeasuredHeight();
            this.measureChildWithMargins(view, n3, 0, n2, 0);
            layoutParams.height = n4;
        }
    }

    private void setChildFrame(View view, int n, int n2, int n3, int n4) {
        view.layout(n, n2, n3 + n, n4 + n2);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    void drawDividersHorizontal(Canvas canvas) {
        View view;
        LayoutParams layoutParams;
        int n;
        int n2 = this.getVirtualChildCount();
        boolean bl = ViewUtils.isLayoutRtl((View)this);
        for (n = 0; n < n2; ++n) {
            view = this.getVirtualChildAt(n);
            if (view == null || view.getVisibility() == 8 || !this.hasDividerBeforeChildAt(n)) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            int n3 = bl ? view.getRight() + layoutParams.rightMargin : view.getLeft() - layoutParams.leftMargin - this.mDividerWidth;
            this.drawVerticalDivider(canvas, n3);
        }
        if (this.hasDividerBeforeChildAt(n2)) {
            view = this.getVirtualChildAt(n2 - 1);
            if (view == null) {
                n = bl ? this.getPaddingLeft() : this.getWidth() - this.getPaddingRight() - this.mDividerWidth;
            } else {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n = bl ? view.getLeft() - layoutParams.leftMargin - this.mDividerWidth : view.getRight() + layoutParams.rightMargin;
            }
            this.drawVerticalDivider(canvas, n);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        View view;
        int n;
        LayoutParams layoutParams;
        int n2 = this.getVirtualChildCount();
        for (n = 0; n < n2; ++n) {
            view = this.getVirtualChildAt(n);
            if (view == null || view.getVisibility() == 8 || !this.hasDividerBeforeChildAt(n)) continue;
            layoutParams = (LayoutParams)view.getLayoutParams();
            this.drawHorizontalDivider(canvas, view.getTop() - layoutParams.topMargin - this.mDividerHeight);
        }
        if (this.hasDividerBeforeChildAt(n2)) {
            view = this.getVirtualChildAt(n2 - 1);
            if (view == null) {
                n = this.getHeight() - this.getPaddingBottom() - this.mDividerHeight;
            } else {
                layoutParams = (LayoutParams)view.getLayoutParams();
                n = view.getBottom() + layoutParams.bottomMargin;
            }
            this.drawHorizontalDivider(canvas, n);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(this.getPaddingLeft() + this.mDividerPadding, n, this.getWidth() - this.getPaddingRight() - this.mDividerPadding, this.mDividerHeight + n);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(n, this.getPaddingTop() + this.mDividerPadding, this.mDividerWidth + n, this.getHeight() - this.getPaddingBottom() - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getBaseline() {
        int n;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (this.getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View view = this.getChildAt(this.mBaselineAlignedChildIndex);
        int n2 = view.getBaseline();
        if (n2 == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int n3 = n = this.mBaselineChildTop;
        if (this.mOrientation == 1) {
            int n4 = this.mGravity & 112;
            n3 = n;
            if (n4 != 48) {
                n3 = n4 != 16 ? (n4 != 80 ? n : this.getBottom() - this.getTop() - this.getPaddingBottom() - this.mTotalLength) : n + (this.getBottom() - this.getTop() - this.getPaddingTop() - this.getPaddingBottom() - this.mTotalLength) / 2;
            }
        }
        return n3 + ((LayoutParams)view.getLayoutParams()).topMargin + n2;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    int getChildrenSkipCount(View view, int n) {
        return 0;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public int getGravity() {
        return this.mGravity;
    }

    int getLocationOffset(View view) {
        return 0;
    }

    int getNextLocationOffset(View view) {
        return 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    View getVirtualChildAt(int n) {
        return this.getChildAt(n);
    }

    int getVirtualChildCount() {
        return this.getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY})
    protected boolean hasDividerBeforeChildAt(int n) {
        boolean bl = false;
        boolean bl2 = false;
        if (n == 0) {
            if ((this.mShowDividers & 1) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        if (n == this.getChildCount()) {
            bl2 = bl;
            if ((this.mShowDividers & 4) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        if ((this.mShowDividers & 2) != 0) {
            --n;
            while (n >= 0) {
                if (this.getChildAt(n).getVisibility() != 8) {
                    return true;
                }
                --n;
            }
            return false;
        }
        return false;
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    void layoutHorizontal(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        boolean bl = ViewUtils.isLayoutRtl((View)this);
        int n7 = this.getPaddingTop();
        int n8 = n4 - n2;
        int n9 = this.getPaddingBottom();
        int n10 = this.getPaddingBottom();
        int n11 = this.getVirtualChildCount();
        n4 = this.mGravity;
        n2 = this.mGravity & 112;
        boolean bl2 = this.mBaselineAligned;
        int[] arrn = this.mMaxAscent;
        int[] arrn2 = this.mMaxDescent;
        n4 = GravityCompat.getAbsoluteGravity(n4 & 8388615, ViewCompat.getLayoutDirection((View)this));
        boolean bl3 = true;
        if (n4 != 1) {
            n = n4 != 5 ? this.getPaddingLeft() : this.getPaddingLeft() + n3 - n - this.mTotalLength;
        } else {
            n4 = this.getPaddingLeft();
            n = (n3 - n - this.mTotalLength) / 2 + n4;
        }
        if (bl) {
            n6 = n11 - 1;
            n5 = -1;
        } else {
            n6 = 0;
            n5 = 1;
        }
        n3 = n7;
        for (n4 = 0; n4 < n11; ++n4) {
            int n12 = n6 + n5 * n4;
            View view = this.getVirtualChildAt(n12);
            if (view == null) {
                n += this.measureNullChild(n12);
                continue;
            }
            if (view.getVisibility() != 8) {
                int n13;
                int n14 = view.getMeasuredWidth();
                int n15 = view.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                int n16 = bl2 && layoutParams.height != -1 ? view.getBaseline() : -1;
                int n17 = n13 = layoutParams.gravity;
                if (n13 < 0) {
                    n17 = n2;
                }
                if ((n17 &= 112) != 16) {
                    if (n17 != 48) {
                        if (n17 != 80) {
                            n17 = n3;
                        } else {
                            n17 = n13 = n8 - n9 - n15 - layoutParams.bottomMargin;
                            if (n16 != -1) {
                                n17 = view.getMeasuredHeight();
                                n17 = n13 - (arrn2[2] - (n17 - n16));
                            }
                        }
                    } else {
                        n17 = layoutParams.topMargin + n3;
                        if (n16 != -1) {
                            n17 += arrn[1] - n16;
                        }
                    }
                } else {
                    n17 = (n8 - n7 - n10 - n15) / 2 + n3 + layoutParams.topMargin - layoutParams.bottomMargin;
                }
                bl3 = true;
                n16 = n;
                if (this.hasDividerBeforeChildAt(n12)) {
                    n16 = n + this.mDividerWidth;
                }
                n = layoutParams.leftMargin + n16;
                this.setChildFrame(view, n + this.getLocationOffset(view), n17, n14, n15);
                n17 = layoutParams.rightMargin;
                n16 = this.getNextLocationOffset(view);
                n4 += this.getChildrenSkipCount(view, n12);
                n += n14 + n17 + n16;
                continue;
            }
            bl3 = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void layoutVertical(int n, int n2, int n3, int n4) {
        int n5 = this.getPaddingLeft();
        int n6 = n3 - n;
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingRight();
        int n9 = this.getVirtualChildCount();
        n = this.mGravity & 112;
        int n10 = this.mGravity;
        if (n != 16) {
            n = n != 80 ? this.getPaddingTop() : this.getPaddingTop() + n4 - n2 - this.mTotalLength;
        } else {
            n = this.getPaddingTop();
            n = (n4 - n2 - this.mTotalLength) / 2 + n;
        }
        n2 = 0;
        do {
            block11 : {
                View view;
                int n11;
                LayoutParams layoutParams;
                int n12;
                block10 : {
                    block8 : {
                        block9 : {
                            block7 : {
                                block6 : {
                                    if (n2 >= n9) {
                                        return;
                                    }
                                    view = this.getVirtualChildAt(n2);
                                    if (view != null) break block6;
                                    n3 = n + this.measureNullChild(n2);
                                    break block7;
                                }
                                n3 = n;
                                if (view.getVisibility() == 8) break block7;
                                n12 = view.getMeasuredWidth();
                                n11 = view.getMeasuredHeight();
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                n3 = n4 = layoutParams.gravity;
                                if (n4 < 0) {
                                    n3 = n10 & 8388615;
                                }
                                if ((n3 = GravityCompat.getAbsoluteGravity(n3, ViewCompat.getLayoutDirection((View)this)) & 7) == 1) break block8;
                                if (n3 == 5) break block9;
                                n3 = layoutParams.leftMargin + n5;
                                break block10;
                            }
                            n = n3;
                            break block11;
                        }
                        n3 = n6 - n7 - n12 - layoutParams.rightMargin;
                        break block10;
                    }
                    n3 = (n6 - n5 - n8 - n12) / 2 + n5 + layoutParams.leftMargin - layoutParams.rightMargin;
                }
                n4 = n;
                if (this.hasDividerBeforeChildAt(n2)) {
                    n4 = n + this.mDividerHeight;
                }
                n = n4 + layoutParams.topMargin;
                this.setChildFrame(view, n3, n + this.getLocationOffset(view), n12, n11);
                n3 = layoutParams.bottomMargin;
                n4 = this.getNextLocationOffset(view);
                n2 += this.getChildrenSkipCount(view, n2);
                n += n11 + n3 + n4;
            }
            ++n2;
        } while (true);
    }

    void measureChildBeforeLayout(View view, int n, int n2, int n3, int n4, int n5) {
        this.measureChildWithMargins(view, n2, n3, n4, n5);
    }

    /*
     * Enabled aggressive block sorting
     */
    void measureHorizontal(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        block58 : {
            int n11;
            int n12;
            block60 : {
                View view;
                int[] arrn;
                int n13;
                block59 : {
                    int n14;
                    int n15;
                    int n16;
                    int n17;
                    boolean bl;
                    View view2;
                    boolean bl2;
                    float f;
                    block57 : {
                        block55 : {
                            block56 : {
                                block54 : {
                                    boolean bl3;
                                    block53 : {
                                        block52 : {
                                            this.mTotalLength = 0;
                                            n4 = this.getVirtualChildCount();
                                            n17 = View.MeasureSpec.getMode((int)n);
                                            n8 = View.MeasureSpec.getMode((int)n2);
                                            if (this.mMaxAscent == null || this.mMaxDescent == null) {
                                                this.mMaxAscent = new int[4];
                                                this.mMaxDescent = new int[4];
                                            }
                                            arrn = this.mMaxAscent;
                                            view = this.mMaxDescent;
                                            arrn[3] = -1;
                                            arrn[2] = -1;
                                            arrn[1] = -1;
                                            arrn[0] = -1;
                                            view[3] = -1;
                                            view[2] = -1;
                                            view[1] = -1;
                                            view[0] = -1;
                                            bl = this.mBaselineAligned;
                                            bl3 = this.mUseLargestChild;
                                            n14 = 1073741824;
                                            bl2 = n17 == 1073741824;
                                            n7 = n10 = (n13 = (n9 = (n15 = (n5 = (n6 = (n12 = 0))))));
                                            n11 = 1;
                                            f = 0.0f;
                                            while (n12 < n4) {
                                                block47 : {
                                                    LayoutParams layoutParams;
                                                    int n18;
                                                    block51 : {
                                                        block50 : {
                                                            block48 : {
                                                                block49 : {
                                                                    block46 : {
                                                                        block45 : {
                                                                            block44 : {
                                                                                view2 = this.getVirtualChildAt(n12);
                                                                                if (view2 != null) break block44;
                                                                                this.mTotalLength += this.measureNullChild(n12);
                                                                                break block45;
                                                                            }
                                                                            if (view2.getVisibility() != 8) break block46;
                                                                            n12 += this.getChildrenSkipCount(view2, n12);
                                                                        }
                                                                        n16 = n14;
                                                                        n14 = n12;
                                                                        n12 = n16;
                                                                        break block47;
                                                                    }
                                                                    if (this.hasDividerBeforeChildAt(n12)) {
                                                                        this.mTotalLength += this.mDividerWidth;
                                                                    }
                                                                    layoutParams = (LayoutParams)view2.getLayoutParams();
                                                                    f += layoutParams.weight;
                                                                    if (n17 != n14 || layoutParams.width != 0 || layoutParams.weight <= 0.0f) break block48;
                                                                    if (bl2) {
                                                                        this.mTotalLength += layoutParams.leftMargin + layoutParams.rightMargin;
                                                                    } else {
                                                                        n14 = this.mTotalLength;
                                                                        this.mTotalLength = Math.max(n14, layoutParams.leftMargin + n14 + layoutParams.rightMargin);
                                                                    }
                                                                    if (!bl) break block49;
                                                                    n14 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                                                                    view2.measure(n14, n14);
                                                                    n14 = n6;
                                                                    break block50;
                                                                }
                                                                n15 = 1;
                                                                break block51;
                                                            }
                                                            if (layoutParams.width == 0 && layoutParams.weight > 0.0f) {
                                                                layoutParams.width = -2;
                                                                n14 = 0;
                                                            } else {
                                                                n14 = Integer.MIN_VALUE;
                                                            }
                                                            n16 = f == 0.0f ? this.mTotalLength : 0;
                                                            View view3 = view2;
                                                            this.measureChildBeforeLayout(view2, n12, n, n16, n2, 0);
                                                            if (n14 != Integer.MIN_VALUE) {
                                                                layoutParams.width = n14;
                                                            }
                                                            n16 = view3.getMeasuredWidth();
                                                            if (bl2) {
                                                                this.mTotalLength += layoutParams.leftMargin + n16 + layoutParams.rightMargin + this.getNextLocationOffset(view3);
                                                            } else {
                                                                n14 = this.mTotalLength;
                                                                this.mTotalLength = Math.max(n14, n14 + n16 + layoutParams.leftMargin + layoutParams.rightMargin + this.getNextLocationOffset(view3));
                                                            }
                                                            n14 = n6;
                                                            if (bl3) {
                                                                n14 = Math.max(n16, n6);
                                                            }
                                                        }
                                                        n6 = n14;
                                                    }
                                                    int n19 = n12;
                                                    n3 = 1073741824;
                                                    if (n8 != 1073741824 && layoutParams.height == -1) {
                                                        n7 = n12 = 1;
                                                    } else {
                                                        n12 = 0;
                                                    }
                                                    n14 = layoutParams.topMargin + layoutParams.bottomMargin;
                                                    n16 = view2.getMeasuredHeight() + n14;
                                                    int n20 = View.combineMeasuredStates((int)n10, (int)view2.getMeasuredState());
                                                    if (bl && (n18 = view2.getBaseline()) != -1) {
                                                        n10 = layoutParams.gravity < 0 ? this.mGravity : layoutParams.gravity;
                                                        n10 = ((n10 & 112) >> 4 & -2) >> 1;
                                                        arrn[n10] = Math.max(arrn[n10], n18);
                                                        view[n10] = (View)Math.max((int)view[n10], n16 - n18);
                                                    }
                                                    n5 = Math.max(n5, n16);
                                                    n11 = n11 != 0 && layoutParams.height == -1 ? 1 : 0;
                                                    if (layoutParams.weight > 0.0f) {
                                                        if (n12 == 0) {
                                                            n14 = n16;
                                                        }
                                                        n12 = Math.max(n13, n14);
                                                    } else {
                                                        if (n12 != 0) {
                                                            n16 = n14;
                                                        }
                                                        n9 = Math.max(n9, n16);
                                                        n12 = n13;
                                                    }
                                                    n14 = this.getChildrenSkipCount(view2, n19) + n19;
                                                    n10 = n20;
                                                    n13 = n12;
                                                    n12 = n3;
                                                }
                                                n16 = n12;
                                                n12 = n14 + 1;
                                                n14 = n16;
                                            }
                                            n12 = n5;
                                            if (this.mTotalLength > 0 && this.hasDividerBeforeChildAt(n4)) {
                                                this.mTotalLength += this.mDividerWidth;
                                            }
                                            if (arrn[1] != -1 || arrn[0] != -1 || arrn[2] != -1) break block52;
                                            n5 = n12;
                                            if (arrn[3] == -1) break block53;
                                        }
                                        n5 = Math.max(n12, Math.max(arrn[3], Math.max(arrn[0], Math.max(arrn[1], arrn[2]))) + Math.max(view[3], Math.max(view[0], Math.max(view[1], view[2]))));
                                    }
                                    if (bl3 && (n17 == Integer.MIN_VALUE || n17 == 0)) {
                                        this.mTotalLength = 0;
                                        for (n12 = 0; n12 < n4; ++n12) {
                                            view2 = this.getVirtualChildAt(n12);
                                            if (view2 == null) {
                                                this.mTotalLength += this.measureNullChild(n12);
                                                continue;
                                            }
                                            if (view2.getVisibility() == 8) {
                                                n12 += this.getChildrenSkipCount(view2, n12);
                                                continue;
                                            }
                                            LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
                                            if (bl2) {
                                                this.mTotalLength += layoutParams.leftMargin + n6 + layoutParams.rightMargin + this.getNextLocationOffset(view2);
                                                continue;
                                            }
                                            n14 = this.mTotalLength;
                                            this.mTotalLength = Math.max(n14, n14 + n6 + layoutParams.leftMargin + layoutParams.rightMargin + this.getNextLocationOffset(view2));
                                        }
                                    }
                                    this.mTotalLength += this.getPaddingLeft() + this.getPaddingRight();
                                    n3 = View.resolveSizeAndState((int)Math.max(this.mTotalLength, this.getSuggestedMinimumWidth()), (int)n, (int)0);
                                    n14 = (16777215 & n3) - this.mTotalLength;
                                    if (n15 != 0 || n14 != 0 && f > 0.0f) break block54;
                                    n12 = Math.max(n9, n13);
                                    if (!bl3 || n17 == 1073741824) break block55;
                                    break block56;
                                }
                                if (this.mWeightSum > 0.0f) {
                                    f = this.mWeightSum;
                                }
                                arrn[3] = -1;
                                arrn[2] = -1;
                                arrn[1] = -1;
                                arrn[0] = -1;
                                view[3] = (View)-1;
                                view[2] = (View)-1;
                                view[1] = (View)-1;
                                view[0] = (View)-1;
                                this.mTotalLength = 0;
                                n13 = -1;
                                n5 = 0;
                                n6 = n11;
                                n12 = n9;
                                n11 = n10;
                                n9 = n14;
                                break block57;
                            }
                            for (n9 = 0; n9 < n4; ++n9) {
                                view = this.getVirtualChildAt(n9);
                                if (view == null || view.getVisibility() == 8 || ((LayoutParams)view.getLayoutParams()).weight <= 0.0f) continue;
                                view.measure(View.MeasureSpec.makeMeasureSpec((int)n6, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824));
                            }
                        }
                        n9 = n12;
                        n6 = n11;
                        break block58;
                    }
                    for (n10 = n5; n10 < n4; ++n10) {
                        view2 = this.getVirtualChildAt(n10);
                        if (view2 == null || view2.getVisibility() == 8) continue;
                        LayoutParams layoutParams = (LayoutParams)view2.getLayoutParams();
                        float f2 = layoutParams.weight;
                        if (f2 > 0.0f) {
                            n15 = (int)((float)n9 * f2 / f);
                            n16 = LinearLayoutCompat.getChildMeasureSpec((int)n2, (int)(this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin), (int)layoutParams.height);
                            if (layoutParams.width == 0 && n17 == 1073741824) {
                                n5 = n15 > 0 ? n15 : 0;
                                view2.measure(View.MeasureSpec.makeMeasureSpec((int)n5, (int)1073741824), n16);
                            } else {
                                n5 = n14 = view2.getMeasuredWidth() + n15;
                                if (n14 < 0) {
                                    n5 = 0;
                                }
                                view2.measure(View.MeasureSpec.makeMeasureSpec((int)n5, (int)1073741824), n16);
                            }
                            n11 = View.combineMeasuredStates((int)n11, (int)(view2.getMeasuredState() & -16777216));
                            f -= f2;
                            n9 -= n15;
                        }
                        if (bl2) {
                            this.mTotalLength += view2.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin + this.getNextLocationOffset(view2);
                        } else {
                            n5 = this.mTotalLength;
                            this.mTotalLength = Math.max(n5, view2.getMeasuredWidth() + n5 + layoutParams.leftMargin + layoutParams.rightMargin + this.getNextLocationOffset(view2));
                        }
                        n5 = n8 != 1073741824 && layoutParams.height == -1 ? 1 : 0;
                        n16 = layoutParams.topMargin + layoutParams.bottomMargin;
                        n14 = view2.getMeasuredHeight() + n16;
                        n15 = Math.max(n13, n14);
                        n13 = n5 != 0 ? n16 : n14;
                        n13 = Math.max(n12, n13);
                        n6 = n6 != 0 && layoutParams.height == -1 ? 1 : 0;
                        if (bl && (n5 = view2.getBaseline()) != -1) {
                            n12 = layoutParams.gravity < 0 ? this.mGravity : layoutParams.gravity;
                            n12 = ((n12 & 112) >> 4 & -2) >> 1;
                            arrn[n12] = Math.max(arrn[n12], n5);
                            view[n12] = (View)Math.max((int)view[n12], n14 - n5);
                        }
                        n12 = n13;
                        n13 = n15;
                    }
                    this.mTotalLength += this.getPaddingLeft() + this.getPaddingRight();
                    if (arrn[1] != -1 || arrn[0] != -1 || arrn[2] != -1) break block59;
                    n9 = n13;
                    if (arrn[3] == -1) break block60;
                }
                n9 = Math.max(n13, Math.max(arrn[3], Math.max(arrn[0], Math.max(arrn[1], arrn[2]))) + Math.max((int)view[3], Math.max((int)view[0], Math.max((int)view[1], (int)view[2]))));
            }
            n10 = n11;
            n5 = n9;
            n9 = n12;
        }
        if (n6 != 0 || n8 == 1073741824) {
            n9 = n5;
        }
        this.setMeasuredDimension(n3 | -16777216 & n10, View.resolveSizeAndState((int)Math.max(n9 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight()), (int)n2, (int)(n10 << 16)));
        if (n7 != 0) {
            this.forceUniformHeight(n4, n);
        }
    }

    int measureNullChild(int n) {
        return 0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void measureVertical(int var1_1, int var2_2) {
        block41 : {
            block40 : {
                block38 : {
                    block39 : {
                        this.mTotalLength = 0;
                        var13_3 = this.getVirtualChildCount();
                        var20_4 = View.MeasureSpec.getMode((int)var1_1);
                        var6_5 = View.MeasureSpec.getMode((int)var2_2);
                        var21_6 = this.mBaselineAlignedChildIndex;
                        var22_7 = this.mUseLargestChild;
                        var12_15 = var14_14 = (var11_13 = (var8_12 = (var5_11 = (var9_10 = (var15_9 = (var10_8 = 0))))));
                        var3_16 = 0.0f;
                        var7_17 = 1;
                        while (var11_13 < var13_3) {
                            var23_22 = this.getVirtualChildAt(var11_13);
                            if (var23_22 == null) {
                                this.mTotalLength += this.measureNullChild(var11_13);
                            } else if (var23_22.getVisibility() == 8) {
                                var11_13 += this.getChildrenSkipCount(var23_22, var11_13);
                            } else {
                                if (this.hasDividerBeforeChildAt(var11_13)) {
                                    this.mTotalLength += this.mDividerHeight;
                                }
                                var25_28 = (LayoutParams)var23_22.getLayoutParams();
                                var3_16 += var25_28.weight;
                                if (var6_5 == 1073741824 && var25_28.height == 0 && var25_28.weight > 0.0f) {
                                    var14_14 = this.mTotalLength;
                                    this.mTotalLength = Math.max(var14_14, var25_28.topMargin + var14_14 + var25_28.bottomMargin);
                                    var14_14 = 1;
                                } else {
                                    if (var25_28.height == 0 && var25_28.weight > 0.0f) {
                                        var25_28.height = -2;
                                        var16_18 = 0;
                                    } else {
                                        var16_18 = Integer.MIN_VALUE;
                                    }
                                    var17_19 = var3_16 == 0.0f ? this.mTotalLength : 0;
                                    var24_23 = var23_22;
                                    this.measureChildBeforeLayout(var23_22, var11_13, var1_1, 0, var2_2, var17_19);
                                    if (var16_18 != Integer.MIN_VALUE) {
                                        var25_28.height = var16_18;
                                    }
                                    var16_18 = var24_23.getMeasuredHeight();
                                    var17_19 = this.mTotalLength;
                                    this.mTotalLength = Math.max(var17_19, var17_19 + var16_18 + var25_28.topMargin + var25_28.bottomMargin + this.getNextLocationOffset(var24_23));
                                    if (var22_7) {
                                        var9_10 = Math.max(var16_18, var9_10);
                                    }
                                }
                                var16_18 = var5_11;
                                var19_21 = var11_13;
                                if (var21_6 >= 0 && var21_6 == var19_21 + 1) {
                                    this.mBaselineChildTop = this.mTotalLength;
                                }
                                if (var19_21 < var21_6 && var25_28.weight > 0.0f) {
                                    throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                                }
                                if (var20_4 != 1073741824 && var25_28.width == -1) {
                                    var12_15 = var11_13 = 1;
                                } else {
                                    var11_13 = 0;
                                }
                                var17_19 = var25_28.leftMargin + var25_28.rightMargin;
                                var18_20 = var23_22.getMeasuredWidth() + var17_19;
                                var15_9 = Math.max(var15_9, var18_20);
                                var10_8 = View.combineMeasuredStates((int)var10_8, (int)var23_22.getMeasuredState());
                                var5_11 = var7_17 != 0 && var25_28.width == -1 ? 1 : 0;
                                if (var25_28.weight > 0.0f) {
                                    if (var11_13 == 0) {
                                        var17_19 = var18_20;
                                    }
                                    var11_13 = Math.max(var16_18, var17_19);
                                    var7_17 = var8_12;
                                    var8_12 = var11_13;
                                } else {
                                    if (var11_13 != 0) {
                                        var18_20 = var17_19;
                                    }
                                    var7_17 = Math.max(var8_12, var18_20);
                                    var8_12 = var16_18;
                                }
                                var16_18 = this.getChildrenSkipCount(var23_22, var19_21);
                                var11_13 = var5_11;
                                var5_11 = var8_12;
                                var8_12 = var7_17;
                                var7_17 = var11_13;
                                var11_13 = var16_18 += var19_21;
                            }
                            ++var11_13;
                        }
                        var16_18 = var10_8;
                        var10_8 = var15_9;
                        if (this.mTotalLength > 0 && this.hasDividerBeforeChildAt(var13_3)) {
                            this.mTotalLength += this.mDividerHeight;
                        }
                        var15_9 = var13_3;
                        if (!var22_7) ** GOTO lbl91
                        var11_13 = var6_5;
                        if (var11_13 == Integer.MIN_VALUE) ** GOTO lbl-1000
                        var13_3 = var10_8;
                        if (var11_13 == 0) lbl-1000: // 2 sources:
                        {
                            this.mTotalLength = 0;
                            var11_13 = 0;
                        } else {
                            do {
                                var10_8 = var13_3;
lbl91: // 2 sources:
                                var13_3 = var6_5;
                                this.mTotalLength += this.getPaddingTop() + this.getPaddingBottom();
                                var17_19 = View.resolveSizeAndState((int)Math.max(this.mTotalLength, this.getSuggestedMinimumHeight()), (int)var2_2, (int)0);
                                var6_5 = (16777215 & var17_19) - this.mTotalLength;
                                if (var14_14 == 0 && (var6_5 == 0 || var3_16 <= 0.0f)) {
                                    var6_5 = Math.max(var8_12, var5_11);
                                    if (!var22_7 || var13_3 == 1073741824) break block38;
                                    break block39;
                                }
                                if (this.mWeightSum > 0.0f) {
                                    var3_16 = this.mWeightSum;
                                }
                                this.mTotalLength = 0;
                                var11_13 = 0;
                                var9_10 = var6_5;
                                var6_5 = var8_12;
                                var8_12 = var10_8;
                                var5_11 = var16_18;
                                break block40;
                                break;
                            } while (true);
                        }
                        do {
                            var13_3 = var10_8;
                            if (var11_13 >= var15_9) ** continue;
                            var23_22 = this.getVirtualChildAt(var11_13);
                            if (var23_22 == null) {
                                this.mTotalLength += this.measureNullChild(var11_13);
                            } else if (var23_22.getVisibility() == 8) {
                                var11_13 += this.getChildrenSkipCount(var23_22, var11_13);
                            } else {
                                var24_25 = (LayoutParams)var23_22.getLayoutParams();
                                var13_3 = this.mTotalLength;
                                this.mTotalLength = Math.max(var13_3, var13_3 + var9_10 + var24_25.topMargin + var24_25.bottomMargin + this.getNextLocationOffset(var23_22));
                            }
                            ++var11_13;
                        } while (true);
                    }
                    for (var5_11 = 0; var5_11 < var15_9; ++var5_11) {
                        var23_22 = this.getVirtualChildAt(var5_11);
                        if (var23_22 == null || var23_22.getVisibility() == 8 || ((LayoutParams)var23_22.getLayoutParams()).weight <= 0.0f) continue;
                        var23_22.measure(View.MeasureSpec.makeMeasureSpec((int)var23_22.getMeasuredWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)var9_10, (int)1073741824));
                    }
                }
                var8_12 = var10_8;
                var5_11 = var6_5;
                break block41;
            }
            for (var10_8 = var11_13; var10_8 < var15_9; ++var10_8) {
                var23_22 = this.getVirtualChildAt(var10_8);
                if (var23_22.getVisibility() == 8) continue;
                var24_27 = (LayoutParams)var23_22.getLayoutParams();
                var4_29 = var24_27.weight;
                if (var4_29 > 0.0f) {
                    var14_14 = (int)((float)var9_10 * var4_29 / var3_16);
                    var16_18 = this.getPaddingLeft();
                    var18_20 = this.getPaddingRight();
                    var11_13 = var9_10 - var14_14;
                    var9_10 = var24_27.leftMargin;
                    var19_21 = var24_27.rightMargin;
                    var21_6 = var24_27.width;
                    var3_16 -= var4_29;
                    var16_18 = LinearLayoutCompat.getChildMeasureSpec((int)var1_1, (int)(var16_18 + var18_20 + var9_10 + var19_21), (int)var21_6);
                    if (var24_27.height == 0 && var13_3 == 1073741824) {
                        var9_10 = var14_14 > 0 ? var14_14 : 0;
                        var23_22.measure(var16_18, View.MeasureSpec.makeMeasureSpec((int)var9_10, (int)1073741824));
                    } else {
                        var9_10 = var14_14 = var23_22.getMeasuredHeight() + var14_14;
                        if (var14_14 < 0) {
                            var9_10 = 0;
                        }
                        var23_22.measure(var16_18, View.MeasureSpec.makeMeasureSpec((int)var9_10, (int)1073741824));
                    }
                    var5_11 = View.combineMeasuredStates((int)var5_11, (int)(var23_22.getMeasuredState() & -256));
                    var9_10 = var11_13;
                }
                var14_14 = var24_27.leftMargin + var24_27.rightMargin;
                var16_18 = var23_22.getMeasuredWidth() + var14_14;
                var11_13 = Math.max(var8_12, var16_18);
                var8_12 = var20_4 != 1073741824 && var24_27.width == -1 ? 1 : 0;
                var8_12 = var8_12 != 0 ? var14_14 : var16_18;
                var14_14 = Math.max(var6_5, var8_12);
                var6_5 = var7_17 != 0 && var24_27.width == -1 ? 1 : 0;
                var7_17 = this.mTotalLength;
                this.mTotalLength = Math.max(var7_17, var23_22.getMeasuredHeight() + var7_17 + var24_27.topMargin + var24_27.bottomMargin + this.getNextLocationOffset(var23_22));
                var8_12 = var11_13;
                var11_13 = var14_14;
                var7_17 = var6_5;
                var6_5 = var11_13;
            }
            this.mTotalLength += this.getPaddingTop() + this.getPaddingBottom();
            var16_18 = var5_11;
            var5_11 = var6_5;
        }
        if (var7_17 != 0 || var20_4 == 1073741824) {
            var5_11 = var8_12;
        }
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(var5_11 + (this.getPaddingLeft() + this.getPaddingRight()), this.getSuggestedMinimumWidth()), (int)var1_1, (int)var16_18), var17_19);
        if (var12_15 == 0) return;
        this.forceUniformWidth(var15_9, var2_2);
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            this.drawDividersVertical(canvas);
            return;
        }
        this.drawDividersHorizontal(canvas);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)LinearLayoutCompat.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)LinearLayoutCompat.class.getName());
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.mOrientation == 1) {
            this.layoutVertical(n, n2, n3, n4);
            return;
        }
        this.layoutHorizontal(n, n2, n3, n4);
    }

    protected void onMeasure(int n, int n2) {
        if (this.mOrientation == 1) {
            this.measureVertical(n, n2);
            return;
        }
        this.measureHorizontal(n, n2);
    }

    public void setBaselineAligned(boolean bl) {
        this.mBaselineAligned = bl;
    }

    public void setBaselineAlignedChildIndex(int n) {
        if (n >= 0 && n < this.getChildCount()) {
            this.mBaselineAlignedChildIndex = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("base aligned child index out of range (0, ");
        stringBuilder.append(this.getChildCount());
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        boolean bl = false;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        if (drawable == null) {
            bl = true;
        }
        this.setWillNotDraw(bl);
        this.requestLayout();
    }

    public void setDividerPadding(int n) {
        this.mDividerPadding = n;
    }

    public void setGravity(int n) {
        if (this.mGravity != n) {
            int n2 = n;
            if ((8388615 & n) == 0) {
                n2 = n | 8388611;
            }
            n = n2;
            if ((n2 & 112) == 0) {
                n = n2 | 48;
            }
            this.mGravity = n;
            this.requestLayout();
        }
    }

    public void setHorizontalGravity(int n) {
        if ((8388615 & this.mGravity) != (n &= 8388615)) {
            this.mGravity = n | this.mGravity & -8388616;
            this.requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean bl) {
        this.mUseLargestChild = bl;
    }

    public void setOrientation(int n) {
        if (this.mOrientation != n) {
            this.mOrientation = n;
            this.requestLayout();
        }
    }

    public void setShowDividers(int n) {
        if (n != this.mShowDividers) {
            this.requestLayout();
        }
        this.mShowDividers = n;
    }

    public void setVerticalGravity(int n) {
        if ((this.mGravity & 112) != (n &= 112)) {
            this.mGravity = n | this.mGravity & -113;
            this.requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface DividerMode {
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int gravity = -1;
        public float weight;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.weight = 0.0f;
        }

        public LayoutParams(int n, int n2, float f) {
            super(n, n2);
            this.weight = f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout);
            this.weight = context.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = context.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            context.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static @interface OrientationMode {
    }

}
