/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.internal.zzaah;
import com.google.android.gms.internal.zzabp;
import com.google.android.gms.internal.zzzv;

public class zzaaq<O extends Api.ApiOptions>
extends zzaah {
    private final zzc<O> zzaBl;

    public zzaaq(zzc<O> zzc2) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.zzaBl = zzc2;
    }

    @Override
    public Looper getLooper() {
        return this.zzaBl.getLooper();
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(@NonNull T t) {
        return this.zzaBl.doRead(t);
    }

    @Override
    public void zza(zzabp zzabp2) {
    }

    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(@NonNull T t) {
        return this.zzaBl.doWrite(t);
    }

    @Override
    public void zzb(zzabp zzabp2) {
    }
}
