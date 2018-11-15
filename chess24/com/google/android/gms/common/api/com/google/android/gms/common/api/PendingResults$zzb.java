/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzzx;

private static final class PendingResults.zzb<R extends Result>
extends zzzx<R> {
    private final R zzayd;

    public PendingResults.zzb(GoogleApiClient googleApiClient, R r) {
        super(googleApiClient);
        this.zzayd = r;
    }

    @Override
    protected R zzc(Status status) {
        return this.zzayd;
    }
}
