/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Batch;
import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import java.util.ArrayList;
import java.util.List;

public static final class Batch.Builder {
    private GoogleApiClient zzamy;
    private List<PendingResult<?>> zzaxE = new ArrayList();

    public Batch.Builder(GoogleApiClient googleApiClient) {
        this.zzamy = googleApiClient;
    }

    public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
        BatchResultToken batchResultToken = new BatchResultToken(this.zzaxE.size());
        this.zzaxE.add(pendingResult);
        return batchResultToken;
    }

    public Batch build() {
        return new Batch(this.zzaxE, this.zzamy, null);
    }
}
