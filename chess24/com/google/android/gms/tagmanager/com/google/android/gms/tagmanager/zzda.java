/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcl;

class zzda
implements zzcl {
    private final long zzafl;
    private final int zzafm;
    private double zzafn;
    private final Object zzafp = new Object();
    private long zzbFY;
    private final zze zzuI;

    public zzda() {
        this(60, 2000L);
    }

    public zzda(int n, long l) {
        this.zzafm = n;
        this.zzafn = this.zzafm;
        this.zzafl = l;
        this.zzuI = zzh.zzyv();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean zzpv() {
        Object object = this.zzafp;
        synchronized (object) {
            double d;
            long l = this.zzuI.currentTimeMillis();
            if (this.zzafn < (double)this.zzafm && (d = (double)(l - this.zzbFY) / (double)this.zzafl) > 0.0) {
                this.zzafn = Math.min((double)this.zzafm, this.zzafn + d);
            }
            this.zzbFY = l;
            if (this.zzafn >= 1.0) {
                this.zzafn -= 1.0;
                return true;
            }
            zzbo.zzbe("No more tokens available.");
            return false;
        }
    }
}
