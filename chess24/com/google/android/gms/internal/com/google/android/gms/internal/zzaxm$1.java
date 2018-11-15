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
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzaxy;

final class zzaxm
extends Api.zza<zzaxy, zzaxo> {
    zzaxm() {
    }

    @Override
    public zzaxy zza(Context context, Looper looper, zzg zzg2, zzaxo zzaxo2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzaxo zzaxo3 = zzaxo2;
        if (zzaxo2 == null) {
            zzaxo3 = zzaxo.zzbCg;
        }
        return new zzaxy(context, looper, true, zzg2, zzaxo3, connectionCallbacks, onConnectionFailedListener);
    }
}
