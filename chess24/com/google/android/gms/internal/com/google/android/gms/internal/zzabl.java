/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzzx;

public class zzabl
extends zzzx<Status> {
    @Deprecated
    public zzabl(Looper looper) {
        super(looper);
    }

    public zzabl(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    protected Status zzb(Status status) {
        return status;
    }

    @Override
    protected /* synthetic */ Result zzc(Status status) {
        return this.zzb(status);
    }
}
