/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

private class RecyclerView.RecyclerViewDataObserver
extends RecyclerView.AdapterDataObserver {
    RecyclerView.RecyclerViewDataObserver() {
    }

    @Override
    public void onChanged() {
        RecyclerView.this.assertNotInLayoutOrScroll(null);
        RecyclerView.this.mState.mStructureChanged = true;
        RecyclerView.this.processDataSetCompletelyChanged(true);
        if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates()) {
            RecyclerView.this.requestLayout();
        }
    }

    @Override
    public void onItemRangeChanged(int n, int n2, Object object) {
        RecyclerView.this.assertNotInLayoutOrScroll(null);
        if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(n, n2, object)) {
            this.triggerUpdateProcessor();
        }
    }

    @Override
    public void onItemRangeInserted(int n, int n2) {
        RecyclerView.this.assertNotInLayoutOrScroll(null);
        if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(n, n2)) {
            this.triggerUpdateProcessor();
        }
    }

    @Override
    public void onItemRangeMoved(int n, int n2, int n3) {
        RecyclerView.this.assertNotInLayoutOrScroll(null);
        if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(n, n2, n3)) {
            this.triggerUpdateProcessor();
        }
    }

    @Override
    public void onItemRangeRemoved(int n, int n2) {
        RecyclerView.this.assertNotInLayoutOrScroll(null);
        if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(n, n2)) {
            this.triggerUpdateProcessor();
        }
    }

    void triggerUpdateProcessor() {
        if (RecyclerView.POST_UPDATES_ON_ANIMATION && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
            ViewCompat.postOnAnimation((View)RecyclerView.this, RecyclerView.this.mUpdateChildViewsRunnable);
            return;
        }
        RecyclerView.this.mAdapterUpdateDuringMeasure = true;
        RecyclerView.this.requestLayout();
    }
}
