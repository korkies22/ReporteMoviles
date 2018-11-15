/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.Nullable;
import android.support.v7.util.BatchingListUpdateCallback;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

public static class SortedList.BatchedCallback<T2>
extends SortedList.Callback<T2> {
    private final BatchingListUpdateCallback mBatchingListUpdateCallback;
    final SortedList.Callback<T2> mWrappedCallback;

    public SortedList.BatchedCallback(SortedList.Callback<T2> callback) {
        this.mWrappedCallback = callback;
        this.mBatchingListUpdateCallback = new BatchingListUpdateCallback(this.mWrappedCallback);
    }

    @Override
    public boolean areContentsTheSame(T2 T2, T2 T22) {
        return this.mWrappedCallback.areContentsTheSame(T2, T22);
    }

    @Override
    public boolean areItemsTheSame(T2 T2, T2 T22) {
        return this.mWrappedCallback.areItemsTheSame(T2, T22);
    }

    @Override
    public int compare(T2 T2, T2 T22) {
        return this.mWrappedCallback.compare(T2, T22);
    }

    public void dispatchLastEvent() {
        this.mBatchingListUpdateCallback.dispatchLastEvent();
    }

    @Nullable
    @Override
    public Object getChangePayload(T2 T2, T2 T22) {
        return this.mWrappedCallback.getChangePayload(T2, T22);
    }

    @Override
    public void onChanged(int n, int n2) {
        this.mBatchingListUpdateCallback.onChanged(n, n2, null);
    }

    @Override
    public void onChanged(int n, int n2, Object object) {
        this.mBatchingListUpdateCallback.onChanged(n, n2, object);
    }

    @Override
    public void onInserted(int n, int n2) {
        this.mBatchingListUpdateCallback.onInserted(n, n2);
    }

    @Override
    public void onMoved(int n, int n2) {
        this.mBatchingListUpdateCallback.onMoved(n, n2);
    }

    @Override
    public void onRemoved(int n, int n2) {
        this.mBatchingListUpdateCallback.onRemoved(n, n2);
    }
}
