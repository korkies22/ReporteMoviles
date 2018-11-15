/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.v7.widget.GapWorker;
import android.support.v7.widget.RecyclerView;

static class GapWorker.Task {
    public int distanceToItem;
    public boolean immediate;
    public int position;
    public RecyclerView view;
    public int viewVelocity;

    GapWorker.Task() {
    }

    public void clear() {
        this.immediate = false;
        this.viewVelocity = 0;
        this.distanceToItem = 0;
        this.view = null;
        this.position = 0;
    }
}
