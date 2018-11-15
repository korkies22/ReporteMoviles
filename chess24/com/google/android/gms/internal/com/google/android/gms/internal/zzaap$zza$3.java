/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzaap;

class zzaap.zza
implements Runnable {
    final /* synthetic */ ConnectionResult zzaBi;

    zzaap.zza(ConnectionResult connectionResult) {
        this.zzaBi = connectionResult;
    }

    @Override
    public void run() {
        zza.this.onConnectionFailed(this.zzaBi);
    }
}
