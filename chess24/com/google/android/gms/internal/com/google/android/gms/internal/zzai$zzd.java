/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzai.zzd
extends zzbun<zzai.zzd> {
    public zzaj.zza[] zzkC;
    public zzaj.zza[] zzkD;
    public zzai.zzc[] zzkE;

    public zzai.zzd() {
        this.zzE();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzd)) {
            return false;
        }
        object = (zzai.zzd)object;
        if (!zzbur.equals(this.zzkC, object.zzkC)) {
            return false;
        }
        if (!zzbur.equals(this.zzkD, object.zzkD)) {
            return false;
        }
        if (!zzbur.equals(this.zzkE, object.zzkE)) {
            return false;
        }
        if (this.zzcrX != null && !this.zzcrX.isEmpty()) {
            return this.zzcrX.equals(object.zzcrX);
        }
        if (object.zzcrX != null) {
            if (object.zzcrX.isEmpty()) {
                return true;
            }
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        int n = this.getClass().getName().hashCode();
        int n2 = zzbur.hashCode(this.zzkC);
        int n3 = zzbur.hashCode(this.zzkD);
        int n4 = zzbur.hashCode(this.zzkE);
        int n5 = this.zzcrX != null && !this.zzcrX.isEmpty() ? this.zzcrX.hashCode() : 0;
        return 31 * ((((527 + n) * 31 + n2) * 31 + n3) * 31 + n4) + n5;
    }

    public zzai.zzd zzE() {
        this.zzkC = zzaj.zza.zzO();
        this.zzkD = zzaj.zza.zzO();
        this.zzkE = zzai.zzc.zzC();
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        int n;
        Object object = this.zzkC;
        int n2 = 0;
        if (object != null && this.zzkC.length > 0) {
            for (n = 0; n < this.zzkC.length; ++n) {
                object = this.zzkC[n];
                if (object == null) continue;
                zzbum2.zza(1, (zzbut)object);
            }
        }
        if (this.zzkD != null && this.zzkD.length > 0) {
            for (n = 0; n < this.zzkD.length; ++n) {
                object = this.zzkD[n];
                if (object == null) continue;
                zzbum2.zza(2, (zzbut)object);
            }
        }
        if (this.zzkE != null && this.zzkE.length > 0) {
            for (n = n2; n < this.zzkE.length; ++n) {
                object = this.zzkE[n];
                if (object == null) continue;
                zzbum2.zza(3, (zzbut)object);
            }
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzq(zzbul2);
    }

    public zzai.zzd zzq(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            zzbun[] arrzzbun;
            int n2;
            if (n != 10) {
                if (n != 18) {
                    if (n != 26) {
                        if (super.zza(zzbul2, n)) continue;
                        return this;
                    }
                    n2 = zzbuw.zzc(zzbul2, 26);
                    n = this.zzkE == null ? 0 : this.zzkE.length;
                    arrzzbun = new zzai.zzc[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkE, 0, arrzzbun, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrzzbun.length - 1) {
                        arrzzbun[n2] = new zzai.zzc();
                        zzbul2.zza(arrzzbun[n2]);
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrzzbun[n2] = new zzai.zzc();
                    zzbul2.zza(arrzzbun[n2]);
                    this.zzkE = arrzzbun;
                    continue;
                }
                n2 = zzbuw.zzc(zzbul2, 18);
                n = this.zzkD == null ? 0 : this.zzkD.length;
                arrzzbun = new zzaj.zza[n2 + n];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.zzkD, 0, arrzzbun, 0, n);
                    n2 = n;
                }
                while (n2 < arrzzbun.length - 1) {
                    arrzzbun[n2] = new zzaj.zza();
                    zzbul2.zza(arrzzbun[n2]);
                    zzbul2.zzacu();
                    ++n2;
                }
                arrzzbun[n2] = new zzaj.zza();
                zzbul2.zza(arrzzbun[n2]);
                this.zzkD = arrzzbun;
                continue;
            }
            n2 = zzbuw.zzc(zzbul2, 10);
            n = this.zzkC == null ? 0 : this.zzkC.length;
            arrzzbun = new zzaj.zza[n2 + n];
            n2 = n;
            if (n != 0) {
                System.arraycopy(this.zzkC, 0, arrzzbun, 0, n);
                n2 = n;
            }
            while (n2 < arrzzbun.length - 1) {
                arrzzbun[n2] = new zzaj.zza();
                zzbul2.zza(arrzzbun[n2]);
                zzbul2.zzacu();
                ++n2;
            }
            arrzzbun[n2] = new zzaj.zza();
            zzbul2.zza(arrzzbun[n2]);
            this.zzkC = arrzzbun;
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = super.zzv();
        Object object = this.zzkC;
        int n3 = 0;
        int n4 = n2;
        if (object != null) {
            n4 = n2;
            if (this.zzkC.length > 0) {
                for (n4 = 0; n4 < this.zzkC.length; ++n4) {
                    object = this.zzkC[n4];
                    n = n2;
                    if (object != null) {
                        n = n2 + zzbum.zzc(1, (zzbut)object);
                    }
                    n2 = n;
                }
                n4 = n2;
            }
        }
        n2 = n4;
        if (this.zzkD != null) {
            n2 = n4;
            if (this.zzkD.length > 0) {
                n2 = n4;
                for (n4 = 0; n4 < this.zzkD.length; ++n4) {
                    object = this.zzkD[n4];
                    n = n2;
                    if (object != null) {
                        n = n2 + zzbum.zzc(2, (zzbut)object);
                    }
                    n2 = n;
                }
            }
        }
        n = n2;
        if (this.zzkE != null) {
            n = n2;
            if (this.zzkE.length > 0) {
                n4 = n3;
                do {
                    n = n2;
                    if (n4 >= this.zzkE.length) break;
                    object = this.zzkE[n4];
                    n = n2;
                    if (object != null) {
                        n = n2 + zzbum.zzc(3, (zzbut)object);
                    }
                    ++n4;
                    n2 = n;
                } while (true);
            }
        }
        return n;
    }
}
