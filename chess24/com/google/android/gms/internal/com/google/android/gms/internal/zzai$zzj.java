/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzai.zzj
extends zzbun<zzai.zzj> {
    public zzai.zzi[] zzlt;
    public zzai.zzf zzlu;
    public String zzlv;

    public zzai.zzj() {
        this.zzN();
    }

    public static zzai.zzj zzg(byte[] arrby) throws zzbus {
        return zzbut.zza(new zzai.zzj(), arrby);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzj)) {
            return false;
        }
        object = (zzai.zzj)object;
        if (!zzbur.equals(this.zzlt, object.zzlt)) {
            return false;
        }
        if (this.zzlu == null ? object.zzlu != null : !this.zzlu.equals(object.zzlu)) {
            return false;
        }
        if (this.zzlv == null ? object.zzlv != null : !this.zzlv.equals(object.zzlv)) {
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
        int n2 = zzbur.hashCode(this.zzlt);
        zzai.zzf zzf2 = this.zzlu;
        int n3 = 0;
        int n4 = zzf2 == null ? 0 : this.zzlu.hashCode();
        int n5 = this.zzlv == null ? 0 : this.zzlv.hashCode();
        int n6 = n3;
        if (this.zzcrX != null) {
            n6 = this.zzcrX.isEmpty() ? n3 : this.zzcrX.hashCode();
        }
        return 31 * ((((527 + n) * 31 + n2) * 31 + n4) * 31 + n5) + n6;
    }

    public zzai.zzj zzN() {
        this.zzlt = zzai.zzi.zzL();
        this.zzlu = null;
        this.zzlv = "";
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzlt != null && this.zzlt.length > 0) {
            for (int i = 0; i < this.zzlt.length; ++i) {
                zzai.zzi zzi2 = this.zzlt[i];
                if (zzi2 == null) continue;
                zzbum2.zza(1, zzi2);
            }
        }
        if (this.zzlu != null) {
            zzbum2.zza(2, this.zzlu);
        }
        if (this.zzlv != null && !this.zzlv.equals("")) {
            zzbum2.zzq(3, this.zzlv);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzw(zzbul2);
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.zzlt != null) {
            n2 = n;
            if (this.zzlt.length > 0) {
                int n3 = 0;
                do {
                    n2 = n;
                    if (n3 >= this.zzlt.length) break;
                    zzai.zzi zzi2 = this.zzlt[n3];
                    n2 = n;
                    if (zzi2 != null) {
                        n2 = n + zzbum.zzc(1, zzi2);
                    }
                    ++n3;
                    n = n2;
                } while (true);
            }
        }
        n = n2;
        if (this.zzlu != null) {
            n = n2 + zzbum.zzc(2, this.zzlu);
        }
        n2 = n;
        if (this.zzlv != null) {
            n2 = n;
            if (!this.zzlv.equals("")) {
                n2 = n + zzbum.zzr(3, this.zzlv);
            }
        }
        return n2;
    }

    public zzai.zzj zzw(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 10) {
                if (n != 18) {
                    if (n != 26) {
                        if (super.zza(zzbul2, n)) continue;
                        return this;
                    }
                    this.zzlv = zzbul2.readString();
                    continue;
                }
                if (this.zzlu == null) {
                    this.zzlu = new zzai.zzf();
                }
                zzbul2.zza(this.zzlu);
                continue;
            }
            int n2 = zzbuw.zzc(zzbul2, 10);
            n = this.zzlt == null ? 0 : this.zzlt.length;
            zzai.zzi[] arrzzi = new zzai.zzi[n2 + n];
            n2 = n;
            if (n != 0) {
                System.arraycopy(this.zzlt, 0, arrzzi, 0, n);
                n2 = n;
            }
            while (n2 < arrzzi.length - 1) {
                arrzzi[n2] = new zzai.zzi();
                zzbul2.zza(arrzzi[n2]);
                zzbul2.zzacu();
                ++n2;
            }
            arrzzi[n2] = new zzai.zzi();
            zzbul2.zza(arrzzi[n2]);
            this.zzlt = arrzzi;
        }
        return this;
    }
}
