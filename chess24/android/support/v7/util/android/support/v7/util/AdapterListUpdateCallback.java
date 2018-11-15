/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.NonNull;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;

public final class AdapterListUpdateCallback
implements ListUpdateCallback {
    @NonNull
    private final RecyclerView.Adapter mAdapter;

    public AdapterListUpdateCallback(@NonNull RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void onChanged(int n, int n2, Object object) {
        this.mAdapter.notifyItemRangeChanged(n, n2, object);
    }

    @Override
    public void onInserted(int n, int n2) {
        this.mAdapter.notifyItemRangeInserted(n, n2);
    }

    @Override
    public void onMoved(int n, int n2) {
        this.mAdapter.notifyItemMoved(n, n2);
    }

    @Override
    public void onRemoved(int n, int n2) {
        this.mAdapter.notifyItemRangeRemoved(n, n2);
    }
}
