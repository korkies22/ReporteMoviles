/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public interface zzad {

    public static final class zza
    extends zzbut {
        public zzb zzaJ;
        public zzc zzaK;

        public zza() {
            this.zzu();
        }

        public static zza zzc(byte[] arrby) throws zzbus {
            return zzbut.zza(new zza(), arrby);
        }

        public zza zza(zzbul zzbul2) throws IOException {
            int n;
            while ((n = zzbul2.zzacu()) != 0) {
                zzbut zzbut2;
                if (n != 10) {
                    if (n != 18) {
                        if (zzbuw.zzb(zzbul2, n)) continue;
                        return this;
                    }
                    if (this.zzaK == null) {
                        this.zzaK = new zzc();
                    }
                    zzbut2 = this.zzaK;
                } else {
                    if (this.zzaJ == null) {
                        this.zzaJ = new zzb();
                    }
                    zzbut2 = this.zzaJ;
                }
                zzbul2.zza(zzbut2);
            }
            return this;
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            if (this.zzaJ != null) {
                zzbum2.zza(1, this.zzaJ);
            }
            if (this.zzaK != null) {
                zzbum2.zza(2, this.zzaK);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zza(zzbul2);
        }

        public zza zzu() {
            this.zzaJ = null;
            this.zzaK = null;
            this.zzcsg = -1;
            return this;
        }

        @Override
        protected int zzv() {
            int n;
            int n2 = n = super.zzv();
            if (this.zzaJ != null) {
                n2 = n + zzbum.zzc(1, this.zzaJ);
            }
            n = n2;
            if (this.zzaK != null) {
                n = n2 + zzbum.zzc(2, this.zzaK);
            }
            return n;
        }
    }

    public static final class zzb
    extends zzbut {
        public Integer zzaL;

        public zzb() {
            this.zzw();
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            if (this.zzaL != null) {
                zzbum2.zzF(27, this.zzaL);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzc(zzbul2);
        }

        public zzb zzc(zzbul zzbul2) throws IOException {
            int n;
            block3 : while ((n = zzbul2.zzacu()) != 0) {
                if (n != 216) {
                    if (zzbuw.zzb(zzbul2, n)) continue;
                    return this;
                }
                n = zzbul2.zzacy();
                switch (n) {
                    default: {
                        continue block3;
                    }
                    case 0: 
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                }
                this.zzaL = n;
            }
            return this;
        }

        @Override
        protected int zzv() {
            int n;
            int n2 = n = super.zzv();
            if (this.zzaL != null) {
                n2 = n + zzbum.zzH(27, this.zzaL);
            }
            return n2;
        }

        public zzb zzw() {
            this.zzcsg = -1;
            return this;
        }
    }

    public static final class zzc
    extends zzbut {
        public String zzaM;
        public String zzaN;
        public String zzaO;
        public String zzaP;
        public String zzaQ;

        public zzc() {
            this.zzx();
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            if (this.zzaM != null) {
                zzbum2.zzq(1, this.zzaM);
            }
            if (this.zzaN != null) {
                zzbum2.zzq(2, this.zzaN);
            }
            if (this.zzaO != null) {
                zzbum2.zzq(3, this.zzaO);
            }
            if (this.zzaP != null) {
                zzbum2.zzq(4, this.zzaP);
            }
            if (this.zzaQ != null) {
                zzbum2.zzq(5, this.zzaQ);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzd(zzbul2);
        }

        public zzc zzd(zzbul zzbul2) throws IOException {
            int n;
            while ((n = zzbul2.zzacu()) != 0) {
                if (n != 10) {
                    if (n != 18) {
                        if (n != 26) {
                            if (n != 34) {
                                if (n != 42) {
                                    if (zzbuw.zzb(zzbul2, n)) continue;
                                    return this;
                                }
                                this.zzaQ = zzbul2.readString();
                                continue;
                            }
                            this.zzaP = zzbul2.readString();
                            continue;
                        }
                        this.zzaO = zzbul2.readString();
                        continue;
                    }
                    this.zzaN = zzbul2.readString();
                    continue;
                }
                this.zzaM = zzbul2.readString();
            }
            return this;
        }

        @Override
        protected int zzv() {
            int n;
            int n2 = n = super.zzv();
            if (this.zzaM != null) {
                n2 = n + zzbum.zzr(1, this.zzaM);
            }
            n = n2;
            if (this.zzaN != null) {
                n = n2 + zzbum.zzr(2, this.zzaN);
            }
            n2 = n;
            if (this.zzaO != null) {
                n2 = n + zzbum.zzr(3, this.zzaO);
            }
            n = n2;
            if (this.zzaP != null) {
                n = n2 + zzbum.zzr(4, this.zzaP);
            }
            n2 = n;
            if (this.zzaQ != null) {
                n2 = n + zzbum.zzr(5, this.zzaQ);
            }
            return n2;
        }

        public zzc zzx() {
            this.zzaM = null;
            this.zzaN = null;
            this.zzaO = null;
            this.zzaP = null;
            this.zzaQ = null;
            this.zzcsg = -1;
            return this;
        }
    }

}
