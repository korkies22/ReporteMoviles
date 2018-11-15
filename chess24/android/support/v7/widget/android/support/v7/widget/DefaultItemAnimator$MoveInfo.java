/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

private static class DefaultItemAnimator.MoveInfo {
    public int fromX;
    public int fromY;
    public RecyclerView.ViewHolder holder;
    public int toX;
    public int toY;

    DefaultItemAnimator.MoveInfo(RecyclerView.ViewHolder viewHolder, int n, int n2, int n3, int n4) {
        this.holder = viewHolder;
        this.fromX = n;
        this.fromY = n2;
        this.toX = n3;
        this.toY = n4;
    }
}
