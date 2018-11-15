/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityRecord
 *  android.view.animation.AnimationUtils
 *  android.widget.EdgeEffect
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.OverScroller
 *  android.widget.ScrollView
 */
package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import java.util.ArrayList;

public class NestedScrollView
extends FrameLayout
implements NestedScrollingParent,
NestedScrollingChild2,
ScrollingView {
    private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int[] SCROLLVIEW_STYLEABLE = new int[]{16843130};
    private static final String TAG = "NestedScrollView";
    private int mActivePointerId = -1;
    private final NestedScrollingChildHelper mChildHelper;
    private View mChildToScrollTo = null;
    private EdgeEffect mEdgeGlowBottom;
    private EdgeEffect mEdgeGlowTop;
    private boolean mFillViewport;
    private boolean mIsBeingDragged = false;
    private boolean mIsLaidOut = false;
    private boolean mIsLayoutDirty = true;
    private int mLastMotionY;
    private long mLastScroll;
    private int mLastScrollerY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnScrollChangeListener mOnScrollChangeListener;
    private final NestedScrollingParentHelper mParentHelper;
    private SavedState mSavedState;
    private final int[] mScrollConsumed = new int[2];
    private final int[] mScrollOffset = new int[2];
    private OverScroller mScroller;
    private boolean mSmoothScrollingEnabled = true;
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    public NestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public NestedScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initScrollView();
        context = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, n, 0);
        this.setFillViewport(context.getBoolean(0, false));
        context.recycle();
        this.mParentHelper = new NestedScrollingParentHelper((ViewGroup)this);
        this.mChildHelper = new NestedScrollingChildHelper((View)this);
        this.setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate((View)this, ACCESSIBILITY_DELEGATE);
    }

    private boolean canScroll() {
        boolean bl = false;
        View view = this.getChildAt(0);
        if (view != null) {
            int n = view.getHeight();
            if (this.getHeight() < n + this.getPaddingTop() + this.getPaddingBottom()) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private static int clamp(int n, int n2, int n3) {
        if (n2 < n3 && n >= 0) {
            if (n2 + n > n3) {
                return n3 - n2;
            }
            return n;
        }
        return 0;
    }

    private void doScrollY(int n) {
        if (n != 0) {
            if (this.mSmoothScrollingEnabled) {
                this.smoothScrollBy(0, n);
                return;
            }
            this.scrollBy(0, n);
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.recycleVelocityTracker();
        this.stopNestedScroll(0);
        if (this.mEdgeGlowTop != null) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
    }

    private void ensureGlows() {
        if (this.getOverScrollMode() != 2) {
            if (this.mEdgeGlowTop == null) {
                Context context = this.getContext();
                this.mEdgeGlowTop = new EdgeEffect(context);
                this.mEdgeGlowBottom = new EdgeEffect(context);
                return;
            }
        } else {
            this.mEdgeGlowTop = null;
            this.mEdgeGlowBottom = null;
        }
    }

    private View findFocusableViewInBounds(boolean bl, int n, int n2) {
        ArrayList arrayList = this.getFocusables(2);
        int n3 = arrayList.size();
        int n4 = 0;
        View view = null;
        for (int i = n4; i < n3; ++i) {
            int n5;
            View view2;
            block3 : {
                View view3;
                block6 : {
                    int n6;
                    block7 : {
                        int n7;
                        block5 : {
                            int n8;
                            block4 : {
                                view3 = (View)arrayList.get(i);
                                n6 = view3.getTop();
                                n8 = view3.getBottom();
                                view2 = view;
                                n5 = n4;
                                if (n >= n8) break block3;
                                view2 = view;
                                n5 = n4;
                                if (n6 >= n2) break block3;
                                n7 = n < n6 && n8 < n2 ? 1 : 0;
                                if (view != null) break block4;
                                view2 = view3;
                                n5 = n7;
                                break block3;
                            }
                            n6 = bl && n6 < view.getTop() || !bl && n8 > view.getBottom() ? 1 : 0;
                            if (n4 == 0) break block5;
                            view2 = view;
                            n5 = n4;
                            if (n7 == 0) break block3;
                            view2 = view;
                            n5 = n4;
                            if (n6 == 0) break block3;
                            break block6;
                        }
                        if (n7 == 0) break block7;
                        view2 = view3;
                        n5 = 1;
                        break block3;
                    }
                    view2 = view;
                    n5 = n4;
                    if (n6 == 0) break block3;
                }
                view2 = view3;
                n5 = n4;
            }
            view = view2;
            n4 = n5;
        }
        return view;
    }

    private void flingWithNestedDispatch(int n) {
        int n2 = this.getScrollY();
        boolean bl = !(n2 <= 0 && n <= 0 || n2 >= this.getScrollRange() && n >= 0);
        float f = n;
        if (!this.dispatchNestedPreFling(0.0f, f)) {
            this.dispatchNestedFling(0.0f, f, bl);
            this.fling(n);
        }
    }

    private float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            Context context = this.getContext();
            if (!context.getTheme().resolveAttribute(16842829, typedValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    private boolean inChild(int n, int n2) {
        int n3 = this.getChildCount();
        boolean bl = false;
        if (n3 > 0) {
            n3 = this.getScrollY();
            View view = this.getChildAt(0);
            boolean bl2 = bl;
            if (n2 >= view.getTop() - n3) {
                bl2 = bl;
                if (n2 < view.getBottom() - n3) {
                    bl2 = bl;
                    if (n >= view.getLeft()) {
                        bl2 = bl;
                        if (n < view.getRight()) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }
        return false;
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
            return;
        }
        this.mVelocityTracker.clear();
    }

    private void initScrollView() {
        this.mScroller = new OverScroller(this.getContext());
        this.setFocusable(true);
        this.setDescendantFocusability(262144);
        this.setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private boolean isOffScreen(View view) {
        return this.isWithinDeltaOfScreen(view, 0, this.getHeight()) ^ true;
    }

    private static boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        if ((view = view.getParent()) instanceof ViewGroup && NestedScrollView.isViewDescendantOf(view, view2)) {
            return true;
        }
        return false;
    }

    private boolean isWithinDeltaOfScreen(View view, int n, int n2) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (this.mTempRect.bottom + n >= this.getScrollY() && this.mTempRect.top - n <= this.getScrollY() + n2) {
            return true;
        }
        return false;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) == this.mActivePointerId) {
            n = n == 0 ? 1 : 0;
            this.mLastMotionY = (int)motionEvent.getY(n);
            this.mActivePointerId = motionEvent.getPointerId(n);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private boolean scrollAndFocus(int n, int n2, int n3) {
        View view;
        int n4 = this.getHeight();
        int n5 = this.getScrollY();
        n4 += n5;
        boolean bl = false;
        boolean bl2 = n == 33;
        Object object = view = this.findFocusableViewInBounds(bl2, n2, n3);
        if (view == null) {
            object = this;
        }
        if (n2 >= n5 && n3 <= n4) {
            bl2 = bl;
        } else {
            n2 = bl2 ? (n2 -= n5) : n3 - n4;
            this.doScrollY(n2);
            bl2 = true;
        }
        if (object != this.findFocus()) {
            object.requestFocus(n);
        }
        return bl2;
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        int n = this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (n != 0) {
            this.scrollBy(0, n);
        }
    }

    private boolean scrollToChildRect(Rect rect, boolean bl) {
        int n = this.computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean bl2 = n != 0;
        if (bl2) {
            if (bl) {
                this.scrollBy(0, n);
                return bl2;
            }
            this.smoothScrollBy(0, n);
        }
        return bl2;
    }

    public void addView(View view) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int n) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, n);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, n, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    public boolean arrowScroll(int n) {
        View view;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view2, n);
        int n2 = this.getMaxScrollAmount();
        if (view != null && this.isWithinDeltaOfScreen(view, n2, this.getHeight())) {
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            view.requestFocus(n);
        } else {
            int n3;
            if (n == 33 && this.getScrollY() < n2) {
                n3 = this.getScrollY();
            } else {
                n3 = n2;
                if (n == 130) {
                    n3 = n2;
                    if (this.getChildCount() > 0) {
                        int n4 = this.getChildAt(0).getBottom() - (this.getScrollY() + this.getHeight() - this.getPaddingBottom());
                        n3 = n2;
                        if (n4 < n2) {
                            n3 = n4;
                        }
                    }
                }
            }
            if (n3 == 0) {
                return false;
            }
            if (n != 130) {
                n3 = - n3;
            }
            this.doScrollY(n3);
        }
        if (view2 != null && view2.isFocused() && this.isOffScreen(view2)) {
            n = this.getDescendantFocusability();
            this.setDescendantFocusability(131072);
            this.requestFocus();
            this.setDescendantFocusability(n);
        }
        return true;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int n;
            this.mScroller.getCurrX();
            int n2 = this.mScroller.getCurrY();
            int n3 = n = n2 - this.mLastScrollerY;
            if (this.dispatchNestedPreScroll(0, n, this.mScrollConsumed, null, 1)) {
                n3 = n - this.mScrollConsumed[1];
            }
            if (n3 != 0) {
                n = this.getScrollRange();
                int n4 = this.getScrollY();
                this.overScrollByCompat(0, n3, this.getScrollX(), n4, 0, n, 0, 0, false);
                int n5 = this.getScrollY() - n4;
                if (!this.dispatchNestedScroll(0, n5, 0, n3 - n5, null, 1)) {
                    n3 = this.getOverScrollMode();
                    n3 = n3 != 0 && (n3 != 1 || n <= 0) ? 0 : 1;
                    if (n3 != 0) {
                        this.ensureGlows();
                        if (n2 <= 0 && n4 > 0) {
                            this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
                        } else if (n2 >= n && n4 < n) {
                            this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
                        }
                    }
                }
            }
            this.mLastScrollerY = n2;
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        if (this.hasNestedScrollingParent(1)) {
            this.stopNestedScroll(1);
        }
        this.mLastScrollerY = 0;
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int n = this.getChildCount();
        int n2 = 0;
        if (n == 0) {
            return 0;
        }
        int n3 = this.getHeight();
        n = this.getScrollY();
        int n4 = n + n3;
        int n5 = this.getVerticalFadingEdgeLength();
        int n6 = n;
        if (rect.top > 0) {
            n6 = n + n5;
        }
        n = n4;
        if (rect.bottom < this.getChildAt(0).getHeight()) {
            n = n4 - n5;
        }
        if (rect.bottom > n && rect.top > n6) {
            n6 = rect.height() > n3 ? rect.top - n6 + 0 : rect.bottom - n + 0;
            return Math.min(n6, this.getChildAt(0).getBottom() - n);
        }
        n4 = n2;
        if (rect.top < n6) {
            n4 = n2;
            if (rect.bottom < n) {
                n = rect.height() > n3 ? 0 - (n - rect.bottom) : 0 - (n6 - rect.top);
                n4 = Math.max(n, - this.getScrollY());
            }
        }
        return n4;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public int computeVerticalScrollRange() {
        int n = this.getChildCount();
        int n2 = this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        if (n == 0) {
            return n2;
        }
        n = this.getChildAt(0).getBottom();
        int n3 = this.getScrollY();
        int n4 = Math.max(0, n - n2);
        if (n3 < 0) {
            return n - n3;
        }
        n2 = n;
        if (n3 > n4) {
            n2 = n + (n3 - n4);
        }
        return n2;
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!super.dispatchKeyEvent(keyEvent) && !this.executeKeyEvent(keyEvent)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        return this.mChildHelper.dispatchNestedFling(f, f2, bl);
    }

    @Override
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.mChildHelper.dispatchNestedPreFling(f, f2);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.mChildHelper.dispatchNestedPreScroll(n, n2, arrn, arrn2);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2, int n3) {
        return this.mChildHelper.dispatchNestedPreScroll(n, n2, arrn, arrn2, n3);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.mChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        return this.mChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn, n5);
    }

    public void draw(Canvas canvas) {
        block12 : {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            block14 : {
                block13 : {
                    super.draw(canvas);
                    if (this.mEdgeGlowTop == null) break block12;
                    int n8 = this.getScrollY();
                    boolean bl = this.mEdgeGlowTop.isFinished();
                    int n9 = 0;
                    if (!bl) {
                        n4 = canvas.save();
                        n6 = this.getWidth();
                        n2 = this.getHeight();
                        n3 = Math.min(0, n8);
                        if (Build.VERSION.SDK_INT >= 21 && !this.getClipToPadding()) {
                            n = 0;
                        } else {
                            n6 -= this.getPaddingLeft() + this.getPaddingRight();
                            n = this.getPaddingLeft() + 0;
                        }
                        n7 = n2;
                        n5 = n3;
                        if (Build.VERSION.SDK_INT >= 21) {
                            n7 = n2;
                            n5 = n3;
                            if (this.getClipToPadding()) {
                                n7 = n2 - (this.getPaddingTop() + this.getPaddingBottom());
                                n5 = n3 + this.getPaddingTop();
                            }
                        }
                        canvas.translate((float)n, (float)n5);
                        this.mEdgeGlowTop.setSize(n6, n7);
                        if (this.mEdgeGlowTop.draw(canvas)) {
                            ViewCompat.postInvalidateOnAnimation((View)this);
                        }
                        canvas.restoreToCount(n4);
                    }
                    if (this.mEdgeGlowBottom.isFinished()) break block12;
                    n4 = canvas.save();
                    n5 = this.getWidth();
                    n3 = this.getHeight();
                    n2 = Math.max(this.getScrollRange(), n8) + n3;
                    if (Build.VERSION.SDK_INT < 21) break block13;
                    n = n9;
                    n6 = n5;
                    if (!this.getClipToPadding()) break block14;
                }
                n6 = n5 - (this.getPaddingLeft() + this.getPaddingRight());
                n = 0 + this.getPaddingLeft();
            }
            n7 = n2;
            n5 = n3;
            if (Build.VERSION.SDK_INT >= 21) {
                n7 = n2;
                n5 = n3;
                if (this.getClipToPadding()) {
                    n5 = n3 - (this.getPaddingTop() + this.getPaddingBottom());
                    n7 = n2 - this.getPaddingBottom();
                }
            }
            canvas.translate((float)(n - n6), (float)n7);
            canvas.rotate(180.0f, (float)n6, 0.0f);
            this.mEdgeGlowBottom.setSize(n6, n5);
            if (this.mEdgeGlowBottom.draw(canvas)) {
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
            canvas.restoreToCount(n4);
        }
    }

    public boolean executeKeyEvent(@NonNull KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        boolean bl = this.canScroll();
        boolean bl2 = false;
        int n = 130;
        if (!bl) {
            if (this.isFocused() && keyEvent.getKeyCode() != 4) {
                View view = this.findFocus();
                keyEvent = view;
                if (view == this) {
                    keyEvent = null;
                }
                keyEvent = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)keyEvent, 130);
                bl = bl2;
                if (keyEvent != null) {
                    bl = bl2;
                    if (keyEvent != this) {
                        bl = bl2;
                        if (keyEvent.requestFocus(130)) {
                            bl = true;
                        }
                    }
                }
                return bl;
            }
            return false;
        }
        if (keyEvent.getAction() == 0) {
            int n2 = keyEvent.getKeyCode();
            if (n2 != 62) {
                switch (n2) {
                    default: {
                        return false;
                    }
                    case 20: {
                        if (!keyEvent.isAltPressed()) {
                            return this.arrowScroll(130);
                        }
                        return this.fullScroll(130);
                    }
                    case 19: 
                }
                if (!keyEvent.isAltPressed()) {
                    return this.arrowScroll(33);
                }
                return this.fullScroll(33);
            }
            if (keyEvent.isShiftPressed()) {
                n = 33;
            }
            this.pageScroll(n);
        }
        return false;
    }

    public void fling(int n) {
        if (this.getChildCount() > 0) {
            this.startNestedScroll(2, 1);
            this.mScroller.fling(this.getScrollX(), this.getScrollY(), 0, n, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
            this.mLastScrollerY = this.getScrollY();
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    public boolean fullScroll(int n) {
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = n3;
        if (n2 != 0 && (n2 = this.getChildCount()) > 0) {
            View view = this.getChildAt(n2 - 1);
            this.mTempRect.bottom = view.getBottom() + this.getPaddingBottom();
            this.mTempRect.top = this.mTempRect.bottom - n3;
        }
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    protected float getBottomFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        int n2 = this.getHeight();
        int n3 = this.getPaddingBottom();
        n2 = this.getChildAt(0).getBottom() - this.getScrollY() - (n2 - n3);
        if (n2 < n) {
            return (float)n2 / (float)n;
        }
        return 1.0f;
    }

    public int getMaxScrollAmount() {
        return (int)(0.5f * (float)this.getHeight());
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    int getScrollRange() {
        int n = this.getChildCount();
        int n2 = 0;
        if (n > 0) {
            n2 = Math.max(0, this.getChildAt(0).getHeight() - (this.getHeight() - this.getPaddingBottom() - this.getPaddingTop()));
        }
        return n2;
    }

    protected float getTopFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        int n2 = this.getScrollY();
        if (n2 < n) {
            return (float)n2 / (float)n;
        }
        return 1.0f;
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return this.mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int n) {
        return this.mChildHelper.hasNestedScrollingParent(n);
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    protected void measureChild(View view, int n, int n2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
    }

    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2), (int)marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin), (int)0));
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0) {
            float f;
            if (motionEvent.getAction() != 8) {
                return false;
            }
            if (!this.mIsBeingDragged && (f = motionEvent.getAxisValue(9)) != 0.0f) {
                int n = (int)(f * this.getVerticalScrollFactorCompat());
                int n2 = this.getScrollRange();
                int n3 = this.getScrollY();
                int n4 = n3 - n;
                if (n4 < 0) {
                    n = 0;
                } else {
                    n = n4;
                    if (n4 > n2) {
                        n = n2;
                    }
                }
                if (n != n3) {
                    super.scrollTo(this.getScrollX(), n);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent object) {
        block12 : {
            block11 : {
                int n = object.getAction();
                if (n == 2 && this.mIsBeingDragged) {
                    return true;
                }
                if ((n &= 255) == 6) break block11;
                switch (n) {
                    default: {
                        break;
                    }
                    case 2: {
                        n = this.mActivePointerId;
                        if (n == -1) break;
                        int n2 = object.findPointerIndex(n);
                        if (n2 == -1) {
                            object = new StringBuilder();
                            object.append("Invalid pointerId=");
                            object.append(n);
                            object.append(" in onInterceptTouchEvent");
                            Log.e((String)TAG, (String)object.toString());
                            break;
                        }
                        n = (int)object.getY(n2);
                        if (Math.abs(n - this.mLastMotionY) > this.mTouchSlop && (2 & this.getNestedScrollAxes()) == 0) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionY = n;
                            this.initVelocityTrackerIfNotExists();
                            this.mVelocityTracker.addMovement((MotionEvent)object);
                            this.mNestedYOffset = 0;
                            object = this.getParent();
                            if (object != null) {
                                object.requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                        break block12;
                    }
                    case 1: 
                    case 3: {
                        this.mIsBeingDragged = false;
                        this.mActivePointerId = -1;
                        this.recycleVelocityTracker();
                        if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                            ViewCompat.postInvalidateOnAnimation((View)this);
                        }
                        this.stopNestedScroll(0);
                        break;
                    }
                    case 0: {
                        n = (int)object.getY();
                        if (!this.inChild((int)object.getX(), n)) {
                            this.mIsBeingDragged = false;
                            this.recycleVelocityTracker();
                            break;
                        }
                        this.mLastMotionY = n;
                        this.mActivePointerId = object.getPointerId(0);
                        this.initOrResetVelocityTracker();
                        this.mVelocityTracker.addMovement((MotionEvent)object);
                        this.mScroller.computeScrollOffset();
                        this.mIsBeingDragged = this.mScroller.isFinished() ^ true;
                        this.startNestedScroll(2, 0);
                        break;
                    }
                }
                break block12;
            }
            this.onSecondaryPointerUp((MotionEvent)object);
        }
        return this.mIsBeingDragged;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && NestedScrollView.isViewDescendantOf(this.mChildToScrollTo, (View)this)) {
            this.scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                this.scrollTo(this.getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            n = this.getChildCount() > 0 ? this.getChildAt(0).getMeasuredHeight() : 0;
            n = Math.max(0, n - (n4 - n2 - this.getPaddingBottom() - this.getPaddingTop()));
            if (this.getScrollY() > n) {
                this.scrollTo(this.getScrollX(), n);
            } else if (this.getScrollY() < 0) {
                this.scrollTo(this.getScrollX(), 0);
            }
        }
        this.scrollTo(this.getScrollX(), this.getScrollY());
        this.mIsLaidOut = true;
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (!this.mFillViewport) {
            return;
        }
        if (View.MeasureSpec.getMode((int)n2) == 0) {
            return;
        }
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n2 = this.getMeasuredHeight();
            if (view.getMeasuredHeight() < n2) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
                view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(n2 - this.getPaddingTop() - this.getPaddingBottom()), (int)1073741824));
            }
        }
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        if (!bl) {
            this.flingWithNestedDispatch((int)f2);
            return true;
        }
        return false;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return this.dispatchNestedPreFling(f, f2);
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        this.dispatchNestedPreScroll(n, n2, arrn, null);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        n = this.getScrollY();
        this.scrollBy(0, n4);
        n = this.getScrollY() - n;
        this.dispatchNestedScroll(0, n, 0, n4 - n, null);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, n);
        this.startNestedScroll(2);
    }

    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
        super.scrollTo(n, n2);
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        if (n == 2) {
            n2 = 130;
        } else {
            n2 = n;
            if (n == 1) {
                n2 = 33;
            }
        }
        View view = rect == null ? FocusFinder.getInstance().findNextFocus((ViewGroup)this, null, n2) : FocusFinder.getInstance().findNextFocusFromRect((ViewGroup)this, rect, n2);
        if (view == null) {
            return false;
        }
        if (this.isOffScreen(view)) {
            return false;
        }
        return view.requestFocus(n2, rect);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mSavedState = object;
        this.requestLayout();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = this.getScrollY();
        return savedState;
    }

    protected void onScrollChanged(int n, int n2, int n3, int n4) {
        super.onScrollChanged(n, n2, n3, n4);
        if (this.mOnScrollChangeListener != null) {
            this.mOnScrollChangeListener.onScrollChange(this, n, n2, n3, n4);
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        View view = this.findFocus();
        if (view != null) {
            if (this == view) {
                return;
            }
            if (this.isWithinDeltaOfScreen(view, 0, n4)) {
                view.getDrawingRect(this.mTempRect);
                this.offsetDescendantRectToMyCoords(view, this.mTempRect);
                this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            }
            return;
        }
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        if ((n & 2) != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.mParentHelper.onStopNestedScroll(view);
        this.stopNestedScroll();
    }

    public boolean onTouchEvent(MotionEvent object) {
        this.initVelocityTrackerIfNotExists();
        MotionEvent motionEvent = MotionEvent.obtain((MotionEvent)object);
        int n = object.getActionMasked();
        if (n == 0) {
            this.mNestedYOffset = 0;
        }
        motionEvent.offsetLocation(0.0f, (float)this.mNestedYOffset);
        switch (n) {
            default: {
                break;
            }
            case 6: {
                this.onSecondaryPointerUp((MotionEvent)object);
                this.mLastMotionY = (int)object.getY(object.findPointerIndex(this.mActivePointerId));
                break;
            }
            case 5: {
                n = object.getActionIndex();
                this.mLastMotionY = (int)object.getY(n);
                this.mActivePointerId = object.getPointerId(n);
                break;
            }
            case 3: {
                if (this.mIsBeingDragged && this.getChildCount() > 0 && this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.mActivePointerId = -1;
                this.endDrag();
                break;
            }
            case 2: {
                int n2;
                int n3 = object.findPointerIndex(this.mActivePointerId);
                if (n3 == -1) {
                    object = new StringBuilder();
                    object.append("Invalid pointerId=");
                    object.append(this.mActivePointerId);
                    object.append(" in onTouchEvent");
                    Log.e((String)TAG, (String)object.toString());
                    break;
                }
                int n4 = (int)object.getY(n3);
                int n5 = n = this.mLastMotionY - n4;
                if (this.dispatchNestedPreScroll(0, n, this.mScrollConsumed, this.mScrollOffset, 0)) {
                    n5 = n - this.mScrollConsumed[1];
                    motionEvent.offsetLocation(0.0f, (float)this.mScrollOffset[1]);
                    this.mNestedYOffset += this.mScrollOffset[1];
                }
                n = n5;
                if (!this.mIsBeingDragged) {
                    n = n5;
                    if (Math.abs(n5) > this.mTouchSlop) {
                        ViewParent viewParent = this.getParent();
                        if (viewParent != null) {
                            viewParent.requestDisallowInterceptTouchEvent(true);
                        }
                        this.mIsBeingDragged = true;
                        n = n5 > 0 ? n5 - this.mTouchSlop : n5 + this.mTouchSlop;
                    }
                }
                if (!this.mIsBeingDragged) break;
                this.mLastMotionY = n4 - this.mScrollOffset[1];
                int n6 = this.getScrollY();
                n4 = this.getScrollRange();
                n5 = this.getOverScrollMode();
                n5 = n5 != 0 && (n5 != 1 || n4 <= 0) ? 0 : 1;
                if (this.overScrollByCompat(0, n, 0, this.getScrollY(), 0, n4, 0, 0, true) && !this.hasNestedScrollingParent(0)) {
                    this.mVelocityTracker.clear();
                }
                if (this.dispatchNestedScroll(0, n2 = this.getScrollY() - n6, 0, n - n2, this.mScrollOffset, 0)) {
                    this.mLastMotionY -= this.mScrollOffset[1];
                    motionEvent.offsetLocation(0.0f, (float)this.mScrollOffset[1]);
                    this.mNestedYOffset += this.mScrollOffset[1];
                    break;
                }
                if (n5 == 0) break;
                this.ensureGlows();
                n5 = n6 + n;
                if (n5 < 0) {
                    EdgeEffectCompat.onPull(this.mEdgeGlowTop, (float)n / (float)this.getHeight(), object.getX(n3) / (float)this.getWidth());
                    if (!this.mEdgeGlowBottom.isFinished()) {
                        this.mEdgeGlowBottom.onRelease();
                    }
                } else if (n5 > n4) {
                    EdgeEffectCompat.onPull(this.mEdgeGlowBottom, (float)n / (float)this.getHeight(), 1.0f - object.getX(n3) / (float)this.getWidth());
                    if (!this.mEdgeGlowTop.isFinished()) {
                        this.mEdgeGlowTop.onRelease();
                    }
                }
                if (this.mEdgeGlowTop == null || this.mEdgeGlowTop.isFinished() && this.mEdgeGlowBottom.isFinished()) break;
                ViewCompat.postInvalidateOnAnimation((View)this);
                break;
            }
            case 1: {
                object = this.mVelocityTracker;
                object.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                n = (int)object.getYVelocity(this.mActivePointerId);
                if (Math.abs(n) > this.mMinimumVelocity) {
                    this.flingWithNestedDispatch(- n);
                } else if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.mActivePointerId = -1;
                this.endDrag();
                break;
            }
            case 0: {
                ViewParent viewParent;
                boolean bl;
                if (this.getChildCount() == 0) {
                    return false;
                }
                this.mIsBeingDragged = bl = this.mScroller.isFinished() ^ true;
                if (bl && (viewParent = this.getParent()) != null) {
                    viewParent.requestDisallowInterceptTouchEvent(true);
                }
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                this.mLastMotionY = (int)object.getY();
                this.mActivePointerId = object.getPointerId(0);
                this.startNestedScroll(2, 0);
            }
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent);
        }
        motionEvent.recycle();
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean overScrollByCompat(int var1_1, int var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, boolean var9_9) {
        var12_10 = this.getOverScrollMode();
        var10_11 = this.computeHorizontalScrollRange();
        var11_12 = this.computeHorizontalScrollExtent();
        var14_13 = false;
        var10_11 = var10_11 > var11_12 ? 1 : 0;
        var11_12 = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent() ? 1 : 0;
        var10_11 = var12_10 != 0 && (var12_10 != 1 || var10_11 == 0) ? 0 : 1;
        var11_12 = var12_10 != 0 && (var12_10 != 1 || var11_12 == 0) ? 0 : 1;
        var1_1 = var10_11 == 0 ? 0 : var7_7;
        var4_4 += var2_2;
        var2_2 = var11_12 == 0 ? 0 : var8_8;
        var7_7 = - var1_1;
        var5_5 = - var2_2;
        var2_2 += var6_6;
        if ((var3_3 += var1_1) > (var1_1 += var5_5)) {
            var9_9 = true;
        } else if (var3_3 < var7_7) {
            var9_9 = true;
            var1_1 = var7_7;
        } else {
            var1_1 = var3_3;
            var9_9 = false;
        }
        if (var4_4 > var2_2) ** GOTO lbl27
        if (var4_4 < var5_5) {
            var2_2 = var5_5;
lbl27: // 2 sources:
            var13_14 = true;
        } else {
            var2_2 = var4_4;
            var13_14 = false;
        }
        if (var13_14 && !this.hasNestedScrollingParent(1)) {
            this.mScroller.springBack(var1_1, var2_2, 0, 0, 0, this.getScrollRange());
        }
        this.onOverScrolled(var1_1, var2_2, var9_9, var13_14);
        if (var9_9 != false) return true;
        var9_9 = var14_13;
        if (var13_14 == false) return var9_9;
        return true;
    }

    public boolean pageScroll(int n) {
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        if (n2 != 0) {
            View view;
            this.mTempRect.top = this.getScrollY() + n3;
            n2 = this.getChildCount();
            if (n2 > 0 && this.mTempRect.top + n3 > (view = this.getChildAt(n2 - 1)).getBottom()) {
                this.mTempRect.top = view.getBottom() - n3;
            }
        } else {
            this.mTempRect.top = this.getScrollY() - n3;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        this.mTempRect.bottom = this.mTempRect.top + n3;
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mIsLayoutDirty) {
            this.scrollToChild(view2);
        } else {
            this.mChildToScrollTo = view2;
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return this.scrollToChildRect(rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        if (bl) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public void scrollTo(int n, int n2) {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n = NestedScrollView.clamp(n, this.getWidth() - this.getPaddingRight() - this.getPaddingLeft(), view.getWidth());
            n2 = NestedScrollView.clamp(n2, this.getHeight() - this.getPaddingBottom() - this.getPaddingTop(), view.getHeight());
            if (n != this.getScrollX() || n2 != this.getScrollY()) {
                super.scrollTo(n, n2);
            }
        }
    }

    public void setFillViewport(boolean bl) {
        if (bl != this.mFillViewport) {
            this.mFillViewport = bl;
            this.requestLayout();
        }
    }

    @Override
    public void setNestedScrollingEnabled(boolean bl) {
        this.mChildHelper.setNestedScrollingEnabled(bl);
    }

    public void setOnScrollChangeListener(@Nullable OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setSmoothScrollingEnabled(boolean bl) {
        this.mSmoothScrollingEnabled = bl;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void smoothScrollBy(int n, int n2) {
        if (this.getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L) {
            n = this.getHeight();
            int n3 = this.getPaddingBottom();
            int n4 = this.getPaddingTop();
            n3 = Math.max(0, this.getChildAt(0).getHeight() - (n - n3 - n4));
            n = this.getScrollY();
            n2 = Math.max(0, Math.min(n2 + n, n3));
            this.mScroller.startScroll(this.getScrollX(), n, 0, n2 - n);
            ViewCompat.postInvalidateOnAnimation((View)this);
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.scrollBy(n, n2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int n, int n2) {
        this.smoothScrollBy(n - this.getScrollX(), n2 - this.getScrollY());
    }

    @Override
    public boolean startNestedScroll(int n) {
        return this.mChildHelper.startNestedScroll(n);
    }

    @Override
    public boolean startNestedScroll(int n, int n2) {
        return this.mChildHelper.startNestedScroll(n, n2);
    }

    @Override
    public void stopNestedScroll() {
        this.mChildHelper.stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int n) {
        this.mChildHelper.stopNestedScroll(n);
    }

    static class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        AccessibilityDelegate() {
        }

        @Override
        public void onInitializeAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent((View)object, accessibilityEvent);
            object = (NestedScrollView)object;
            accessibilityEvent.setClassName((CharSequence)ScrollView.class.getName());
            boolean bl = object.getScrollRange() > 0;
            accessibilityEvent.setScrollable(bl);
            accessibilityEvent.setScrollX(object.getScrollX());
            accessibilityEvent.setScrollY(object.getScrollY());
            AccessibilityRecordCompat.setMaxScrollX((AccessibilityRecord)accessibilityEvent, object.getScrollX());
            AccessibilityRecordCompat.setMaxScrollY((AccessibilityRecord)accessibilityEvent, object.getScrollRange());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int n;
            super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
            object = (NestedScrollView)object;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (object.isEnabled() && (n = object.getScrollRange()) > 0) {
                accessibilityNodeInfoCompat.setScrollable(true);
                if (object.getScrollY() > 0) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (object.getScrollY() < n) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
        }

        @Override
        public boolean performAccessibilityAction(View object, int n, Bundle bundle) {
            if (super.performAccessibilityAction((View)object, n, bundle)) {
                return true;
            }
            if (!(object = (NestedScrollView)object).isEnabled()) {
                return false;
            }
            if (n != 4096) {
                if (n != 8192) {
                    return false;
                }
                n = object.getHeight();
                int n2 = object.getPaddingBottom();
                int n3 = object.getPaddingTop();
                n = Math.max(object.getScrollY() - (n - n2 - n3), 0);
                if (n != object.getScrollY()) {
                    object.smoothScrollTo(0, n);
                    return true;
                }
                return false;
            }
            n = object.getHeight();
            int n4 = object.getPaddingBottom();
            int n5 = object.getPaddingTop();
            n = Math.min(object.getScrollY() + (n - n4 - n5), object.getScrollRange());
            if (n != object.getScrollY()) {
                object.smoothScrollTo(0, n);
                return true;
            }
            return false;
        }
    }

    public static interface OnScrollChangeListener {
        public void onScrollChange(NestedScrollView var1, int var2, int var3, int var4, int var5);
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
        public int scrollPosition;

        SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HorizontalScrollView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode((Object)this)));
            stringBuilder.append(" scrollPosition=");
            stringBuilder.append(this.scrollPosition);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.scrollPosition);
        }

    }

}
