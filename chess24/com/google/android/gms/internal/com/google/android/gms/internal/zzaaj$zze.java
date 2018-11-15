/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzaaj;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxu;
import java.util.concurrent.locks.Lock;

private class zzaaj.zze
implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener {
    private zzaaj.zze() {
    }

    @Override
    public void onConnected(Bundle bundle) {
        zzaaj.this.zzazS.zza(new zzaaj.zzd(zzaaj.this));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzaaj.this.zzazn.lock();
        try {
            if (zzaaj.this.zze(connectionResult)) {
                zzaaj.this.zzvF();
                zzaaj.this.zzvC();
            } else {
                zzaaj.this.zzf(connectionResult);
            }
            return;
        }
        finally {
            zzaaj.this.zzazn.unlock();
        }
    }

    @Override
    public void onConnectionSuspended(int n) {
    }
}
