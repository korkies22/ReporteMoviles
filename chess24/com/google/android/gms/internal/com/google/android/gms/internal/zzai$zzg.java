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

public static final class zzai.zzg
extends zzbun<zzai.zzg> {
    private static volatile zzai.zzg[] zzkW;
    public int[] zzkX;
    public int[] zzkY;
    public int[] zzkZ;
    public int[] zzla;
    public int[] zzlb;
    public int[] zzlc;
    public int[] zzld;
    public int[] zzle;
    public int[] zzlf;
    public int[] zzlg;

    public zzai.zzg() {
        this.zzJ();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzai.zzg[] zzI() {
        if (zzkW != null) return zzkW;
        Object object = zzbur.zzcsf;
        synchronized (object) {
            if (zzkW != null) return zzkW;
            zzkW = new zzai.zzg[0];
            return zzkW;
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzg)) {
            return false;
        }
        object = (zzai.zzg)object;
        if (!zzbur.equals(this.zzkX, object.zzkX)) {
            return false;
        }
        if (!zzbur.equals(this.zzkY, object.zzkY)) {
            return false;
        }
        if (!zzbur.equals(this.zzkZ, object.zzkZ)) {
            return false;
        }
        if (!zzbur.equals(this.zzla, object.zzla)) {
            return false;
        }
        if (!zzbur.equals(this.zzlb, object.zzlb)) {
            return false;
        }
        if (!zzbur.equals(this.zzlc, object.zzlc)) {
            return false;
        }
        if (!zzbur.equals(this.zzld, object.zzld)) {
            return false;
        }
        if (!zzbur.equals(this.zzle, object.zzle)) {
            return false;
        }
        if (!zzbur.equals(this.zzlf, object.zzlf)) {
            return false;
        }
        if (!zzbur.equals(this.zzlg, object.zzlg)) {
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
        int n2 = zzbur.hashCode(this.zzkX);
        int n3 = zzbur.hashCode(this.zzkY);
        int n4 = zzbur.hashCode(this.zzkZ);
        int n5 = zzbur.hashCode(this.zzla);
        int n6 = zzbur.hashCode(this.zzlb);
        int n7 = zzbur.hashCode(this.zzlc);
        int n8 = zzbur.hashCode(this.zzld);
        int n9 = zzbur.hashCode(this.zzle);
        int n10 = zzbur.hashCode(this.zzlf);
        int n11 = zzbur.hashCode(this.zzlg);
        int n12 = this.zzcrX != null && !this.zzcrX.isEmpty() ? this.zzcrX.hashCode() : 0;
        return 31 * (((((((((((527 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n11) + n12;
    }

    public zzai.zzg zzJ() {
        this.zzkX = zzbuw.zzcsi;
        this.zzkY = zzbuw.zzcsi;
        this.zzkZ = zzbuw.zzcsi;
        this.zzla = zzbuw.zzcsi;
        this.zzlb = zzbuw.zzcsi;
        this.zzlc = zzbuw.zzcsi;
        this.zzld = zzbuw.zzcsi;
        this.zzle = zzbuw.zzcsi;
        this.zzlf = zzbuw.zzcsi;
        this.zzlg = zzbuw.zzcsi;
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        int n;
        int[] arrn = this.zzkX;
        int n2 = 0;
        if (arrn != null && this.zzkX.length > 0) {
            for (n = 0; n < this.zzkX.length; ++n) {
                zzbum2.zzF(1, this.zzkX[n]);
            }
        }
        if (this.zzkY != null && this.zzkY.length > 0) {
            for (n = 0; n < this.zzkY.length; ++n) {
                zzbum2.zzF(2, this.zzkY[n]);
            }
        }
        if (this.zzkZ != null && this.zzkZ.length > 0) {
            for (n = 0; n < this.zzkZ.length; ++n) {
                zzbum2.zzF(3, this.zzkZ[n]);
            }
        }
        if (this.zzla != null && this.zzla.length > 0) {
            for (n = 0; n < this.zzla.length; ++n) {
                zzbum2.zzF(4, this.zzla[n]);
            }
        }
        if (this.zzlb != null && this.zzlb.length > 0) {
            for (n = 0; n < this.zzlb.length; ++n) {
                zzbum2.zzF(5, this.zzlb[n]);
            }
        }
        if (this.zzlc != null && this.zzlc.length > 0) {
            for (n = 0; n < this.zzlc.length; ++n) {
                zzbum2.zzF(6, this.zzlc[n]);
            }
        }
        if (this.zzld != null && this.zzld.length > 0) {
            for (n = 0; n < this.zzld.length; ++n) {
                zzbum2.zzF(7, this.zzld[n]);
            }
        }
        if (this.zzle != null && this.zzle.length > 0) {
            for (n = 0; n < this.zzle.length; ++n) {
                zzbum2.zzF(8, this.zzle[n]);
            }
        }
        if (this.zzlf != null && this.zzlf.length > 0) {
            for (n = 0; n < this.zzlf.length; ++n) {
                zzbum2.zzF(9, this.zzlf[n]);
            }
        }
        if (this.zzlg != null && this.zzlg.length > 0) {
            for (n = n2; n < this.zzlg.length; ++n) {
                zzbum2.zzF(10, this.zzlg[n]);
            }
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzt(zzbul2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public zzai.zzg zzt(zzbul var1_1) throws IOException {
        block23 : do {
            var2_2 = var1_1.zzacu();
            switch (var2_2) {
                default: {
                    if (super.zza(var1_1, var2_2)) continue block23;
                    return this;
                }
                case 82: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzlg == null ? 0 : this.zzlg.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlg, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzlg = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 80: {
                    var3_3 = zzbuw.zzc(var1_1, 80);
                    var2_2 = this.zzlg == null ? 0 : this.zzlg.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlg, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzlg = var5_5;
                    continue block23;
                }
                case 74: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzlf == null ? 0 : this.zzlf.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlf, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzlf = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 72: {
                    var3_3 = zzbuw.zzc(var1_1, 72);
                    var2_2 = this.zzlf == null ? 0 : this.zzlf.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlf, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzlf = var5_5;
                    continue block23;
                }
                case 66: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzle == null ? 0 : this.zzle.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzle, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzle = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 64: {
                    var3_3 = zzbuw.zzc(var1_1, 64);
                    var2_2 = this.zzle == null ? 0 : this.zzle.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzle, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzle = var5_5;
                    continue block23;
                }
                case 58: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzld == null ? 0 : this.zzld.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzld, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzld = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 56: {
                    var3_3 = zzbuw.zzc(var1_1, 56);
                    var2_2 = this.zzld == null ? 0 : this.zzld.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzld, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzld = var5_5;
                    continue block23;
                }
                case 50: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzlc == null ? 0 : this.zzlc.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlc, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzlc = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 48: {
                    var3_3 = zzbuw.zzc(var1_1, 48);
                    var2_2 = this.zzlc == null ? 0 : this.zzlc.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlc, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzlc = var5_5;
                    continue block23;
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
                    var2_2 = this.zzlb == null ? 0 : this.zzlb.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlb, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzlb = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 40: {
                    var3_3 = zzbuw.zzc(var1_1, 40);
                    var2_2 = this.zzlb == null ? 0 : this.zzlb.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzlb, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzlb = var5_5;
                    continue block23;
                }
                case 34: {
                    var4_4 = var1_1.zzqj(var1_1.zzacD());
                    var2_2 = var1_1.getPosition();
                    var3_3 = 0;
                    while (var1_1.zzacI() > 0) {
                        var1_1.zzacy();
                        ++var3_3;
                    }
                    var1_1.zzql(var2_2);
                    var2_2 = this.zzla == null ? 0 : this.zzla.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzla, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzla = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 32: {
                    var3_3 = zzbuw.zzc(var1_1, 32);
                    var2_2 = this.zzla == null ? 0 : this.zzla.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzla, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzla = var5_5;
                    continue block23;
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
                    var2_2 = this.zzkZ == null ? 0 : this.zzkZ.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzkZ, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzkZ = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 24: {
                    var3_3 = zzbuw.zzc(var1_1, 24);
                    var2_2 = this.zzkZ == null ? 0 : this.zzkZ.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzkZ, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzkZ = var5_5;
                    continue block23;
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
                    var2_2 = this.zzkY == null ? 0 : this.zzkY.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzkY, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzkY = var5_5;
                    var2_2 = var4_4;
                    ** GOTO lbl370
                }
                case 16: {
                    var3_3 = zzbuw.zzc(var1_1, 16);
                    var2_2 = this.zzkY == null ? 0 : this.zzkY.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzkY, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzkY = var5_5;
                    continue block23;
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
                    var2_2 = this.zzkX == null ? 0 : this.zzkX.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzkX, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length) {
                        var5_5[var3_3] = var1_1.zzacy();
                        ++var3_3;
                    }
                    this.zzkX = var5_5;
                    var2_2 = var4_4;
lbl370: // 10 sources:
                    var1_1.zzqk(var2_2);
                    continue block23;
                }
                case 8: {
                    var3_3 = zzbuw.zzc(var1_1, 8);
                    var2_2 = this.zzkX == null ? 0 : this.zzkX.length;
                    var5_5 = new int[var3_3 + var2_2];
                    var3_3 = var2_2;
                    if (var2_2 != 0) {
                        System.arraycopy(this.zzkX, 0, var5_5, 0, var2_2);
                        var3_3 = var2_2;
                    }
                    while (var3_3 < var5_5.length - 1) {
                        var5_5[var3_3] = var1_1.zzacy();
                        var1_1.zzacu();
                        ++var3_3;
                    }
                    var5_5[var3_3] = var1_1.zzacy();
                    this.zzkX = var5_5;
                    continue block23;
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
        int[] arrn = this.zzkX;
        int n3 = 0;
        int n4 = n2;
        if (arrn != null) {
            n4 = n2;
            if (this.zzkX.length > 0) {
                n = n4 = 0;
                while (n4 < this.zzkX.length) {
                    n += zzbum.zzqp(this.zzkX[n4]);
                    ++n4;
                }
                n4 = n2 + n + this.zzkX.length * 1;
            }
        }
        n = n4;
        if (this.zzkY != null) {
            n = n4;
            if (this.zzkY.length > 0) {
                n2 = n = 0;
                while (n < this.zzkY.length) {
                    n2 += zzbum.zzqp(this.zzkY[n]);
                    ++n;
                }
                n = n4 + n2 + this.zzkY.length * 1;
            }
        }
        n4 = n;
        if (this.zzkZ != null) {
            n4 = n;
            if (this.zzkZ.length > 0) {
                n2 = n4 = 0;
                while (n4 < this.zzkZ.length) {
                    n2 += zzbum.zzqp(this.zzkZ[n4]);
                    ++n4;
                }
                n4 = n + n2 + this.zzkZ.length * 1;
            }
        }
        n = n4;
        if (this.zzla != null) {
            n = n4;
            if (this.zzla.length > 0) {
                n2 = n = 0;
                while (n < this.zzla.length) {
                    n2 += zzbum.zzqp(this.zzla[n]);
                    ++n;
                }
                n = n4 + n2 + this.zzla.length * 1;
            }
        }
        n4 = n;
        if (this.zzlb != null) {
            n4 = n;
            if (this.zzlb.length > 0) {
                n2 = n4 = 0;
                while (n4 < this.zzlb.length) {
                    n2 += zzbum.zzqp(this.zzlb[n4]);
                    ++n4;
                }
                n4 = n + n2 + this.zzlb.length * 1;
            }
        }
        n = n4;
        if (this.zzlc != null) {
            n = n4;
            if (this.zzlc.length > 0) {
                n2 = n = 0;
                while (n < this.zzlc.length) {
                    n2 += zzbum.zzqp(this.zzlc[n]);
                    ++n;
                }
                n = n4 + n2 + this.zzlc.length * 1;
            }
        }
        n4 = n;
        if (this.zzld != null) {
            n4 = n;
            if (this.zzld.length > 0) {
                n2 = n4 = 0;
                while (n4 < this.zzld.length) {
                    n2 += zzbum.zzqp(this.zzld[n4]);
                    ++n4;
                }
                n4 = n + n2 + this.zzld.length * 1;
            }
        }
        n = n4;
        if (this.zzle != null) {
            n = n4;
            if (this.zzle.length > 0) {
                n2 = n = 0;
                while (n < this.zzle.length) {
                    n2 += zzbum.zzqp(this.zzle[n]);
                    ++n;
                }
                n = n4 + n2 + this.zzle.length * 1;
            }
        }
        n4 = n;
        if (this.zzlf != null) {
            n4 = n;
            if (this.zzlf.length > 0) {
                n2 = n4 = 0;
                while (n4 < this.zzlf.length) {
                    n2 += zzbum.zzqp(this.zzlf[n4]);
                    ++n4;
                }
                n4 = n + n2 + this.zzlf.length * 1;
            }
        }
        n = n4;
        if (this.zzlg != null) {
            n = n4;
            if (this.zzlg.length > 0) {
                n2 = 0;
                for (n = n3; n < this.zzlg.length; ++n) {
                    n2 += zzbum.zzqp(this.zzlg[n]);
                }
                n = n4 + n2 + 1 * this.zzlg.length;
            }
        }
        return n;
    }
}
