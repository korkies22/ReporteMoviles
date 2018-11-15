/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.recyclerview.extensions;

import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import java.util.List;
import java.util.concurrent.Executor;

class AsyncListDiffer
implements Runnable {
    final /* synthetic */ List val$newList;
    final /* synthetic */ List val$oldList;
    final /* synthetic */ int val$runGeneration;

    AsyncListDiffer(List list, List list2, int n) {
        this.val$oldList = list;
        this.val$newList = list2;
        this.val$runGeneration = n;
    }

    @Override
    public void run() {
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback(){

            @Override
            public boolean areContentsTheSame(int n, int n2) {
                return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(1.this.val$oldList.get(n), 1.this.val$newList.get(n2));
            }

            @Override
            public boolean areItemsTheSame(int n, int n2) {
                return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(1.this.val$oldList.get(n), 1.this.val$newList.get(n2));
            }

            @Nullable
            @Override
            public Object getChangePayload(int n, int n2) {
                return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(1.this.val$oldList.get(n), 1.this.val$newList.get(n2));
            }

            @Override
            public int getNewListSize() {
                return 1.this.val$newList.size();
            }

            @Override
            public int getOldListSize() {
                return 1.this.val$oldList.size();
            }
        });
        AsyncListDiffer.this.mConfig.getMainThreadExecutor().execute(new Runnable(){

            @Override
            public void run() {
                if (AsyncListDiffer.this.mMaxScheduledGeneration == 1.this.val$runGeneration) {
                    AsyncListDiffer.this.latchList(1.this.val$newList, diffResult);
                }
            }
        });
    }

}
