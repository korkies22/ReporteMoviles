/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.widget;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

public static abstract class RecyclerView.AdapterDataObserver {
    public void onChanged() {
    }

    public void onItemRangeChanged(int n, int n2) {
    }

    public void onItemRangeChanged(int n, int n2, @Nullable Object object) {
        this.onItemRangeChanged(n, n2);
    }

    public void onItemRangeInserted(int n, int n2) {
    }

    public void onItemRangeMoved(int n, int n2, int n3) {
    }

    public void onItemRangeRemoved(int n, int n2) {
    }
}
