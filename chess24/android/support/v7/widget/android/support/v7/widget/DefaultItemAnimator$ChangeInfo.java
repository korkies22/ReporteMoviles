/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

private static class DefaultItemAnimator.ChangeInfo {
    public int fromX;
    public int fromY;
    public RecyclerView.ViewHolder newHolder;
    public RecyclerView.ViewHolder oldHolder;
    public int toX;
    public int toY;

    private DefaultItemAnimator.ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        this.oldHolder = viewHolder;
        this.newHolder = viewHolder2;
    }

    DefaultItemAnimator.ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n, int n2, int n3, int n4) {
        this(viewHolder, viewHolder2);
        this.fromX = n;
        this.fromY = n2;
        this.toX = n3;
        this.toY = n4;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ChangeInfo{oldHolder=");
        stringBuilder.append(this.oldHolder);
        stringBuilder.append(", newHolder=");
        stringBuilder.append(this.newHolder);
        stringBuilder.append(", fromX=");
        stringBuilder.append(this.fromX);
        stringBuilder.append(", fromY=");
        stringBuilder.append(this.fromY);
        stringBuilder.append(", toX=");
        stringBuilder.append(this.toX);
        stringBuilder.append(", toY=");
        stringBuilder.append(this.toY);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
