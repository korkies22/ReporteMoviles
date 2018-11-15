/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.TraceCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.List;

public static abstract class RecyclerView.Adapter<VH extends RecyclerView.ViewHolder> {
    private boolean mHasStableIds = false;
    private final RecyclerView.AdapterDataObservable mObservable = new RecyclerView.AdapterDataObservable();

    public final void bindViewHolder(@NonNull VH object, int n) {
        object.mPosition = n;
        if (this.hasStableIds()) {
            object.mItemId = this.getItemId(n);
        }
        object.setFlags(1, 519);
        TraceCompat.beginSection(RecyclerView.TRACE_BIND_VIEW_TAG);
        this.onBindViewHolder(object, n, object.getUnmodifiedPayloads());
        object.clearPayload();
        object = object.itemView.getLayoutParams();
        if (object instanceof RecyclerView.LayoutParams) {
            ((RecyclerView.LayoutParams)object).mInsetsDirty = true;
        }
        TraceCompat.endSection();
    }

    public final VH createViewHolder(@NonNull ViewGroup object, int n) {
        try {
            TraceCompat.beginSection(RecyclerView.TRACE_CREATE_VIEW_TAG);
            object = this.onCreateViewHolder((ViewGroup)object, n);
            if (object.itemView.getParent() != null) {
                throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
            }
            object.mItemViewType = n;
            return (VH)object;
        }
        finally {
            TraceCompat.endSection();
        }
    }

    public abstract int getItemCount();

    public long getItemId(int n) {
        return -1L;
    }

    public int getItemViewType(int n) {
        return 0;
    }

    public final boolean hasObservers() {
        return this.mObservable.hasObservers();
    }

    public final boolean hasStableIds() {
        return this.mHasStableIds;
    }

    public final void notifyDataSetChanged() {
        this.mObservable.notifyChanged();
    }

    public final void notifyItemChanged(int n) {
        this.mObservable.notifyItemRangeChanged(n, 1);
    }

    public final void notifyItemChanged(int n, @Nullable Object object) {
        this.mObservable.notifyItemRangeChanged(n, 1, object);
    }

    public final void notifyItemInserted(int n) {
        this.mObservable.notifyItemRangeInserted(n, 1);
    }

    public final void notifyItemMoved(int n, int n2) {
        this.mObservable.notifyItemMoved(n, n2);
    }

    public final void notifyItemRangeChanged(int n, int n2) {
        this.mObservable.notifyItemRangeChanged(n, n2);
    }

    public final void notifyItemRangeChanged(int n, int n2, @Nullable Object object) {
        this.mObservable.notifyItemRangeChanged(n, n2, object);
    }

    public final void notifyItemRangeInserted(int n, int n2) {
        this.mObservable.notifyItemRangeInserted(n, n2);
    }

    public final void notifyItemRangeRemoved(int n, int n2) {
        this.mObservable.notifyItemRangeRemoved(n, n2);
    }

    public final void notifyItemRemoved(int n) {
        this.mObservable.notifyItemRangeRemoved(n, 1);
    }

    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    }

    public abstract void onBindViewHolder(@NonNull VH var1, int var2);

    public void onBindViewHolder(@NonNull VH VH, int n, @NonNull List<Object> list) {
        this.onBindViewHolder(VH, n);
    }

    @NonNull
    public abstract VH onCreateViewHolder(@NonNull ViewGroup var1, int var2);

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    }

    public boolean onFailedToRecycleView(@NonNull VH VH) {
        return false;
    }

    public void onViewAttachedToWindow(@NonNull VH VH) {
    }

    public void onViewDetachedFromWindow(@NonNull VH VH) {
    }

    public void onViewRecycled(@NonNull VH VH) {
    }

    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver adapterDataObserver) {
        this.mObservable.registerObserver((Object)adapterDataObserver);
    }

    public void setHasStableIds(boolean bl) {
        if (this.hasObservers()) {
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }
        this.mHasStableIds = bl;
    }

    public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver adapterDataObserver) {
        this.mObservable.unregisterObserver((Object)adapterDataObserver);
    }
}
