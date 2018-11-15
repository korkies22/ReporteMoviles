/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.recyclerview.extensions;

import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import java.util.List;

class AsyncListDiffer
extends DiffUtil.Callback {
    AsyncListDiffer() {
    }

    @Override
    public boolean areContentsTheSame(int n, int n2) {
        return 1.this.this$0.mConfig.getDiffCallback().areContentsTheSame(1.this.val$oldList.get(n), 1.this.val$newList.get(n2));
    }

    @Override
    public boolean areItemsTheSame(int n, int n2) {
        return 1.this.this$0.mConfig.getDiffCallback().areItemsTheSame(1.this.val$oldList.get(n), 1.this.val$newList.get(n2));
    }

    @Nullable
    @Override
    public Object getChangePayload(int n, int n2) {
        return 1.this.this$0.mConfig.getDiffCallback().getChangePayload(1.this.val$oldList.get(n), 1.this.val$newList.get(n2));
    }

    @Override
    public int getNewListSize() {
        return 1.this.val$newList.size();
    }

    @Override
    public int getOldListSize() {
        return 1.this.val$oldList.size();
    }
}
