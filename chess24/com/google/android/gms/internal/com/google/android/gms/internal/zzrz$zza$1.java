/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzrz;
import com.google.android.gms.internal.zzsu;

class zzrz.zza
implements Runnable {
    final /* synthetic */ zzsu zzadr;

    zzrz.zza(zzsu zzsu2) {
        this.zzadr = zzsu2;
    }

    @Override
    public void run() {
        if (!zza.this.zzado.isConnected()) {
            zza.this.zzado.zzbP("Connected to service after a timeout");
            zza.this.zzado.zza(this.zzadr);
        }
    }
}
