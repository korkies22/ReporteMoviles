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
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzai.zzf
extends zzbun<zzai.zzf> {
    public String version;
    public String[] zzkG;
    public String[] zzkH;
    public zzaj.zza[] zzkI;
    public zzai.zze[] zzkJ;
    public zzai.zzb[] zzkK;
    public zzai.zzb[] zzkL;
    public zzai.zzb[] zzkM;
    public zzai.zzg[] zzkN;
    public String zzkO;
    public String zzkP;
    public String zzkQ;
    public zzai.zza zzkR;
    public float zzkS;
    public boolean zzkT;
    public String[] zzkU;
    public int zzkV;

    public zzai.zzf() {
        this.zzH();
    }

    public static zzai.zzf zzf(byte[] arrby) throws zzbus {
        return zzbut.zza(new zzai.zzf(), arrby);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzf)) {
            return false;
        }
        object = (zzai.zzf)object;
        if (!zzbur.equals(this.zzkG, object.zzkG)) {
            return false;
        }
        if (!zzbur.equals(this.zzkH, object.zzkH)) {
            return false;
        }
        if (!zzbur.equals(this.zzkI, object.zzkI)) {
            return false;
        }
        if (!zzbur.equals(this.zzkJ, object.zzkJ)) {
            return false;
        }
        if (!zzbur.equals(this.zzkK, object.zzkK)) {
            return false;
        }
        if (!zzbur.equals(this.zzkL, object.zzkL)) {
            return false;
        }
        if (!zzbur.equals(this.zzkM, object.zzkM)) {
            return false;
        }
        if (!zzbur.equals(this.zzkN, object.zzkN)) {
            return false;
        }
        if (this.zzkO == null ? object.zzkO != null : !this.zzkO.equals(object.zzkO)) {
            return false;
        }
        if (this.zzkP == null ? object.zzkP != null : !this.zzkP.equals(object.zzkP)) {
            return false;
        }
        if (this.zzkQ == null ? object.zzkQ != null : !this.zzkQ.equals(object.zzkQ)) {
            return false;
        }
        if (this.version == null ? object.version != null : !this.version.equals(object.version)) {
            return false;
        }
        if (this.zzkR == null ? object.zzkR != null : !this.zzkR.equals(object.zzkR)) {
            return false;
        }
        if (Float.floatToIntBits(this.zzkS) != Float.floatToIntBits(object.zzkS)) {
            return false;
        }
        if (this.zzkT != object.zzkT) {
            return false;
        }
        if (!zzbur.equals(this.zzkU, object.zzkU)) {
            return false;
        }
        if (this.zzkV != object.zzkV) {
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
        int n2 = zzbur.hashCode(this.zzkG);
        int n3 = zzbur.hashCode(this.zzkH);
        int n4 = zzbur.hashCode(this.zzkI);
        int n5 = zzbur.hashCode(this.zzkJ);
        int n6 = zzbur.hashCode(this.zzkK);
        int n7 = zzbur.hashCode(this.zzkL);
        int n8 = zzbur.hashCode(this.zzkM);
        int n9 = zzbur.hashCode(this.zzkN);
        String string = this.zzkO;
        int n10 = 0;
        int n11 = string == null ? 0 : this.zzkO.hashCode();
        int n12 = this.zzkP == null ? 0 : this.zzkP.hashCode();
        int n13 = this.zzkQ == null ? 0 : this.zzkQ.hashCode();
        int n14 = this.version == null ? 0 : this.version.hashCode();
        int n15 = this.zzkR == null ? 0 : this.zzkR.hashCode();
        int n16 = Float.floatToIntBits(this.zzkS);
        int n17 = this.zzkT ? 1231 : 1237;
        int n18 = zzbur.hashCode(this.zzkU);
        int n19 = this.zzkV;
        int n20 = n10;
        if (this.zzcrX != null) {
            n20 = this.zzcrX.isEmpty() ? n10 : this.zzcrX.hashCode();
        }
        return 31 * ((((((((((((((((((527 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n11) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n15) * 31 + n16) * 31 + n17) * 31 + n18) * 31 + n19) + n20;
    }

    public zzai.zzf zzH() {
        this.zzkG = zzbuw.zzcsn;
        this.zzkH = zzbuw.zzcsn;
        this.zzkI = zzaj.zza.zzO();
        this.zzkJ = zzai.zze.zzF();
        this.zzkK = zzai.zzb.zzA();
        this.zzkL = zzai.zzb.zzA();
        this.zzkM = zzai.zzb.zzA();
        this.zzkN = zzai.zzg.zzI();
        this.zzkO = "";
        this.zzkP = "";
        this.zzkQ = "0";
        this.version = "";
        this.zzkR = null;
        this.zzkS = 0.0f;
        this.zzkT = false;
        this.zzkU = zzbuw.zzcsn;
        this.zzkV = 0;
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        int n;
        Object object = this.zzkH;
        int n2 = 0;
        if (object != null && this.zzkH.length > 0) {
            for (n = 0; n < this.zzkH.length; ++n) {
                object = this.zzkH[n];
                if (object == null) continue;
                zzbum2.zzq(1, (String)object);
            }
        }
        if (this.zzkI != null && this.zzkI.length > 0) {
            for (n = 0; n < this.zzkI.length; ++n) {
                object = this.zzkI[n];
                if (object == null) continue;
                zzbum2.zza(2, (zzbut)object);
            }
        }
        if (this.zzkJ != null && this.zzkJ.length > 0) {
            for (n = 0; n < this.zzkJ.length; ++n) {
                object = this.zzkJ[n];
                if (object == null) continue;
                zzbum2.zza(3, (zzbut)object);
            }
        }
        if (this.zzkK != null && this.zzkK.length > 0) {
            for (n = 0; n < this.zzkK.length; ++n) {
                object = this.zzkK[n];
                if (object == null) continue;
                zzbum2.zza(4, (zzbut)object);
            }
        }
        if (this.zzkL != null && this.zzkL.length > 0) {
            for (n = 0; n < this.zzkL.length; ++n) {
                object = this.zzkL[n];
                if (object == null) continue;
                zzbum2.zza(5, (zzbut)object);
            }
        }
        if (this.zzkM != null && this.zzkM.length > 0) {
            for (n = 0; n < this.zzkM.length; ++n) {
                object = this.zzkM[n];
                if (object == null) continue;
                zzbum2.zza(6, (zzbut)object);
            }
        }
        if (this.zzkN != null && this.zzkN.length > 0) {
            for (n = 0; n < this.zzkN.length; ++n) {
                object = this.zzkN[n];
                if (object == null) continue;
                zzbum2.zza(7, (zzbut)object);
            }
        }
        if (this.zzkO != null && !this.zzkO.equals("")) {
            zzbum2.zzq(9, this.zzkO);
        }
        if (this.zzkP != null && !this.zzkP.equals("")) {
            zzbum2.zzq(10, this.zzkP);
        }
        if (this.zzkQ != null && !this.zzkQ.equals("0")) {
            zzbum2.zzq(12, this.zzkQ);
        }
        if (this.version != null && !this.version.equals("")) {
            zzbum2.zzq(13, this.version);
        }
        if (this.zzkR != null) {
            zzbum2.zza(14, this.zzkR);
        }
        if (Float.floatToIntBits(this.zzkS) != Float.floatToIntBits(0.0f)) {
            zzbum2.zzc(15, this.zzkS);
        }
        if (this.zzkU != null && this.zzkU.length > 0) {
            for (n = 0; n < this.zzkU.length; ++n) {
                object = this.zzkU[n];
                if (object == null) continue;
                zzbum2.zzq(16, (String)object);
            }
        }
        if (this.zzkV != 0) {
            zzbum2.zzF(17, this.zzkV);
        }
        if (this.zzkT) {
            zzbum2.zzg(18, this.zzkT);
        }
        if (this.zzkG != null && this.zzkG.length > 0) {
            for (n = n2; n < this.zzkG.length; ++n) {
                object = this.zzkG[n];
                if (object == null) continue;
                zzbum2.zzq(19, (String)object);
            }
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzs(zzbul2);
    }

    public zzai.zzf zzs(zzbul zzbul2) throws IOException {
        block20 : do {
            int n = zzbul2.zzacu();
            switch (n) {
                int n2;
                Object[] arrobject;
                default: {
                    if (super.zza(zzbul2, n)) continue block20;
                    return this;
                }
                case 154: {
                    n2 = zzbuw.zzc(zzbul2, 154);
                    n = this.zzkG == null ? 0 : this.zzkG.length;
                    arrobject = new String[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkG, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = zzbul2.readString();
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = zzbul2.readString();
                    this.zzkG = arrobject;
                    continue block20;
                }
                case 144: {
                    this.zzkT = zzbul2.zzacA();
                    continue block20;
                }
                case 136: {
                    this.zzkV = zzbul2.zzacy();
                    continue block20;
                }
                case 130: {
                    n2 = zzbuw.zzc(zzbul2, 130);
                    n = this.zzkU == null ? 0 : this.zzkU.length;
                    arrobject = new String[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkU, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = zzbul2.readString();
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = zzbul2.readString();
                    this.zzkU = arrobject;
                    continue block20;
                }
                case 125: {
                    this.zzkS = zzbul2.readFloat();
                    continue block20;
                }
                case 114: {
                    if (this.zzkR == null) {
                        this.zzkR = new zzai.zza();
                    }
                    zzbul2.zza(this.zzkR);
                    continue block20;
                }
                case 106: {
                    this.version = zzbul2.readString();
                    continue block20;
                }
                case 98: {
                    this.zzkQ = zzbul2.readString();
                    continue block20;
                }
                case 82: {
                    this.zzkP = zzbul2.readString();
                    continue block20;
                }
                case 74: {
                    this.zzkO = zzbul2.readString();
                    continue block20;
                }
                case 58: {
                    n2 = zzbuw.zzc(zzbul2, 58);
                    n = this.zzkN == null ? 0 : this.zzkN.length;
                    arrobject = new zzai.zzg[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkN, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = new zzai.zzg();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = new zzai.zzg();
                    zzbul2.zza((zzbut)arrobject[n2]);
                    this.zzkN = arrobject;
                    continue block20;
                }
                case 50: {
                    n2 = zzbuw.zzc(zzbul2, 50);
                    n = this.zzkM == null ? 0 : this.zzkM.length;
                    arrobject = new zzai.zzb[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkM, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = new zzai.zzb();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = new zzai.zzb();
                    zzbul2.zza((zzbut)arrobject[n2]);
                    this.zzkM = arrobject;
                    continue block20;
                }
                case 42: {
                    n2 = zzbuw.zzc(zzbul2, 42);
                    n = this.zzkL == null ? 0 : this.zzkL.length;
                    arrobject = new zzai.zzb[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkL, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = new zzai.zzb();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = new zzai.zzb();
                    zzbul2.zza((zzbut)arrobject[n2]);
                    this.zzkL = arrobject;
                    continue block20;
                }
                case 34: {
                    n2 = zzbuw.zzc(zzbul2, 34);
                    n = this.zzkK == null ? 0 : this.zzkK.length;
                    arrobject = new zzai.zzb[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkK, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = new zzai.zzb();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = new zzai.zzb();
                    zzbul2.zza((zzbut)arrobject[n2]);
                    this.zzkK = arrobject;
                    continue block20;
                }
                case 26: {
                    n2 = zzbuw.zzc(zzbul2, 26);
                    n = this.zzkJ == null ? 0 : this.zzkJ.length;
                    arrobject = new zzai.zze[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkJ, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = new zzai.zze();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = new zzai.zze();
                    zzbul2.zza((zzbut)arrobject[n2]);
                    this.zzkJ = arrobject;
                    continue block20;
                }
                case 18: {
                    n2 = zzbuw.zzc(zzbul2, 18);
                    n = this.zzkI == null ? 0 : this.zzkI.length;
                    arrobject = new zzaj.zza[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkI, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = new zzaj.zza();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = new zzaj.zza();
                    zzbul2.zza((zzbut)arrobject[n2]);
                    this.zzkI = arrobject;
                    continue block20;
                }
                case 10: {
                    n2 = zzbuw.zzc(zzbul2, 10);
                    n = this.zzkH == null ? 0 : this.zzkH.length;
                    arrobject = new String[n2 + n];
                    n2 = n;
                    if (n != 0) {
                        System.arraycopy(this.zzkH, 0, arrobject, 0, n);
                        n2 = n;
                    }
                    while (n2 < arrobject.length - 1) {
                        arrobject[n2] = zzbul2.readString();
                        zzbul2.zzacu();
                        ++n2;
                    }
                    arrobject[n2] = zzbul2.readString();
                    this.zzkH = arrobject;
                    continue block20;
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
        int n2;
        int n3;
        int n4;
        int n5 = super.zzv();
        Object object = this.zzkH;
        int n6 = 0;
        int n7 = n5;
        if (object != null) {
            n7 = n5;
            if (this.zzkH.length > 0) {
                n = n7 = (n4 = 0);
                n3 = n7;
                for (n7 = n4; n7 < this.zzkH.length; ++n7) {
                    object = this.zzkH[n7];
                    n2 = n3;
                    n4 = n;
                    if (object != null) {
                        n4 = n + 1;
                        n2 = n3 + zzbum.zzkc((String)object);
                    }
                    n3 = n2;
                    n = n4;
                }
                n7 = n5 + n3 + 1 * n;
            }
        }
        n3 = n7;
        if (this.zzkI != null) {
            n3 = n7;
            if (this.zzkI.length > 0) {
                for (n3 = 0; n3 < this.zzkI.length; ++n3) {
                    object = this.zzkI[n3];
                    n = n7;
                    if (object != null) {
                        n = n7 + zzbum.zzc(2, (zzbut)object);
                    }
                    n7 = n;
                }
                n3 = n7;
            }
        }
        n7 = n3;
        if (this.zzkJ != null) {
            n7 = n3;
            if (this.zzkJ.length > 0) {
                n7 = n3;
                for (n3 = 0; n3 < this.zzkJ.length; ++n3) {
                    object = this.zzkJ[n3];
                    n = n7;
                    if (object != null) {
                        n = n7 + zzbum.zzc(3, (zzbut)object);
                    }
                    n7 = n;
                }
            }
        }
        n3 = n7;
        if (this.zzkK != null) {
            n3 = n7;
            if (this.zzkK.length > 0) {
                for (n3 = 0; n3 < this.zzkK.length; ++n3) {
                    object = this.zzkK[n3];
                    n = n7;
                    if (object != null) {
                        n = n7 + zzbum.zzc(4, (zzbut)object);
                    }
                    n7 = n;
                }
                n3 = n7;
            }
        }
        n7 = n3;
        if (this.zzkL != null) {
            n7 = n3;
            if (this.zzkL.length > 0) {
                n7 = n3;
                for (n3 = 0; n3 < this.zzkL.length; ++n3) {
                    object = this.zzkL[n3];
                    n = n7;
                    if (object != null) {
                        n = n7 + zzbum.zzc(5, (zzbut)object);
                    }
                    n7 = n;
                }
            }
        }
        n3 = n7;
        if (this.zzkM != null) {
            n3 = n7;
            if (this.zzkM.length > 0) {
                for (n3 = 0; n3 < this.zzkM.length; ++n3) {
                    object = this.zzkM[n3];
                    n = n7;
                    if (object != null) {
                        n = n7 + zzbum.zzc(6, (zzbut)object);
                    }
                    n7 = n;
                }
                n3 = n7;
            }
        }
        n7 = n3;
        if (this.zzkN != null) {
            n7 = n3;
            if (this.zzkN.length > 0) {
                n7 = n3;
                for (n3 = 0; n3 < this.zzkN.length; ++n3) {
                    object = this.zzkN[n3];
                    n = n7;
                    if (object != null) {
                        n = n7 + zzbum.zzc(7, (zzbut)object);
                    }
                    n7 = n;
                }
            }
        }
        n3 = n7;
        if (this.zzkO != null) {
            n3 = n7;
            if (!this.zzkO.equals("")) {
                n3 = n7 + zzbum.zzr(9, this.zzkO);
            }
        }
        n7 = n3;
        if (this.zzkP != null) {
            n7 = n3;
            if (!this.zzkP.equals("")) {
                n7 = n3 + zzbum.zzr(10, this.zzkP);
            }
        }
        n3 = n7;
        if (this.zzkQ != null) {
            n3 = n7;
            if (!this.zzkQ.equals("0")) {
                n3 = n7 + zzbum.zzr(12, this.zzkQ);
            }
        }
        n7 = n3;
        if (this.version != null) {
            n7 = n3;
            if (!this.version.equals("")) {
                n7 = n3 + zzbum.zzr(13, this.version);
            }
        }
        n3 = n7;
        if (this.zzkR != null) {
            n3 = n7 + zzbum.zzc(14, this.zzkR);
        }
        n7 = n3;
        if (Float.floatToIntBits(this.zzkS) != Float.floatToIntBits(0.0f)) {
            n7 = n3 + zzbum.zzd(15, this.zzkS);
        }
        n3 = n7;
        if (this.zzkU != null) {
            n3 = n7;
            if (this.zzkU.length > 0) {
                n4 = n3 = (n2 = 0);
                n = n3;
                for (n3 = n2; n3 < this.zzkU.length; ++n3) {
                    object = this.zzkU[n3];
                    n5 = n;
                    n2 = n4;
                    if (object != null) {
                        n2 = n4 + 1;
                        n5 = n + zzbum.zzkc((String)object);
                    }
                    n = n5;
                    n4 = n2;
                }
                n3 = n7 + n + n4 * 2;
            }
        }
        n = n3;
        if (this.zzkV != 0) {
            n = n3 + zzbum.zzH(17, this.zzkV);
        }
        n7 = n;
        if (this.zzkT) {
            n7 = n + zzbum.zzh(18, this.zzkT);
        }
        n3 = n7;
        if (this.zzkG != null) {
            n3 = n7;
            if (this.zzkG.length > 0) {
                n4 = n = 0;
                for (n3 = n6; n3 < this.zzkG.length; ++n3) {
                    object = this.zzkG[n3];
                    n5 = n;
                    n2 = n4;
                    if (object != null) {
                        n2 = n4 + 1;
                        n5 = n + zzbum.zzkc((String)object);
                    }
                    n = n5;
                    n4 = n2;
                }
                n3 = n7 + n + 2 * n4;
            }
        }
        return n3;
    }
}
