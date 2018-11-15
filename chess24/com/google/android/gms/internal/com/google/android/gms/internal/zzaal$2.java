/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzabl;
import java.util.concurrent.atomic.AtomicReference;

class zzaal
implements GoogleApiClient.ConnectionCallbacks {
    final /* synthetic */ AtomicReference zzaAA;
    final /* synthetic */ zzabl zzaAB;

    zzaal(AtomicReference atomicReference, zzabl zzabl2) {
        this.zzaAA = atomicReference;
        this.zzaAB = zzabl2;
    }

    @Override
    public void onConnected(Bundle bundle) {
        zzaal.this.zza((GoogleApiClient)this.zzaAA.get(), this.zzaAB, true);
    }

    @Override
    public void onConnectionSuspended(int n) {
    }
}
