/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaca;

static abstract class zzaca.zza
extends zzaca<Status> {
    public zzaca.zza(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    public Status zzb(Status status) {
        return status;
    }

    @Override
    public /* synthetic */ Result zzc(Status status) {
        return this.zzb(status);
    }
}
