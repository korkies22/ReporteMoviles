/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzh;

public class TaskCompletionSource<TResult> {
    private final zzh<TResult> zzbLF = new zzh();

    @NonNull
    public Task<TResult> getTask() {
        return this.zzbLF;
    }

    public void setException(@NonNull Exception exception) {
        this.zzbLF.setException(exception);
    }

    public void setResult(TResult TResult) {
        this.zzbLF.setResult(TResult);
    }

    public boolean trySetException(@NonNull Exception exception) {
        return this.zzbLF.trySetException(exception);
    }

    public boolean trySetResult(TResult TResult) {
        return this.zzbLF.trySetResult(TResult);
    }
}
