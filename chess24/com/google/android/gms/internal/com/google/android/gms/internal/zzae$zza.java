/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbut;
import java.io.IOException;

public static final class zzae.zza
extends zzbun<zzae.zza> {
    public String stackTrace = null;
    public String zzaR = null;
    public Long zzaS = null;
    public String zzaT = null;
    public String zzaU = null;
    public Long zzaV = null;
    public Long zzaW = null;
    public String zzaX = null;
    public Long zzaY = null;
    public String zzaZ = null;

    public zzae.zza() {
        this.zzcsg = -1;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzaR != null) {
            zzbum2.zzq(1, this.zzaR);
        }
        if (this.zzaS != null) {
            zzbum2.zzb(2, this.zzaS);
        }
        if (this.stackTrace != null) {
            zzbum2.zzq(3, this.stackTrace);
        }
        if (this.zzaT != null) {
            zzbum2.zzq(4, this.zzaT);
        }
        if (this.zzaU != null) {
            zzbum2.zzq(5, this.zzaU);
        }
        if (this.zzaV != null) {
            zzbum2.zzb(6, this.zzaV);
        }
        if (this.zzaW != null) {
            zzbum2.zzb(7, this.zzaW);
        }
        if (this.zzaX != null) {
            zzbum2.zzq(8, this.zzaX);
        }
        if (this.zzaY != null) {
            zzbum2.zzb(9, this.zzaY);
        }
        if (this.zzaZ != null) {
            zzbum2.zzq(10, this.zzaZ);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zze(zzbul2);
    }

    public zzae.zza zze(zzbul zzbul2) throws IOException {
        block13 : do {
            int n = zzbul2.zzacu();
            switch (n) {
                default: {
                    if (super.zza(zzbul2, n)) continue block13;
                    return this;
                }
                case 82: {
                    this.zzaZ = zzbul2.readString();
                    continue block13;
                }
                case 72: {
                    this.zzaY = zzbul2.zzacx();
                    continue block13;
                }
                case 66: {
                    this.zzaX = zzbul2.readString();
                    continue block13;
                }
                case 56: {
                    this.zzaW = zzbul2.zzacx();
                    continue block13;
                }
                case 48: {
                    this.zzaV = zzbul2.zzacx();
                    continue block13;
                }
                case 42: {
                    this.zzaU = zzbul2.readString();
                    continue block13;
                }
                case 34: {
                    this.zzaT = zzbul2.readString();
                    continue block13;
                }
                case 26: {
                    this.stackTrace = zzbul2.readString();
                    continue block13;
                }
                case 16: {
                    this.zzaS = zzbul2.zzacx();
                    continue block13;
                }
                case 10: {
                    this.zzaR = zzbul2.readString();
                    continue block13;
                }
                case 0: 
            }
            break;
        } while (true);
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.zzaR != null) {
            n2 = n + zzbum.zzr(1, this.zzaR);
        }
        n = n2;
        if (this.zzaS != null) {
            n = n2 + zzbum.zzf(2, this.zzaS);
        }
        n2 = n;
        if (this.stackTrace != null) {
            n2 = n + zzbum.zzr(3, this.stackTrace);
        }
        n = n2;
        if (this.zzaT != null) {
            n = n2 + zzbum.zzr(4, this.zzaT);
        }
        n2 = n;
        if (this.zzaU != null) {
            n2 = n + zzbum.zzr(5, this.zzaU);
        }
        n = n2;
        if (this.zzaV != null) {
            n = n2 + zzbum.zzf(6, this.zzaV);
        }
        n2 = n;
        if (this.zzaW != null) {
            n2 = n + zzbum.zzf(7, this.zzaW);
        }
        n = n2;
        if (this.zzaX != null) {
            n = n2 + zzbum.zzr(8, this.zzaX);
        }
        n2 = n;
        if (this.zzaY != null) {
            n2 = n + zzbum.zzf(9, this.zzaY);
        }
        n = n2;
        if (this.zzaZ != null) {
            n = n2 + zzbum.zzr(10, this.zzaZ);
        }
        return n;
    }
}
