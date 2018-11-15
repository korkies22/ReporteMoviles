/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzacb;

final class zzabx
extends Api.zza<zzacb, Api.ApiOptions.NoOptions> {
    zzabx() {
    }

    @Override
    public /* synthetic */ Api.zze zza(Context context, Looper looper, zzg zzg2, Object object, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zzf(context, looper, zzg2, (Api.ApiOptions.NoOptions)object, connectionCallbacks, onConnectionFailedListener);
    }

    public zzacb zzf(Context context, Looper looper, zzg zzg2, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzacb(context, looper, zzg2, connectionCallbacks, onConnectionFailedListener);
    }
}
