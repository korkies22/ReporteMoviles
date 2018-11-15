/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbut;
import java.io.IOException;

public static final class zzaf.zza.zza
extends zzbun<zzaf.zza.zza> {
    private static volatile zzaf.zza.zza[] zzcb;
    public Long zzbm = null;
    public Long zzbn = null;
    public Long zzcc = null;
    public Long zzcd = null;
    public Long zzce = null;
    public Long zzcf = null;
    public Integer zzcg = null;
    public Long zzch = null;
    public Long zzci = null;
    public Long zzcj = null;
    public Integer zzck = null;
    public Long zzcl = null;

    public zzaf.zza.zza() {
        this.zzcsg = -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzaf.zza.zza[] zzy() {
        if (zzcb != null) return zzcb;
        Object object = zzbur.zzcsf;
        synchronized (object) {
            if (zzcb != null) return zzcb;
            zzcb = new zzaf.zza.zza[0];
            return zzcb;
        }
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzbm != null) {
            zzbum2.zzb(1, this.zzbm);
        }
        if (this.zzbn != null) {
            zzbum2.zzb(2, this.zzbn);
        }
        if (this.zzcc != null) {
            zzbum2.zzb(3, this.zzcc);
        }
        if (this.zzcd != null) {
            zzbum2.zzb(4, this.zzcd);
        }
        if (this.zzce != null) {
            zzbum2.zzb(5, this.zzce);
        }
        if (this.zzcf != null) {
            zzbum2.zzb(6, this.zzcf);
        }
        if (this.zzcg != null) {
            zzbum2.zzF(7, this.zzcg);
        }
        if (this.zzch != null) {
            zzbum2.zzb(8, this.zzch);
        }
        if (this.zzci != null) {
            zzbum2.zzb(9, this.zzci);
        }
        if (this.zzcj != null) {
            zzbum2.zzb(10, this.zzcj);
        }
        if (this.zzck != null) {
            zzbum2.zzF(11, this.zzck);
        }
        if (this.zzcl != null) {
            zzbum2.zzb(12, this.zzcl);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzg(zzbul2);
    }

    public zzaf.zza.zza zzg(zzbul zzbul2) throws IOException {
        block21 : do {
            int n = zzbul2.zzacu();
            switch (n) {
                default: {
                    if (super.zza(zzbul2, n)) continue block21;
                    return this;
                }
                case 96: {
                    this.zzcl = zzbul2.zzacx();
                    continue block21;
                }
                case 88: {
                    n = zzbul2.zzacy();
                    if (n != 1000) {
                        switch (n) {
                            default: {
                                continue block21;
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                        }
                    }
                    this.zzck = n;
                    continue block21;
                }
                case 80: {
                    this.zzcj = zzbul2.zzacx();
                    continue block21;
                }
                case 72: {
                    this.zzci = zzbul2.zzacx();
                    continue block21;
                }
                case 64: {
                    this.zzch = zzbul2.zzacx();
                    continue block21;
                }
                case 56: {
                    n = zzbul2.zzacy();
                    if (n != 1000) {
                        switch (n) {
                            default: {
                                continue block21;
                            }
                            case 0: 
                            case 1: 
                            case 2: 
                        }
                    }
                    this.zzcg = n;
                    continue block21;
                }
                case 48: {
                    this.zzcf = zzbul2.zzacx();
                    continue block21;
                }
                case 40: {
                    this.zzce = zzbul2.zzacx();
                    continue block21;
                }
                case 32: {
                    this.zzcd = zzbul2.zzacx();
                    continue block21;
                }
                case 24: {
                    this.zzcc = zzbul2.zzacx();
                    continue block21;
                }
                case 16: {
                    this.zzbn = zzbul2.zzacx();
                    continue block21;
                }
                case 8: {
                    this.zzbm = zzbul2.zzacx();
                    continue block21;
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
        if (this.zzbm != null) {
            n2 = n + zzbum.zzf(1, this.zzbm);
        }
        n = n2;
        if (this.zzbn != null) {
            n = n2 + zzbum.zzf(2, this.zzbn);
        }
        n2 = n;
        if (this.zzcc != null) {
            n2 = n + zzbum.zzf(3, this.zzcc);
        }
        n = n2;
        if (this.zzcd != null) {
            n = n2 + zzbum.zzf(4, this.zzcd);
        }
        n2 = n;
        if (this.zzce != null) {
            n2 = n + zzbum.zzf(5, this.zzce);
        }
        n = n2;
        if (this.zzcf != null) {
            n = n2 + zzbum.zzf(6, this.zzcf);
        }
        n2 = n;
        if (this.zzcg != null) {
            n2 = n + zzbum.zzH(7, this.zzcg);
        }
        n = n2;
        if (this.zzch != null) {
            n = n2 + zzbum.zzf(8, this.zzch);
        }
        n2 = n;
        if (this.zzci != null) {
            n2 = n + zzbum.zzf(9, this.zzci);
        }
        n = n2;
        if (this.zzcj != null) {
            n = n2 + zzbum.zzf(10, this.zzcj);
        }
        n2 = n;
        if (this.zzck != null) {
            n2 = n + zzbum.zzH(11, this.zzck);
        }
        n = n2;
        if (this.zzcl != null) {
            n = n2 + zzbum.zzf(12, this.zzcl);
        }
        return n;
    }
}
