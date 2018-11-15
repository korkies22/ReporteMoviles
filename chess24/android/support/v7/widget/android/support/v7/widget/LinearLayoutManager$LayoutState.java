/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

static class LinearLayoutManager.LayoutState {
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

    LinearLayoutManager.LayoutState() {
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
