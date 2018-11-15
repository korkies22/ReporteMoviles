/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.internal.zzaaj;
import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaan;

class zzaaj.zzb
extends zzaan.zza {
    final /* synthetic */ zzf.zzf zzaAf;

    zzaaj.zzb(zzaaj.zzb zzb2, zzaam zzaam2, zzf.zzf zzf2) {
        this.zzaAf = zzf2;
        super(zzaam2);
    }

    @Override
    public void zzvA() {
        this.zzaAf.zzg(new ConnectionResult(16, null));
    }
}
