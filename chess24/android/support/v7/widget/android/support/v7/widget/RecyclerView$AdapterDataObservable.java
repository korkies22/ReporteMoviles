/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.Observable
 */
package android.support.v7.widget;

import android.database.Observable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

static class RecyclerView.AdapterDataObservable
extends Observable<RecyclerView.AdapterDataObserver> {
    RecyclerView.AdapterDataObservable() {
    }

    public boolean hasObservers() {
        return this.mObservers.isEmpty() ^ true;
    }

    public void notifyChanged() {
        for (int i = this.mObservers.size() - 1; i >= 0; --i) {
            ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onChanged();
        }
    }

    public void notifyItemMoved(int n, int n2) {
        for (int i = this.mObservers.size() - 1; i >= 0; --i) {
            ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeMoved(n, n2, 1);
        }
    }

    public void notifyItemRangeChanged(int n, int n2) {
        this.notifyItemRangeChanged(n, n2, null);
    }

    public void notifyItemRangeChanged(int n, int n2, @Nullable Object object) {
        for (int i = this.mObservers.size() - 1; i >= 0; --i) {
            ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(n, n2, object);
        }
    }

    public void notifyItemRangeInserted(int n, int n2) {
        for (int i = this.mObservers.size() - 1; i >= 0; --i) {
            ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(n, n2);
        }
    }

    public void notifyItemRangeRemoved(int n, int n2) {
        for (int i = this.mObservers.size() - 1; i >= 0; --i) {
            ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(n, n2);
        }
    }
}
