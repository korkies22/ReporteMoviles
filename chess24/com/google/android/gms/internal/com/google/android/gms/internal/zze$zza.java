/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zze;
import com.google.android.gms.internal.zzk;
import com.google.android.gms.internal.zzm;
import com.google.android.gms.internal.zzr;

private class zze.zza
implements Runnable {
    private final zzk zzt;
    private final zzm zzu;
    private final Runnable zzv;

    public zze.zza(zze zze2, zzk zzk2, zzm zzm2, Runnable runnable) {
        this.zzt = zzk2;
        this.zzu = zzm2;
        this.zzv = runnable;
    }

    @Override
    public void run() {
        if (this.zzu.isSuccess()) {
            this.zzt.zza(this.zzu.result);
        } else {
            this.zzt.zzc(this.zzu.zzaf);
        }
        if (this.zzu.zzag) {
            this.zzt.zzc("intermediate-response");
        } else {
            this.zzt.zzd("done");
        }
        if (this.zzv != null) {
            this.zzv.run();
        }
    }
}
