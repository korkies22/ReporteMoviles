/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzf;

final class zzl
implements zzf.zzb {
    zzl() {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ConnectionCallbacks.this.onConnected(bundle);
    }

    @Override
    public void onConnectionSuspended(int n) {
        ConnectionCallbacks.this.onConnectionSuspended(n);
    }
}
