/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.internal.zzaaj;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaan;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;

private static class zzaaj.zza
implements zzf.zzf {
    private final WeakReference<zzaaj> zzaAb;
    private final Api<?> zzawb;
    private final int zzazb;

    public zzaaj.zza(zzaaj zzaaj2, Api<?> api, int n) {
        this.zzaAb = new WeakReference<zzaaj>(zzaaj2);
        this.zzawb = api;
        this.zzazb = n;
    }

    static /* synthetic */ int zza(zzaaj.zza zza2) {
        return zza2.zzazb;
    }

    @Override
    public void zzg(@NonNull ConnectionResult connectionResult) {
        zzaaj zzaaj2;
        block7 : {
            zzaaj2 = (zzaaj)this.zzaAb.get();
            if (zzaaj2 == null) {
                return;
            }
            boolean bl = Looper.myLooper() == zzaaj.zzd((zzaaj)zzaaj2).zzazd.getLooper();
            zzac.zza(bl, (Object)"onReportServiceBinding must be called on the GoogleApiClient handler thread");
            zzaaj2.zzazn.lock();
            bl = zzaaj2.zzcv(0);
            if (bl) break block7;
            zzaaj2.zzazn.unlock();
            return;
        }
        try {
            if (!connectionResult.isSuccess()) {
                zzaaj2.zzb(connectionResult, this.zzawb, this.zzazb);
            }
            if (zzaaj2.zzvB()) {
                zzaaj2.zzvC();
            }
            return;
        }
        finally {
            zzaaj2.zzazn.unlock();
        }
    }
}
