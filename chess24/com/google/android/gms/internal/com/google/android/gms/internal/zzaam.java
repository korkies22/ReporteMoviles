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
import com.google.android.gms.internal.zzzv;

public interface zzaam {
    public void begin();

    public void connect();

    public boolean disconnect();

    public void onConnected(Bundle var1);

    public void onConnectionSuspended(int var1);

    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(T var1);

    public void zza(ConnectionResult var1, Api<?> var2, int var3);

    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(T var1);
}
