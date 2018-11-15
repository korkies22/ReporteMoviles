/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabx;
import com.google.android.gms.internal.zzacb;
import com.google.android.gms.internal.zzzv;

abstract class zzaca<R extends Result>
extends zzzv.zza<R, zzacb> {
    public zzaca(GoogleApiClient googleApiClient) {
        super(zzabx.API, googleApiClient);
    }

    static abstract class zza
    extends zzaca<Status> {
        public zza(GoogleApiClient googleApiClient) {
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

}
