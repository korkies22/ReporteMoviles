/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class AsyncListDiffer<T> {
    private final AsyncDifferConfig<T> mConfig;
    @Nullable
    private List<T> mList;
    private int mMaxScheduledGeneration;
    @NonNull
    private List<T> mReadOnlyList = Collections.emptyList();
    private final ListUpdateCallback mUpdateCallback;

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.mUpdateCallback = listUpdateCallback;
        this.mConfig = asyncDifferConfig;
    }

    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.mUpdateCallback = new AdapterListUpdateCallback(adapter);
        this.mConfig = new AsyncDifferConfig.Builder<T>(itemCallback).build();
    }

    private void latchList(@NonNull List<T> list, @NonNull DiffUtil.DiffResult diffResult) {
        this.mList = list;
        this.mReadOnlyList = Collections.unmodifiableList(list);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.mReadOnlyList;
    }

    public void submitList(final List<T> list) {
        int n;
        if (list == this.mList) {
            return;
        }
        this.mMaxScheduledGeneration = n = this.mMaxScheduledGeneration + 1;
        if (list == null) {
            n = this.mList.size();
            this.mList = null;
            this.mReadOnlyList = Collections.emptyList();
            this.mUpdateCallback.onRemoved(0, n);
            return;
        }
        if (this.mList == null) {
            this.mList = list;
            this.mReadOnlyList = Collections.unmodifiableList(list);
            this.mUpdateCallback.onInserted(0, list.size());
            return;
        }
        final List<T> list2 = this.mList;
        this.mConfig.getBackgroundThreadExecutor().execute(new Runnable(){

            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback(){

                    @Override
                    public boolean areContentsTheSame(int n, int n2) {
                        return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(list2.get(n), list.get(n2));
                    }

                    @Override
                    public boolean areItemsTheSame(int n, int n2) {
                        return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(list2.get(n), list.get(n2));
                    }

                    @Nullable
                    @Override
                    public Object getChangePayload(int n, int n2) {
                        return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(list2.get(n), list.get(n2));
                    }

                    @Override
                    public int getNewListSize() {
                        return list.size();
                    }

                    @Override
                    public int getOldListSize() {
                        return list2.size();
                    }
                });
                AsyncListDiffer.this.mConfig.getMainThreadExecutor().execute(new Runnable(){

                    @Override
                    public void run() {
                        if (AsyncListDiffer.this.mMaxScheduledGeneration == n) {
                            AsyncListDiffer.this.latchList(list, diffResult);
                        }
                    }
                });
            }

        });
    }

}
