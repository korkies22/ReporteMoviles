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
import java.io.IOException;

public static final class zzai.zzc
extends zzbun<zzai.zzc> {
    private static volatile zzai.zzc[] zzkx;
    public String zzaA;
    public boolean zzkA;
    public long zzkB;
    public long zzky;
    public long zzkz;

    public zzai.zzc() {
        this.zzD();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzai.zzc[] zzC() {
        if (zzkx != null) return zzkx;
        Object object = zzbur.zzcsf;
        synchronized (object) {
            if (zzkx != null) return zzkx;
            zzkx = new zzai.zzc[0];
            return zzkx;
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzc)) {
            return false;
        }
        object = (zzai.zzc)object;
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

    public zzai.zzc zzD() {
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

    public zzai.zzc zzp(zzbul zzbul2) throws IOException {
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
