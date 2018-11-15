/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzcl;

class zzbm
implements zzcl {
    private final String zzaca;
    private final long zzafl;
    private final int zzafm;
    private double zzafn;
    private long zzafo;
    private final Object zzafp = new Object();
    private final long zzbEF;
    private final zze zzuI;

    public zzbm(int n, int n2, long l, long l2, String string, zze zze2) {
        this.zzafm = n2;
        this.zzafn = Math.min(n, n2);
        this.zzafl = l;
        this.zzbEF = l2;
        this.zzaca = string;
        this.zzuI = zze2;
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
            if (l - this.zzafo < this.zzbEF) {
                String string = this.zzaca;
                StringBuilder stringBuilder = new StringBuilder(34 + String.valueOf(string).length());
                stringBuilder.append("Excessive ");
                stringBuilder.append(string);
                stringBuilder.append(" detected; call ignored.");
                zzbo.zzbe(stringBuilder.toString());
                return false;
            }
            if (this.zzafn < (double)this.zzafm && (d = (double)(l - this.zzafo) / (double)this.zzafl) > 0.0) {
                this.zzafn = Math.min((double)this.zzafm, this.zzafn + d);
            }
            this.zzafo = l;
            if (this.zzafn >= 1.0) {
                this.zzafn -= 1.0;
                return true;
            }
            String string = this.zzaca;
            StringBuilder stringBuilder = new StringBuilder(34 + String.valueOf(string).length());
            stringBuilder.append("Excessive ");
            stringBuilder.append(string);
            stringBuilder.append(" detected; call ignored.");
            zzbo.zzbe(stringBuilder.toString());
            return false;
        }
    }
}
