/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import java.util.concurrent.TimeUnit;

public final class BatchResult
implements Result {
    private final Status zzahq;
    private final PendingResult<?>[] zzaxC;

    BatchResult(Status status, PendingResult<?>[] arrpendingResult) {
        this.zzahq = status;
        this.zzaxC = arrpendingResult;
    }

    @Override
    public Status getStatus() {
        return this.zzahq;
    }

    public <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        boolean bl = batchResultToken.mId < this.zzaxC.length;
        zzac.zzb(bl, (Object)"The result token does not belong to this batch");
        return (R)this.zzaxC[batchResultToken.mId].await(0L, TimeUnit.MILLISECONDS);
    }
}
