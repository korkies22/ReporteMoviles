/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzg;
import java.util.Iterator;
import java.util.List;

class zzh
implements Runnable {
    final /* synthetic */ zze zzabm;

    zzh(zze zze2) {
        this.zzabm = zze2;
    }

    @Override
    public void run() {
        this.zzabm.zzmi().zza(this.zzabm);
        Iterator iterator = zzh.this.zzabh.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        zzh.this.zzb(this.zzabm);
    }
}
