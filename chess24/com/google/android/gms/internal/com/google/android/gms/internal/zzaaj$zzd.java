/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.BinderThread;
import com.google.android.gms.internal.zzaaj;
import com.google.android.gms.internal.zzaam;
import com.google.android.gms.internal.zzaan;
import com.google.android.gms.internal.zzaxr;
import com.google.android.gms.internal.zzayb;
import java.lang.ref.WeakReference;

private static class zzaaj.zzd
extends zzaxr {
    private final WeakReference<zzaaj> zzaAb;

    zzaaj.zzd(zzaaj zzaaj2) {
        this.zzaAb = new WeakReference<zzaaj>(zzaaj2);
    }

    @BinderThread
    @Override
    public void zzb(final zzayb zzayb2) {
        final zzaaj zzaaj2 = (zzaaj)this.zzaAb.get();
        if (zzaaj2 == null) {
            return;
        }
        zzaaj2.zzazK.zza(new zzaan.zza(this, zzaaj2){

            @Override
            public void zzvA() {
                zzaaj2.zza(zzayb2);
            }
        });
    }

}
