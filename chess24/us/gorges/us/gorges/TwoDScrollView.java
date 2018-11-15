/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.animation.AnimationUtils
 *  android.widget.FrameLayout
 *  android.widget.Scroller
 */
package us.gorges;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Scroller;
import java.util.ArrayList;

public class TwoDScrollView
extends FrameLayout {
    static final int ANIMATED_SCROLL_GAP = 250;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private View mChildToScrollTo = null;
    private boolean mIsBeingDragged = false;
    private boolean mIsLayoutDirty = true;
    private float mLastMotionX;
    private float mLastMotionY;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private Scroller mScroller;
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;
    private boolean mTwoDScrollViewMovedFocus;
    private VelocityTracker mVelocityTracker;

    public TwoDScrollView(Context context) {
        super(context);
        this.initTwoDScrollView();
    }

    public TwoDScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.initTwoDScrollView();
    }

    public TwoDScrollView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initTwoDScrollView();
    }

    private boolean canScroll() {
        boolean bl = false;
        View view = this.getChildAt(0);
        if (view != null) {
            int n = view.getHeight();
            int n2 = view.getWidth();
            if (this.getHeight() < n + this.getPaddingTop() + this.getPaddingBottom() || this.getWidth() < n2 + this.getPaddingLeft() + this.getPaddingRight()) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private int clamp(int n, int n2, int n3) {
        if (n2 < n3 && n >= 0) {
            if (n2 + n > n3) {
                return n3 - n2;
            }
            return n;
        }
        return 0;
    }

    private void doScroll(int n, int n2) {
        if (n != 0 || n2 != 0) {
            this.smoothScrollBy(n, n2);
        }
    }

    private View findFocusableViewInBounds(boolean bl, int n, int n2, boolean bl2, int n3, int n4) {
        ArrayList arrayList = this.getFocusables(2);
        int n5 = arrayList.size();
        View view = null;
        int n6 = 0;
        int n7 = 0;
        do {
            int n8;
            View view2;
            block3 : {
                View view3;
                block6 : {
                    int n9;
                    int n10;
                    block7 : {
                        int n11;
                        block5 : {
                            int n12;
                            int n13;
                            block4 : {
                                n11 = n;
                                if (n6 >= n5) break;
                                view3 = (View)arrayList.get(n6);
                                n9 = view3.getTop();
                                n12 = view3.getBottom();
                                n10 = view3.getLeft();
                                n13 = view3.getRight();
                                view2 = view;
                                n8 = n7;
                                if (n11 >= n12) break block3;
                                view2 = view;
                                n8 = n7;
                                if (n9 >= n2) break block3;
                                view2 = view;
                                n8 = n7;
                                if (n3 >= n13) break block3;
                                view2 = view;
                                n8 = n7;
                                if (n10 >= n4) break block3;
                                n11 = n11 < n9 && n12 < n2 && n3 < n10 && n13 < n4 ? 1 : 0;
                                if (view != null) break block4;
                                view2 = view3;
                                n8 = n11;
                                break block3;
                            }
                            n9 = bl && n9 < view.getTop() || !bl && n12 > view.getBottom() ? 1 : 0;
                            n10 = bl2 && n10 < view.getLeft() || !bl2 && n13 > view.getRight() ? 1 : 0;
                            if (n7 == 0) break block5;
                            view2 = view;
                            n8 = n7;
                            if (n11 == 0) break block3;
                            view2 = view;
                            n8 = n7;
                            if (n9 == 0) break block3;
                            view2 = view;
                            n8 = n7;
                            if (n10 == 0) break block3;
                            break block6;
                        }
                        if (n11 == 0) break block7;
                        view2 = view3;
                        n8 = 1;
                        break block3;
                    }
                    view2 = view;
                    n8 = n7;
                    if (n9 == 0) break block3;
                    view2 = view;
                    n8 = n7;
                    if (n10 == 0) break block3;
                }
                view2 = view3;
                n8 = n7;
            }
            ++n6;
            view = view2;
            n7 = n8;
        } while (true);
        return view;
    }

    private View findFocusableViewInMyBounds(boolean bl, int n, boolean bl2, int n2, View view) {
        int n3 = this.getVerticalFadingEdgeLength() / 2;
        int n4 = n + n3;
        n = n + this.getHeight() - n3;
        int n5 = this.getHorizontalFadingEdgeLength() / 2;
        n3 = n2 + n5;
        n2 = n2 + this.getWidth() - n5;
        if (view != null && view.getTop() < n && view.getBottom() > n4 && view.getLeft() < n2 && view.getRight() > n3) {
            return view;
        }
        return this.findFocusableViewInBounds(bl, n4, n, bl2, n3, n2);
    }

    private void initTwoDScrollView() {
        this.mScroller = new Scroller(this.getContext());
        this.setFocusable(true);
        this.setDescendantFocusability(262144);
        this.setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        if ((view = view.getParent()) instanceof ViewGroup && this.isViewDescendantOf(view, view2)) {
            return true;
        }
        return false;
    }

    private boolean scrollAndFocus(int n, int n2, int n3, int n4, int n5, int n6) {
        View view;
        int n7 = this.getHeight();
        int n8 = this.getScrollY();
        int n9 = n8 + n7;
        boolean bl = n == 33;
        int n10 = this.getWidth();
        n7 = this.getScrollX();
        n10 = n7 + n10;
        boolean bl2 = n4 == 33;
        Object object = view = this.findFocusableViewInBounds(bl, n2, n3, bl2, n5, n6);
        if (view == null) {
            object = this;
        }
        if (n2 >= n8 && n3 <= n9 || n5 >= n7 && n6 <= n10) {
            bl = false;
        } else {
            n2 = bl ? (n2 -= n8) : n3 - n9;
            n3 = bl2 ? n5 - n7 : n6 - n10;
            this.doScroll(n3, n2);
            bl = true;
        }
        if (object != this.findFocus() && object.requestFocus(n)) {
            this.mTwoDScrollViewMovedFocus = true;
            this.mTwoDScrollViewMovedFocus = false;
        }
        return bl;
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
            throw new IllegalStateException("TwoDScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int n) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("TwoDScrollView can host only one direct child");
        }
        super.addView(view, n);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("TwoDScrollView can host only one direct child");
        }
        super.addView(view, n, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) {
            throw new IllegalStateException("TwoDScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    public boolean arrowScroll(int n, boolean bl) {
        View view;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view2 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view2, n);
        int n2 = bl ? this.getMaxScrollAmountHorizontal() : this.getMaxScrollAmountVertical();
        if (!bl) {
            if (view2 != null) {
                view2.getDrawingRect(this.mTempRect);
                this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
                this.doScroll(0, this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
                view2.requestFocus(n);
            } else {
                int n3;
                if (n == 33 && this.getScrollY() < n2) {
                    n3 = this.getScrollY();
                } else {
                    n3 = n2;
                    if (n == 130) {
                        n3 = n2;
                        if (this.getChildCount() > 0) {
                            int n4 = this.getChildAt(0).getBottom() - (this.getScrollY() + this.getHeight());
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
                this.doScroll(0, n3);
            }
        } else if (view2 != null) {
            view2.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view2, this.mTempRect);
            this.doScroll(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect), 0);
            view2.requestFocus(n);
        } else {
            int n5;
            if (n == 33 && this.getScrollY() < n2) {
                n5 = this.getScrollY();
            } else {
                n5 = n2;
                if (n == 130) {
                    n5 = n2;
                    if (this.getChildCount() > 0) {
                        int n6 = this.getChildAt(0).getBottom() - (this.getScrollY() + this.getHeight());
                        n5 = n2;
                        if (n6 < n2) {
                            n5 = n6;
                        }
                    }
                }
            }
            if (n5 == 0) {
                return false;
            }
            if (n != 130) {
                n5 = - n5;
            }
            this.doScroll(n5, 0);
        }
        return true;
    }

    protected int computeHorizontalScrollRange() {
        if (this.getChildCount() == 0) {
            return this.getWidth();
        }
        return this.getChildAt(0).getRight();
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (this.getChildCount() > 0) {
                View view = this.getChildAt(0);
                this.scrollTo(this.clamp(n3, this.getWidth() - this.getPaddingRight() - this.getPaddingLeft(), view.getWidth()), this.clamp(n4, this.getHeight() - this.getPaddingBottom() - this.getPaddingTop(), view.getHeight()));
            } else {
                this.scrollTo(n3, n4);
            }
            if (n != this.getScrollX() || n2 != this.getScrollY()) {
                this.onScrollChanged(this.getScrollX(), this.getScrollY(), n, n2);
            }
            this.postInvalidate();
        }
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

    protected int computeVerticalScrollRange() {
        if (this.getChildCount() == 0) {
            return this.getHeight();
        }
        return this.getChildAt(0).getBottom();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return this.executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        boolean bl = this.canScroll();
        boolean bl2 = false;
        if (!bl) {
            if (this.isFocused()) {
                View view = this.findFocus();
                keyEvent = view;
                if (view == this) {
                    keyEvent = null;
                }
                if ((keyEvent = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)keyEvent, 130)) != null && keyEvent != this && keyEvent.requestFocus(130)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        if (keyEvent.getAction() == 0) {
            switch (keyEvent.getKeyCode()) {
                default: {
                    return false;
                }
                case 22: {
                    if (!keyEvent.isAltPressed()) {
                        return this.arrowScroll(66, true);
                    }
                    return this.fullScroll(66, true);
                }
                case 21: {
                    if (!keyEvent.isAltPressed()) {
                        return this.arrowScroll(17, true);
                    }
                    return this.fullScroll(17, true);
                }
                case 20: {
                    if (!keyEvent.isAltPressed()) {
                        return this.arrowScroll(130, false);
                    }
                    return this.fullScroll(130, false);
                }
                case 19: 
            }
            if (!keyEvent.isAltPressed()) {
                return this.arrowScroll(33, false);
            }
            bl2 = this.fullScroll(33, false);
        }
        return bl2;
    }

    public void fling(int n, int n2) {
        if (this.getChildCount() > 0) {
            View view;
            int n3 = this.getHeight();
            int n4 = this.getPaddingBottom();
            int n5 = this.getPaddingTop();
            int n6 = this.getChildAt(0).getHeight();
            int n7 = this.getWidth();
            int n8 = this.getPaddingRight();
            int n9 = this.getPaddingLeft();
            int n10 = this.getChildAt(0).getWidth();
            this.mScroller.fling(this.getScrollX(), this.getScrollY(), n, n2, 0, n10 - (n7 - n8 - n9), 0, n6 - (n3 - n4 - n5));
            boolean bl = n2 > 0;
            boolean bl2 = n > 0;
            Object object = view = this.findFocusableViewInMyBounds(bl2, this.mScroller.getFinalX(), bl, this.mScroller.getFinalY(), this.findFocus());
            if (view == null) {
                object = this;
            }
            if (object != this.findFocus()) {
                n = bl ? 130 : 33;
                if (object.requestFocus(n)) {
                    this.mTwoDScrollViewMovedFocus = true;
                    this.mTwoDScrollViewMovedFocus = false;
                }
            }
            this.awakenScrollBars(this.mScroller.getDuration());
            this.invalidate();
        }
    }

    public boolean fullScroll(int n, boolean bl) {
        if (!bl) {
            int n2 = n == 130 ? 1 : 0;
            int n3 = this.getHeight();
            this.mTempRect.top = 0;
            this.mTempRect.bottom = n3;
            if (n2 != 0 && (n2 = this.getChildCount()) > 0) {
                View view = this.getChildAt(n2 - 1);
                this.mTempRect.bottom = view.getBottom();
                this.mTempRect.top = this.mTempRect.bottom - n3;
            }
            return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom, 0, 0, 0);
        }
        int n4 = n == 130 ? 1 : 0;
        int n5 = this.getWidth();
        this.mTempRect.left = 0;
        this.mTempRect.right = n5;
        if (n4 != 0 && (n4 = this.getChildCount()) > 0) {
            View view = this.getChildAt(n4 - 1);
            this.mTempRect.right = view.getBottom();
            this.mTempRect.left = this.mTempRect.right - n5;
        }
        return this.scrollAndFocus(0, 0, 0, n, this.mTempRect.top, this.mTempRect.bottom);
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

    protected float getLeftFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getHorizontalFadingEdgeLength();
        if (this.getScrollX() < n) {
            return (float)this.getScrollX() / (float)n;
        }
        return 1.0f;
    }

    public int getMaxScrollAmountHorizontal() {
        return (int)(0.5f * (float)this.getWidth());
    }

    public int getMaxScrollAmountVertical() {
        return (int)(0.5f * (float)this.getHeight());
    }

    protected float getRightFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getHorizontalFadingEdgeLength();
        int n2 = this.getWidth();
        int n3 = this.getPaddingRight();
        n2 = this.getChildAt(0).getRight() - this.getScrollX() - (n2 - n3);
        if (n2 < n) {
            return (float)n2 / (float)n;
        }
        return 1.0f;
    }

    protected float getTopFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        if (this.getScrollY() < n) {
            return (float)this.getScrollY() / (float)n;
        }
        return 1.0f;
    }

    protected void measureChild(View view, int n, int n2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(TwoDScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
    }

    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(View.MeasureSpec.makeMeasureSpec((int)(marginLayoutParams.leftMargin + marginLayoutParams.rightMargin), (int)0), View.MeasureSpec.makeMeasureSpec((int)(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin), (int)0));
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() == 1) {
            int n = motionEvent.getAction();
            if (n == 2 && this.mIsBeingDragged) {
                return true;
            }
            if (!this.canScroll()) {
                this.mIsBeingDragged = false;
                return false;
            }
            float f = motionEvent.getY();
            float f2 = motionEvent.getX();
            switch (n) {
                default: {
                    break;
                }
                case 2: {
                    n = (int)Math.abs(f - this.mLastMotionY);
                    int n2 = (int)Math.abs(f2 - this.mLastMotionX);
                    if (n <= this.mTouchSlop && n2 <= this.mTouchSlop) break;
                    this.mIsBeingDragged = true;
                    break;
                }
                case 1: 
                case 3: {
                    this.mIsBeingDragged = false;
                    break;
                }
                case 0: {
                    this.mLastMotionY = f;
                    this.mLastMotionX = f2;
                    this.mIsBeingDragged = this.mScroller.isFinished() ^ true;
                }
            }
            return this.mIsBeingDragged;
        }
        return false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && this.isViewDescendantOf(this.mChildToScrollTo, (View)this)) {
            this.scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        this.scrollTo(this.getScrollX(), this.getScrollY());
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
        return view.requestFocus(n2, rect);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        View view = this.findFocus();
        if (view != null) {
            if (this == view) {
                return;
            }
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.doScroll(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect), this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1_1) {
        var4_2 = var1_1.getPointerCount();
        var5_3 = 0;
        if (var4_2 != 1) return false;
        if (var1_1.getAction() == 0 && var1_1.getEdgeFlags() != 0) {
            return false;
        }
        if (!this.canScroll()) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(var1_1);
        var4_2 = var1_1.getAction();
        var2_4 = var1_1.getY();
        var3_5 = var1_1.getX();
        switch (var4_2) {
            default: {
                return true;
            }
            case 2: {
                var4_2 = (int)(this.mLastMotionX - var3_5);
                var6_6 = (int)(this.mLastMotionY - var2_4);
                this.mLastMotionX = var3_5;
                this.mLastMotionY = var2_4;
                if (var4_2 >= 0) ** GOTO lbl25
                if (this.getScrollX() >= 0) ** GOTO lbl34
                ** GOTO lbl-1000
lbl25: // 1 sources:
                if (var4_2 > 0) {
                    var7_7 = this.getWidth();
                    var8_8 = this.getPaddingRight();
                    var7_7 = this.getChildAt(0).getRight() - this.getScrollX() - (var7_7 - var8_8);
                    ** if (var7_7 <= 0) goto lbl-1000
lbl-1000: // 1 sources:
                    {
                        var4_2 = Math.min((int)var7_7, (int)var4_2);
                        ** GOTO lbl34
                    }
                }
                ** GOTO lbl34
lbl-1000: // 2 sources:
                {
                    var4_2 = 0;
                }
lbl34: // 4 sources:
                if (var6_6 >= 0) ** GOTO lbl37
                if (this.getScrollY() >= 0) ** GOTO lbl46
                ** GOTO lbl47
lbl37: // 1 sources:
                if (var6_6 > 0) {
                    var7_7 = this.getHeight();
                    var8_8 = this.getPaddingBottom();
                    var7_7 = this.getChildAt(0).getBottom() - this.getScrollY() - (var7_7 - var8_8);
                    if (var7_7 > 0) {
                        var5_3 = Math.min(var7_7, var6_6);
                        ** GOTO lbl47
                    } else {
                        ** GOTO lbl45
                    }
                }
                ** GOTO lbl46
lbl45: // 2 sources:
                ** GOTO lbl47
lbl46: // 2 sources:
                var5_3 = var6_6;
lbl47: // 4 sources:
                if (var5_3 == 0) {
                    if (var4_2 == 0) return true;
                }
                this.scrollBy(var4_2, var5_3);
                return true;
            }
            case 1: {
                var1_1 = this.mVelocityTracker;
                var1_1.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                var4_2 = (int)var1_1.getXVelocity();
                var5_3 = (int)var1_1.getYVelocity();
                if (Math.abs(var4_2) + Math.abs(var5_3) > this.mMinimumVelocity && this.getChildCount() > 0) {
                    this.fling(- var4_2, - var5_3);
                }
                if (this.mVelocityTracker == null) return true;
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                return true;
            }
            case 0: 
        }
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        this.mLastMotionY = var2_4;
        this.mLastMotionX = var3_5;
        return true;
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mTwoDScrollViewMovedFocus) {
            if (!this.mIsLayoutDirty) {
                this.scrollToChild(view2);
            } else {
                this.mChildToScrollTo = view2;
            }
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return this.scrollToChildRect(rect, bl);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public void scrollTo(int n, int n2) {
        if (this.getChildCount() > 0) {
            View view = this.getChildAt(0);
            n = this.clamp(n, this.getWidth() - this.getPaddingRight() - this.getPaddingLeft(), view.getWidth());
            n2 = this.clamp(n2, this.getHeight() - this.getPaddingBottom() - this.getPaddingTop(), view.getHeight());
            if (n != this.getScrollX() || n2 != this.getScrollY()) {
                super.scrollTo(n, n2);
            }
        }
    }

    public final void smoothScrollBy(int n, int n2) {
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L) {
            this.mScroller.startScroll(this.getScrollX(), this.getScrollY(), n, n2);
            this.awakenScrollBars(this.mScroller.getDuration());
            this.invalidate();
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
}
