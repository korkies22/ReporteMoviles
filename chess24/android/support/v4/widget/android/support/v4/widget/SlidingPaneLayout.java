/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout
extends ViewGroup {
    private static final int DEFAULT_FADE_COLOR = -858993460;
    private static final int DEFAULT_OVERHANG_SIZE = 32;
    static final SlidingPanelLayoutImpl IMPL = Build.VERSION.SDK_INT >= 17 ? new SlidingPanelLayoutImplJBMR1() : (Build.VERSION.SDK_INT >= 16 ? new SlidingPanelLayoutImplJB() : new SlidingPanelLayoutImplBase());
    private static final int MIN_FLING_VELOCITY = 400;
    private static final String TAG = "SlidingPaneLayout";
    private boolean mCanSlide;
    private int mCoveredFadeColor;
    final ViewDragHelper mDragHelper;
    private boolean mFirstLayout = true;
    private float mInitialMotionX;
    private float mInitialMotionY;
    boolean mIsUnableToDrag;
    private final int mOverhangSize;
    private PanelSlideListener mPanelSlideListener;
    private int mParallaxBy;
    private float mParallaxOffset;
    final ArrayList<DisableLayerRunnable> mPostedRunnables = new ArrayList();
    boolean mPreservedOpenState;
    private Drawable mShadowDrawableLeft;
    private Drawable mShadowDrawableRight;
    float mSlideOffset;
    int mSlideRange;
    View mSlideableView;
    private int mSliderFadeColor = -858993460;
    private final Rect mTmpRect = new Rect();

    public SlidingPaneLayout(@NonNull Context context) {
        this(context, null);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingPaneLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        float f = context.getResources().getDisplayMetrics().density;
        this.mOverhangSize = (int)(32.0f * f + 0.5f);
        this.setWillNotDraw(false);
        ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegate());
        ViewCompat.setImportantForAccessibility((View)this, 1);
        this.mDragHelper = ViewDragHelper.create(this, 0.5f, new DragHelperCallback());
        this.mDragHelper.setMinVelocity(400.0f * f);
    }

    private boolean closePane(View view, int n) {
        if (!this.mFirstLayout && !this.smoothSlideTo(0.0f, n)) {
            return false;
        }
        this.mPreservedOpenState = false;
        return true;
    }

    private void dimChildView(View object, float f, int n) {
        LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
        if (f > 0.0f && n != 0) {
            int n2 = (int)((float)((-16777216 & n) >>> 24) * f);
            if (layoutParams.dimPaint == null) {
                layoutParams.dimPaint = new Paint();
            }
            layoutParams.dimPaint.setColorFilter((ColorFilter)new PorterDuffColorFilter(n2 << 24 | n & 16777215, PorterDuff.Mode.SRC_OVER));
            if (object.getLayerType() != 2) {
                object.setLayerType(2, layoutParams.dimPaint);
            }
            this.invalidateChildRegion((View)object);
            return;
        }
        if (object.getLayerType() != 0) {
            if (layoutParams.dimPaint != null) {
                layoutParams.dimPaint.setColorFilter(null);
            }
            object = new DisableLayerRunnable((View)object);
            this.mPostedRunnables.add((DisableLayerRunnable)object);
            ViewCompat.postOnAnimation((View)this, (Runnable)object);
        }
    }

    private boolean openPane(View view, int n) {
        if (!this.mFirstLayout && !this.smoothSlideTo(1.0f, n)) {
            return false;
        }
        this.mPreservedOpenState = true;
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void parallaxOtherViews(float var1_1) {
        var8_2 = this.isLayoutRtlSupport();
        var10_3 = (LayoutParams)this.mSlideableView.getLayoutParams();
        var9_4 = var10_3.dimWhenOffset;
        var4_5 = 0;
        if (!var9_4) ** GOTO lbl-1000
        var3_6 = var8_2 != false ? var10_3.rightMargin : var10_3.leftMargin;
        if (var3_6 <= 0) {
            var3_6 = 1;
        } else lbl-1000: // 2 sources:
        {
            var3_6 = 0;
        }
        var7_7 = this.getChildCount();
        while (var4_5 < var7_7) {
            var10_3 = this.getChildAt(var4_5);
            if (var10_3 != this.mSlideableView) {
                var5_9 = (int)((1.0f - this.mParallaxOffset) * (float)this.mParallaxBy);
                this.mParallaxOffset = var1_1;
                var5_9 = var6_10 = var5_9 - (int)((1.0f - var1_1) * (float)this.mParallaxBy);
                if (var8_2) {
                    var5_9 = - var6_10;
                }
                var10_3.offsetLeftAndRight(var5_9);
                if (var3_6 != 0) {
                    var2_8 = var8_2 != false ? this.mParallaxOffset - 1.0f : 1.0f - this.mParallaxOffset;
                    this.dimChildView((View)var10_3, var2_8, this.mCoveredFadeColor);
                }
            }
            ++var4_5;
        }
    }

    private static boolean viewIsOpaque(View view) {
        if (view.isOpaque()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            return false;
        }
        if ((view = view.getBackground()) != null) {
            if (view.getOpacity() == -1) {
                return true;
            }
            return false;
        }
        return false;
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                int n6;
                int n7 = n2 + n4;
                View view2 = viewGroup.getChildAt(i);
                if (n7 < view2.getLeft() || n7 >= view2.getRight() || (n6 = n3 + n5) < view2.getTop() || n6 >= view2.getBottom() || !this.canScroll(view2, true, n, n7 - view2.getLeft(), n6 - view2.getTop())) continue;
                return true;
            }
        }
        if (bl) {
            if (!this.isLayoutRtlSupport()) {
                n = - n;
            }
            if (view.canScrollHorizontally(n)) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public boolean canSlide() {
        return this.mCanSlide;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams)) {
            return true;
        }
        return false;
    }

    public boolean closePane() {
        return this.closePane(this.mSlideableView, 0);
    }

    public void computeScroll() {
        if (this.mDragHelper.continueSettling(true)) {
            if (!this.mCanSlide) {
                this.mDragHelper.abort();
                return;
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    void dispatchOnPanelClosed(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelClosed(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelOpened(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelOpened(view);
        }
        this.sendAccessibilityEvent(32);
    }

    void dispatchOnPanelSlide(View view) {
        if (this.mPanelSlideListener != null) {
            this.mPanelSlideListener.onPanelSlide(view, this.mSlideOffset);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable = this.isLayoutRtlSupport() ? this.mShadowDrawableRight : this.mShadowDrawableLeft;
        View view = this.getChildCount() > 1 ? this.getChildAt(1) : null;
        if (view != null) {
            int n;
            int n2;
            if (drawable == null) {
                return;
            }
            int n3 = view.getTop();
            int n4 = view.getBottom();
            int n5 = drawable.getIntrinsicWidth();
            if (this.isLayoutRtlSupport()) {
                n = view.getRight();
                n2 = n5 + n;
            } else {
                n = n2 = view.getLeft();
                n5 = n2 - n5;
                n2 = n;
                n = n5;
            }
            drawable.setBounds(n, n3, n2, n4);
            drawable.draw(canvas);
            return;
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n = canvas.save();
        if (this.mCanSlide && !layoutParams.slideable && this.mSlideableView != null) {
            canvas.getClipBounds(this.mTmpRect);
            if (this.isLayoutRtlSupport()) {
                this.mTmpRect.left = Math.max(this.mTmpRect.left, this.mSlideableView.getRight());
            } else {
                this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
            }
            canvas.clipRect(this.mTmpRect);
        }
        boolean bl = super.drawChild(canvas, view, l);
        canvas.restoreToCount(n);
        return bl;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    @ColorInt
    public int getCoveredFadeColor() {
        return this.mCoveredFadeColor;
    }

    public int getParallaxDistance() {
        return this.mParallaxBy;
    }

    @ColorInt
    public int getSliderFadeColor() {
        return this.mSliderFadeColor;
    }

    void invalidateChildRegion(View view) {
        IMPL.invalidateChildRegion(this, view);
    }

    boolean isDimmed(View object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        object = (LayoutParams)object.getLayoutParams();
        boolean bl2 = bl;
        if (this.mCanSlide) {
            bl2 = bl;
            if (object.dimWhenOffset) {
                bl2 = bl;
                if (this.mSlideOffset > 0.0f) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    boolean isLayoutRtlSupport() {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            return true;
        }
        return false;
    }

    public boolean isOpen() {
        if (this.mCanSlide && this.mSlideOffset != 1.0f) {
            return false;
        }
        return true;
    }

    public boolean isSlideable() {
        return this.mCanSlide;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
        int n = this.mPostedRunnables.size();
        for (int i = 0; i < n; ++i) {
            this.mPostedRunnables.get(i).run();
        }
        this.mPostedRunnables.clear();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onInterceptTouchEvent(MotionEvent var1_1) {
        block5 : {
            block6 : {
                block7 : {
                    var4_2 = var1_1.getActionMasked();
                    var6_3 = this.mCanSlide;
                    var5_4 = true;
                    if (!var6_3 && var4_2 == 0 && this.getChildCount() > 1 && (var7_5 = this.getChildAt(1)) != null) {
                        this.mPreservedOpenState = this.mDragHelper.isViewUnder(var7_5, (int)var1_1.getX(), (int)var1_1.getY()) ^ true;
                    }
                    if (!this.mCanSlide || this.mIsUnableToDrag && var4_2 != 0) break block5;
                    if (var4_2 == 3 || var4_2 == 1) break block6;
                    if (var4_2 == 0) break block7;
                    if (var4_2 == 2) {
                        var3_6 = var1_1.getX();
                        var2_8 = var1_1.getY();
                        var3_6 = Math.abs(var3_6 - this.mInitialMotionX);
                        var2_8 = Math.abs(var2_8 - this.mInitialMotionY);
                        if (var3_6 > (float)this.mDragHelper.getTouchSlop() && var2_8 > var3_6) {
                            this.mDragHelper.cancel();
                            this.mIsUnableToDrag = true;
                            return false;
                        }
                    }
                    ** GOTO lbl-1000
                }
                this.mIsUnableToDrag = false;
                var2_9 = var1_1.getX();
                var3_7 = var1_1.getY();
                this.mInitialMotionX = var2_9;
                this.mInitialMotionY = var3_7;
                if (this.mDragHelper.isViewUnder(this.mSlideableView, (int)var2_9, (int)var3_7) && this.isDimmed(this.mSlideableView)) {
                    var4_2 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var4_2 = 0;
                }
                if (this.mDragHelper.shouldInterceptTouchEvent(var1_1) != false) return var5_4;
                if (var4_2 == 0) return false;
                return true;
            }
            this.mDragHelper.cancel();
            return false;
        }
        this.mDragHelper.cancel();
        return super.onInterceptTouchEvent(var1_1);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        var14_6 = this.isLayoutRtlSupport();
        if (var14_6) {
            this.mDragHelper.setEdgeTrackingEnabled(2);
        } else {
            this.mDragHelper.setEdgeTrackingEnabled(1);
        }
        var9_7 = var4_4 - var2_2;
        var2_2 = var14_6 != false ? this.getPaddingRight() : this.getPaddingLeft();
        var4_4 = var14_6 != false ? this.getPaddingLeft() : this.getPaddingRight();
        var11_8 = this.getPaddingTop();
        var10_9 = this.getChildCount();
        if (this.mFirstLayout) {
            var6_10 = this.mCanSlide != false && this.mPreservedOpenState != false ? 1.0f : 0.0f;
            this.mSlideOffset = var6_10;
        }
        var3_3 = var2_2;
        for (var5_5 = 0; var5_5 < var10_9; ++var5_5) {
            block14 : {
                var15_15 = this.getChildAt(var5_5);
                if (var15_15.getVisibility() == 8) continue;
                var16_16 = (LayoutParams)var15_15.getLayoutParams();
                var12_13 = var15_15.getMeasuredWidth();
                if (!var16_16.slideable) break block14;
                var7_11 = var16_16.leftMargin;
                var13_14 = var16_16.rightMargin;
                var8_12 = var9_7 - var4_4;
                this.mSlideRange = var13_14 = Math.min(var2_2, var8_12 - this.mOverhangSize) - var3_3 - (var7_11 + var13_14);
                var7_11 = var14_6 != false ? var16_16.rightMargin : var16_16.leftMargin;
                var1_1 = var3_3 + var7_11 + var13_14 + var12_13 / 2 > var8_12;
                var16_16.dimWhenOffset = var1_1;
                var8_12 = (int)((float)var13_14 * this.mSlideOffset);
                var3_3 = var7_11 + var8_12 + var3_3;
                this.mSlideOffset = (float)var8_12 / (float)this.mSlideRange;
                ** GOTO lbl38
            }
            if (this.mCanSlide && this.mParallaxBy != 0) {
                var7_11 = (int)((1.0f - this.mSlideOffset) * (float)this.mParallaxBy);
                var3_3 = var2_2;
            } else {
                var3_3 = var2_2;
lbl38: // 2 sources:
                var7_11 = 0;
            }
            if (var14_6) {
                var8_12 = var9_7 - var3_3 + var7_11;
                var7_11 = var8_12 - var12_13;
            } else {
                var7_11 = var3_3 - var7_11;
                var8_12 = var7_11 + var12_13;
            }
            var15_15.layout(var7_11, var11_8, var8_12, var15_15.getMeasuredHeight() + var11_8);
            var2_2 += var15_15.getWidth();
        }
        if (this.mFirstLayout) {
            if (this.mCanSlide) {
                if (this.mParallaxBy != 0) {
                    this.parallaxOtherViews(this.mSlideOffset);
                }
                if (((LayoutParams)this.mSlideableView.getLayoutParams()).dimWhenOffset) {
                    this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
                }
            } else {
                for (var2_2 = 0; var2_2 < var10_9; ++var2_2) {
                    this.dimChildView(this.getChildAt(var2_2), 0.0f, this.mSliderFadeColor);
                }
            }
            this.updateObscuredViewsVisibility(this.mSlideableView);
        }
        this.mFirstLayout = false;
    }

    protected void onMeasure(int n, int n2) {
        RuntimeException runtimeException;
        super("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
        throw runtimeException;
    }

    void onPanelDragged(int n) {
        if (this.mSlideableView == null) {
            this.mSlideOffset = 0.0f;
            return;
        }
        boolean bl = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        int n2 = this.mSlideableView.getWidth();
        int n3 = n;
        if (bl) {
            n3 = this.getWidth() - n - n2;
        }
        n = bl ? this.getPaddingRight() : this.getPaddingLeft();
        n2 = bl ? layoutParams.rightMargin : layoutParams.leftMargin;
        this.mSlideOffset = (float)(n3 - (n + n2)) / (float)this.mSlideRange;
        if (this.mParallaxBy != 0) {
            this.parallaxOtherViews(this.mSlideOffset);
        }
        if (layoutParams.dimWhenOffset) {
            this.dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
        }
        this.dispatchOnPanelSlide(this.mSlideableView);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        if (parcelable.isOpen) {
            this.openPane();
        } else {
            this.closePane();
        }
        this.mPreservedOpenState = parcelable.isOpen;
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        boolean bl = this.isSlideable() ? this.isOpen() : this.mPreservedOpenState;
        savedState.isOpen = bl;
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3) {
            this.mFirstLayout = true;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mCanSlide) {
            return super.onTouchEvent(motionEvent);
        }
        this.mDragHelper.processTouchEvent(motionEvent);
        switch (motionEvent.getActionMasked()) {
            default: {
                return true;
            }
            case 1: {
                int n;
                float f;
                float f2;
                float f3;
                float f4;
                if (!this.isDimmed(this.mSlideableView) || (f2 = (f = motionEvent.getX()) - this.mInitialMotionX) * f2 + (f3 = (f4 = motionEvent.getY()) - this.mInitialMotionY) * f3 >= (float)((n = this.mDragHelper.getTouchSlop()) * n) || !this.mDragHelper.isViewUnder(this.mSlideableView, (int)f, (int)f4)) break;
                this.closePane(this.mSlideableView, 0);
                return true;
            }
            case 0: {
                float f = motionEvent.getX();
                float f5 = motionEvent.getY();
                this.mInitialMotionX = f;
                this.mInitialMotionY = f5;
            }
        }
        return true;
    }

    public boolean openPane() {
        return this.openPane(this.mSlideableView, 0);
    }

    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!this.isInTouchMode() && !this.mCanSlide) {
            boolean bl = view == this.mSlideableView;
            this.mPreservedOpenState = bl;
        }
    }

    void setAllChildrenVisible() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() != 4) continue;
            view.setVisibility(0);
        }
    }

    public void setCoveredFadeColor(@ColorInt int n) {
        this.mCoveredFadeColor = n;
    }

    public void setPanelSlideListener(@Nullable PanelSlideListener panelSlideListener) {
        this.mPanelSlideListener = panelSlideListener;
    }

    public void setParallaxDistance(int n) {
        this.mParallaxBy = n;
        this.requestLayout();
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        this.setShadowDrawableLeft(drawable);
    }

    public void setShadowDrawableLeft(@Nullable Drawable drawable) {
        this.mShadowDrawableLeft = drawable;
    }

    public void setShadowDrawableRight(@Nullable Drawable drawable) {
        this.mShadowDrawableRight = drawable;
    }

    @Deprecated
    public void setShadowResource(@DrawableRes int n) {
        this.setShadowDrawable(this.getResources().getDrawable(n));
    }

    public void setShadowResourceLeft(int n) {
        this.setShadowDrawableLeft(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setShadowResourceRight(int n) {
        this.setShadowDrawableRight(ContextCompat.getDrawable(this.getContext(), n));
    }

    public void setSliderFadeColor(@ColorInt int n) {
        this.mSliderFadeColor = n;
    }

    @Deprecated
    public void smoothSlideClosed() {
        this.closePane();
    }

    @Deprecated
    public void smoothSlideOpen() {
        this.openPane();
    }

    boolean smoothSlideTo(float f, int n) {
        if (!this.mCanSlide) {
            return false;
        }
        boolean bl = this.isLayoutRtlSupport();
        LayoutParams layoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
        if (bl) {
            n = this.getPaddingRight();
            int n2 = layoutParams.rightMargin;
            int n3 = this.mSlideableView.getWidth();
            n = (int)((float)this.getWidth() - ((float)(n + n2) + f * (float)this.mSlideRange + (float)n3));
        } else {
            n = (int)((float)(this.getPaddingLeft() + layoutParams.leftMargin) + f * (float)this.mSlideRange);
        }
        if (this.mDragHelper.smoothSlideViewTo(this.mSlideableView, n, this.mSlideableView.getTop())) {
            this.setAllChildrenVisible();
            ViewCompat.postInvalidateOnAnimation((View)this);
            return true;
        }
        return false;
    }

    void updateObscuredViewsVisibility(View view) {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl = this.isLayoutRtlSupport();
        int n5 = bl ? this.getWidth() - this.getPaddingRight() : this.getPaddingLeft();
        int n6 = bl ? this.getPaddingLeft() : this.getWidth() - this.getPaddingRight();
        int n7 = this.getPaddingTop();
        int n8 = this.getHeight();
        int n9 = this.getPaddingBottom();
        if (view != null && SlidingPaneLayout.viewIsOpaque(view)) {
            n3 = view.getLeft();
            n4 = view.getRight();
            n = view.getTop();
            n2 = view.getBottom();
        } else {
            n3 = 0;
            n4 = 0;
            n = 0;
            n2 = 0;
        }
        int n10 = this.getChildCount();
        for (int i = 0; i < n10; ++i) {
            View view2 = this.getChildAt(i);
            if (view2 == view) {
                return;
            }
            if (view2.getVisibility() == 8) continue;
            int n11 = bl ? n6 : n5;
            int n12 = Math.max(n11, view2.getLeft());
            int n13 = Math.max(n7, view2.getTop());
            n11 = bl ? n5 : n6;
            n11 = Math.min(n11, view2.getRight());
            int n14 = Math.min(n8 - n9, view2.getBottom());
            n11 = n12 >= n3 && n13 >= n && n11 <= n4 && n14 <= n2 ? 4 : 0;
            view2.setVisibility(n11);
        }
    }

    class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() {
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
            accessibilityNodeInfoCompat.setMovementGranularities(accessibilityNodeInfoCompat2.getMovementGranularities());
        }

        public boolean filter(View view) {
            return SlidingPaneLayout.this.isDimmed(view);
        }

        @Override
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)SlidingPaneLayout.class.getName());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat2);
            this.copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompat2);
            accessibilityNodeInfoCompat2.recycle();
            accessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
            accessibilityNodeInfoCompat.setSource(view);
            view = ViewCompat.getParentForAccessibility(view);
            if (view instanceof View) {
                accessibilityNodeInfoCompat.setParent(view);
            }
            int n = SlidingPaneLayout.this.getChildCount();
            for (int i = 0; i < n; ++i) {
                view = SlidingPaneLayout.this.getChildAt(i);
                if (this.filter(view) || view.getVisibility() != 0) continue;
                ViewCompat.setImportantForAccessibility(view, 1);
                accessibilityNodeInfoCompat.addChild(view);
            }
        }

        @Override
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!this.filter(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }
    }

    private class DisableLayerRunnable
    implements Runnable {
        final View mChildView;

        DisableLayerRunnable(View view) {
            this.mChildView = view;
        }

        @Override
        public void run() {
            if (this.mChildView.getParent() == SlidingPaneLayout.this) {
                this.mChildView.setLayerType(0, null);
                SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
            }
            SlidingPaneLayout.this.mPostedRunnables.remove(this);
        }
    }

    private class DragHelperCallback
    extends ViewDragHelper.Callback {
        DragHelperCallback() {
        }

        @Override
        public int clampViewPositionHorizontal(View object, int n, int n2) {
            object = (LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
            if (SlidingPaneLayout.this.isLayoutRtlSupport()) {
                n2 = SlidingPaneLayout.this.getWidth() - (SlidingPaneLayout.this.getPaddingRight() + object.rightMargin + SlidingPaneLayout.this.mSlideableView.getWidth());
                int n3 = SlidingPaneLayout.this.mSlideRange;
                return Math.max(Math.min(n, n2), n2 - n3);
            }
            n2 = SlidingPaneLayout.this.getPaddingLeft() + object.leftMargin;
            int n4 = SlidingPaneLayout.this.mSlideRange;
            return Math.min(Math.max(n, n2), n4 + n2);
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return SlidingPaneLayout.this.mSlideRange;
        }

        @Override
        public void onEdgeDragStarted(int n, int n2) {
            SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, n2);
        }

        @Override
        public void onViewCaptured(View view, int n) {
            SlidingPaneLayout.this.setAllChildrenVisible();
        }

        @Override
        public void onViewDragStateChanged(int n) {
            if (SlidingPaneLayout.this.mDragHelper.getViewDragState() == 0) {
                if (SlidingPaneLayout.this.mSlideOffset == 0.0f) {
                    SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView);
                    SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView);
                    SlidingPaneLayout.this.mPreservedOpenState = false;
                    return;
                }
                SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView);
                SlidingPaneLayout.this.mPreservedOpenState = true;
            }
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            SlidingPaneLayout.this.onPanelDragged(n);
            SlidingPaneLayout.this.invalidate();
        }

        @Override
        public void onViewReleased(View view, float f, float f2) {
            int n;
            block7 : {
                int n2;
                block8 : {
                    LayoutParams layoutParams;
                    block4 : {
                        int n3;
                        block6 : {
                            block5 : {
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                if (!SlidingPaneLayout.this.isLayoutRtlSupport()) break block4;
                                n3 = SlidingPaneLayout.this.getPaddingRight() + layoutParams.rightMargin;
                                if (f < 0.0f) break block5;
                                n = n3;
                                if (f != 0.0f) break block6;
                                n = n3;
                                if (SlidingPaneLayout.this.mSlideOffset <= 0.5f) break block6;
                            }
                            n = n3 + SlidingPaneLayout.this.mSlideRange;
                        }
                        n3 = SlidingPaneLayout.this.mSlideableView.getWidth();
                        n = SlidingPaneLayout.this.getWidth() - n - n3;
                        break block7;
                    }
                    n = SlidingPaneLayout.this.getPaddingLeft();
                    n2 = layoutParams.leftMargin + n;
                    if (f > 0.0f) break block8;
                    n = n2;
                    if (f != 0.0f) break block7;
                    n = n2;
                    if (SlidingPaneLayout.this.mSlideOffset <= 0.5f) break block7;
                }
                n = n2 + SlidingPaneLayout.this.mSlideRange;
            }
            SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(n, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            if (SlidingPaneLayout.this.mIsUnableToDrag) {
                return false;
            }
            return ((LayoutParams)view.getLayoutParams()).slideable;
        }
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        private static final int[] ATTRS = new int[]{16843137};
        Paint dimPaint;
        boolean dimWhenOffset;
        boolean slideable;
        public float weight = 0.0f;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, ATTRS);
            this.weight = context.getFloat(0, 0.0f);
            context.recycle();
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.weight = layoutParams.weight;
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    public static interface PanelSlideListener {
        public void onPanelClosed(@NonNull View var1);

        public void onPanelOpened(@NonNull View var1);

        public void onPanelSlide(@NonNull View var1, float var2);
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, null);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean isOpen;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            boolean bl = parcel.readInt() != 0;
            this.isOpen = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

    }

    public static class SimplePanelSlideListener
    implements PanelSlideListener {
        @Override
        public void onPanelClosed(View view) {
        }

        @Override
        public void onPanelOpened(View view) {
        }

        @Override
        public void onPanelSlide(View view, float f) {
        }
    }

    static interface SlidingPanelLayoutImpl {
        public void invalidateChildRegion(SlidingPaneLayout var1, View var2);
    }

    static class SlidingPanelLayoutImplBase
    implements SlidingPanelLayoutImpl {
        SlidingPanelLayoutImplBase() {
        }

        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.postInvalidateOnAnimation((View)slidingPaneLayout, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    @RequiresApi(value=16)
    static class SlidingPanelLayoutImplJB
    extends SlidingPanelLayoutImplBase {
        private Method mGetDisplayList;
        private Field mRecreateDisplayList;

        SlidingPanelLayoutImplJB() {
            try {
                this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)SlidingPaneLayout.TAG, (String)"Couldn't fetch getDisplayList method; dimming won't work right.", (Throwable)noSuchMethodException);
            }
            try {
                this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
                this.mRecreateDisplayList.setAccessible(true);
                return;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                Log.e((String)SlidingPaneLayout.TAG, (String)"Couldn't fetch mRecreateDisplayList field; dimming will be slow.", (Throwable)noSuchFieldException);
                return;
            }
        }

        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            if (this.mGetDisplayList != null && this.mRecreateDisplayList != null) {
                try {
                    this.mRecreateDisplayList.setBoolean((Object)view, true);
                    this.mGetDisplayList.invoke((Object)view, null);
                }
                catch (Exception exception) {
                    Log.e((String)SlidingPaneLayout.TAG, (String)"Error refreshing display list state", (Throwable)exception);
                }
                super.invalidateChildRegion(slidingPaneLayout, view);
                return;
            }
            view.invalidate();
        }
    }

    @RequiresApi(value=17)
    static class SlidingPanelLayoutImplJBMR1
    extends SlidingPanelLayoutImplBase {
        SlidingPanelLayoutImplJBMR1() {
        }

        @Override
        public void invalidateChildRegion(SlidingPaneLayout slidingPaneLayout, View view) {
            ViewCompat.setLayerPaint(view, ((LayoutParams)view.getLayoutParams()).dimPaint);
        }
    }

}
