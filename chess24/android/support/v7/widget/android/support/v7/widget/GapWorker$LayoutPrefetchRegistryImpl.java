/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.GapWorker;
import android.support.v7.widget.RecyclerView;
import java.util.Arrays;

static class GapWorker.LayoutPrefetchRegistryImpl
implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {
    int mCount;
    int[] mPrefetchArray;
    int mPrefetchDx;
    int mPrefetchDy;

    GapWorker.LayoutPrefetchRegistryImpl() {
    }

    @Override
    public void addPosition(int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("Layout positions must be non-negative");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("Pixel distance must be non-negative");
        }
        int n3 = this.mCount * 2;
        if (this.mPrefetchArray == null) {
            this.mPrefetchArray = new int[4];
            Arrays.fill(this.mPrefetchArray, -1);
        } else if (n3 >= this.mPrefetchArray.length) {
            int[] arrn = this.mPrefetchArray;
            this.mPrefetchArray = new int[n3 * 2];
            System.arraycopy(arrn, 0, this.mPrefetchArray, 0, arrn.length);
        }
        this.mPrefetchArray[n3] = n;
        this.mPrefetchArray[n3 + 1] = n2;
        ++this.mCount;
    }

    void clearPrefetchPositions() {
        if (this.mPrefetchArray != null) {
            Arrays.fill(this.mPrefetchArray, -1);
        }
        this.mCount = 0;
    }

    void collectPrefetchPositionsFromView(RecyclerView recyclerView, boolean bl) {
        this.mCount = 0;
        if (this.mPrefetchArray != null) {
            Arrays.fill(this.mPrefetchArray, -1);
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.mLayout;
        if (recyclerView.mAdapter != null && layoutManager != null && layoutManager.isItemPrefetchEnabled()) {
            if (bl) {
                if (!recyclerView.mAdapterHelper.hasPendingUpdates()) {
                    layoutManager.collectInitialPrefetchPositions(recyclerView.mAdapter.getItemCount(), this);
                }
            } else if (!recyclerView.hasPendingAdapterUpdates()) {
                layoutManager.collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, recyclerView.mState, this);
            }
            if (this.mCount > layoutManager.mPrefetchMaxCountObserved) {
                layoutManager.mPrefetchMaxCountObserved = this.mCount;
                layoutManager.mPrefetchMaxObservedInInitialPrefetch = bl;
                recyclerView.mRecycler.updateViewCacheSize();
            }
        }
    }

    boolean lastPrefetchIncludedPosition(int n) {
        if (this.mPrefetchArray != null) {
            int n2 = this.mCount;
            for (int i = 0; i < n2 * 2; i += 2) {
                if (this.mPrefetchArray[i] != n) continue;
                return true;
            }
        }
        return false;
    }

    void setPrefetchVector(int n, int n2) {
        this.mPrefetchDx = n;
        this.mPrefetchDy = n2;
    }
}
