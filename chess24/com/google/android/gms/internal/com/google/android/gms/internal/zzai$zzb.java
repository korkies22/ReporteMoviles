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
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzai.zzb
extends zzbun<zzai.zzb> {
    private static volatile zzai.zzb[] zzks;
    public int name;
    public int[] zzkt;
    public int zzku;
    public boolean zzkv;
    public boolean zzkw;

    public zzai.zzb() {
        this.zzB();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzai.zzb[] zzA() {
        if (zzks != null) return zzks;
        Object object = zzbur.zzcsf;
        synchronized (object) {
            if (zzks != null) return zzks;
            zzks = new zzai.zzb[0];
            return zzks;
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzb)) {
            return false;
        }
        object = (zzai.zzb)object;
        if (!zzbur.equals(this.zzkt, object.zzkt)) {
            return false;
        }
        if (this.zzku != object.zzku) {
            return false;
        }
        if (this.name != object.name) {
            return false;
        }
        if (this.zzkv != object.zzkv) {
            return false;
        }
        if (this.zzkw != object.zzkw) {
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
        int n2 = zzbur.hashCode(this.zzkt);
        int n3 = this.zzku;
        int n4 = this.name;
        boolean bl = this.zzkv;
        int n5 = 1237;
        int n6 = bl ? 1231 : 1237;
        if (this.zzkw) {
            n5 = 1231;
        }
        int n7 = this.zzcrX != null && !this.zzcrX.isEmpty() ? this.zzcrX.hashCode() : 0;
        return 31 * ((((((527 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n6) * 31 + n5) + n7;
    }

    public zzai.zzb zzB() {
        this.zzkt = zzbuw.zzcsi;
        this.zzku = 0;
        this.name = 0;
        this.zzkv = false;
        this.zzkw = false;
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzkw) {
            zzbum2.zzg(1, this.zzkw);
        }
        zzbum2.zzF(2, this.zzku);
        if (this.zzkt != null && this.zzkt.length > 0) {
            for (int i = 0; i < this.zzkt.length; ++i) {
                zzbum2.zzF(3, this.zzkt[i]);
            }
        }
        if (this.name != 0) {
            zzbum2.zzF(4, this.name);
        }
        if (this.zzkv) {
            zzbum2.zzg(6, this.zzkv);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzo(zzbul2);
    }

    public zzai.zzb zzo(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 8) {
                if (n != 16) {
                    int n2;
                    int[] arrn;
                    if (n != 24) {
                        if (n != 26) {
                            if (n != 32) {
                                if (n != 48) {
                                    if (super.zza(zzbul2, n)) continue;
                                    return this;
                                }
                                this.zzkv = zzbul2.zzacA();
                                continue;
                            }
                            this.name = zzbul2.zzacy();
                            continue;
                        }
                        int n3 = zzbul2.zzqj(zzbul2.zzacD());
                        n = zzbul2.getPosition();
                        n2 = 0;
                        while (zzbul2.zzacI() > 0) {
                            zzbul2.zzacy();
                            ++n2;
                        }
                        zzbul2.zzql(n);
                        n = this.zzkt == null ? 0 : this.zzkt.length;
                        arrn = new int[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzkt, 0, arrn, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrn.length) {
                            arrn[n2] = zzbul2.zzacy();
                            ++n2;
                        }
                        this.zzkt = arrn;
                        zzbul2.zzqk(n3);
                        continue;
                    }
                    n2 = zzbuw.zzc(zzbul2, 24);
                    n = this.zzkt == null ? 0 : this.zzkt.length;
                    arrn = new int[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkt, 0, arrn, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrn.length - 1) {
                        arrn[n2] = zzbul2.zzacy();
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrn[n2] = zzbul2.zzacy();
                    this.zzkt = arrn;
                    continue;
                }
                this.zzku = zzbul2.zzacy();
                continue;
            }
            this.zzkw = zzbul2.zzacA();
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2;
        int n3 = n2 = super.zzv();
        if (this.zzkw) {
            n3 = n2 + zzbum.zzh(1, this.zzkw);
        }
        n3 = n = n3 + zzbum.zzH(2, this.zzku);
        if (this.zzkt != null) {
            n3 = n;
            if (this.zzkt.length > 0) {
                n2 = 0;
                for (n3 = 0; n3 < this.zzkt.length; ++n3) {
                    n2 += zzbum.zzqp(this.zzkt[n3]);
                }
                n3 = n + n2 + 1 * this.zzkt.length;
            }
        }
        n2 = n3;
        if (this.name != 0) {
            n2 = n3 + zzbum.zzH(4, this.name);
        }
        n3 = n2;
        if (this.zzkv) {
            n3 = n2 + zzbum.zzh(6, this.zzkv);
        }
        return n3;
    }
}
