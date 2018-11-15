/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.internal.zzsw;

public class zzsv {
    private final String zzaca;
    private final long zzafl;
    private final int zzafm;
    private double zzafn;
    private long zzafo;
    private final Object zzafp = new Object();
    private final zze zzuI;

    public zzsv(int n, long l, String string, zze zze2) {
        this.zzafm = n;
        this.zzafn = this.zzafm;
        this.zzafl = l;
        this.zzaca = string;
        this.zzuI = zze2;
    }

    public zzsv(String string, zze zze2) {
        this(60, 2000L, string, zze2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean zzpv() {
        Object object = this.zzafp;
        synchronized (object) {
            double d;
            long l = this.zzuI.currentTimeMillis();
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
            zzsw.zzbe(stringBuilder.toString());
            return false;
        }
    }
}
