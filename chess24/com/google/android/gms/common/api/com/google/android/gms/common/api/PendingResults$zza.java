/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzzx;

private static final class PendingResults.zza<R extends Result>
extends zzzx<R> {
    private final R zzayc;

    public PendingResults.zza(R r) {
        super(Looper.getMainLooper());
        this.zzayc = r;
    }

    @Override
    protected R zzc(Status status) {
        if (status.getStatusCode() != this.zzayc.getStatus().getStatusCode()) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
        return this.zzayc;
    }
}
