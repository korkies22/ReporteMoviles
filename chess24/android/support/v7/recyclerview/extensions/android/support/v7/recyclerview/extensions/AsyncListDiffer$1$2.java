/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.recyclerview.extensions;

import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import java.util.List;

class AsyncListDiffer
implements Runnable {
    final /* synthetic */ DiffUtil.DiffResult val$result;

    AsyncListDiffer(DiffUtil.DiffResult diffResult) {
        this.val$result = diffResult;
    }

    @Override
    public void run() {
        if (1.this.this$0.mMaxScheduledGeneration == 1.this.val$runGeneration) {
            1.this.this$0.latchList(1.this.val$newList, this.val$result);
        }
    }
}
