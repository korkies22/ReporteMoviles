/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public static class RecyclerView.ItemAnimator.ItemHolderInfo {
    public int bottom;
    public int changeFlags;
    public int left;
    public int right;
    public int top;

    public RecyclerView.ItemAnimator.ItemHolderInfo setFrom(RecyclerView.ViewHolder viewHolder) {
        return this.setFrom(viewHolder, 0);
    }

    public RecyclerView.ItemAnimator.ItemHolderInfo setFrom(RecyclerView.ViewHolder viewHolder, int n) {
        viewHolder = viewHolder.itemView;
        this.left = viewHolder.getLeft();
        this.top = viewHolder.getTop();
        this.right = viewHolder.getRight();
        this.bottom = viewHolder.getBottom();
        return this;
    }
}
