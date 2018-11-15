/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewBoundsCheck;
import android.support.v7.widget.ViewInfoStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;

public static abstract class RecyclerView.LayoutManager {
    boolean mAutoMeasure = false;
    ChildHelper mChildHelper;
    private int mHeight;
    private int mHeightMode;
    ViewBoundsCheck mHorizontalBoundCheck = new ViewBoundsCheck(this.mHorizontalBoundCheckCallback);
    private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback = new ViewBoundsCheck.Callback(){

        @Override
        public View getChildAt(int n) {
            return LayoutManager.this.getChildAt(n);
        }

        @Override
        public int getChildCount() {
            return LayoutManager.this.getChildCount();
        }

        @Override
        public int getChildEnd(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            return LayoutManager.this.getDecoratedRight(view) + layoutParams.rightMargin;
        }

        @Override
        public int getChildStart(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            return LayoutManager.this.getDecoratedLeft(view) - layoutParams.leftMargin;
        }

        @Override
        public View getParent() {
            return LayoutManager.this.mRecyclerView;
        }

        @Override
        public int getParentEnd() {
            return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
        }

        @Override
        public int getParentStart() {
            return LayoutManager.this.getPaddingLeft();
        }
    };
    boolean mIsAttachedToWindow = false;
    private boolean mItemPrefetchEnabled = true;
    private boolean mMeasurementCacheEnabled = true;
    int mPrefetchMaxCountObserved;
    boolean mPrefetchMaxObservedInInitialPrefetch;
    RecyclerView mRecyclerView;
    boolean mRequestedSimpleAnimations = false;
    @Nullable
    RecyclerView.SmoothScroller mSmoothScroller;
    ViewBoundsCheck mVerticalBoundCheck = new ViewBoundsCheck(this.mVerticalBoundCheckCallback);
    private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback = new ViewBoundsCheck.Callback(){

        @Override
        public View getChildAt(int n) {
            return LayoutManager.this.getChildAt(n);
        }

        @Override
        public int getChildCount() {
            return LayoutManager.this.getChildCount();
        }

        @Override
        public int getChildEnd(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            return LayoutManager.this.getDecoratedBottom(view) + layoutParams.bottomMargin;
        }

        @Override
        public int getChildStart(View view) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
            return LayoutManager.this.getDecoratedTop(view) - layoutParams.topMargin;
        }

        @Override
        public View getParent() {
            return LayoutManager.this.mRecyclerView;
        }

        @Override
        public int getParentEnd() {
            return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
        }

