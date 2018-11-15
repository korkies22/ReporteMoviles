/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 */
package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaad;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzzq;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzx;

public static class zzzq.zzb<A extends zzzv.zza<? extends Result, Api.zzb>>
extends zzzq {
    protected final A zzayp;

    public zzzq.zzb(int n, A a) {
        super(n);
        this.zzayp = a;
    }

    @Override
    public void zza(@NonNull zzaad zzaad2, boolean bl) {
        zzaad2.zza((zzzx<? extends Result>)this.zzayp, bl);
    }

    @Override
    public void zza(zzaap.zza<?> zza2) throws DeadObjectException {
        this.zzayp.zzb((Api.zze)zza2.zzvr());
    }

    @Override
    public void zzy(@NonNull Status status) {
        this.zzayp.zzA(status);
    }
}
