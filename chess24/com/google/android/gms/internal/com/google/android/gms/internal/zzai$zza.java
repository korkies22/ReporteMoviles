/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbut;
import java.io.IOException;

public static final class zzai.zza
extends zzbun<zzai.zza> {
    public int level;
    public int zzkq;
    public int zzkr;

    public zzai.zza() {
        this.zzz();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zza)) {
            return false;
        }
        object = (zzai.zza)object;
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

    public zzai.zza zzn(zzbul zzbul2) throws IOException {
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

    public zzai.zza zzz() {
        this.level = 1;
        this.zzkq = 0;
        this.zzkr = 0;
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }
}