        @Override
        public int getParentStart() {
            return LayoutManager.this.getPaddingTop();
        }
    };
    private int mWidth;
    private int mWidthMode;

    static /* synthetic */ void access$1400(RecyclerView.LayoutManager layoutManager, RecyclerView.SmoothScroller smoothScroller) {
        layoutManager.onSmoothScrollerStopped(smoothScroller);
    }

    private void addViewInt(View view, int n, boolean bl) {
        Object object = RecyclerView.getChildViewHolderInt(view);
        if (!bl && !object.isRemoved()) {
            this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout((RecyclerView.ViewHolder)object);
        } else {
            this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout((RecyclerView.ViewHolder)object);
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        if (!object.wasReturnedFromScrap() && !object.isScrap()) {
            if (view.getParent() == this.mRecyclerView) {
                int n2 = this.mChildHelper.indexOfChild(view);
                int n3 = n;
                if (n == -1) {
                    n3 = this.mChildHelper.getChildCount();
                }
                if (n2 == -1) {
                    object = new StringBuilder();
                    object.append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                    object.append(this.mRecyclerView.indexOfChild(view));
                    object.append(this.mRecyclerView.exceptionLabel());
                    throw new IllegalStateException(object.toString());
                }
                if (n2 != n3) {
                    this.mRecyclerView.mLayout.moveView(n2, n3);
                }
            } else {
                this.mChildHelper.addView(view, n, false);
                layoutParams.mInsetsDirty = true;
                if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
                    this.mSmoothScroller.onChildAttachedToWindow(view);
                }
            }
        } else {
            if (object.isScrap()) {
                object.unScrap();
            } else {
                object.clearReturnedFromScrapFlag();
            }
            this.mChildHelper.attachViewToParent(view, n, view.getLayoutParams(), false);
        }
        if (layoutParams.mPendingInvalidate) {
            object.itemView.invalidate();
            layoutParams.mPendingInvalidate = false;
        }
    }

    public static int chooseSize(int n, int n2, int n3) {
        int n4 = View.MeasureSpec.getMode((int)n);
        n = View.MeasureSpec.getSize((int)n);
        if (n4 != Integer.MIN_VALUE) {
            if (n4 != 1073741824) {
                return Math.max(n2, n3);
            }
            return n;
        }
        return Math.min(n, Math.max(n2, n3));
    }

    private void detachViewInternal(int n, View view) {
        this.mChildHelper.detachViewFromParent(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int getChildMeasureSpec(int n, int n2, int n3, int n4, boolean bl) {
        int n5;
        block10 : {
            int n6;
            block11 : {
                block9 : {
                    block8 : {
                        n5 = 0;
                        n6 = Math.max(0, n - n3);
                        if (!bl) break block8;
                        if (n4 >= 0) break block9;
                        if (n4 == -1) {
                            n = n2 != Integer.MIN_VALUE && (n2 == 0 || n2 != 1073741824) ? (n2 = 0) : n6;
                            n3 = n;
                            n = n2;
                            return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                        }
                        break block10;
                    }
                    if (n4 < 0) break block11;
                }
                n3 = n4;
                n = 1073741824;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            }
            if (n4 == -1) {
                n = n2;
                n3 = n6;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            }
            if (n4 == -2) {
                if (n2 != Integer.MIN_VALUE) {
                    n3 = n6;
                    n = n5;
                    if (n2 != 1073741824) return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
                }
                n = Integer.MIN_VALUE;
                n3 = n6;
                return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
            }
        }
        n3 = 0;
        n = n5;
        return View.MeasureSpec.makeMeasureSpec((int)n3, (int)n);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Deprecated
    public static int getChildMeasureSpec(int n, int n2, int n3, boolean bl) {
        block10 : {
            int n4;
            block5 : {
                block9 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                block4 : {
                                    n4 = 0;
                                    n = Math.max(0, n - n2);
                                    if (!bl) break block4;
                                    if (n3 < 0) break block5;
                                    break block6;
                                }
                                if (n3 < 0) break block7;
                            }
                            n = n3;
                            break block8;
                        }
                        if (n3 != -1) break block9;
                    }
                    n2 = 1073741824;
                    return View.MeasureSpec.makeMeasureSpec((int)n, (int)n2);
                }
                if (n3 == -2) break block10;
            }
            n = 0;
            n2 = n4;
            return View.MeasureSpec.makeMeasureSpec((int)n, (int)n2);
        }
        n2 = Integer.MIN_VALUE;
        return View.MeasureSpec.makeMeasureSpec((int)n, (int)n2);
    }

    private int[] getChildRectangleOnScreenScrollAmount(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
        int n = this.getPaddingLeft();
        int n2 = this.getPaddingTop();
        int n3 = this.getWidth();
        int n4 = this.getPaddingRight();
        int n5 = this.getHeight();
        int n6 = this.getPaddingBottom();
        int n7 = view.getLeft() + rect.left - view.getScrollX();
        int n8 = view.getTop() + rect.top - view.getScrollY();
        int n9 = rect.width();
        int n10 = rect.height();
        int n11 = n7 - n;
        n = Math.min(0, n11);
        int n12 = n8 - n2;
        n2 = Math.min(0, n12);
        n4 = n9 + n7 - (n3 - n4);
        n3 = Math.max(0, n4);
        n5 = Math.max(0, n10 + n8 - (n5 - n6));
        if (this.getLayoutDirection() == 1) {
            n = n3 != 0 ? n3 : Math.max(n, n4);
        } else if (n == 0) {
            n = Math.min(n11, n3);
        }
        if (n2 == 0) {
            n2 = Math.min(n12, n5);
        }
        return new int[]{n, n2};
    }

    public static Properties getProperties(Context context, AttributeSet attributeSet, int n, int n2) {
        Properties properties = new Properties();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.RecyclerView, n, n2);
        properties.orientation = context.getInt(R.styleable.RecyclerView_android_orientation, 1);
        properties.spanCount = context.getInt(R.styleable.RecyclerView_spanCount, 1);
        properties.reverseLayout = context.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
        properties.stackFromEnd = context.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
        context.recycle();
        return properties;
    }

    private boolean isFocusedChildVisibleAfterScrolling(RecyclerView recyclerView, int n, int n2) {
        if ((recyclerView = recyclerView.getFocusedChild()) == null) {
            return false;
        }
        int n3 = this.getPaddingLeft();
        int n4 = this.getPaddingTop();
        int n5 = this.getWidth();
        int n6 = this.getPaddingRight();
        int n7 = this.getHeight();
        int n8 = this.getPaddingBottom();
        Rect rect = this.mRecyclerView.mTempRect;
        this.getDecoratedBoundsWithMargins((View)recyclerView, rect);
        if (rect.left - n < n5 - n6 && rect.right - n > n3 && rect.top - n2 < n7 - n8) {
            if (rect.bottom - n2 <= n4) {
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean isMeasurementUpToDate(int n, int n2, int n3) {
        int n4 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        boolean bl = false;
        boolean bl2 = false;
        if (n3 > 0 && n != n3) {
            return false;
        }
        if (n4 != Integer.MIN_VALUE) {
            if (n4 != 0) {
                if (n4 != 1073741824) {
                    return false;
                }
                if (n2 == n) {
                    bl2 = true;
                }
                return bl2;
            }
            return true;
        }
        bl2 = bl;
        if (n2 >= n) {
            bl2 = true;
        }
        return bl2;
    }

    private void onSmoothScrollerStopped(RecyclerView.SmoothScroller smoothScroller) {
        if (this.mSmoothScroller == smoothScroller) {
            this.mSmoothScroller = null;
        }
    }

    private void scrapOrRecycleView(RecyclerView.Recycler recycler, int n, View view) {
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (viewHolder.shouldIgnore()) {
            return;
        }
        if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
            this.removeViewAt(n);
            recycler.recycleViewHolderInternal(viewHolder);
            return;
        }
        this.detachViewAt(n);
        recycler.scrapView(view);
        this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
    }

    public void addDisappearingView(View view) {
        this.addDisappearingView(view, -1);
    }

    public void addDisappearingView(View view, int n) {
        this.addViewInt(view, n, true);
    }

    public void addView(View view) {
        this.addView(view, -1);
    }

    public void addView(View view, int n) {
        this.addViewInt(view, n, false);
    }

    public void assertInLayoutOrScroll(String string) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.assertInLayoutOrScroll(string);
        }
    }

    public void assertNotInLayoutOrScroll(String string) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.assertNotInLayoutOrScroll(string);
        }
    }

    public void attachView(View view) {
        this.attachView(view, -1);
    }

    public void attachView(View view, int n) {
        this.attachView(view, n, (RecyclerView.LayoutParams)view.getLayoutParams());
    }

    public void attachView(View view, int n, RecyclerView.LayoutParams layoutParams) {
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (viewHolder.isRemoved()) {
            this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
        } else {
            this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
        }
        this.mChildHelper.attachViewToParent(view, n, (ViewGroup.LayoutParams)layoutParams, viewHolder.isRemoved());
    }

    public void calculateItemDecorationsForChild(View view, Rect rect) {
        if (this.mRecyclerView == null) {
            rect.set(0, 0, 0, 0);
            return;
        }
        rect.set(this.mRecyclerView.getItemDecorInsetsForChild(view));
    }

    public boolean canScrollHorizontally() {
        return false;
    }

    public boolean canScrollVertically() {
        return false;
    }

    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        if (layoutParams != null) {
            return true;
        }
        return false;
    }

    public void collectAdjacentPrefetchPositions(int n, int n2, RecyclerView.State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
    }

    public void collectInitialPrefetchPositions(int n, LayoutPrefetchRegistry layoutPrefetchRegistry) {
    }

    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return 0;
    }

    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return 0;
    }

    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return 0;
    }

    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return 0;
    }

    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return 0;
    }

    public int computeVerticalScrollRange(RecyclerView.State state) {
        return 0;
    }

    public void detachAndScrapAttachedViews(RecyclerView.Recycler recycler) {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            this.scrapOrRecycleView(recycler, i, this.getChildAt(i));
        }
    }

    public void detachAndScrapView(View view, RecyclerView.Recycler recycler) {
        this.scrapOrRecycleView(recycler, this.mChildHelper.indexOfChild(view), view);
    }

    public void detachAndScrapViewAt(int n, RecyclerView.Recycler recycler) {
        this.scrapOrRecycleView(recycler, n, this.getChildAt(n));
    }

    public void detachView(View view) {
        int n = this.mChildHelper.indexOfChild(view);
        if (n >= 0) {
            this.detachViewInternal(n, view);
        }
    }

    public void detachViewAt(int n) {
        this.detachViewInternal(n, this.getChildAt(n));
    }

    void dispatchAttachedToWindow(RecyclerView recyclerView) {
        this.mIsAttachedToWindow = true;
        this.onAttachedToWindow(recyclerView);
    }

    void dispatchDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        this.mIsAttachedToWindow = false;
        this.onDetachedFromWindow(recyclerView, recycler);
    }

    public void endAnimation(View view) {
        if (this.mRecyclerView.mItemAnimator != null) {
            this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(view));
        }
    }

    @Nullable
    public View findContainingItemView(View view) {
        if (this.mRecyclerView == null) {
            return null;
        }
        if ((view = this.mRecyclerView.findContainingItemView(view)) == null) {
            return null;
        }
        if (this.mChildHelper.isHidden(view)) {
            return null;
        }
        return view;
    }

    public View findViewByPosition(int n) {
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder == null || viewHolder.getLayoutPosition() != n || viewHolder.shouldIgnore() || !this.mRecyclerView.mState.isPreLayout() && viewHolder.isRemoved()) continue;
            return view;
        }
        return null;
    }

    public abstract RecyclerView.LayoutParams generateDefaultLayoutParams();

    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new RecyclerView.LayoutParams(context, attributeSet);
    }

    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof RecyclerView.LayoutParams) {
            return new RecyclerView.LayoutParams((RecyclerView.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new RecyclerView.LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new RecyclerView.LayoutParams(layoutParams);
    }

    public int getBaseline() {
        return -1;
    }

    public int getBottomDecorationHeight(View view) {
        return ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets.bottom;
    }

    public View getChildAt(int n) {
        if (this.mChildHelper != null) {
            return this.mChildHelper.getChildAt(n);
        }
        return null;
    }

    public int getChildCount() {
        if (this.mChildHelper != null) {
            return this.mChildHelper.getChildCount();
        }
        return 0;
    }

    public boolean getClipToPadding() {
        if (this.mRecyclerView != null && this.mRecyclerView.mClipToPadding) {
            return true;
        }
        return false;
    }

    public int getColumnCountForAccessibility(RecyclerView.Recycler object, RecyclerView.State state) {
        object = this.mRecyclerView;
        int n = 1;
        if (object != null) {
            if (this.mRecyclerView.mAdapter == null) {
                return 1;
            }
            if (this.canScrollHorizontally()) {
                n = this.mRecyclerView.mAdapter.getItemCount();
            }
            return n;
        }
        return 1;
    }

    public int getDecoratedBottom(View view) {
        return view.getBottom() + this.getBottomDecorationHeight(view);
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(view, rect);
    }

    public int getDecoratedLeft(View view) {
        return view.getLeft() - this.getLeftDecorationWidth(view);
    }

    public int getDecoratedMeasuredHeight(View view) {
        Rect rect = ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets;
        return view.getMeasuredHeight() + rect.top + rect.bottom;
    }

    public int getDecoratedMeasuredWidth(View view) {
        Rect rect = ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets;
        return view.getMeasuredWidth() + rect.left + rect.right;
    }

    public int getDecoratedRight(View view) {
        return view.getRight() + this.getRightDecorationWidth(view);
    }

    public int getDecoratedTop(View view) {
        return view.getTop() - this.getTopDecorationHeight(view);
    }

    public View getFocusedChild() {
        if (this.mRecyclerView == null) {
            return null;
        }
        View view = this.mRecyclerView.getFocusedChild();
        if (view != null) {
            if (this.mChildHelper.isHidden(view)) {
                return null;
            }
            return view;
        }
        return null;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getHeightMode() {
        return this.mHeightMode;
    }

    public int getItemCount() {
        RecyclerView.Adapter adapter = this.mRecyclerView != null ? this.mRecyclerView.getAdapter() : null;
        if (adapter != null) {
            return adapter.getItemCount();
        }
        return 0;
    }

    public int getItemViewType(View view) {
        return RecyclerView.getChildViewHolderInt(view).getItemViewType();
    }

    public int getLayoutDirection() {
        return ViewCompat.getLayoutDirection((View)this.mRecyclerView);
    }

    public int getLeftDecorationWidth(View view) {
        return ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets.left;
    }

    public int getMinimumHeight() {
        return ViewCompat.getMinimumHeight((View)this.mRecyclerView);
    }

    public int getMinimumWidth() {
        return ViewCompat.getMinimumWidth((View)this.mRecyclerView);
    }

    public int getPaddingBottom() {
        if (this.mRecyclerView != null) {
            return this.mRecyclerView.getPaddingBottom();
        }
        return 0;
    }

    public int getPaddingEnd() {
        if (this.mRecyclerView != null) {
            return ViewCompat.getPaddingEnd((View)this.mRecyclerView);
        }
        return 0;
    }

    public int getPaddingLeft() {
        if (this.mRecyclerView != null) {
            return this.mRecyclerView.getPaddingLeft();
        }
        return 0;
    }

    public int getPaddingRight() {
        if (this.mRecyclerView != null) {
            return this.mRecyclerView.getPaddingRight();
        }
        return 0;
    }

    public int getPaddingStart() {
        if (this.mRecyclerView != null) {
            return ViewCompat.getPaddingStart((View)this.mRecyclerView);
        }
        return 0;
    }

    public int getPaddingTop() {
        if (this.mRecyclerView != null) {
            return this.mRecyclerView.getPaddingTop();
        }
        return 0;
    }

    public int getPosition(View view) {
        return ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
    }

    public int getRightDecorationWidth(View view) {
        return ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets.right;
    }

    public int getRowCountForAccessibility(RecyclerView.Recycler object, RecyclerView.State state) {
        object = this.mRecyclerView;
        int n = 1;
        if (object != null) {
            if (this.mRecyclerView.mAdapter == null) {
                return 1;
            }
            if (this.canScrollVertically()) {
                n = this.mRecyclerView.mAdapter.getItemCount();
            }
            return n;
        }
        return 1;
    }

    public int getSelectionModeForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }

    public int getTopDecorationHeight(View view) {
        return ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets.top;
    }

    public void getTransformedBoundingBox(View view, boolean bl, Rect rect) {
        Rect rect2;
        if (bl) {
            rect2 = ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets;
            rect.set(- rect2.left, - rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
        } else {
            rect.set(0, 0, view.getWidth(), view.getHeight());
        }
        if (this.mRecyclerView != null && (rect2 = view.getMatrix()) != null && !rect2.isIdentity()) {
            RectF rectF = this.mRecyclerView.mTempRectF;
            rectF.set(rect);
            rect2.mapRect(rectF);
            rect.set((int)Math.floor(rectF.left), (int)Math.floor(rectF.top), (int)Math.ceil(rectF.right), (int)Math.ceil(rectF.bottom));
        }
        rect.offset(view.getLeft(), view.getTop());
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getWidthMode() {
        return this.mWidthMode;
    }

    boolean hasFlexibleChildInBothOrientations() {
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            ViewGroup.LayoutParams layoutParams = this.getChildAt(i).getLayoutParams();
            if (layoutParams.width >= 0 || layoutParams.height >= 0) continue;
            return true;
        }
        return false;
    }

    public boolean hasFocus() {
        if (this.mRecyclerView != null && this.mRecyclerView.hasFocus()) {
            return true;
        }
        return false;
    }

    public void ignoreView(View object) {
        if (object.getParent() == this.mRecyclerView && this.mRecyclerView.indexOfChild((View)object) != -1) {
            object = RecyclerView.getChildViewHolderInt((View)object);
            object.addFlags(128);
            this.mRecyclerView.mViewInfoStore.removeViewHolder((RecyclerView.ViewHolder)object);
            return;
        }
        object = new StringBuilder();
        object.append("View should be fully attached to be ignored");
        object.append(this.mRecyclerView.exceptionLabel());
        throw new IllegalArgumentException(object.toString());
    }

    public boolean isAttachedToWindow() {
        return this.mIsAttachedToWindow;
    }

    public boolean isAutoMeasureEnabled() {
        return this.mAutoMeasure;
    }

    public boolean isFocused() {
        if (this.mRecyclerView != null && this.mRecyclerView.isFocused()) {
            return true;
        }
        return false;
    }

    public final boolean isItemPrefetchEnabled() {
        return this.mItemPrefetchEnabled;
    }

    public boolean isLayoutHierarchical(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return false;
    }

    public boolean isMeasurementCacheEnabled() {
        return this.mMeasurementCacheEnabled;
    }

    public boolean isSmoothScrolling() {
        if (this.mSmoothScroller != null && this.mSmoothScroller.isRunning()) {
            return true;
        }
        return false;
    }

    public boolean isViewPartiallyVisible(@NonNull View view, boolean bl, boolean bl2) {
        bl2 = this.mHorizontalBoundCheck.isViewWithinBoundFlags(view, 24579) && this.mVerticalBoundCheck.isViewWithinBoundFlags(view, 24579);
        if (bl) {
            return bl2;
        }
        return bl2 ^ true;
    }

    public void layoutDecorated(View view, int n, int n2, int n3, int n4) {
        Rect rect = ((RecyclerView.LayoutParams)view.getLayoutParams()).mDecorInsets;
        view.layout(n + rect.left, n2 + rect.top, n3 - rect.right, n4 - rect.bottom);
    }

    public void layoutDecoratedWithMargins(View view, int n, int n2, int n3, int n4) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        view.layout(n + rect.left + layoutParams.leftMargin, n2 + rect.top + layoutParams.topMargin, n3 - rect.right - layoutParams.rightMargin, n4 - rect.bottom - layoutParams.bottomMargin);
    }

    public void measureChild(View view, int n, int n2) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
        int n3 = rect.left;
        int n4 = rect.right;
        int n5 = rect.top;
        int n6 = rect.bottom;
        n = RecyclerView.LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
        if (this.shouldMeasureChild(view, n, n2 = RecyclerView.LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
            view.measure(n, n2);
        }
    }

    public void measureChildWithMargins(View view, int n, int n2) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
        Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(view);
        int n3 = rect.left;
        int n4 = rect.right;
        int n5 = rect.top;
        int n6 = rect.bottom;
        n = RecyclerView.LayoutManager.getChildMeasureSpec(this.getWidth(), this.getWidthMode(), this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + (n + (n3 + n4)), layoutParams.width, this.canScrollHorizontally());
        if (this.shouldMeasureChild(view, n, n2 = RecyclerView.LayoutManager.getChildMeasureSpec(this.getHeight(), this.getHeightMode(), this.getPaddingTop() + this.getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + (n2 + (n5 + n6)), layoutParams.height, this.canScrollVertically()), layoutParams)) {
            view.measure(n, n2);
        }
    }

    public void moveView(int n, int n2) {
        Object object = this.getChildAt(n);
        if (object == null) {
            object = new StringBuilder();
            object.append("Cannot move a child from non-existing index:");
            object.append(n);
            object.append(this.mRecyclerView.toString());
            throw new IllegalArgumentException(object.toString());
        }
        this.detachViewAt(n);
        this.attachView((View)object, n2);
    }

    public void offsetChildrenHorizontal(int n) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.offsetChildrenHorizontal(n);
        }
    }

    public void offsetChildrenVertical(int n) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.offsetChildrenVertical(n);
        }
    }

    public void onAdapterChanged(RecyclerView.Adapter adapter, RecyclerView.Adapter adapter2) {
    }

    public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int n, int n2) {
        return false;
    }

    @CallSuper
    public void onAttachedToWindow(RecyclerView recyclerView) {
    }

    @Deprecated
    public void onDetachedFromWindow(RecyclerView recyclerView) {
    }

    @CallSuper
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        this.onDetachedFromWindow(recyclerView);
    }

    @Nullable
    public View onFocusSearchFailed(View view, int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return null;
    }

    public void onInitializeAccessibilityEvent(RecyclerView.Recycler object, RecyclerView.State state, AccessibilityEvent accessibilityEvent) {
        if (this.mRecyclerView != null) {
            boolean bl;
            if (accessibilityEvent == null) {
                return;
            }
            object = this.mRecyclerView;
            boolean bl2 = bl = true;
            if (!object.canScrollVertically(1)) {
                bl2 = bl;
                if (!this.mRecyclerView.canScrollVertically(-1)) {
                    bl2 = bl;
                    if (!this.mRecyclerView.canScrollHorizontally(-1)) {
                        bl2 = this.mRecyclerView.canScrollHorizontally(1) ? bl : false;
                    }
                }
            }
            accessibilityEvent.setScrollable(bl2);
            if (this.mRecyclerView.mAdapter != null) {
                accessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount());
            }
            return;
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityEvent);
    }

    void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        this.onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, accessibilityNodeInfoCompat);
    }

    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
            accessibilityNodeInfoCompat.addAction(8192);
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
            accessibilityNodeInfoCompat.addAction(4096);
            accessibilityNodeInfoCompat.setScrollable(true);
        }
        accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(this.getRowCountForAccessibility(recycler, state), this.getColumnCountForAccessibility(recycler, state), this.isLayoutHierarchical(recycler, state), this.getSelectionModeForAccessibility(recycler, state)));
    }

    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        boolean bl = this.canScrollVertically();
        int n = 0;
        int n2 = bl ? this.getPosition(view) : 0;
        if (this.canScrollHorizontally()) {
            n = this.getPosition(view);
        }
        accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(n2, 1, n, 1, false, false));
    }

    void onInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (viewHolder != null && !viewHolder.isRemoved() && !this.mChildHelper.isHidden(viewHolder.itemView)) {
            this.onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, accessibilityNodeInfoCompat);
        }
    }

    public View onInterceptFocusSearch(View view, int n) {
        return null;
    }

    public void onItemsAdded(RecyclerView recyclerView, int n, int n2) {
    }

    public void onItemsChanged(RecyclerView recyclerView) {
    }

    public void onItemsMoved(RecyclerView recyclerView, int n, int n2, int n3) {
    }

    public void onItemsRemoved(RecyclerView recyclerView, int n, int n2) {
    }

    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2) {
    }

    public void onItemsUpdated(RecyclerView recyclerView, int n, int n2, Object object) {
        this.onItemsUpdated(recyclerView, n, n2);
    }

    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.e((String)"RecyclerView", (String)"You must override onLayoutChildren(Recycler recycler, State state) ");
    }

    public void onLayoutCompleted(RecyclerView.State state) {
    }

    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2) {
        this.mRecyclerView.defaultOnMeasure(n, n2);
    }

    public boolean onRequestChildFocus(RecyclerView recyclerView, RecyclerView.State state, View view, View view2) {
        return this.onRequestChildFocus(recyclerView, view, view2);
    }

    @Deprecated
    public boolean onRequestChildFocus(RecyclerView recyclerView, View view, View view2) {
        if (!this.isSmoothScrolling() && !recyclerView.isComputingLayout()) {
            return false;
        }
        return true;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
    }

    public Parcelable onSaveInstanceState() {
        return null;
    }

    public void onScrollStateChanged(int n) {
    }

    boolean performAccessibilityAction(int n, Bundle bundle) {
        return this.performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, n, bundle);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean performAccessibilityAction(RecyclerView.Recycler var1_1, RecyclerView.State var2_2, int var3_3, Bundle var4_4) {
        block6 : {
            block4 : {
                block5 : {
                    if (this.mRecyclerView == null) {
                        return false;
                    }
                    if (var3_3 == 4096) break block4;
                    if (var3_3 == 8192) break block5;
                    var5_5 = var3_3 = 0;
                    break block6;
                }
                var3_3 = this.mRecyclerView.canScrollVertically(-1) != false ? - this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() : 0;
                var6_6 = var3_3;
                if (!this.mRecyclerView.canScrollHorizontally(-1)) ** GOTO lbl-1000
                var5_5 = - this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                break block6;
            }
            var3_3 = this.mRecyclerView.canScrollVertically(1) != false ? this.getHeight() - this.getPaddingTop() - this.getPaddingBottom() : 0;
            var6_6 = var3_3;
            if (this.mRecyclerView.canScrollHorizontally(1)) {
                var5_5 = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
            } else lbl-1000: // 2 sources:
            {
                var5_5 = 0;
                var3_3 = var6_6;
            }
        }
        if (var3_3 == 0 && var5_5 == 0) {
            return false;
        }
        this.mRecyclerView.scrollBy(var5_5, var3_3);
        return true;
    }

    public boolean performAccessibilityActionForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, int n, Bundle bundle) {
        return false;
    }

    boolean performAccessibilityActionForItem(View view, int n, Bundle bundle) {
        return this.performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, view, n, bundle);
    }

    public void postOnAnimation(Runnable runnable) {
        if (this.mRecyclerView != null) {
            ViewCompat.postOnAnimation((View)this.mRecyclerView, runnable);
        }
    }

    public void removeAllViews() {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            this.mChildHelper.removeViewAt(i);
        }
    }

    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            if (RecyclerView.getChildViewHolderInt(this.getChildAt(i)).shouldIgnore()) continue;
            this.removeAndRecycleViewAt(i, recycler);
        }
    }

    void removeAndRecycleScrapInt(RecyclerView.Recycler recycler) {
        int n = recycler.getScrapCount();
        for (int i = n - 1; i >= 0; --i) {
            View view = recycler.getScrapViewAt(i);
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
            if (viewHolder.shouldIgnore()) continue;
            viewHolder.setIsRecyclable(false);
            if (viewHolder.isTmpDetached()) {
                this.mRecyclerView.removeDetachedView(view, false);
            }
            if (this.mRecyclerView.mItemAnimator != null) {
                this.mRecyclerView.mItemAnimator.endAnimation(viewHolder);
            }
            viewHolder.setIsRecyclable(true);
            recycler.quickRecycleScrapView(view);
        }
        recycler.clearScrap();
        if (n > 0) {
            this.mRecyclerView.invalidate();
        }
    }

    public void removeAndRecycleView(View view, RecyclerView.Recycler recycler) {
        this.removeView(view);
        recycler.recycleView(view);
    }

    public void removeAndRecycleViewAt(int n, RecyclerView.Recycler recycler) {
        View view = this.getChildAt(n);
        this.removeViewAt(n);
        recycler.recycleView(view);
    }

    public boolean removeCallbacks(Runnable runnable) {
        if (this.mRecyclerView != null) {
            return this.mRecyclerView.removeCallbacks(runnable);
        }
        return false;
    }

    public void removeDetachedView(View view) {
        this.mRecyclerView.removeDetachedView(view, false);
    }

    public void removeView(View view) {
        this.mChildHelper.removeView(view);
    }

    public void removeViewAt(int n) {
        if (this.getChildAt(n) != null) {
            this.mChildHelper.removeViewAt(n);
        }
    }

    public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean bl) {
        return this.requestChildRectangleOnScreen(recyclerView, view, rect, bl, false);
    }

    public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View arrn, Rect rect, boolean bl, boolean bl2) {
        arrn = this.getChildRectangleOnScreenScrollAmount(recyclerView, (View)arrn, rect, bl);
        int n = arrn[0];
        int n2 = arrn[1];
        if (bl2 && !this.isFocusedChildVisibleAfterScrolling(recyclerView, n, n2) || n == 0 && n2 == 0) {
            return false;
        }
        if (bl) {
            recyclerView.scrollBy(n, n2);
            return true;
        }
        recyclerView.smoothScrollBy(n, n2);
        return true;
    }

    public void requestLayout() {
        if (this.mRecyclerView != null) {
            this.mRecyclerView.requestLayout();
        }
    }

    public void requestSimpleAnimationsInNextLayout() {
        this.mRequestedSimpleAnimations = true;
    }

    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }

    public void scrollToPosition(int n) {
    }

    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return 0;
    }

    @Deprecated
    public void setAutoMeasureEnabled(boolean bl) {
        this.mAutoMeasure = bl;
    }

    void setExactMeasureSpecsFrom(RecyclerView recyclerView) {
        this.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec((int)recyclerView.getWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)recyclerView.getHeight(), (int)1073741824));
    }

    public final void setItemPrefetchEnabled(boolean bl) {
        if (bl != this.mItemPrefetchEnabled) {
            this.mItemPrefetchEnabled = bl;
            this.mPrefetchMaxCountObserved = 0;
            if (this.mRecyclerView != null) {
                this.mRecyclerView.mRecycler.updateViewCacheSize();
            }
        }
    }

    void setMeasureSpecs(int n, int n2) {
        this.mWidth = View.MeasureSpec.getSize((int)n);
        this.mWidthMode = View.MeasureSpec.getMode((int)n);
        if (this.mWidthMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
            this.mWidth = 0;
        }
        this.mHeight = View.MeasureSpec.getSize((int)n2);
        this.mHeightMode = View.MeasureSpec.getMode((int)n2);
        if (this.mHeightMode == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC) {
            this.mHeight = 0;
        }
    }

    public void setMeasuredDimension(int n, int n2) {
        this.mRecyclerView.setMeasuredDimension(n, n2);
    }

    public void setMeasuredDimension(Rect rect, int n, int n2) {
        int n3 = rect.width();
        int n4 = this.getPaddingLeft();
        int n5 = this.getPaddingRight();
        int n6 = rect.height();
        int n7 = this.getPaddingTop();
        int n8 = this.getPaddingBottom();
        this.setMeasuredDimension(RecyclerView.LayoutManager.chooseSize(n, n3 + n4 + n5, this.getMinimumWidth()), RecyclerView.LayoutManager.chooseSize(n2, n6 + n7 + n8, this.getMinimumHeight()));
    }

    void setMeasuredDimensionFromChildren(int n, int n2) {
        int n3;
        int n4 = this.getChildCount();
        if (n4 == 0) {
            this.mRecyclerView.defaultOnMeasure(n, n2);
            return;
        }
        int n5 = Integer.MAX_VALUE;
        int n6 = n3 = Integer.MIN_VALUE;
        int n7 = Integer.MAX_VALUE;
        for (int i = 0; i < n4; ++i) {
            View view = this.getChildAt(i);
            Rect rect = this.mRecyclerView.mTempRect;
            this.getDecoratedBoundsWithMargins(view, rect);
            int n8 = n5;
            if (rect.left < n5) {
                n8 = rect.left;
            }
            int n9 = n3;
            if (rect.right > n3) {
                n9 = rect.right;
            }
            n3 = n7;
            if (rect.top < n7) {
                n3 = rect.top;
            }
            int n10 = n6;
            if (rect.bottom > n6) {
                n10 = rect.bottom;
            }
            n7 = n3;
            n5 = n8;
            n3 = n9;
            n6 = n10;
        }
        this.mRecyclerView.mTempRect.set(n5, n7, n3, n6);
        this.setMeasuredDimension(this.mRecyclerView.mTempRect, n, n2);
    }

    public void setMeasurementCacheEnabled(boolean bl) {
        this.mMeasurementCacheEnabled = bl;
    }

    void setRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            this.mRecyclerView = null;
            this.mChildHelper = null;
            this.mWidth = 0;
            this.mHeight = 0;
        } else {
            this.mRecyclerView = recyclerView;
            this.mChildHelper = recyclerView.mChildHelper;
            this.mWidth = recyclerView.getWidth();
            this.mHeight = recyclerView.getHeight();
        }
        this.mWidthMode = 1073741824;
        this.mHeightMode = 1073741824;
    }

    boolean shouldMeasureChild(View view, int n, int n2, RecyclerView.LayoutParams layoutParams) {
        if (!view.isLayoutRequested() && this.mMeasurementCacheEnabled && RecyclerView.LayoutManager.isMeasurementUpToDate(view.getWidth(), n, layoutParams.width) && RecyclerView.LayoutManager.isMeasurementUpToDate(view.getHeight(), n2, layoutParams.height)) {
            return false;
        }
        return true;
    }

    boolean shouldMeasureTwice() {
        return false;
    }

    boolean shouldReMeasureChild(View view, int n, int n2, RecyclerView.LayoutParams layoutParams) {
        if (this.mMeasurementCacheEnabled && RecyclerView.LayoutManager.isMeasurementUpToDate(view.getMeasuredWidth(), n, layoutParams.width) && RecyclerView.LayoutManager.isMeasurementUpToDate(view.getMeasuredHeight(), n2, layoutParams.height)) {
            return false;
        }
        return true;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int n) {
        Log.e((String)"RecyclerView", (String)"You must override smoothScrollToPosition to support smooth scrolling");
    }

    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        if (this.mSmoothScroller != null && smoothScroller != this.mSmoothScroller && this.mSmoothScroller.isRunning()) {
            this.mSmoothScroller.stop();
        }
        this.mSmoothScroller = smoothScroller;
        this.mSmoothScroller.start(this.mRecyclerView, this);
    }

    public void stopIgnoringView(View object) {
        object = RecyclerView.getChildViewHolderInt((View)object);
        object.stopIgnoring();
        object.resetInternal();
        object.addFlags(4);
    }

    void stopSmoothScroller() {
        if (this.mSmoothScroller != null) {
            this.mSmoothScroller.stop();
        }
    }

    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    public static interface LayoutPrefetchRegistry {
        public void addPosition(int var1, int var2);
    }

    public static class Properties {
        public int orientation;
        public boolean reverseLayout;
        public int spanCount;
        public boolean stackFromEnd;
    }

}
