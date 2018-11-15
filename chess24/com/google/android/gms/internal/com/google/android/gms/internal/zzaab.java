/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.zzc;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaap;
import com.google.android.gms.internal.zzabj;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzzy;
import com.google.android.gms.internal.zzzz;

public final class zzaab<O extends Api.ApiOptions>
extends zzc<O> {
    private final Api.zza<? extends zzaxn, zzaxo> zzaxY;
    private final Api.zze zzazq;
    private final zzzy zzazr;
    private final zzg zzazs;

    public zzaab(@NonNull Context context, Api<O> api, Looper looper, @NonNull Api.zze zze2, @NonNull zzzy zzzy2, zzg zzg2, Api.zza<? extends zzaxn, zzaxo> zza2) {
        super(context, api, looper);
        this.zzazq = zze2;
        this.zzazr = zzzy2;
        this.zzazs = zzg2;
        this.zzaxY = zza2;
        this.zzaxK.zza(this);
    }

    @Override
    public Api.zze buildApiClient(Looper looper, zzaap.zza<O> zza2) {
        this.zzazr.zza(zza2);
        return this.zzazq;
    }

    @Override
    public zzabj createSignInCoordinator(Context context, Handler handler) {
        return new zzabj(context, handler, this.zzazs, this.zzaxY);
    }

    public Api.zze zzvr() {
        return this.zzazq;
    }
}
