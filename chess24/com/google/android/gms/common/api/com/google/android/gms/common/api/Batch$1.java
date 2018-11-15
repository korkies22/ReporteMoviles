/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.BatchResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

class Batch
implements PendingResult.zza {
    Batch() {
    }

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
            com.google.android.gms.common.api.Batch.zzb(Batch.this);
            if (Batch.this.zzaxz == 0) {
                if (Batch.this.zzaxB) {
                    com.google.android.gms.common.api.Batch.super.cancel();
                } else {
                    status = Batch.this.zzaxA ? new Status(13) : Status.zzayh;
                    Batch.this.zzb(new BatchResult(status, Batch.this.zzaxC));
                }
            }
            return;
        }
    }
}
