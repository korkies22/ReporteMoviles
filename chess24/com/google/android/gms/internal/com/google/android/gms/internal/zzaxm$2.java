/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaxm;
import com.google.android.gms.internal.zzaxy;

final class zzaxm
extends Api.zza<zzaxy, zzaxm.zza> {
    zzaxm() {
    }

    @Override
    public zzaxy zza(Context context, Looper looper, zzg zzg2, zzaxm.zza zza2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzaxy(context, looper, false, zzg2, zza2.zzOd(), connectionCallbacks, onConnectionFailedListener);
    }
}
