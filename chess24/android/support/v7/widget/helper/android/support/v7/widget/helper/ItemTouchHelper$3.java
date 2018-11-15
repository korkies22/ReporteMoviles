/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import java.util.List;

class ItemTouchHelper
extends ItemTouchHelper.RecoverAnimation {
    final /* synthetic */ RecyclerView.ViewHolder val$prevSelected;
    final /* synthetic */ int val$swipeDir;

    ItemTouchHelper(RecyclerView.ViewHolder viewHolder, int n, int n2, float f, float f2, float f3, float f4, int n3, RecyclerView.ViewHolder viewHolder2) {
        this.val$swipeDir = n3;
        this.val$prevSelected = viewHolder2;
        super(viewHolder, n, n2, f, f2, f3, f4);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        super.onAnimationEnd(animator);
        if (this.mOverridden) {
            return;
        }
        if (this.val$swipeDir <= 0) {
            ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, this.val$prevSelected);
        } else {
            ItemTouchHelper.this.mPendingCleanup.add(this.val$prevSelected.itemView);
            this.mIsPendingCleanup = true;
            if (this.val$swipeDir > 0) {
                ItemTouchHelper.this.postDispatchSwipe(this, this.val$swipeDir);
            }
        }
        if (ItemTouchHelper.this.mOverdrawChild == this.val$prevSelected.itemView) {
            ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(this.val$prevSelected.itemView);
        }
    }
}
