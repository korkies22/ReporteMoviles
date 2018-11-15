/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbuo;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public interface zzai {

    public static final class zza
    extends zzbun<zza> {
        public int level;
        public int zzkq;
        public int zzkr;

        public zza() {
            this.zzz();
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
            if (this.level != object.level) {
                return false;
            }
            if (this.zzkq != object.zzkq) {
                return false;
            }
            if (this.zzkr != object.zzkr) {
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
            int n2 = this.level;
            int n3 = this.zzkq;
            int n4 = this.zzkr;
            int n5 = this.zzcrX != null && !this.zzcrX.isEmpty() ? this.zzcrX.hashCode() : 0;
            return 31 * ((((527 + n) * 31 + n2) * 31 + n3) * 31 + n4) + n5;
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            if (this.level != 1) {
                zzbum2.zzF(1, this.level);
            }
            if (this.zzkq != 0) {
                zzbum2.zzF(2, this.zzkq);
            }
            if (this.zzkr != 0) {
                zzbum2.zzF(3, this.zzkr);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzn(zzbul2);
        }

        public zza zzn(zzbul zzbul2) throws IOException {
            int n;
            block3 : while ((n = zzbul2.zzacu()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (n != 24) {
                            if (super.zza(zzbul2, n)) continue;
                            return this;
                        }
                        this.zzkr = zzbul2.zzacy();
                        continue;
                    }
                    this.zzkq = zzbul2.zzacy();
                    continue;
                }
                n = zzbul2.zzacy();
                switch (n) {
                    default: {
                        continue block3;
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                }
                this.level = n;
            }
            return this;
        }

        @Override
        protected int zzv() {
            int n;
            int n2 = n = super.zzv();
            if (this.level != 1) {
                n2 = n + zzbum.zzH(1, this.level);
            }
            n = n2;
            if (this.zzkq != 0) {
                n = n2 + zzbum.zzH(2, this.zzkq);
            }
            n2 = n;
            if (this.zzkr != 0) {
                n2 = n + zzbum.zzH(3, this.zzkr);
            }
            return n2;
        }

        public zza zzz() {
            this.level = 1;
            this.zzkq = 0;
            this.zzkr = 0;
            this.zzcrX = null;
            this.zzcsg = -1;
            return this;
        }
    }

    public static final class zzb
    extends zzbun<zzb> {
        private static volatile zzb[] zzks;
        public int name;
        public int[] zzkt;
        public int zzku;
        public boolean zzkv;
        public boolean zzkw;

        public zzb() {
            this.zzB();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static zzb[] zzA() {
            if (zzks != null) return zzks;
            Object object = zzbur.zzcsf;
            synchronized (object) {
                if (zzks != null) return zzks;
                zzks = new zzb[0];
                return zzks;
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzb)) {
                return false;
            }
            object = (zzb)object;
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

        public zzb zzB() {
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

        public zzb zzo(zzbul zzbul2) throws IOException {
            int n;
            while ((n = zzbul2.zzacu()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        int[] arrn;
                        int n2;
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

    public static final class zzc
    extends zzbun<zzc> {
        private static volatile zzc[] zzkx;
        public String zzaA;
        public boolean zzkA;
        public long zzkB;
        public long zzky;
        public long zzkz;

        public zzc() {
            this.zzD();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static zzc[] zzC() {
            if (zzkx != null) return zzkx;
            Object object = zzbur.zzcsf;
            synchronized (object) {
                if (zzkx != null) return zzkx;
                zzkx = new zzc[0];
                return zzkx;
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzc)) {
                return false;
            }
            object = (zzc)object;
            if (this.zzaA == null ? object.zzaA != null : !this.zzaA.equals(object.zzaA)) {
                return false;
            }
            if (this.zzky != object.zzky) {
                return false;
            }
            if (this.zzkz != object.zzkz) {
                return false;
            }
            if (this.zzkA != object.zzkA) {
                return false;
            }
            if (this.zzkB != object.zzkB) {
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
            String string = this.zzaA;
            int n2 = 0;
            int n3 = string == null ? 0 : this.zzaA.hashCode();
            int n4 = (int)(this.zzky ^ this.zzky >>> 32);
            int n5 = (int)(this.zzkz ^ this.zzkz >>> 32);
            int n6 = this.zzkA ? 1231 : 1237;
            int n7 = (int)(this.zzkB ^ this.zzkB >>> 32);
            int n8 = n2;
            if (this.zzcrX != null) {
                n8 = this.zzcrX.isEmpty() ? n2 : this.zzcrX.hashCode();
            }
            return 31 * ((((((527 + n) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) + n8;
        }

        public zzc zzD() {
            this.zzaA = "";
            this.zzky = 0L;
            this.zzkz = Integer.MAX_VALUE;
            this.zzkA = false;
            this.zzkB = 0L;
            this.zzcrX = null;
            this.zzcsg = -1;
            return this;
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            if (this.zzaA != null && !this.zzaA.equals("")) {
                zzbum2.zzq(1, this.zzaA);
            }
            if (this.zzky != 0L) {
                zzbum2.zzb(2, this.zzky);
            }
            if (this.zzkz != Integer.MAX_VALUE) {
                zzbum2.zzb(3, this.zzkz);
            }
            if (this.zzkA) {
                zzbum2.zzg(4, this.zzkA);
            }
            if (this.zzkB != 0L) {
                zzbum2.zzb(5, this.zzkB);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzp(zzbul2);
        }

        public zzc zzp(zzbul zzbul2) throws IOException {
            int n;
            while ((n = zzbul2.zzacu()) != 0) {
                if (n != 10) {
                    if (n != 16) {
                        if (n != 24) {
                            if (n != 32) {
                                if (n != 40) {
                                    if (super.zza(zzbul2, n)) continue;
                                    return this;
                                }
                                this.zzkB = zzbul2.zzacx();
                                continue;
                            }
                            this.zzkA = zzbul2.zzacA();
                            continue;
                        }
                        this.zzkz = zzbul2.zzacx();
                        continue;
                    }
                    this.zzky = zzbul2.zzacx();
                    continue;
                }
                this.zzaA = zzbul2.readString();
            }
            return this;
        }

        @Override
        protected int zzv() {
            int n;
            int n2 = n = super.zzv();
            if (this.zzaA != null) {
                n2 = n;
                if (!this.zzaA.equals("")) {
                    n2 = n + zzbum.zzr(1, this.zzaA);
                }
            }
            n = n2;
            if (this.zzky != 0L) {
                n = n2 + zzbum.zzf(2, this.zzky);
            }
            n2 = n;
            if (this.zzkz != Integer.MAX_VALUE) {
                n2 = n + zzbum.zzf(3, this.zzkz);
            }
            n = n2;
            if (this.zzkA) {
                n = n2 + zzbum.zzh(4, this.zzkA);
            }
            n2 = n;
            if (this.zzkB != 0L) {
                n2 = n + zzbum.zzf(5, this.zzkB);
            }
            return n2;
        }
    }

    public static final class zzd
    extends zzbun<zzd> {
        public zzaj.zza[] zzkC;
        public zzaj.zza[] zzkD;
        public zzc[] zzkE;

        public zzd() {
            this.zzE();
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzd)) {
                return false;
            }
            object = (zzd)object;
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

        public zzd zzE() {
            this.zzkC = zzaj.zza.zzO();
            this.zzkD = zzaj.zza.zzO();
            this.zzkE = zzc.zzC();
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

        public zzd zzq(zzbul zzbul2) throws IOException {
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
                        arrzzbun = new zzc[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzkE, 0, arrzzbun, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrzzbun.length - 1) {
                            arrzzbun[n2] = new zzc();
                            zzbul2.zza(arrzzbun[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrzzbun[n2] = new zzc();
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

    public static final class zze
    extends zzbun<zze> {
        private static volatile zze[] zzkF;
        public int key;
        public int value;

        public zze() {
            this.zzG();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static zze[] zzF() {
            if (zzkF != null) return zzkF;
            Object object = zzbur.zzcsf;
            synchronized (object) {
                if (zzkF != null) return zzkF;
                zzkF = new zze[0];
                return zzkF;
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zze)) {
                return false;
            }
            object = (zze)object;
            if (this.key != object.key) {
                return false;
            }
            if (this.value != object.value) {
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
            int n2 = this.key;
            int n3 = this.value;
            int n4 = this.zzcrX != null && !this.zzcrX.isEmpty() ? this.zzcrX.hashCode() : 0;
            return 31 * (((527 + n) * 31 + n2) * 31 + n3) + n4;
        }

        public zze zzG() {
            this.key = 0;
            this.value = 0;
            this.zzcrX = null;
            this.zzcsg = -1;
            return this;
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            zzbum2.zzF(1, this.key);
            zzbum2.zzF(2, this.value);
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzr(zzbul2);
        }

        public zze zzr(zzbul zzbul2) throws IOException {
            int n;
            while ((n = zzbul2.zzacu()) != 0) {
                if (n != 8) {
                    if (n != 16) {
                        if (super.zza(zzbul2, n)) continue;
                        return this;
                    }
                    this.value = zzbul2.zzacy();
                    continue;
                }
                this.key = zzbul2.zzacy();
            }
            return this;
        }

        @Override
        protected int zzv() {
            return super.zzv() + zzbum.zzH(1, this.key) + zzbum.zzH(2, this.value);
        }
    }

    public static final class zzf
    extends zzbun<zzf> {
        public String version;
        public String[] zzkG;
        public String[] zzkH;
        public zzaj.zza[] zzkI;
        public zze[] zzkJ;
        public zzb[] zzkK;
        public zzb[] zzkL;
        public zzb[] zzkM;
        public zzg[] zzkN;
        public String zzkO;
        public String zzkP;
        public String zzkQ;
        public zza zzkR;
        public float zzkS;
        public boolean zzkT;
        public String[] zzkU;
        public int zzkV;

        public zzf() {
            this.zzH();
        }

        public static zzf zzf(byte[] arrby) throws zzbus {
            return zzbut.zza(new zzf(), arrby);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzf)) {
                return false;
            }
            object = (zzf)object;
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

        public zzf zzH() {
            this.zzkG = zzbuw.zzcsn;
            this.zzkH = zzbuw.zzcsn;
            this.zzkI = zzaj.zza.zzO();
            this.zzkJ = zze.zzF();
            this.zzkK = zzb.zzA();
            this.zzkL = zzb.zzA();
            this.zzkM = zzb.zzA();
            this.zzkN = zzg.zzI();
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

        public zzf zzs(zzbul zzbul2) throws IOException {
            block20 : do {
                int n = zzbul2.zzacu();
                switch (n) {
                    Object[] arrobject;
                    int n2;
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
                            this.zzkR = new zza();
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
                        arrobject = new zzg[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzkN, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = new zzg();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = new zzg();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzkN = arrobject;
                        continue block20;
                    }
                    case 50: {
                        n2 = zzbuw.zzc(zzbul2, 50);
                        n = this.zzkM == null ? 0 : this.zzkM.length;
                        arrobject = new zzb[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzkM, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = new zzb();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = new zzb();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzkM = arrobject;
                        continue block20;
                    }
                    case 42: {
                        n2 = zzbuw.zzc(zzbul2, 42);
                        n = this.zzkL == null ? 0 : this.zzkL.length;
                        arrobject = new zzb[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzkL, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = new zzb();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = new zzb();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzkL = arrobject;
                        continue block20;
                    }
                    case 34: {
                        n2 = zzbuw.zzc(zzbul2, 34);
                        n = this.zzkK == null ? 0 : this.zzkK.length;
                        arrobject = new zzb[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzkK, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = new zzb();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = new zzb();
                        zzbul2.zza((zzbut)arrobject[n2]);
                        this.zzkK = arrobject;
                        continue block20;
                    }
                    case 26: {
                        n2 = zzbuw.zzc(zzbul2, 26);
                        n = this.zzkJ == null ? 0 : this.zzkJ.length;
                        arrobject = new zze[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzkJ, 0, arrobject, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrobject.length - 1) {
                            arrobject[n2] = new zze();
                            zzbul2.zza((zzbut)arrobject[n2]);
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrobject[n2] = new zze();
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
                    n3 = n7 = (n4 = 0);
                    n = n7;
                    for (n7 = n4; n7 < this.zzkH.length; ++n7) {
                        object = this.zzkH[n7];
                        n2 = n;
                        n4 = n3;
                        if (object != null) {
                            n4 = n3 + 1;
                            n2 = n + zzbum.zzkc((String)object);
                        }
                        n = n2;
                        n3 = n4;
                    }
                    n7 = n5 + n + 1 * n3;
                }
            }
            n = n7;
            if (this.zzkI != null) {
                n = n7;
                if (this.zzkI.length > 0) {
                    for (n = 0; n < this.zzkI.length; ++n) {
                        object = this.zzkI[n];
                        n3 = n7;
                        if (object != null) {
                            n3 = n7 + zzbum.zzc(2, (zzbut)object);
                        }
                        n7 = n3;
                    }
                    n = n7;
                }
            }
            n7 = n;
            if (this.zzkJ != null) {
                n7 = n;
                if (this.zzkJ.length > 0) {
                    n7 = n;
                    for (n = 0; n < this.zzkJ.length; ++n) {
                        object = this.zzkJ[n];
                        n3 = n7;
                        if (object != null) {
                            n3 = n7 + zzbum.zzc(3, (zzbut)object);
                        }
                        n7 = n3;
                    }
                }
            }
            n = n7;
            if (this.zzkK != null) {
                n = n7;
                if (this.zzkK.length > 0) {
                    for (n = 0; n < this.zzkK.length; ++n) {
                        object = this.zzkK[n];
                        n3 = n7;
                        if (object != null) {
                            n3 = n7 + zzbum.zzc(4, (zzbut)object);
                        }
                        n7 = n3;
                    }
                    n = n7;
                }
            }
            n7 = n;
            if (this.zzkL != null) {
                n7 = n;
                if (this.zzkL.length > 0) {
                    n7 = n;
                    for (n = 0; n < this.zzkL.length; ++n) {
                        object = this.zzkL[n];
                        n3 = n7;
                        if (object != null) {
                            n3 = n7 + zzbum.zzc(5, (zzbut)object);
                        }
                        n7 = n3;
                    }
                }
            }
            n = n7;
            if (this.zzkM != null) {
                n = n7;
                if (this.zzkM.length > 0) {
                    for (n = 0; n < this.zzkM.length; ++n) {
                        object = this.zzkM[n];
                        n3 = n7;
                        if (object != null) {
                            n3 = n7 + zzbum.zzc(6, (zzbut)object);
                        }
                        n7 = n3;
                    }
                    n = n7;
                }
            }
            n7 = n;
            if (this.zzkN != null) {
                n7 = n;
                if (this.zzkN.length > 0) {
                    n7 = n;
                    for (n = 0; n < this.zzkN.length; ++n) {
                        object = this.zzkN[n];
                        n3 = n7;
                        if (object != null) {
                            n3 = n7 + zzbum.zzc(7, (zzbut)object);
                        }
                        n7 = n3;
                    }
                }
            }
            n = n7;
            if (this.zzkO != null) {
                n = n7;
                if (!this.zzkO.equals("")) {
                    n = n7 + zzbum.zzr(9, this.zzkO);
                }
            }
            n7 = n;
            if (this.zzkP != null) {
                n7 = n;
                if (!this.zzkP.equals("")) {
                    n7 = n + zzbum.zzr(10, this.zzkP);
                }
            }
            n = n7;
            if (this.zzkQ != null) {
                n = n7;
                if (!this.zzkQ.equals("0")) {
                    n = n7 + zzbum.zzr(12, this.zzkQ);
                }
            }
            n7 = n;
            if (this.version != null) {
                n7 = n;
                if (!this.version.equals("")) {
                    n7 = n + zzbum.zzr(13, this.version);
                }
            }
            n = n7;
            if (this.zzkR != null) {
                n = n7 + zzbum.zzc(14, this.zzkR);
            }
            n7 = n;
            if (Float.floatToIntBits(this.zzkS) != Float.floatToIntBits(0.0f)) {
                n7 = n + zzbum.zzd(15, this.zzkS);
            }
            n = n7;
            if (this.zzkU != null) {
                n = n7;
                if (this.zzkU.length > 0) {
                    n4 = n = (n2 = 0);
                    n3 = n;
                    for (n = n2; n < this.zzkU.length; ++n) {
                        object = this.zzkU[n];
                        n5 = n3;
                        n2 = n4;
                        if (object != null) {
                            n2 = n4 + 1;
                            n5 = n3 + zzbum.zzkc((String)object);
                        }
                        n3 = n5;
                        n4 = n2;
                    }
                    n = n7 + n3 + n4 * 2;
                }
            }
            n3 = n;
            if (this.zzkV != 0) {
                n3 = n + zzbum.zzH(17, this.zzkV);
            }
            n7 = n3;
            if (this.zzkT) {
                n7 = n3 + zzbum.zzh(18, this.zzkT);
            }
            n = n7;
            if (this.zzkG != null) {
                n = n7;
                if (this.zzkG.length > 0) {
                    n4 = n3 = 0;
                    for (n = n6; n < this.zzkG.length; ++n) {
                        object = this.zzkG[n];
                        n5 = n3;
                        n2 = n4;
                        if (object != null) {
                            n2 = n4 + 1;
                            n5 = n3 + zzbum.zzkc((String)object);
                        }
                        n3 = n5;
                        n4 = n2;
                    }
                    n = n7 + n3 + 2 * n4;
                }
            }
            return n;
        }
    }

    public static final class zzg
    extends zzbun<zzg> {
        private static volatile zzg[] zzkW;
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

        public zzg() {
            this.zzJ();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static zzg[] zzI() {
            if (zzkW != null) return zzkW;
            Object object = zzbur.zzcsf;
            synchronized (object) {
                if (zzkW != null) return zzkW;
                zzkW = new zzg[0];
                return zzkW;
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzg)) {
                return false;
            }
            object = (zzg)object;
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

        public zzg zzJ() {
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
        public zzg zzt(zzbul var1_1) throws IOException {
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

    public static final class zzh
    extends zzbun<zzh> {
        public static final zzbuo<zzaj.zza, zzh> zzlh = zzbuo.zza(11, zzh.class, 810L);
        private static final zzh[] zzli = new zzh[0];
        public int[] zzlj;
        public int[] zzlk;
        public int[] zzll;
        public int zzlm;
        public int[] zzln;
        public int zzlo;
        public int zzlp;

        public zzh() {
            this.zzK();
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzh)) {
                return false;
            }
            object = (zzh)object;
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

        public zzh zzK() {
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
        public zzh zzu(zzbul var1_1) throws IOException {
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

    public static final class zzi
    extends zzbun<zzi> {
        private static volatile zzi[] zzlq;
        public String name;
        public zzaj.zza zzlr;
        public zzd zzls;

        public zzi() {
            this.zzM();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static zzi[] zzL() {
            if (zzlq != null) return zzlq;
            Object object = zzbur.zzcsf;
            synchronized (object) {
                if (zzlq != null) return zzlq;
                zzlq = new zzi[0];
                return zzlq;
            }
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzi)) {
                return false;
            }
            object = (zzi)object;
            if (this.name == null ? object.name != null : !this.name.equals(object.name)) {
                return false;
            }
            if (this.zzlr == null ? object.zzlr != null : !this.zzlr.equals(object.zzlr)) {
                return false;
            }
            if (this.zzls == null ? object.zzls != null : !this.zzls.equals(object.zzls)) {
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
            String string = this.name;
            int n2 = 0;
            int n3 = string == null ? 0 : this.name.hashCode();
            int n4 = this.zzlr == null ? 0 : this.zzlr.hashCode();
            int n5 = this.zzls == null ? 0 : this.zzls.hashCode();
            int n6 = n2;
            if (this.zzcrX != null) {
                n6 = this.zzcrX.isEmpty() ? n2 : this.zzcrX.hashCode();
            }
            return 31 * ((((527 + n) * 31 + n3) * 31 + n4) * 31 + n5) + n6;
        }

        public zzi zzM() {
            this.name = "";
            this.zzlr = null;
            this.zzls = null;
            this.zzcrX = null;
            this.zzcsg = -1;
            return this;
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            if (this.name != null && !this.name.equals("")) {
                zzbum2.zzq(1, this.name);
            }
            if (this.zzlr != null) {
                zzbum2.zza(2, this.zzlr);
            }
            if (this.zzls != null) {
                zzbum2.zza(3, this.zzls);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzv(zzbul2);
        }

        @Override
        protected int zzv() {
            int n;
            int n2 = n = super.zzv();
            if (this.name != null) {
                n2 = n;
                if (!this.name.equals("")) {
                    n2 = n + zzbum.zzr(1, this.name);
                }
            }
            n = n2;
            if (this.zzlr != null) {
                n = n2 + zzbum.zzc(2, this.zzlr);
            }
            n2 = n;
            if (this.zzls != null) {
                n2 = n + zzbum.zzc(3, this.zzls);
            }
            return n2;
        }

        public zzi zzv(zzbul zzbul2) throws IOException {
            int n;
            while ((n = zzbul2.zzacu()) != 0) {
                if (n != 10) {
                    zzbun zzbun2;
                    if (n != 18) {
                        if (n != 26) {
                            if (super.zza(zzbul2, n)) continue;
                            return this;
                        }
                        if (this.zzls == null) {
                            this.zzls = new zzd();
                        }
                        zzbun2 = this.zzls;
                    } else {
                        if (this.zzlr == null) {
                            this.zzlr = new zzaj.zza();
                        }
                        zzbun2 = this.zzlr;
                    }
                    zzbul2.zza(zzbun2);
                    continue;
                }
                this.name = zzbul2.readString();
            }
            return this;
        }
    }

    public static final class zzj
    extends zzbun<zzj> {
        public zzi[] zzlt;
        public zzf zzlu;
        public String zzlv;

        public zzj() {
            this.zzN();
        }

        public static zzj zzg(byte[] arrby) throws zzbus {
            return zzbut.zza(new zzj(), arrby);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof zzj)) {
                return false;
            }
            object = (zzj)object;
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
            zzf zzf2 = this.zzlu;
            int n3 = 0;
            int n4 = zzf2 == null ? 0 : this.zzlu.hashCode();
            int n5 = this.zzlv == null ? 0 : this.zzlv.hashCode();
            int n6 = n3;
            if (this.zzcrX != null) {
                n6 = this.zzcrX.isEmpty() ? n3 : this.zzcrX.hashCode();
            }
            return 31 * ((((527 + n) * 31 + n2) * 31 + n4) * 31 + n5) + n6;
        }

        public zzj zzN() {
            this.zzlt = zzi.zzL();
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
                    zzi zzi2 = this.zzlt[i];
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
                        zzi zzi2 = this.zzlt[n3];
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

        public zzj zzw(zzbul zzbul2) throws IOException {
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
                        this.zzlu = new zzf();
                    }
                    zzbul2.zza(this.zzlu);
                    continue;
                }
                int n2 = zzbuw.zzc(zzbul2, 10);
                n = this.zzlt == null ? 0 : this.zzlt.length;
                zzi[] arrzzi = new zzi[n2 + n];
                n2 = n;
                if (n != 0) {
                    System.arraycopy(this.zzlt, 0, arrzzi, 0, n);
                    n2 = n;
                }
                while (n2 < arrzzi.length - 1) {
                    arrzzi[n2] = new zzi();
                    zzbul2.zza(arrzzi[n2]);
                    zzbul2.zzacu();
                    ++n2;
                }
                arrzzi[n2] = new zzi();
                zzbul2.zza(arrzzi[n2]);
                this.zzlt = arrzzi;
            }
            return this;
        }
    }

}
