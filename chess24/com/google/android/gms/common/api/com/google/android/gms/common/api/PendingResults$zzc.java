/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzzx;

private static final class PendingResults.zzc<R extends Result>
extends zzzx<R> {
    public PendingResults.zzc(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    protected R zzc(Status status) {
        throw new UnsupportedOperationException("Creating failed results is not supported");
    }
}
