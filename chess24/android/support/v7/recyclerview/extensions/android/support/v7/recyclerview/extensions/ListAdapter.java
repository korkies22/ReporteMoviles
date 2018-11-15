/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.List;

public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder>
extends RecyclerView.Adapter<VH> {
    private final AsyncListDiffer<T> mHelper;

    protected ListAdapter(@NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.mHelper = new AsyncListDiffer<T>(new AdapterListUpdateCallback(this), asyncDifferConfig);
    }

    protected ListAdapter(@NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.mHelper = new AsyncListDiffer<T>(new AdapterListUpdateCallback(this), new AsyncDifferConfig.Builder<T>(itemCallback).build());
    }

    protected T getItem(int n) {
        return this.mHelper.getCurrentList().get(n);
    }

    @Override
    public int getItemCount() {
        return this.mHelper.getCurrentList().size();
    }

    public void submitList(List<T> list) {
        this.mHelper.submitList(list);
    }
}
