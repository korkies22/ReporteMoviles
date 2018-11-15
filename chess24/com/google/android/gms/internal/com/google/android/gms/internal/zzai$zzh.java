/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbuo;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzai.zzh
extends zzbun<zzai.zzh> {
    public static final zzbuo<zzaj.zza, zzai.zzh> zzlh = zzbuo.zza(11, zzai.zzh.class, 810L);
    private static final zzai.zzh[] zzli = new zzai.zzh[0];
    public int[] zzlj;
    public int[] zzlk;
    public int[] zzll;
    public int zzlm;
    public int[] zzln;
    public int zzlo;
    public int zzlp;

    public zzai.zzh() {
        this.zzK();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzh)) {
            return false;
        }
        object = (zzai.zzh)object;
        if (!zzbur.equals(this.zzlj, object.zzlj)) {
            return false;
        }
        if (!zzbur.equals(this.zzlk, object.zzlk)) {
            return false;
        }
        if (!zzbur.equals(this.zzll, object.zzll)) {
            return false;
        }
        if (this.zzlm != object.zzlm) {
            return false;
        }
        if (!zzbur.equals(this.zzln, object.zzln)) {
            return false;
        }
        if (this.zzlo != object.zzlo) {
            return false;
        }
        if (this.zzlp != object.zzlp) {
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
        int n2 = zzbur.hashCode(this.zzlj);
        int n3 = zzbur.hashCode(this.zzlk);
        int n4 = zzbur.hashCode(this.zzll);
        int n5 = this.zzlm;
        int n6 = zzbur.hashCode(this.zzln);
        int n7 = this.zzlo;
        int n8 = this.zzlp;
        int n9 = this.zzcrX != null && !this.zzcrX.isEmpty() ? this.zzcrX.hashCode() : 0;
        return 31 * ((((((((527 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) + n9;
    }

    public zzai.zzh zzK() {
        this.zzlj = zzbuw.zzcsi;
        this.zzlk = zzbuw.zzcsi;
        this.zzll = zzbuw.zzcsi;
        this.zzlm = 0;
        this.zzln = zzbuw.zzcsi;
        this.zzlo = 0;
        this.zzlp = 0;
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        int n;
        int[] arrn = this.zzlj;
        int n2 = 0;
        if (arrn != null && this.zzlj.length > 0) {
            for (n = 0; n < this.zzlj.length; ++n) {
                zzbum2.zzF(1, this.zzlj[n]);
            }
        }
        if (this.zzlk != null && this.zzlk.length > 0) {
            for (n = 0; n < this.zzlk.length; ++n) {
                zzbum2.zzF(2, this.zzlk[n]);
            }
        }
        if (this.zzll != null && this.zzll.length > 0) {
            for (n = 0; n < this.zzll.length; ++n) {
                zzbum2.zzF(3, this.zzll[n]);
            }
        }
        if (this.zzlm != 0) {
            zzbum2.zzF(4, this.zzlm);
        }
        if (this.zzln != null && this.zzln.length > 0) {
            for (n = n2; n < this.zzln.length; ++n) {
                zzbum2.zzF(5, this.zzln[n]);
            }
        }
        if (this.zzlo != 0) {
            zzbum2.zzF(6, this.zzlo);
        }
        if (this.zzlp != 0) {
            zzbum2.zzF(7, this.zzlp);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzu(zzbul2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public zzai.zzh zzu(zzbul var1_1) throws IOException {
        block14 : do {
            var2_2 = var1_1.zzacu();
            switch (var2_2) {
                default: {
                    if (super.zza(var1_1, var2_2)) continue block14;
                    return this;
                }
                case 56: {
                    this.zzlp = var1_1.zzacy();
                    continue block14;
                }
                case 48: {
                    this.zzlo = var1_1.zzacy();
                    continue block14;
                }
                case 42: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzln == null ? 0 : this.zzln.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzln, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzln = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl151
                }
                case 40: {
                    var3_3 = zzbuw.zzc(var1_1, 40);
                    var2_2 = this.zzln == null ? 0 : this.zzln.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzln, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzln = var5_5;
                    continue block14;
                }
                case 32: {
                    this.zzlm = var1_1.zzacy();
                    continue block14;
                }
                case 26: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzll == null ? 0 : this.zzll.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzll, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzll = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl151
                }
                case 24: {
                    var3_3 = zzbuw.zzc(var1_1, 24);
                    var2_2 = this.zzll == null ? 0 : this.zzll.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzll, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzll = var5_5;
                    continue block14;
                }
                case 18: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzlk == null ? 0 : this.zzlk.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlk, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzlk = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl151
                }
                case 16: {
                    var3_3 = zzbuw.zzc(var1_1, 16);
                    var2_2 = this.zzlk == null ? 0 : this.zzlk.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlk, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzlk = var5_5;
                    continue block14;
                }
                case 10: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzlj == null ? 0 : this.zzlj.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlj, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzlj = var5_5;
                    var2_2 = var4_4;
lbl151: // 4 sources:
                    var1_1.zzqk(var2_2);
                    continue block14;
                }
                case 8: {
                    var3_3 = zzbuw.zzc(var1_1, 8);
                    var2_2 = this.zzlj == null ? 0 : this.zzlj.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlj, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzlj = var5_5;
                    continue block14;
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
        int n2 = super.zzv();
        int[] arrn = this.zzlj;
        int n3 = 0;
        int n4 = n2;
        if (arrn != null) {
            n4 = n2;
            if (this.zzlj.length > 0) {
                n = n4 = 0;
                while (n4 < this.zzlj.length) {
                    n += zzbum.zzqp(this.zzlj[n4]);
                    ++n4;
                }
                n4 = n2 + n + this.zzlj.length * 1;
            }
        }
        n = n4;
        if (this.zzlk != null) {
            n = n4;
            if (this.zzlk.length > 0) {
                n2 = n = 0;
                while (n < this.zzlk.length) {
                    n2 += zzbum.zzqp(this.zzlk[n]);
                    ++n;
                }
                n = n4 + n2 + this.zzlk.length * 1;
            }
        }
        n2 = n;
        if (this.zzll != null) {
            n2 = n;
            if (this.zzll.length > 0) {
                n2 = n4 = 0;
                while (n4 < this.zzll.length) {
                    n2 += zzbum.zzqp(this.zzll[n4]);
                    ++n4;
                }
                n2 = n + n2 + this.zzll.length * 1;
            }
        }
        n4 = n2;
        if (this.zzlm != 0) {
            n4 = n2 + zzbum.zzH(4, this.zzlm);
        }
        n = n4;
        if (this.zzln != null) {
            n = n4;
            if (this.zzln.length > 0) {
                n2 = 0;
                for (n = n3; n < this.zzln.length; ++n) {
                    n2 += zzbum.zzqp(this.zzln[n]);
                }
                n = n4 + n2 + 1 * this.zzln.length;
            }
        }
        n4 = n;
        if (this.zzlo != 0) {
            n4 = n + zzbum.zzH(6, this.zzlo);
        }
        n = n4;
        if (this.zzlp != 0) {
            n = n4 + zzbum.zzH(7, this.zzlp);
        }
        return n;
    }
}
