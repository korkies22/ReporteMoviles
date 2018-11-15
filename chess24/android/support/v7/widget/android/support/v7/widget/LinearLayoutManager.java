/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ScrollbarHelper;
import android.support.v7.widget.ViewBoundsCheck;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LinearLayoutManager
extends RecyclerView.LayoutManager
implements ItemTouchHelper.ViewDropHandler,
RecyclerView.SmoothScroller.ScrollVectorProvider {
    static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final AnchorInfo mAnchorInfo = new AnchorInfo();
    private int mInitialPrefetchItemCount = 2;
    private boolean mLastStackFromEnd;
    private final LayoutChunkResult mLayoutChunkResult = new LayoutChunkResult();
    private LayoutState mLayoutState;
    int mOrientation = 1;
    OrientationHelper mOrientationHelper;
    SavedState mPendingSavedState = null;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    private boolean mRecycleChildrenOnDetach;
    private boolean mReverseLayout = false;
    boolean mShouldReverseLayout = false;
    private boolean mSmoothScrollbarEnabled = true;
    private boolean mStackFromEnd = false;

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int n, boolean bl) {
        this.setOrientation(n);
        this.setReverseLayout(bl);
    }

    public LinearLayoutManager(Context object, AttributeSet attributeSet, int n, int n2) {
        object = LinearLayoutManager.getProperties(object, attributeSet, n, n2);
        this.setOrientation(object.orientation);
        this.setReverseLayout(object.reverseLayout);
        this.setStackFromEnd(object.stackFromEnd);
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollExtent(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollOffset(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollRange(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
    }

    private View findFirstPartiallyOrCompletelyInvisibleChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findOnePartiallyOrCompletelyInvisibleChild(0, this.getChildCount());
    }

    private View findFirstReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findReferenceChild(recycler, state, 0, this.getChildCount(), state.getItemCount());
    }

    private View findFirstVisibleChildClosestToEnd(boolean bl, boolean bl2) {
        if (this.mShouldReverseLayout) {
            return this.findOneVisibleChild(0, this.getChildCount(), bl, bl2);
        }
        return this.findOneVisibleChild(this.getChildCount() - 1, -1, bl, bl2);
    }

    private View findFirstVisibleChildClosestToStart(boolean bl, boolean bl2) {
        if (this.mShouldReverseLayout) {
            return this.findOneVisibleChild(this.getChildCount() - 1, -1, bl, bl2);
        }
        return this.findOneVisibleChild(0, this.getChildCount(), bl, bl2);
    }

    private View findLastPartiallyOrCompletelyInvisibleChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findOnePartiallyOrCompletelyInvisibleChild(this.getChildCount() - 1, -1);
    }

    private View findLastReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findReferenceChild(recycler, state, this.getChildCount() - 1, -1, state.getItemCount());
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToEnd(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mShouldReverseLayout) {
            return this.findFirstPartiallyOrCompletelyInvisibleChild(recycler, state);
        }
        return this.findLastPartiallyOrCompletelyInvisibleChild(recycler, state);
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToStart(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mShouldReverseLayout) {
            return this.findLastPartiallyOrCompletelyInvisibleChild(recycler, state);
        }
        return this.findFirstPartiallyOrCompletelyInvisibleChild(recycler, state);
    }

    private View findReferenceChildClosestToEnd(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mShouldReverseLayout) {
            return this.findFirstReferenceChild(recycler, state);
        }
        return this.findLastReferenceChild(recycler, state);
    }

    private View findReferenceChildClosestToStart(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mShouldReverseLayout) {
            return this.findLastReferenceChild(recycler, state);
        }
        return this.findFirstReferenceChild(recycler, state);
    }

    private int fixLayoutEndGap(int n, RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n2 = this.mOrientationHelper.getEndAfterPadding() - n;
        if (n2 > 0) {
            n2 = - this.scrollBy(- n2, recycler, state);
            if (bl && (n = this.mOrientationHelper.getEndAfterPadding() - (n + n2)) > 0) {
                this.mOrientationHelper.offsetChildren(n);
                return n + n2;
            }
            return n2;
        }
        return 0;
    }

    private int fixLayoutStartGap(int n, RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n2 = n - this.mOrientationHelper.getStartAfterPadding();
        if (n2 > 0) {
            n2 = - this.scrollBy(n2, recycler, state);
            if (bl && (n = n + n2 - this.mOrientationHelper.getStartAfterPadding()) > 0) {
                this.mOrientationHelper.offsetChildren(- n);
                return n2 - n;
            }
            return n2;
        }
        return 0;
    }

    private View getChildClosestToEnd() {
        int n = this.mShouldReverseLayout ? 0 : this.getChildCount() - 1;
        return this.getChildAt(n);
    }

    private View getChildClosestToStart() {
        int n = this.mShouldReverseLayout ? this.getChildCount() - 1 : 0;
        return this.getChildAt(n);
    }

    private void layoutForPredictiveAnimations(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2) {
        if (state.willRunPredictiveAnimations() && this.getChildCount() != 0 && !state.isPreLayout()) {
            int n3;
            int n4;
            if (!this.supportsPredictiveItemAnimations()) {
                return;
            }
            List<RecyclerView.ViewHolder> list = recycler.getScrapList();
            int n5 = list.size();
            int n6 = this.getPosition(this.getChildAt(0));
            int n7 = n3 = (n4 = 0);
            int n8 = n3;
            for (n3 = n4; n3 < n5; ++n3) {
                RecyclerView.ViewHolder viewHolder = list.get(n3);
                if (viewHolder.isRemoved()) continue;
                int n9 = viewHolder.getLayoutPosition();
                n4 = 1;
                boolean bl = n9 < n6;
                if (bl != this.mShouldReverseLayout) {
                    n4 = -1;
                }
                if (n4 == -1) {
                    n8 += this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView);
                    continue;
                }
                n7 += this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView);
            }
            this.mLayoutState.mScrapList = list;
            if (n8 > 0) {
                this.updateLayoutStateToFillStart(this.getPosition(this.getChildClosestToStart()), n);
                this.mLayoutState.mExtra = n8;
                this.mLayoutState.mAvailable = 0;
                this.mLayoutState.assignPositionFromScrapList();
                this.fill(recycler, this.mLayoutState, state, false);
            }
            if (n7 > 0) {
                this.updateLayoutStateToFillEnd(this.getPosition(this.getChildClosestToEnd()), n2);
                this.mLayoutState.mExtra = n7;
                this.mLayoutState.mAvailable = 0;
                this.mLayoutState.assignPositionFromScrapList();
                this.fill(recycler, this.mLayoutState, state, false);
            }
            this.mLayoutState.mScrapList = null;
            return;
        }
    }

    private void logChildren() {
        Log.d((String)TAG, (String)"internal representation of views on the screen");
        for (int i = 0; i < this.getChildCount(); ++i) {
            View view = this.getChildAt(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("item ");
            stringBuilder.append(this.getPosition(view));
            stringBuilder.append(", coord:");
            stringBuilder.append(this.mOrientationHelper.getDecoratedStart(view));
            Log.d((String)TAG, (String)stringBuilder.toString());
        }
        Log.d((String)TAG, (String)"==============");
    }

    private void recycleByLayoutState(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (layoutState.mRecycle) {
            if (layoutState.mInfinite) {
                return;
            }
            if (layoutState.mLayoutDirection == -1) {
                this.recycleViewsFromEnd(recycler, layoutState.mScrollingOffset);
                return;
            }
            this.recycleViewsFromStart(recycler, layoutState.mScrollingOffset);
            return;
        }
    }

    private void recycleChildren(RecyclerView.Recycler recycler, int n, int n2) {
        if (n == n2) {
            return;
        }
        if (n2 > n) {
            --n2;
            while (n2 >= n) {
                this.removeAndRecycleViewAt(n2, recycler);
                --n2;
            }
        } else {
            for (int i = n; i > n2; --i) {
                this.removeAndRecycleViewAt(i, recycler);
            }
        }
    }

    private void recycleViewsFromEnd(RecyclerView.Recycler recycler, int n) {
        int n2 = this.getChildCount();
        if (n < 0) {
            return;
        }
        int n3 = this.mOrientationHelper.getEnd() - n;
        if (this.mShouldReverseLayout) {
            for (n = 0; n < n2; ++n) {
                View view = this.getChildAt(n);
                if (this.mOrientationHelper.getDecoratedStart(view) >= n3 && this.mOrientationHelper.getTransformedStartWithDecoration(view) >= n3) {
                    continue;
                }
                this.recycleChildren(recycler, 0, n);
                return;
            }
        } else {
            for (n = --n2; n >= 0; --n) {
                View view = this.getChildAt(n);
                if (this.mOrientationHelper.getDecoratedStart(view) >= n3 && this.mOrientationHelper.getTransformedStartWithDecoration(view) >= n3) {
                    continue;
                }
                this.recycleChildren(recycler, n2, n);
                return;
            }
        }
    }

    private void recycleViewsFromStart(RecyclerView.Recycler recycler, int n) {
        if (n < 0) {
            return;
        }
        int n2 = this.getChildCount();
        if (this.mShouldReverseLayout) {
            for (int i = --n2; i >= 0; --i) {
                View view = this.getChildAt(i);
                if (this.mOrientationHelper.getDecoratedEnd(view) <= n && this.mOrientationHelper.getTransformedEndWithDecoration(view) <= n) {
                    continue;
                }
                this.recycleChildren(recycler, n2, i);
                return;
            }
        } else {
            for (int i = 0; i < n2; ++i) {
                View view = this.getChildAt(i);
                if (this.mOrientationHelper.getDecoratedEnd(view) <= n && this.mOrientationHelper.getTransformedEndWithDecoration(view) <= n) {
                    continue;
                }
                this.recycleChildren(recycler, 0, i);
                return;
            }
        }
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation != 1 && this.isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout ^ true;
            return;
        }
        this.mShouldReverseLayout = this.mReverseLayout;
    }

    private boolean updateAnchorFromChildren(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo) {
        int n = this.getChildCount();
        int n2 = 0;
        if (n == 0) {
            return false;
        }
        View view = this.getFocusedChild();
        if (view != null && anchorInfo.isViewValidAsAnchor(view, state)) {
            anchorInfo.assignFromViewAndKeepVisibleRect(view, this.getPosition(view));
            return true;
        }
        if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        }
        recycler = anchorInfo.mLayoutFromEnd ? this.findReferenceChildClosestToEnd(recycler, state) : this.findReferenceChildClosestToStart(recycler, state);
        if (recycler != null) {
            anchorInfo.assignFromView((View)recycler, this.getPosition((View)recycler));
            if (!state.isPreLayout() && this.supportsPredictiveItemAnimations()) {
                if (this.mOrientationHelper.getDecoratedStart((View)recycler) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd((View)recycler) < this.mOrientationHelper.getStartAfterPadding()) {
                    n2 = 1;
                }
                if (n2 != 0) {
                    n2 = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
                    anchorInfo.mCoordinate = n2;
                }
            }
            return true;
        }
        return false;
    }

    private boolean updateAnchorFromPendingData(RecyclerView.State state, AnchorInfo anchorInfo) {
        boolean bl = state.isPreLayout();
        boolean bl2 = false;
        if (!bl) {
            if (this.mPendingScrollPosition == -1) {
                return false;
            }
            if (this.mPendingScrollPosition >= 0 && this.mPendingScrollPosition < state.getItemCount()) {
                anchorInfo.mPosition = this.mPendingScrollPosition;
                if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
                    anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
                    if (anchorInfo.mLayoutFromEnd) {
                        anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset;
                        return true;
                    }
                    anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset;
                    return true;
                }
                if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                    state = this.findViewByPosition(this.mPendingScrollPosition);
                    if (state != null) {
                        if (this.mOrientationHelper.getDecoratedMeasurement((View)state) > this.mOrientationHelper.getTotalSpace()) {
                            anchorInfo.assignCoordinateFromPadding();
                            return true;
                        }
                        if (this.mOrientationHelper.getDecoratedStart((View)state) - this.mOrientationHelper.getStartAfterPadding() < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                            anchorInfo.mLayoutFromEnd = false;
                            return true;
                        }
                        if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd((View)state) < 0) {
                            anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                            anchorInfo.mLayoutFromEnd = true;
                            return true;
                        }
                        int n = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd((View)state) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart((View)state);
                        anchorInfo.mCoordinate = n;
                        return true;
                    }
                    if (this.getChildCount() > 0) {
                        int n = this.getPosition(this.getChildAt(0));
                        bl = this.mPendingScrollPosition < n;
                        if (bl == this.mShouldReverseLayout) {
                            bl2 = true;
                        }
                        anchorInfo.mLayoutFromEnd = bl2;
                    }
                    anchorInfo.assignCoordinateFromPadding();
                    return true;
                }
                anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
                if (this.mShouldReverseLayout) {
                    anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset;
                    return true;
                }
                anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset;
                return true;
            }
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        return false;
    }

    private void updateAnchorInfoForLayout(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo) {
        if (this.updateAnchorFromPendingData(state, anchorInfo)) {
            return;
        }
        if (this.updateAnchorFromChildren(recycler, state, anchorInfo)) {
            return;
        }
        anchorInfo.assignCoordinateFromPadding();
        int n = this.mStackFromEnd ? state.getItemCount() - 1 : 0;
        anchorInfo.mPosition = n;
    }

    private void updateLayoutState(int n, int n2, boolean bl, RecyclerView.State object) {
        this.mLayoutState.mInfinite = this.resolveIsInfinite();
        this.mLayoutState.mExtra = this.getExtraLayoutSpace((RecyclerView.State)object);
        this.mLayoutState.mLayoutDirection = n;
        int n3 = -1;
        if (n == 1) {
            object = this.mLayoutState;
            object.mExtra += this.mOrientationHelper.getEndPadding();
            object = this.getChildClosestToEnd();
            LayoutState layoutState = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                n3 = 1;
            }
            layoutState.mItemDirection = n3;
            this.mLayoutState.mCurrentPosition = this.getPosition((View)object) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd((View)object);
            n = this.mOrientationHelper.getDecoratedEnd((View)object) - this.mOrientationHelper.getEndAfterPadding();
        } else {
            object = this.getChildClosestToStart();
            LayoutState layoutState = this.mLayoutState;
            layoutState.mExtra += this.mOrientationHelper.getStartAfterPadding();
            layoutState = this.mLayoutState;
            if (this.mShouldReverseLayout) {
                n3 = 1;
            }
            layoutState.mItemDirection = n3;
            this.mLayoutState.mCurrentPosition = this.getPosition((View)object) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart((View)object);
            n = - this.mOrientationHelper.getDecoratedStart((View)object) + this.mOrientationHelper.getStartAfterPadding();
        }
        this.mLayoutState.mAvailable = n2;
        if (bl) {
            object = this.mLayoutState;
            object.mAvailable -= n;
        }
        this.mLayoutState.mScrollingOffset = n;
    }

    private void updateLayoutStateToFillEnd(int n, int n2) {
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - n2;
        LayoutState layoutState = this.mLayoutState;
        int n3 = this.mShouldReverseLayout ? -1 : 1;
        layoutState.mItemDirection = n3;
        this.mLayoutState.mCurrentPosition = n;
        this.mLayoutState.mLayoutDirection = 1;
        this.mLayoutState.mOffset = n2;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo anchorInfo) {
        this.updateLayoutStateToFillEnd(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    private void updateLayoutStateToFillStart(int n, int n2) {
        this.mLayoutState.mAvailable = n2 - this.mOrientationHelper.getStartAfterPadding();
        this.mLayoutState.mCurrentPosition = n;
        LayoutState layoutState = this.mLayoutState;
        n = this.mShouldReverseLayout ? 1 : -1;
        layoutState.mItemDirection = n;
        this.mLayoutState.mLayoutDirection = -1;
        this.mLayoutState.mOffset = n2;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(AnchorInfo anchorInfo) {
        this.updateLayoutStateToFillStart(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    @Override
    public void assertNotInLayoutOrScroll(String string) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(string);
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        if (this.mOrientation == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        if (this.mOrientation == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void collectAdjacentPrefetchPositions(int n, int n2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.mOrientation != 0) {
            n = n2;
        }
        if (this.getChildCount() != 0) {
            if (n == 0) {
                return;
            }
            this.ensureLayoutState();
            n2 = n > 0 ? 1 : -1;
            this.updateLayoutState(n2, Math.abs(n), true, state);
            this.collectPrefetchPositionsForLayoutState(state, this.mLayoutState, layoutPrefetchRegistry);
            return;
        }
    }

    @Override
    public void collectInitialPrefetchPositions(int n, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        boolean bl;
        int n2;
        SavedState savedState = this.mPendingSavedState;
        int n3 = -1;
        if (savedState != null && this.mPendingSavedState.hasValidAnchor()) {
            bl = this.mPendingSavedState.mAnchorLayoutFromEnd;
            n2 = this.mPendingSavedState.mAnchorPosition;
        } else {
            this.resolveShouldLayoutReverse();
            bl = this.mShouldReverseLayout;
            n2 = this.mPendingScrollPosition == -1 ? (bl ? n - 1 : 0) : this.mPendingScrollPosition;
        }
        if (!bl) {
            n3 = 1;
        }
        int n4 = 0;
        int n5 = n2;
        for (n2 = n4; n2 < this.mInitialPrefetchItemCount && n5 >= 0 && n5 < n; n5 += n3, ++n2) {
            layoutPrefetchRegistry.addPosition(n5, 0);
        }
    }

    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int n = layoutState.mCurrentPosition;
        if (n >= 0 && n < state.getItemCount()) {
            layoutPrefetchRegistry.addPosition(n, Math.max(0, layoutState.mScrollingOffset));
        }
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    @Override
    public PointF computeScrollVectorForPosition(int n) {
        if (this.getChildCount() == 0) {
            return null;
        }
        boolean bl = false;
        int n2 = this.getPosition(this.getChildAt(0));
        int n3 = 1;
        if (n < n2) {
            bl = true;
        }
        n = n3;
        if (bl != this.mShouldReverseLayout) {
            n = -1;
        }
        if (this.mOrientation == 0) {
            return new PointF((float)n, 0.0f);
        }
        return new PointF(0.0f, (float)n);
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    int convertFocusDirectionToLayoutDirection(int n) {
        int n2 = Integer.MIN_VALUE;
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n != 130) {
                        switch (n) {
                            default: {
                                return Integer.MIN_VALUE;
                            }
                            case 2: {
                                if (this.mOrientation == 1) {
                                    return 1;
                                }
                                if (this.isLayoutRTL()) {
                                    return -1;
                                }
                                return 1;
                            }
                            case 1: 
                        }
                        if (this.mOrientation == 1) {
                            return -1;
                        }
                        if (this.isLayoutRTL()) {
                            return 1;
                        }
                        return -1;
                    }
                    if (this.mOrientation == 1) {
                        n2 = 1;
                    }
                    return n2;
                }
                if (this.mOrientation == 0) {
                    n2 = 1;
                }
                return n2;
            }
            if (this.mOrientation == 1) {
                return -1;
            }
            return Integer.MIN_VALUE;
        }
        if (this.mOrientation == 0) {
            return -1;
        }
        return Integer.MIN_VALUE;
    }

    LayoutState createLayoutState() {
        return new LayoutState();
    }

    void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = this.createLayoutState();
        }
    }

    int fill(RecyclerView.Recycler recycler, LayoutState layoutState, RecyclerView.State state, boolean bl) {
        int n = layoutState.mAvailable;
        if (layoutState.mScrollingOffset != Integer.MIN_VALUE) {
            if (layoutState.mAvailable < 0) {
                layoutState.mScrollingOffset += layoutState.mAvailable;
            }
            this.recycleByLayoutState(recycler, layoutState);
        }
        int n2 = layoutState.mAvailable + layoutState.mExtra;
        LayoutChunkResult layoutChunkResult = this.mLayoutChunkResult;
        while ((layoutState.mInfinite || n2 > 0) && layoutState.hasMore(state)) {
            int n3;
            block9 : {
                block8 : {
                    layoutChunkResult.resetInternal();
                    this.layoutChunk(recycler, state, layoutState, layoutChunkResult);
                    if (layoutChunkResult.mFinished) break;
                    layoutState.mOffset += layoutChunkResult.mConsumed * layoutState.mLayoutDirection;
                    if (!layoutChunkResult.mIgnoreConsumed || this.mLayoutState.mScrapList != null) break block8;
                    n3 = n2;
                    if (state.isPreLayout()) break block9;
                }
                layoutState.mAvailable -= layoutChunkResult.mConsumed;
                n3 = n2 - layoutChunkResult.mConsumed;
            }
            if (layoutState.mScrollingOffset != Integer.MIN_VALUE) {
                layoutState.mScrollingOffset += layoutChunkResult.mConsumed;
                if (layoutState.mAvailable < 0) {
                    layoutState.mScrollingOffset += layoutState.mAvailable;
                }
                this.recycleByLayoutState(recycler, layoutState);
            }
            n2 = n3;
            if (!bl) continue;
            n2 = n3;
            if (!layoutChunkResult.mFocusable) continue;
        }
        return n - layoutState.mAvailable;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View view = this.findOneVisibleChild(0, this.getChildCount(), true, false);
        if (view == null) {
            return -1;
        }
        return this.getPosition(view);
    }

    public int findFirstVisibleItemPosition() {
        View view = this.findOneVisibleChild(0, this.getChildCount(), false, true);
        if (view == null) {
            return -1;
        }
        return this.getPosition(view);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View view = this.findOneVisibleChild(this.getChildCount() - 1, -1, true, false);
        if (view == null) {
            return -1;
        }
        return this.getPosition(view);
    }

    public int findLastVisibleItemPosition() {
        View view = this.findOneVisibleChild(this.getChildCount() - 1, -1, false, true);
        if (view == null) {
            return -1;
        }
        return this.getPosition(view);
    }

    View findOnePartiallyOrCompletelyInvisibleChild(int n, int n2) {
        int n3;
        this.ensureLayoutState();
        int n4 = n2 > n ? 1 : (n2 < n ? -1 : 0);
        if (n4 == 0) {
            return this.getChildAt(n);
        }
        if (this.mOrientationHelper.getDecoratedStart(this.getChildAt(n)) < this.mOrientationHelper.getStartAfterPadding()) {
            n4 = 16644;
            n3 = 16388;
        } else {
            n4 = 4161;
            n3 = 4097;
        }
        if (this.mOrientation == 0) {
            return this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
        }
        return this.mVerticalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
    }

    View findOneVisibleChild(int n, int n2, boolean bl, boolean bl2) {
        this.ensureLayoutState();
        int n3 = 320;
        int n4 = bl ? 24579 : 320;
        if (!bl2) {
            n3 = 0;
        }
        if (this.mOrientation == 0) {
            return this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
        }
        return this.mVerticalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
    }

    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2, int n3) {
        this.ensureLayoutState();
        int n4 = this.mOrientationHelper.getStartAfterPadding();
        int n5 = this.mOrientationHelper.getEndAfterPadding();
        int n6 = n2 > n ? 1 : -1;
        state = null;
        recycler = null;
        while (n != n2) {
            View view = this.getChildAt(n);
            int n7 = this.getPosition(view);
            RecyclerView.State state2 = state;
            RecyclerView.Recycler recycler2 = recycler;
            if (n7 >= 0) {
                state2 = state;
                recycler2 = recycler;
                if (n7 < n3) {
                    if (((RecyclerView.LayoutParams)view.getLayoutParams()).isItemRemoved()) {
                        state2 = state;
                        recycler2 = recycler;
                        if (recycler == null) {
                            recycler2 = view;
                            state2 = state;
                        }
                    } else {
                        if (this.mOrientationHelper.getDecoratedStart(view) < n5 && this.mOrientationHelper.getDecoratedEnd(view) >= n4) {
                            return view;
                        }
                        state2 = state;
                        recycler2 = recycler;
                        if (state == null) {
                            state2 = view;
                            recycler2 = recycler;
                        }
                    }
                }
            }
            n += n6;
            state = state2;
            recycler = recycler2;
        }
        if (state != null) {
            return state;
        }
        return recycler;
    }

    @Override
    public View findViewByPosition(int n) {
        View view;
        int n2 = this.getChildCount();
        if (n2 == 0) {
            return null;
        }
        int n3 = n - this.getPosition(this.getChildAt(0));
        if (n3 >= 0 && n3 < n2 && this.getPosition(view = this.getChildAt(n3)) == n) {
            return view;
        }
        return super.findViewByPosition(n);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (state.hasTargetScrollPosition()) {
            return this.mOrientationHelper.getTotalSpace();
        }
        return 0;
    }

    public int getInitialPrefetchItemCount() {
        return this.mInitialPrefetchItemCount;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    protected boolean isLayoutRTL() {
        if (this.getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State object, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int n;
        int n2;
        int n3;
        int n4;
        if ((recycler = layoutState.next(recycler)) == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        object = (RecyclerView.LayoutParams)recycler.getLayoutParams();
        if (layoutState.mScrapList == null) {
            boolean bl = this.mShouldReverseLayout;
            boolean bl2 = layoutState.mLayoutDirection == -1;
            if (bl == bl2) {
                this.addView((View)recycler);
            } else {
                this.addView((View)recycler, 0);
            }
        } else {
            boolean bl = this.mShouldReverseLayout;
            boolean bl3 = layoutState.mLayoutDirection == -1;
            if (bl == bl3) {
                this.addDisappearingView((View)recycler);
            } else {
                this.addDisappearingView((View)recycler, 0);
            }
        }
        this.measureChildWithMargins((View)recycler, 0, 0);
        layoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement((View)recycler);
        if (this.mOrientation == 1) {
            if (this.isLayoutRTL()) {
                n2 = this.getWidth() - this.getPaddingRight();
                n = n2 - this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
            } else {
                n = this.getPaddingLeft();
                n2 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler) + n;
            }
            if (layoutState.mLayoutDirection == -1) {
                n4 = layoutState.mOffset;
                int n5 = layoutState.mOffset - layoutChunkResult.mConsumed;
                n3 = n2;
                n2 = n5;
            } else {
                int n6 = layoutState.mOffset;
                n4 = layoutState.mOffset + layoutChunkResult.mConsumed;
                n3 = n2;
                n2 = n6;
            }
        } else {
            n3 = this.getPaddingTop();
            n2 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler) + n3;
            if (layoutState.mLayoutDirection == -1) {
                n4 = layoutState.mOffset;
                n = layoutState.mOffset;
                int n7 = layoutChunkResult.mConsumed;
                int n8 = n2;
                n -= n7;
                n2 = n3;
                n3 = n4;
                n4 = n8;
            } else {
                int n9 = layoutState.mOffset;
                int n10 = layoutState.mOffset + layoutChunkResult.mConsumed;
                n = n3;
                n4 = n2;
                n3 = n10;
                n2 = n;
                n = n9;
            }
        }
        this.layoutDecoratedWithMargins((View)recycler, n, n2, n3, n4);
        if (object.isItemRemoved() || object.isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = recycler.hasFocusable();
    }

    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo, int n) {
    }

    @Override
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (this.mRecycleChildrenOnDetach) {
            this.removeAndRecycleAllViews(recycler);
            recycler.clear();
        }
    }

    @Override
    public View onFocusSearchFailed(View view, int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.resolveShouldLayoutReverse();
        if (this.getChildCount() == 0) {
            return null;
        }
        if ((n = this.convertFocusDirectionToLayoutDirection(n)) == Integer.MIN_VALUE) {
            return null;
        }
        this.ensureLayoutState();
        this.ensureLayoutState();
        this.updateLayoutState(n, (int)(0.33333334f * (float)this.mOrientationHelper.getTotalSpace()), false, state);
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
        this.mLayoutState.mRecycle = false;
        this.fill(recycler, this.mLayoutState, state, true);
        view = n == -1 ? this.findPartiallyOrCompletelyInvisibleChildClosestToStart(recycler, state) : this.findPartiallyOrCompletelyInvisibleChildClosestToEnd(recycler, state);
        recycler = n == -1 ? this.getChildClosestToStart() : this.getChildClosestToEnd();
        if (recycler.hasFocusable()) {
            if (view == null) {
                return null;
            }
            return recycler;
        }
        return view;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.getChildCount() > 0) {
            accessibilityEvent.setFromIndex(this.findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(this.findLastVisibleItemPosition());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int n;
        Object object = this.mPendingSavedState;
        int n2 = -1;
        if ((object != null || this.mPendingScrollPosition != -1) && state.getItemCount() == 0) {
            this.removeAndRecycleAllViews(recycler);
            return;
        }
        if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
        }
        this.ensureLayoutState();
        this.mLayoutState.mRecycle = false;
        this.resolveShouldLayoutReverse();
        object = this.getFocusedChild();
        if (this.mAnchorInfo.mValid && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null) {
            if (object != null && (this.mOrientationHelper.getDecoratedStart((View)object) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd((View)object) <= this.mOrientationHelper.getStartAfterPadding())) {
                this.mAnchorInfo.assignFromViewAndKeepVisibleRect((View)object, this.getPosition((View)object));
            }
        } else {
            this.mAnchorInfo.reset();
            this.mAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout ^ this.mStackFromEnd;
            this.updateAnchorInfoForLayout(recycler, state, this.mAnchorInfo);
            this.mAnchorInfo.mValid = true;
        }
        int n3 = this.getExtraLayoutSpace(state);
        if (this.mLayoutState.mLastScrollDelta >= 0) {
            n = n3;
            n3 = 0;
        } else {
            n = 0;
        }
        int n4 = n3 + this.mOrientationHelper.getStartAfterPadding();
        int n5 = n + this.mOrientationHelper.getEndPadding();
        n = n4;
        n3 = n5;
        if (state.isPreLayout()) {
            n = n4;
            n3 = n5;
            if (this.mPendingScrollPosition != -1) {
                n = n4;
                n3 = n5;
                if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                    object = this.findViewByPosition(this.mPendingScrollPosition);
                    n = n4;
                    n3 = n5;
                    if (object != null) {
                        if (this.mShouldReverseLayout) {
                            n = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd((View)object) - this.mPendingScrollPositionOffset;
                        } else {
                            n = this.mOrientationHelper.getDecoratedStart((View)object);
                            n3 = this.mOrientationHelper.getStartAfterPadding();
                            n = this.mPendingScrollPositionOffset - (n - n3);
                        }
                        if (n > 0) {
                            n = n4 + n;
                            n3 = n5;
                        } else {
                            n3 = n5 - n;
                            n = n4;
                        }
                    }
                }
            }
        }
        if (this.mAnchorInfo.mLayoutFromEnd ? this.mShouldReverseLayout : !this.mShouldReverseLayout) {
            n2 = 1;
        }
        this.onAnchorReady(recycler, state, this.mAnchorInfo, n2);
        this.detachAndScrapAttachedViews(recycler);
        this.mLayoutState.mInfinite = this.resolveIsInfinite();
        this.mLayoutState.mIsPreLayout = state.isPreLayout();
        if (this.mAnchorInfo.mLayoutFromEnd) {
            this.updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.mExtra = n;
            this.fill(recycler, this.mLayoutState, state, false);
            n2 = this.mLayoutState.mOffset;
            n5 = this.mLayoutState.mCurrentPosition;
            n = n3;
            if (this.mLayoutState.mAvailable > 0) {
                n = n3 + this.mLayoutState.mAvailable;
            }
            this.updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.mExtra = n;
            object = this.mLayoutState;
            object.mCurrentPosition += this.mLayoutState.mItemDirection;
            this.fill(recycler, this.mLayoutState, state, false);
            n4 = this.mLayoutState.mOffset;
            n3 = n2;
            n = n4;
            if (this.mLayoutState.mAvailable > 0) {
                n = this.mLayoutState.mAvailable;
                this.updateLayoutStateToFillStart(n5, n2);
                this.mLayoutState.mExtra = n;
                this.fill(recycler, this.mLayoutState, state, false);
                n3 = this.mLayoutState.mOffset;
                n = n4;
            }
        } else {
            this.updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.mExtra = n3;
            this.fill(recycler, this.mLayoutState, state, false);
            n2 = this.mLayoutState.mOffset;
            n5 = this.mLayoutState.mCurrentPosition;
            n3 = n;
            if (this.mLayoutState.mAvailable > 0) {
                n3 = n + this.mLayoutState.mAvailable;
            }
            this.updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.mExtra = n3;
            object = this.mLayoutState;
            object.mCurrentPosition += this.mLayoutState.mItemDirection;
            this.fill(recycler, this.mLayoutState, state, false);
            n3 = n4 = this.mLayoutState.mOffset;
            n = n2;
            if (this.mLayoutState.mAvailable > 0) {
                n = this.mLayoutState.mAvailable;
                this.updateLayoutStateToFillEnd(n5, n2);
                this.mLayoutState.mExtra = n;
                this.fill(recycler, this.mLayoutState, state, false);
                n = this.mLayoutState.mOffset;
                n3 = n4;
            }
        }
        n2 = n3;
        n4 = n;
        if (this.getChildCount() > 0) {
            if (this.mShouldReverseLayout ^ this.mStackFromEnd) {
                n4 = this.fixLayoutEndGap(n, recycler, state, true);
                n2 = n3 + n4;
                n3 = this.fixLayoutStartGap(n2, recycler, state, false);
                n2 += n3;
                n4 = n + n4 + n3;
            } else {
                n2 = this.fixLayoutStartGap(n3, recycler, state, true);
                n4 = this.fixLayoutEndGap(n += n2, recycler, state, false);
                n2 = n3 + n2 + n4;
                n4 = n + n4;
            }
        }
        this.layoutForPredictiveAnimations(recycler, state, n2, n4);
        if (!state.isPreLayout()) {
            this.mOrientationHelper.onLayoutComplete();
        } else {
            this.mAnchorInfo.reset();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mAnchorInfo.reset();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState)parcelable;
            this.requestLayout();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        if (this.getChildCount() > 0) {
            boolean bl;
            this.ensureLayoutState();
            savedState.mAnchorLayoutFromEnd = bl = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            if (bl) {
                View view = this.getChildClosestToEnd();
                savedState.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view);
                savedState.mAnchorPosition = this.getPosition(view);
                return savedState;
            }
            View view = this.getChildClosestToStart();
            savedState.mAnchorPosition = this.getPosition(view);
            savedState.mAnchorOffset = this.mOrientationHelper.getDecoratedStart(view) - this.mOrientationHelper.getStartAfterPadding();
            return savedState;
        }
        savedState.invalidateAnchor();
        return savedState;
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    @Override
    public void prepareForDrop(View view, View view2, int n, int n2) {
        this.assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        this.ensureLayoutState();
        this.resolveShouldLayoutReverse();
        n = this.getPosition(view);
        n2 = this.getPosition(view2);
        n = n < n2 ? 1 : -1;
        if (this.mShouldReverseLayout) {
            if (n == 1) {
                this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart(view2) + this.mOrientationHelper.getDecoratedMeasurement(view)));
                return;
            }
            this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view2));
            return;
        }
        if (n == -1) {
            this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getDecoratedStart(view2));
            return;
        }
        this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getDecoratedEnd(view2) - this.mOrientationHelper.getDecoratedMeasurement(view));
    }

    boolean resolveIsInfinite() {
        if (this.mOrientationHelper.getMode() == 0 && this.mOrientationHelper.getEnd() == 0) {
            return true;
        }
        return false;
    }

    int scrollBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.getChildCount() != 0) {
            if (n == 0) {
                return 0;
            }
            this.mLayoutState.mRecycle = true;
            this.ensureLayoutState();
            int n2 = n > 0 ? 1 : -1;
            int n3 = Math.abs(n);
            this.updateLayoutState(n2, n3, true, state);
            int n4 = this.mLayoutState.mScrollingOffset + this.fill(recycler, this.mLayoutState, state, false);
            if (n4 < 0) {
                return 0;
            }
            if (n3 > n4) {
                n = n2 * n4;
            }
            this.mOrientationHelper.offsetChildren(- n);
            this.mLayoutState.mLastScrollDelta = n;
            return n;
        }
        return 0;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return 0;
        }
        return this.scrollBy(n, recycler, state);
    }

    @Override
    public void scrollToPosition(int n) {
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        this.requestLayout();
    }

    public void scrollToPositionWithOffset(int n, int n2) {
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = n2;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        this.requestLayout();
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return 0;
        }
        return this.scrollBy(n, recycler, state);
    }

    public void setInitialPrefetchItemCount(int n) {
        this.mInitialPrefetchItemCount = n;
    }

    public void setOrientation(int n) {
        if (n != 0 && n != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid orientation:");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.assertNotInLayoutOrScroll(null);
        if (n != this.mOrientation || this.mOrientationHelper == null) {
            this.mAnchorInfo.mOrientationHelper = this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, n);
            this.mOrientation = n;
            this.requestLayout();
        }
    }

    public void setRecycleChildrenOnDetach(boolean bl) {
        this.mRecycleChildrenOnDetach = bl;
    }

    public void setReverseLayout(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (bl == this.mReverseLayout) {
            return;
        }
        this.mReverseLayout = bl;
        this.requestLayout();
    }

    public void setSmoothScrollbarEnabled(boolean bl) {
        this.mSmoothScrollbarEnabled = bl;
    }

    public void setStackFromEnd(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd == bl) {
            return;
        }
        this.mStackFromEnd = bl;
        this.requestLayout();
    }

    @Override
    boolean shouldMeasureTwice() {
        if (this.getHeightMode() != 1073741824 && this.getWidthMode() != 1073741824 && this.hasFlexibleChildInBothOrientations()) {
            return true;
        }
        return false;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView object, RecyclerView.State state, int n) {
        object = new LinearSmoothScroller(object.getContext());
        object.setTargetPosition(n);
        this.startSmoothScroll((RecyclerView.SmoothScroller)object);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd) {
            return true;
        }
        return false;
    }

    void validateChildOrder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("validating child count ");
        stringBuilder.append(this.getChildCount());
        Log.d((String)TAG, (String)stringBuilder.toString());
        if (this.getChildCount() < 1) {
            return;
        }
        boolean bl = false;
        boolean bl2 = false;
        int n = this.getPosition(this.getChildAt(0));
        int n2 = this.mOrientationHelper.getDecoratedStart(this.getChildAt(0));
        if (this.mShouldReverseLayout) {
            for (int i = 1; i < this.getChildCount(); ++i) {
                stringBuilder = this.getChildAt(i);
                int n3 = this.getPosition((View)stringBuilder);
                int n4 = this.mOrientationHelper.getDecoratedStart((View)stringBuilder);
                if (n3 < n) {
                    this.logChildren();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("detected invalid position. loc invalid? ");
                    if (n4 < n2) {
                        bl2 = true;
                    }
                    stringBuilder.append(bl2);
                    throw new RuntimeException(stringBuilder.toString());
                }
                if (n4 <= n2) continue;
                this.logChildren();
                throw new RuntimeException("detected invalid location");
            }
        } else {
            for (int i = 1; i < this.getChildCount(); ++i) {
                stringBuilder = this.getChildAt(i);
                int n5 = this.getPosition((View)stringBuilder);
                int n6 = this.mOrientationHelper.getDecoratedStart((View)stringBuilder);
                if (n5 < n) {
                    this.logChildren();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("detected invalid position. loc invalid? ");
                    bl2 = bl;
                    if (n6 < n2) {
                        bl2 = true;
                    }
                    stringBuilder.append(bl2);
                    throw new RuntimeException(stringBuilder.toString());
                }
                if (n6 >= n2) continue;
                this.logChildren();
                throw new RuntimeException("detected invalid location");
            }
        }
    }

    static class AnchorInfo {
        int mCoordinate;
        boolean mLayoutFromEnd;
        OrientationHelper mOrientationHelper;
        int mPosition;
        boolean mValid;

        AnchorInfo() {
            this.reset();
        }

        void assignCoordinateFromPadding() {
            int n = this.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = n;
        }

        public void assignFromView(View view, int n) {
            this.mCoordinate = this.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd(view) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart(view);
            this.mPosition = n;
        }

        public void assignFromViewAndKeepVisibleRect(View view, int n) {
            int n2 = this.mOrientationHelper.getTotalSpaceChange();
            if (n2 >= 0) {
                this.assignFromView(view, n);
                return;
            }
            this.mPosition = n;
            if (this.mLayoutFromEnd) {
                n = this.mOrientationHelper.getEndAfterPadding() - n2 - this.mOrientationHelper.getDecoratedEnd(view);
                this.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - n;
                if (n > 0) {
                    n2 = this.mOrientationHelper.getDecoratedMeasurement(view);
                    int n3 = this.mCoordinate;
                    int n4 = this.mOrientationHelper.getStartAfterPadding();
                    if ((n2 = n3 - n2 - (n4 + Math.min(this.mOrientationHelper.getDecoratedStart(view) - n4, 0))) < 0) {
                        this.mCoordinate += Math.min(n, - n2);
                        return;
                    }
                }
            } else {
                int n5 = this.mOrientationHelper.getDecoratedStart(view);
                n = n5 - this.mOrientationHelper.getStartAfterPadding();
                this.mCoordinate = n5;
                if (n > 0) {
                    int n6 = this.mOrientationHelper.getDecoratedMeasurement(view);
                    int n7 = this.mOrientationHelper.getEndAfterPadding();
                    int n8 = this.mOrientationHelper.getDecoratedEnd(view);
                    n2 = this.mOrientationHelper.getEndAfterPadding() - Math.min(0, n7 - n2 - n8) - (n5 + n6);
                    if (n2 < 0) {
                        this.mCoordinate -= Math.min(n, - n2);
                    }
                }
            }
        }

        boolean isViewValidAsAnchor(View object, RecyclerView.State state) {
            if (!(object = (RecyclerView.LayoutParams)object.getLayoutParams()).isItemRemoved() && object.getViewLayoutPosition() >= 0 && object.getViewLayoutPosition() < state.getItemCount()) {
                return true;
            }
            return false;
        }

        void reset() {
            this.mPosition = -1;
            this.mCoordinate = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mValid = false;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AnchorInfo{mPosition=");
            stringBuilder.append(this.mPosition);
            stringBuilder.append(", mCoordinate=");
            stringBuilder.append(this.mCoordinate);
            stringBuilder.append(", mLayoutFromEnd=");
            stringBuilder.append(this.mLayoutFromEnd);
            stringBuilder.append(", mValid=");
            stringBuilder.append(this.mValid);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    protected static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        void resetInternal() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    static class LayoutState {
        static final int INVALID_LAYOUT = Integer.MIN_VALUE;
        static final int ITEM_DIRECTION_HEAD = -1;
        static final int ITEM_DIRECTION_TAIL = 1;
        static final int LAYOUT_END = 1;
        static final int LAYOUT_START = -1;
        static final int SCROLLING_OFFSET_NaN = Integer.MIN_VALUE;
        static final String TAG = "LLM#LayoutState";
        int mAvailable;
        int mCurrentPosition;
        int mExtra = 0;
        boolean mInfinite;
        boolean mIsPreLayout = false;
        int mItemDirection;
        int mLastScrollDelta;
        int mLayoutDirection;
        int mOffset;
        boolean mRecycle = true;
        List<RecyclerView.ViewHolder> mScrapList = null;
        int mScrollingOffset;

        LayoutState() {
        }

        private View nextViewFromScrapList() {
            int n = this.mScrapList.size();
            for (int i = 0; i < n; ++i) {
                View view = this.mScrapList.get((int)i).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                if (layoutParams.isItemRemoved() || this.mCurrentPosition != layoutParams.getViewLayoutPosition()) continue;
                this.assignPositionFromScrapList(view);
                return view;
            }
            return null;
        }

        public void assignPositionFromScrapList() {
            this.assignPositionFromScrapList(null);
        }

        public void assignPositionFromScrapList(View view) {
            if ((view = this.nextViewInLimitedList(view)) == null) {
                this.mCurrentPosition = -1;
                return;
            }
            this.mCurrentPosition = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        boolean hasMore(RecyclerView.State state) {
            if (this.mCurrentPosition >= 0 && this.mCurrentPosition < state.getItemCount()) {
                return true;
            }
            return false;
        }

        void log() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("avail:");
            stringBuilder.append(this.mAvailable);
            stringBuilder.append(", ind:");
            stringBuilder.append(this.mCurrentPosition);
            stringBuilder.append(", dir:");
            stringBuilder.append(this.mItemDirection);
            stringBuilder.append(", offset:");
            stringBuilder.append(this.mOffset);
            stringBuilder.append(", layoutDir:");
            stringBuilder.append(this.mLayoutDirection);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }

        View next(RecyclerView.Recycler recycler) {
            if (this.mScrapList != null) {
                return this.nextViewFromScrapList();
            }
            recycler = recycler.getViewForPosition(this.mCurrentPosition);
            this.mCurrentPosition += this.mItemDirection;
            return recycler;
        }

        public View nextViewInLimitedList(View view) {
            int n = this.mScrapList.size();
            View view2 = null;
            int n2 = Integer.MAX_VALUE;
            for (int i = 0; i < n; ++i) {
                View view3 = this.mScrapList.get((int)i).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view3.getLayoutParams();
                View view4 = view2;
                int n3 = n2;
                if (view3 != view) {
                    if (layoutParams.isItemRemoved()) {
                        view4 = view2;
                        n3 = n2;
                    } else {
                        int n4 = (layoutParams.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
                        if (n4 < 0) {
                            view4 = view2;
                            n3 = n2;
                        } else {
                            view4 = view2;
                            n3 = n2;
                            if (n4 < n2) {
                                if (n4 == 0) {
                                    return view3;
                                }
                                view4 = view3;
                                n3 = n4;
                            }
                        }
                    }
                }
                view2 = view4;
                n2 = n3;
            }
            return view2;
        }
    }

    @RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorOffset;
        int mAnchorPosition;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mAnchorOffset = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.mAnchorLayoutFromEnd = bl;
        }

        public SavedState(SavedState savedState) {
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mAnchorOffset = savedState.mAnchorOffset;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
        }

        public int describeContents() {
            return 0;
        }

        boolean hasValidAnchor() {
            if (this.mAnchorPosition >= 0) {
                return true;
            }
            return false;
        }

        void invalidateAnchor() {
            this.mAnchorPosition = -1;
        }

        public void writeToParcel(Parcel parcel, int n) {
            RuntimeException runtimeException;
            super("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:269)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
            throw runtimeException;
        }

    }

}
