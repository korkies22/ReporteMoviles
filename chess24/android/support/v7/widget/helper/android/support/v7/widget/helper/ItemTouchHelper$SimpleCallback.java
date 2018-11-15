/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget.helper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public static abstract class ItemTouchHelper.SimpleCallback
extends ItemTouchHelper.Callback {
    private int mDefaultDragDirs;
    private int mDefaultSwipeDirs;

    public ItemTouchHelper.SimpleCallback(int n, int n2) {
        this.mDefaultSwipeDirs = n2;
        this.mDefaultDragDirs = n;
    }

    public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return this.mDefaultDragDirs;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return ItemTouchHelper.SimpleCallback.makeMovementFlags(this.getDragDirs(recyclerView, viewHolder), this.getSwipeDirs(recyclerView, viewHolder));
    }

    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return this.mDefaultSwipeDirs;
    }

    public void setDefaultDragDirs(int n) {
        this.mDefaultDragDirs = n;
    }

    public void setDefaultSwipeDirs(int n) {
        this.mDefaultSwipeDirs = n;
    }
}
