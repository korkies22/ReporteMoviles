/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbut;
import java.io.IOException;

public static final class zzaf.zza.zzb
extends zzbun<zzaf.zza.zzb> {
    public Long zzbO = null;
    public Long zzbP = null;
    public Long zzcm = null;

    public zzaf.zza.zzb() {
        this.zzcsg = -1;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzbO != null) {
            zzbum2.zzb(1, this.zzbO);
        }
        if (this.zzbP != null) {
            zzbum2.zzb(2, this.zzbP);
        }
        if (this.zzcm != null) {
            zzbum2.zzb(3, this.zzcm);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzh(zzbul2);
    }

    public zzaf.zza.zzb zzh(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 8) {
                if (n != 16) {
                    if (n != 24) {
                        if (super.zza(zzbul2, n)) continue;
                        return this;
                    }
                    this.zzcm = zzbul2.zzacx();
                    continue;
                }
                this.zzbP = zzbul2.zzacx();
                continue;
            }
            this.zzbO = zzbul2.zzacx();
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.zzbO != null) {
            n2 = n + zzbum.zzf(1, this.zzbO);
        }
        n = n2;
        if (this.zzbP != null) {
            n = n2 + zzbum.zzf(2, this.zzbP);
        }
        n2 = n;
        if (this.zzcm != null) {
            n2 = n + zzbum.zzf(3, this.zzcm);
        }
        return n2;
    }
}
