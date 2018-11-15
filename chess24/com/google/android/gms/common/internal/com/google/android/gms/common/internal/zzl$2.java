/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzf;

final class zzl
implements zzf.zzc {
    zzl() {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        OnConnectionFailedListener.this.onConnectionFailed(connectionResult);
    }
}
