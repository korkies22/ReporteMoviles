/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzabl;

class zzaal
implements GoogleApiClient.OnConnectionFailedListener {
    final /* synthetic */ zzabl zzaAB;

    zzaal(com.google.android.gms.internal.zzaal zzaal2, zzabl zzabl2) {
        this.zzaAB = zzabl2;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaAB.zzb(new Status(8));
    }
}
