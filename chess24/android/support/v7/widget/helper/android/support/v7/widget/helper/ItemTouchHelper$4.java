/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

class ItemTouchHelper
implements Runnable {
    final /* synthetic */ ItemTouchHelper.RecoverAnimation val$anim;
    final /* synthetic */ int val$swipeDir;

    ItemTouchHelper(ItemTouchHelper.RecoverAnimation recoverAnimation, int n) {
        this.val$anim = recoverAnimation;
        this.val$swipeDir = n;
    }

    @Override
    public void run() {
        if (ItemTouchHelper.this.mRecyclerView != null && ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() && !this.val$anim.mOverridden && this.val$anim.mViewHolder.getAdapterPosition() != -1) {
            RecyclerView.ItemAnimator itemAnimator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
            if (!(itemAnimator != null && itemAnimator.isRunning(null) || ItemTouchHelper.this.hasRunningRecoverAnim())) {
                ItemTouchHelper.this.mCallback.onSwiped(this.val$anim.mViewHolder, this.val$swipeDir);
                return;
            }
            ItemTouchHelper.this.mRecyclerView.post((Runnable)this);
        }
    }
}
