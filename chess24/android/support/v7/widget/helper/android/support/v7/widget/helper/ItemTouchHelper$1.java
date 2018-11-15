/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class ItemTouchHelper
implements Runnable {
    ItemTouchHelper() {
    }

    @Override
    public void run() {
        if (ItemTouchHelper.this.mSelected != null && ItemTouchHelper.this.scrollIfNecessary()) {
            if (ItemTouchHelper.this.mSelected != null) {
                ItemTouchHelper.this.moveIfNecessary(ItemTouchHelper.this.mSelected);
            }
            ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
            ViewCompat.postOnAnimation((View)ItemTouchHelper.this.mRecyclerView, this);
        }
    }
}
