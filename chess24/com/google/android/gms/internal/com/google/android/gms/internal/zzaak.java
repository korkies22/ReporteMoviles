/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzzv;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;

public class zzaak
implements zzaam {
    private final zzaan zzazK;

    public zzaak(zzaan zzaan2) {
        this.zzazK = zzaan2;
    }

    @Override
    public void begin() {
        this.zzazK.zzvQ();
        this.zzazK.zzazd.zzaAs = Collections.emptySet();
    }

    @Override
    public void connect() {
        this.zzazK.zzvO();
    }

    @Override
    public boolean disconnect() {
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int n) {
    }

    @Override
    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(T t) {
        this.zzazK.zzazd.zzaAl.add(t);
        return t;
    }

    @Override
    public void zza(ConnectionResult connectionResult, Api<?> api, int n) {
    }

    @Override
    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }
}
