/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package android.support.v7.widget;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

class StaggeredGridLayoutManager.Span {
    static final int INVALID_LINE = Integer.MIN_VALUE;
    int mCachedEnd = Integer.MIN_VALUE;
    int mCachedStart = Integer.MIN_VALUE;
    int mDeletedSize = 0;
    final int mIndex;
    ArrayList<View> mViews = new ArrayList();

    StaggeredGridLayoutManager.Span(int n) {
        this.mIndex = n;
    }

    void appendToSpan(View view) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = this.getLayoutParams(view);
        layoutParams.mSpan = this;
        this.mViews.add(view);
        this.mCachedEnd = Integer.MIN_VALUE;
        if (this.mViews.size() == 1) {
            this.mCachedStart = Integer.MIN_VALUE;
        }
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
    }

    void cacheReferenceLineAndClear(boolean bl, int n) {
        int n2 = bl ? this.getEndLine(Integer.MIN_VALUE) : this.getStartLine(Integer.MIN_VALUE);
        this.clear();
        if (n2 == Integer.MIN_VALUE) {
            return;
        }
        if (bl && n2 < StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding() || !bl && n2 > StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding()) {
            return;
        }
        int n3 = n2;
        if (n != Integer.MIN_VALUE) {
            n3 = n2 + n;
        }
        this.mCachedEnd = n3;
        this.mCachedStart = n3;
    }

    void calculateCachedEnd() {
        Object object = this.mViews.get(this.mViews.size() - 1);
        StaggeredGridLayoutManager.LayoutParams layoutParams = this.getLayoutParams((View)object);
        this.mCachedEnd = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd((View)object);
        if (layoutParams.mFullSpan && (object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition())) != null && object.mGapDir == 1) {
            this.mCachedEnd += object.getGapForSpan(this.mIndex);
        }
    }

    void calculateCachedStart() {
        Object object = this.mViews.get(0);
        StaggeredGridLayoutManager.LayoutParams layoutParams = this.getLayoutParams((View)object);
        this.mCachedStart = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart((View)object);
        if (layoutParams.mFullSpan && (object = StaggeredGridLayoutManager.this.mLazySpanLookup.getFullSpanItem(layoutParams.getViewLayoutPosition())) != null && object.mGapDir == -1) {
            this.mCachedStart -= object.getGapForSpan(this.mIndex);
        }
    }

    void clear() {
        this.mViews.clear();
        this.invalidateCache();
        this.mDeletedSize = 0;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        if (StaggeredGridLayoutManager.this.mReverseLayout) {
            return this.findOneVisibleChild(this.mViews.size() - 1, -1, true);
        }
        return this.findOneVisibleChild(0, this.mViews.size(), true);
    }

    public int findFirstPartiallyVisibleItemPosition() {
        if (StaggeredGridLayoutManager.this.mReverseLayout) {
            return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
        }
        return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
    }

    public int findFirstVisibleItemPosition() {
        if (StaggeredGridLayoutManager.this.mReverseLayout) {
            return this.findOneVisibleChild(this.mViews.size() - 1, -1, false);
        }
        return this.findOneVisibleChild(0, this.mViews.size(), false);
    }

    public int findLastCompletelyVisibleItemPosition() {
        if (StaggeredGridLayoutManager.this.mReverseLayout) {
            return this.findOneVisibleChild(0, this.mViews.size(), true);
        }
        return this.findOneVisibleChild(this.mViews.size() - 1, -1, true);
    }

    public int findLastPartiallyVisibleItemPosition() {
        if (StaggeredGridLayoutManager.this.mReverseLayout) {
            return this.findOnePartiallyVisibleChild(0, this.mViews.size(), true);
        }
        return this.findOnePartiallyVisibleChild(this.mViews.size() - 1, -1, true);
    }

    public int findLastVisibleItemPosition() {
        if (StaggeredGridLayoutManager.this.mReverseLayout) {
            return this.findOneVisibleChild(0, this.mViews.size(), false);
        }
        return this.findOneVisibleChild(this.mViews.size() - 1, -1, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    int findOnePartiallyOrCompletelyVisibleChild(int n, int n2, boolean bl, boolean bl2, boolean bl3) {
        int n3 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getStartAfterPadding();
        int n4 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getEndAfterPadding();
        int n5 = n2 > n ? 1 : -1;
        while (n != n2) {
            View view = this.mViews.get(n);
            int n6 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedStart(view);
            int n7 = StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedEnd(view);
            boolean bl4 = false;
            boolean bl5 = bl3 ? n6 <= n4 : n6 < n4;
            if (bl3 ? n7 >= n3 : n7 > n3) {
                bl4 = true;
            }
            if (bl5 && bl4) {
                if (bl && bl2) {
                    if (n6 >= n3 && n7 <= n4) {
                        return StaggeredGridLayoutManager.this.getPosition(view);
                    }
                } else {
                    if (bl2) {
                        return StaggeredGridLayoutManager.this.getPosition(view);
                    }
                    if (n6 < n3 || n7 > n4) {
                        return StaggeredGridLayoutManager.this.getPosition(view);
                    }
                }
            }
            n += n5;
        }
        return -1;
    }

    int findOnePartiallyVisibleChild(int n, int n2, boolean bl) {
        return this.findOnePartiallyOrCompletelyVisibleChild(n, n2, false, false, bl);
    }

    int findOneVisibleChild(int n, int n2, boolean bl) {
        return this.findOnePartiallyOrCompletelyVisibleChild(n, n2, bl, true, false);
    }

    public int getDeletedSize() {
        return this.mDeletedSize;
    }

    int getEndLine() {
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            return this.mCachedEnd;
        }
        this.calculateCachedEnd();
        return this.mCachedEnd;
    }

    int getEndLine(int n) {
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            return this.mCachedEnd;
        }
        if (this.mViews.size() == 0) {
            return n;
        }
        this.calculateCachedEnd();
        return this.mCachedEnd;
    }

    public View getFocusableViewAfter(int n, int n2) {
        View view;
        block9 : {
            view = null;
            View view2 = null;
            if (n2 == -1) {
                int n3 = this.mViews.size();
                n2 = 0;
                do {
                    view = view2;
                    if (n2 >= n3) break block9;
                    View view3 = this.mViews.get(n2);
                    if (StaggeredGridLayoutManager.this.mReverseLayout) {
                        view = view2;
                        if (StaggeredGridLayoutManager.this.getPosition(view3) <= n) break block9;
                    }
                    if (!StaggeredGridLayoutManager.this.mReverseLayout && StaggeredGridLayoutManager.this.getPosition(view3) >= n) {
                        return view2;
                    }
                    view = view2;
                    if (view3.hasFocusable()) {
                        ++n2;
                        view2 = view3;
                        continue;
                    }
                    break block9;
                    break;
                } while (true);
            }
            n2 = this.mViews.size() - 1;
            view2 = view;
            do {
                view = view2;
                if (n2 < 0) break;
                View view4 = this.mViews.get(n2);
                if (StaggeredGridLayoutManager.this.mReverseLayout) {
                    view = view2;
                    if (StaggeredGridLayoutManager.this.getPosition(view4) >= n) break;
                }
                if (!StaggeredGridLayoutManager.this.mReverseLayout && StaggeredGridLayoutManager.this.getPosition(view4) <= n) {
                    return view2;
                }
                view = view2;
                if (!view4.hasFocusable()) break;
                --n2;
                view2 = view4;
            } while (true);
        }
        return view;
    }

    StaggeredGridLayoutManager.LayoutParams getLayoutParams(View view) {
        return (StaggeredGridLayoutManager.LayoutParams)view.getLayoutParams();
    }

    int getStartLine() {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            return this.mCachedStart;
        }
        this.calculateCachedStart();
        return this.mCachedStart;
    }

    int getStartLine(int n) {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            return this.mCachedStart;
        }
        if (this.mViews.size() == 0) {
            return n;
        }
        this.calculateCachedStart();
        return this.mCachedStart;
    }

    void invalidateCache() {
        this.mCachedStart = Integer.MIN_VALUE;
        this.mCachedEnd = Integer.MIN_VALUE;
    }

    void onOffset(int n) {
        if (this.mCachedStart != Integer.MIN_VALUE) {
            this.mCachedStart += n;
        }
        if (this.mCachedEnd != Integer.MIN_VALUE) {
            this.mCachedEnd += n;
        }
    }

    void popEnd() {
        int n = this.mViews.size();
        View view = this.mViews.remove(n - 1);
        StaggeredGridLayoutManager.LayoutParams layoutParams = this.getLayoutParams(view);
        layoutParams.mSpan = null;
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
        if (n == 1) {
            this.mCachedStart = Integer.MIN_VALUE;
        }
        this.mCachedEnd = Integer.MIN_VALUE;
    }

    void popStart() {
        View view = this.mViews.remove(0);
        StaggeredGridLayoutManager.LayoutParams layoutParams = this.getLayoutParams(view);
        layoutParams.mSpan = null;
        if (this.mViews.size() == 0) {
            this.mCachedEnd = Integer.MIN_VALUE;
        }
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            this.mDeletedSize -= StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
        this.mCachedStart = Integer.MIN_VALUE;
    }

    void prependToSpan(View view) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = this.getLayoutParams(view);
        layoutParams.mSpan = this;
        this.mViews.add(0, view);
        this.mCachedStart = Integer.MIN_VALUE;
        if (this.mViews.size() == 1) {
            this.mCachedEnd = Integer.MIN_VALUE;
        }
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            this.mDeletedSize += StaggeredGridLayoutManager.this.mPrimaryOrientation.getDecoratedMeasurement(view);
        }
    }

    void setLine(int n) {
        this.mCachedStart = n;
        this.mCachedEnd = n;
    }
}
