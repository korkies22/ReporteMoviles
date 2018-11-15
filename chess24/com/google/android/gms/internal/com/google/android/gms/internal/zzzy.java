/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzzz;

public class zzzy
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    public final Api<?> zzawb;
    private final int zzazb;
    private zzzz zzazc;

    public zzzy(Api<?> api, int n) {
        this.zzawb = api;
        this.zzazb = n;
    }

    private void zzvi() {
        zzac.zzb(this.zzazc, (Object)"Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        this.zzvi();
        this.zzazc.onConnected(bundle);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzvi();
        this.zzazc.zza(connectionResult, this.zzawb, this.zzazb);
    }

    @Override
    public void onConnectionSuspended(int n) {
        this.zzvi();
        this.zzazc.onConnectionSuspended(n);
    }

    public void zza(zzzz zzzz2) {
        this.zzazc = zzzz2;
    }
}
