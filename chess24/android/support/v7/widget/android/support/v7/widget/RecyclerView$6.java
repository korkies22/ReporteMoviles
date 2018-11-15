/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.widget.AdapterHelper;
import android.support.v7.widget.ChildHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class RecyclerView
implements AdapterHelper.Callback {
    RecyclerView() {
    }

    void dispatchUpdate(AdapterHelper.UpdateOp updateOp) {
        int n = updateOp.cmd;
        if (n != 4) {
            if (n != 8) {
                switch (n) {
                    default: {
                        return;
                    }
                    case 2: {
                        RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                        return;
                    }
                    case 1: 
                }
                RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, updateOp.positionStart, updateOp.itemCount);
                return;
            }
            RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, 1);
            return;
        }
        RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, updateOp.positionStart, updateOp.itemCount, updateOp.payload);
    }

    @Override
    public RecyclerView.ViewHolder findViewHolder(int n) {
        RecyclerView.ViewHolder viewHolder = RecyclerView.this.findViewHolderForPosition(n, true);
        if (viewHolder == null) {
            return null;
        }
        if (RecyclerView.this.mChildHelper.isHidden(viewHolder.itemView)) {
            return null;
        }
        return viewHolder;
    }

    @Override
    public void markViewHoldersUpdated(int n, int n2, Object object) {
        RecyclerView.this.viewRangeUpdate(n, n2, object);
        RecyclerView.this.mItemsChanged = true;
    }

    @Override
    public void offsetPositionsForAdd(int n, int n2) {
        RecyclerView.this.offsetPositionRecordsForInsert(n, n2);
        RecyclerView.this.mItemsAddedOrRemoved = true;
    }

    @Override
    public void offsetPositionsForMove(int n, int n2) {
        RecyclerView.this.offsetPositionRecordsForMove(n, n2);
        RecyclerView.this.mItemsAddedOrRemoved = true;
    }

    @Override
    public void offsetPositionsForRemovingInvisible(int n, int n2) {
        RecyclerView.this.offsetPositionRecordsForRemove(n, n2, true);
        RecyclerView.this.mItemsAddedOrRemoved = true;
        RecyclerView.State state = RecyclerView.this.mState;
        state.mDeletedInvisibleItemCountSincePreviousLayout += n2;
    }

    @Override
    public void offsetPositionsForRemovingLaidOutOrNewView(int n, int n2) {
        RecyclerView.this.offsetPositionRecordsForRemove(n, n2, false);
        RecyclerView.this.mItemsAddedOrRemoved = true;
    }

    @Override
    public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {
        this.dispatchUpdate(updateOp);
    }

    @Override
    public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {
        this.dispatchUpdate(updateOp);
    }
}
