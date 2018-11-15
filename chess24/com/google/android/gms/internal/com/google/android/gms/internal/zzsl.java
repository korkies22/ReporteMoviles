/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzrw;
import com.google.android.gms.internal.zzsx;

abstract class zzsl {
    private static volatile Handler zzaec;
    private final zzrw zzacN;
    private volatile long zzaed;
    private final Runnable zzv;

    zzsl(zzrw zzrw2) {
        zzac.zzw(zzrw2);
        this.zzacN = zzrw2;
        this.zzv = new Runnable(){

            @Override
            public void run() {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    zzsl.this.zzacN.zznt().zzg(this);
                    return;
                }
                boolean bl = zzsl.this.zzcv();
                zzsl.this.zzaed = 0L;
                if (bl) {
                    zzsl.this.run();
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Handler getHandler() {
        if (zzaec != null) {
            return zzaec;
        }
        synchronized (zzsl.class) {
            if (zzaec != null) return zzaec;
            zzaec = new Handler(this.zzacN.getContext().getMainLooper());
            return zzaec;
        }
    }

    public void cancel() {
        this.zzaed = 0L;
        this.getHandler().removeCallbacks(this.zzv);
    }

    public abstract void run();

    public boolean zzcv() {
        if (this.zzaed != 0L) {
            return true;
        }
        return false;
    }

    public long zzpa() {
        if (this.zzaed == 0L) {
            return 0L;
        }
        return Math.abs(this.zzacN.zznq().currentTimeMillis() - this.zzaed);
    }

    public void zzx(long l) {
        this.cancel();
        if (l >= 0L) {
            this.zzaed = this.zzacN.zznq().currentTimeMillis();
            if (!this.getHandler().postDelayed(this.zzv, l)) {
                this.zzacN.zznr().zze("Failed to schedule delayed post. time", l);
            }
        }
    }

    public void zzy(long l) {
        if (!this.zzcv()) {
            return;
        }
        long l2 = 0L;
        if (l < 0L) {
            this.cancel();
            return;
        }
        if ((l -= Math.abs(this.zzacN.zznq().currentTimeMillis() - this.zzaed)) < 0L) {
            l = l2;
        }
        this.getHandler().removeCallbacks(this.zzv);
        if (!this.getHandler().postDelayed(this.zzv, l)) {
            this.zzacN.zznr().zze("Failed to adjust delayed post. time", l);
        }
    }

}
