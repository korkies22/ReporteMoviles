/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import java.io.IOException;

public interface zzbgg {

    public static final class zza
    extends zzbun<zza> {
        public long zzbLh;
        public zzai.zzj zzbLi;
        public zzai.zzf zzlu;

        public zza() {
            this.zzRT();
        }

        public static zza zzP(byte[] arrby) throws zzbus {
            return zzbut.zza(new zza(), arrby);
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
            if (this.zzbLh != object.zzbLh) {
                return false;
            }
            if (this.zzlu == null ? object.zzlu != null : !this.zzlu.equals(object.zzlu)) {
                return false;
            }
            if (this.zzbLi == null ? object.zzbLi != null : !this.zzbLi.equals(object.zzbLi)) {
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
            int n2 = (int)(this.zzbLh ^ this.zzbLh >>> 32);
            zzai.zzf zzf2 = this.zzlu;
            int n3 = 0;
            int n4 = zzf2 == null ? 0 : this.zzlu.hashCode();
            int n5 = this.zzbLi == null ? 0 : this.zzbLi.hashCode();
            int n6 = n3;
            if (this.zzcrX != null) {
                n6 = this.zzcrX.isEmpty() ? n3 : this.zzcrX.hashCode();
            }
            return 31 * ((((527 + n) * 31 + n2) * 31 + n4) * 31 + n5) + n6;
        }

        public zza zzRT() {
            this.zzbLh = 0L;
            this.zzlu = null;
            this.zzbLi = null;
            this.zzcrX = null;
            this.zzcsg = -1;
            return this;
        }

        public zza zzW(zzbul zzbul2) throws IOException {
            int n;
            while ((n = zzbul2.zzacu()) != 0) {
                if (n != 8) {
                    zzbun zzbun2;
                    if (n != 18) {
                        if (n != 26) {
                            if (super.zza(zzbul2, n)) continue;
                            return this;
                        }
                        if (this.zzbLi == null) {
                            this.zzbLi = new zzai.zzj();
                        }
                        zzbun2 = this.zzbLi;
                    } else {
                        if (this.zzlu == null) {
                            this.zzlu = new zzai.zzf();
                        }
                        zzbun2 = this.zzlu;
                    }
                    zzbul2.zza(zzbun2);
                    continue;
                }
                this.zzbLh = zzbul2.zzacx();
            }
            return this;
        }

        @Override
        public void zza(zzbum zzbum2) throws IOException {
            zzbum2.zzb(1, this.zzbLh);
            if (this.zzlu != null) {
                zzbum2.zza(2, this.zzlu);
            }
            if (this.zzbLi != null) {
                zzbum2.zza(3, this.zzbLi);
            }
            super.zza(zzbum2);
        }

        @Override
        public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
            return this.zzW(zzbul2);
        }

        @Override
        protected int zzv() {
            int n;
            int n2 = n = super.zzv() + zzbum.zzf(1, this.zzbLh);
            if (this.zzlu != null) {
                n2 = n + zzbum.zzc(2, this.zzlu);
            }
            n = n2;
            if (this.zzbLi != null) {
                n = n2 + zzbum.zzc(3, this.zzbLi);
            }
            return n;
        }
    }

}
