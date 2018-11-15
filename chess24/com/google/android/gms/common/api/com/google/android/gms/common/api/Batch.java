/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResult;
import com.google.android.gms.common.api.BatchResultToken;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzzx;
import java.util.ArrayList;
import java.util.List;

public final class Batch
extends zzzx<BatchResult> {
    private boolean zzaxA;
    private boolean zzaxB;
    private final PendingResult<?>[] zzaxC;
    private int zzaxz;
    private final Object zzrN = new Object();

    private Batch(List<PendingResult<?>> list, GoogleApiClient object) {
        super((GoogleApiClient)object);
        this.zzaxz = list.size();
        this.zzaxC = new PendingResult[this.zzaxz];
        if (list.isEmpty()) {
            this.zzb(new BatchResult(Status.zzayh, this.zzaxC));
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            this.zzaxC[i] = object = list.get(i);
            object.zza(new PendingResult.zza(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void zzx(Status status) {
                    Object object = Batch.this.zzrN;
                    synchronized (object) {
                        if (Batch.this.isCanceled()) {
                            return;
                        }
                        if (status.isCanceled()) {
                            Batch.this.zzaxB = true;
                        } else if (!status.isSuccess()) {
                            Batch.this.zzaxA = true;
                        }
                        Batch.zzb(Batch.this);
                        if (Batch.this.zzaxz == 0) {
                            if (Batch.this.zzaxB) {
                                Batch.super.cancel();
                            } else {
                                status = Batch.this.zzaxA ? new Status(13) : Status.zzayh;
                                Batch.this.zzb(new BatchResult(status, Batch.this.zzaxC));
                            }
                        }
                        return;
                    }
                }
            });
        }
    }

    static /* synthetic */ int zzb(Batch batch) {
        int n = batch.zzaxz;
        batch.zzaxz = n - 1;
        return n;
    }

    @Override
    public void cancel() {
        super.cancel();
        PendingResult<?>[] arrpendingResult = this.zzaxC;
        int n = arrpendingResult.length;
        for (int i = 0; i < n; ++i) {
            arrpendingResult[i].cancel();
        }
    }

    public BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zzaxC);
    }

    @Override
    public /* synthetic */ Result zzc(Status status) {
        return this.createFailedResult(status);
    }

    public static final class Builder {
        private GoogleApiClient zzamy;
        private List<PendingResult<?>> zzaxE = new ArrayList();

        public Builder(GoogleApiClient googleApiClient) {
            this.zzamy = googleApiClient;
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken batchResultToken = new BatchResultToken(this.zzaxE.size());
            this.zzaxE.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.zzaxE, this.zzamy);
        }
    }

}
