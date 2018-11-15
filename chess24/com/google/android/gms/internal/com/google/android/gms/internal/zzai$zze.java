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

public static final class zzai.zze
extends zzbun<zzai.zze> {
    private static volatile zzai.zze[] zzkF;
    public int key;
    public int value;

    public zzai.zze() {
        this.zzG();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzai.zze[] zzF() {
        if (zzkF != null) return zzkF;
        Object object = zzbur.zzcsf;
        synchronized (object) {
            if (zzkF != null) return zzkF;
            zzkF = new zzai.zze[0];
            return zzkF;
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zze)) {
            return false;
        }
        object = (zzai.zze)object;
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

    public zzai.zze zzG() {
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

    public zzai.zze zzr(zzbul zzbul2) throws IOException {
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
