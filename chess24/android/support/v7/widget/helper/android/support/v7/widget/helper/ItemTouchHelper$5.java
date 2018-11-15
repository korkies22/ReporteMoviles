/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

class ItemTouchHelper
implements RecyclerView.ChildDrawingOrderCallback {
    ItemTouchHelper() {
    }

    @Override
    public int onGetChildDrawingOrder(int n, int n2) {
        int n3;
        if (ItemTouchHelper.this.mOverdrawChild == null) {
            return n2;
        }
        int n4 = n3 = ItemTouchHelper.this.mOverdrawChildPosition;
        if (n3 == -1) {
            ItemTouchHelper.this.mOverdrawChildPosition = n4 = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
        }
        if (n2 == n - 1) {
            return n4;
        }
        if (n2 < n4) {
            return n2;
        }
        return n2 + 1;
    }
}
