/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public interface zzaj {

    public static final class zza
    extends zzbun<zza> {
        private static volatile zza[] zzlw;
        public String string;
        public int type;
        public String zzlA;
        public String zzlB;
        public long zzlC;
        public boolean zzlD;
        public zza[] zzlE;
        public int[] zzlF;
        public boolean zzlG;
        public zza[] zzlx;
        public zza[] zzly;
        public zza[] zzlz;

        public zza() {
            this.zzP();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static zza[] zzO() {
            if (zzlw != null) return zzlw;
            Object object = zzbur.zzcsf;
            synchronized (object) {
                if (zzlw != null) return zzlw;
                zzlw = new zza[0];
                return zzlw;
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zza)) {
                return false;
            }
            object = (zza)object;
            if (this.type != object.type) {
                return false;
            }
            if (this.string == null ? object.string != null : !this.string.equals(object.string)) {
                return false;
            }
            if (!zzbur.equals(this.zzlx, object.zzlx)) {
                return false;
            }
            if (!zzbur.equals(this.zzly, object.zzly)) {
                return false;
            }
            if (!zzbur.equals(this.zzlz, object.zzlz)) {
                return false;
            }
            if (this.zzlA == null ? object.zzlA != null : !this.zzlA.equals(object.zzlA)) {
                return false;
            }
            if (this.zzlB == null ? object.zzlB != null : !this.zzlB.equals(object.zzlB)) {
                return false;
            }
            if (this.zzlC != object.zzlC) {
                return false;
            }
            if (this.zzlD != object.zzlD) {
                return false;
            }
            if (!zzbur.equals(this.zzlE, object.zzlE)) {
                return false;
            }
            if (!zzbur.equals(this.zzlF, object.zzlF)) {
                return false;
            }
            if (this.zzlG != object.zzlG) {
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
            int n2 = this.type;
            String string = this.string;
            int n3 = 0;
            int n4 = string == null ? 0 : this.string.hashCode();
            int n5 = zzbur.hashCode(this.zzlx);
            int n6 = zzbur.hashCode(this.zzly);
            int n7 = zzbur.hashCode(this.zzlz);
            int n8 = this.zzlA == null ? 0 : this.zzlA.hashCode();
            int n9 = this.zzlB == null ? 0 : this.zzlB.hashCode();
            int n10 = (int)(this.zzlC ^ this.zzlC >>> 32);
            boolean bl = this.zzlD;
            int n11 = 1237;
            int n12 = bl ? 1231 : 1237;
            int n13 = zzbur.hashCode(this.zzlE);
            int n14 = zzbur.hashCode(this.zzlF);
            if (this.zzlG) {
                n11 = 1231;
            }
            int n15 = n3;
            if (this.zzcrX != null) {
                n15 = this.zzcrX.isEmpty() ? n3 : this.zzcrX.hashCode();
            }
            return 31 * (((((((((((((527 + n) * 31 + n2) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n10) * 31 + n12) * 31 + n13) * 31 + n14) * 31 + n11) + n15;
        }

        public zza zzP() {
            this.type = 1;
            this.string = "";
            this.zzlx = zza.zzO();
            this.zzly = zza.zzO();
            this.zzlz = zza.zzO();
            this.zzlA = "";
            this.zzlB = "";
            this.zzlC = 0L;
            this.zzlD = false;
            this.zzlE = zza.zzO();
            this.zzlF = zzbuw.zzcsi;
            this.zzlG = false;
            this.zzcrX = null;
            this.zzcsg = -1;
            return this;
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            int n;
            zzbum2.zzF(1, this.type);
            if (this.string != null && !this.string.equals("")) {
                zzbum2.zzq(2, this.string);
            }
            Object object = this.zzlx;
            int n2 = 0;
            if (object != null && this.zzlx.length > 0) {
                for (n = 0; n < this.zzlx.length; ++n) {
                    object = this.zzlx[n];
                    if (object == null) continue;
                    zzbum2.zza(3, (zzbut)object);
                }
            }
            if (this.zzly != null && this.zzly.length > 0) {
                for (n = 0; n < this.zzly.length; ++n) {
                    object = this.zzly[n];
                    if (object == null) continue;
                    zzbum2.zza(4, (zzbut)object);
                }
            }
            if (this.zzlz != null && this.zzlz.length > 0) {
                for (n = 0; n < this.zzlz.length; ++n) {
                    object = this.zzlz[n];
                    if (object == null) continue;
                    zzbum2.zza(5, (zzbut)object);
                }
            }
            if (this.zzlA != null && !this.zzlA.equals("")) {
                zzbum2.zzq(6, this.zzlA);
            }
            if (this.zzlB != null && !this.zzlB.equals("")) {
                zzbum2.zzq(7, this.zzlB);
            }
            if (this.zzlC != 0L) {
                zzbum2.zzb(8, this.zzlC);
            }
            if (this.zzlG) {
                zzbum2.zzg(9, this.zzlG);
            }
            if (this.zzlF != null && this.zzlF.length > 0) {
                for (n = 0; n < this.zzlF.length; ++n) {
                    zzbum2.zzF(10, this.zzlF[n]);
                }
            }
            if (this.zzlE != null && this.zzlE.length > 0) {
                for (n = n2; n < this.zzlE.length; ++n) {
                    object = this.zzlE[n];
                    if (object == null) continue;
                    zzbum2.zza(11, (zzbut)object);
                }
            }
            if (this.zzlD) {
                zzbum2.zzg(12, this.zzlD);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzx(zzbul2);
        }

        @Override
        protected int zzv() {
            int n;
            int n2;
            int n3 = n2 = super.zzv() + zzbum.zzH(1, this.type);
            if (this.string != null) {
                n3 = n2;
                if (!this.string.equals("")) {
                    n3 = n2 + zzbum.zzr(2, this.string);
                }
            }
            Object object = this.zzlx;
            int n4 = 0;
            n2 = n3;
            if (object != null) {
                n2 = n3;
                if (this.zzlx.length > 0) {
                    for (n2 = 0; n2 < this.zzlx.length; ++n2) {
                        object = this.zzlx[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + zzbum.zzc(3, (zzbut)object);
                        }
                        n3 = n;
                    }
                    n2 = n3;
                }
            }
            n3 = n2;
            if (this.zzly != null) {
                n3 = n2;
                if (this.zzly.length > 0) {
                    n3 = n2;
                    for (n2 = 0; n2 < this.zzly.length; ++n2) {
                        object = this.zzly[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + zzbum.zzc(4, (zzbut)object);
                        }
                        n3 = n;
                    }
                }
            }
            n2 = n3;
            if (this.zzlz != null) {
                n2 = n3;
                if (this.zzlz.length > 0) {
                    for (n2 = 0; n2 < this.zzlz.length; ++n2) {
                        object = this.zzlz[n2];
                        n = n3;
                        if (object != null) {
                            n = n3 + zzbum.zzc(5, (zzbut)object);
                        }
                        n3 = n;
                    }
                    n2 = n3;
                }
            }
            n3 = n2;
            if (this.zzlA != null) {
                n3 = n2;
                if (!this.zzlA.equals("")) {
                    n3 = n2 + zzbum.zzr(6, this.zzlA);
                }
            }
            n2 = n3;
            if (this.zzlB != null) {
                n2 = n3;
                if (!this.zzlB.equals("")) {
                    n2 = n3 + zzbum.zzr(7, this.zzlB);
                }
            }
            n3 = n2;
            if (this.zzlC != 0L) {
                n3 = n2 + zzbum.zzf(8, this.zzlC);
            }
            n2 = n3;
            if (this.zzlG) {
                n2 = n3 + zzbum.zzh(9, this.zzlG);
            }
            n3 = n2;
            if (this.zzlF != null) {
                n3 = n2;
                if (this.zzlF.length > 0) {
                    n = n3 = 0;
                    while (n3 < this.zzlF.length) {
                        n += zzbum.zzqp(this.zzlF[n3]);
                        ++n3;
                    }
                    n3 = n2 + n + 1 * this.zzlF.length;
                }
            }
            n2 = n3;
            if (this.zzlE != null) {
                n2 = n3;
                if (this.zzlE.length > 0) {
                    n = n4;
                    do {
                        n2 = n3;
                        if (n >= this.zzlE.length) break;
                        object = this.zzlE[n];
                        n2 = n3;
                        if (object != null) {
                            n2 = n3 + zzbum.zzc(11, (zzbut)object);
                        }
                        ++n;
                        n3 = n2;
                    } while (true);
                }
            }
            n3 = n2;
            if (this.zzlD) {
                n3 = n2 + zzbum.zzh(12, this.zzlD);
            }
            return n3;
        }

        public zza zzx(zzbul zzbul2) throws IOException {
            block28 : do {
                int n = zzbul2.zzacu();
                switch (n) {
                    Object[] arrobject;
                    int n2;
                    int n3;
                    default: {
                        if (super.zza(zzbul2, n)) continue block28;
                        return this;
                    }
                    case 96: {
                        this.zzlD = zzbul2.zzacA();
                        continue block28;
                    }
                    case 90: {
                        n2 = zzbuw.zzc(zzbul2, 90);
                        n = this.zzlE == null ? 0 : this.zzlE.length;
                        arrobject = new zza[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzlE, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = (int)new zza();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = (int)new zza();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzlE = arrobject;
                        continue block28;
                    }
                    case 82: {
                        n3 = zzbul2.zzqj(zzbul2.zzacD());
                        n = zzbul2.getPosition();
                        n2 = 0;
                        block30 : while (zzbul2.zzacI() > 0) {
                            switch (zzbul2.zzacy()) {
                                default: {
                                    continue block30;
                                }
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: 
                            }
                            ++n2;
                        }
                        if (n2 != 0) {
                            zzbul2.zzql(n);
                            n = this.zzlF == null ? 0 : this.zzlF.length;
                            arrobject = new int[n2 + n];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.zzlF, 0, arrobject, 0, n);
                                n2 = n;
                            }
                            block31 : while (zzbul2.zzacI() > 0) {
                                n = zzbul2.zzacy();
                                switch (n) {
                                    default: {
                                        continue block31;
                                    }
                                    case 1: 
                                    case 2: 
                                    case 3: 
                                    case 4: 
                                    case 5: 
                                    case 6: 
                                    case 7: 
                                    case 8: 
                                    case 9: 
                                    case 10: 
                                    case 11: 
                                    case 12: 
                                    case 13: 
                                    case 14: 
                                    case 15: 
                                    case 16: 
                                    case 17: 
                                }
                                arrobject[n2] = n;
                                ++n2;
                            }
                            this.zzlF = arrobject;
                        }
                        zzbul2.zzqk(n3);
                        continue block28;
                    }
                    case 80: {
                        n3 = zzbuw.zzc(zzbul2, 80);
                        arrobject = new int[n3];
                        n = n2 = 0;
                        while (n2 < n3) {
                            if (n2 != 0) {
                                zzbul2.zzacu();
                            }
                            int n4 = zzbul2.zzacy();
                            switch (n4) {
                                default: {
                                    break;
                                }
                                case 1: 
                                case 2: 
                                case 3: 
                                case 4: 
                                case 5: 
                                case 6: 
                                case 7: 
                                case 8: 
                                case 9: 
                                case 10: 
                                case 11: 
                                case 12: 
                                case 13: 
                                case 14: 
                                case 15: 
                                case 16: 
                                case 17: {
                                    arrobject[n] = n4;
                                    ++n;
                                }
                            }
                            ++n2;
                        }
                        if (n == 0) continue block28;
                        n2 = this.zzlF == null ? 0 : this.zzlF.length;
                        if (n2 == 0 && n == arrobject.length) {
                            this.zzlF = arrobject;
                            continue block28;
                        }
                        int[] arrn = new int[n2 + n];
                        if (n2 != 0) {
                            System.arraycopy(this.zzlF, 0, arrn, 0, n2);
                        }
                        System.arraycopy(arrobject, 0, arrn, n2, n);
                        this.zzlF = arrn;
                        continue block28;
                    }
                    case 72: {
                        this.zzlG = zzbul2.zzacA();
                        continue block28;
                    }
                    case 64: {
                        this.zzlC = zzbul2.zzacx();
                        continue block28;
                    }
                    case 58: {
                        this.zzlB = zzbul2.readString();
                        continue block28;
                    }
                    case 50: {
                        this.zzlA = zzbul2.readString();
                        continue block28;
                    }
                    case 42: {
                        n2 = zzbuw.zzc(zzbul2, 42);
                        n = this.zzlz == null ? 0 : this.zzlz.length;
                        arrobject = new zza[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzlz, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = (int)new zza();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = (int)new zza();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzlz = arrobject;
                        continue block28;
                    }
                    case 34: {
                        n2 = zzbuw.zzc(zzbul2, 34);
                        n = this.zzly == null ? 0 : this.zzly.length;
                        arrobject = new zza[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzly, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = (int)new zza();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = (int)new zza();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzly = arrobject;
                        continue block28;
                    }
                    case 26: {
                        n2 = zzbuw.zzc(zzbul2, 26);
                        n = this.zzlx == null ? 0 : this.zzlx.length;
                        arrobject = new zza[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzlx, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = (int)new zza();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = (int)new zza();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzlx = arrobject;
                        continue block28;
                    }
                    case 18: {
                        this.string = zzbul2.readString();
                        continue block28;
                    }
                    case 8: {
                        n = zzbul2.zzacy();
                        switch (n) {
                            default: {
                                continue block28;
                            }
                            case 1: 
                            case 2: 
                            case 3: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 7: 
                            case 8: 
                        }
                        this.type = n;
                        continue block28;
                    }
                    case 0: 
                }
                break;
            } while (true);
            return this;
        }
    }

}
