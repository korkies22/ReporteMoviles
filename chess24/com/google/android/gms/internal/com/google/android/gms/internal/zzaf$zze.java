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

public static final class zzaf.zze
extends zzbun<zzaf.zze> {
    public Long zzcn = null;
    public String zzcx = null;
    public byte[] zzcy = null;

    public zzaf.zze() {
        this.zzcsg = -1;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzcn != null) {
            zzbum2.zzb(1, this.zzcn);
        }
        if (this.zzcx != null) {
            zzbum2.zzq(3, this.zzcx);
        }
        if (this.zzcy != null) {
            zzbum2.zzb(4, this.zzcy);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzl(zzbul2);
    }

    public zzaf.zze zzl(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 8) {
                if (n != 26) {
                    if (n != 34) {
                        if (super.zza(zzbul2, n)) continue;
                        return this;
                    }
                    this.zzcy = zzbul2.readBytes();
                    continue;
                }
                this.zzcx = zzbul2.readString();
                continue;
            }
            this.zzcn = zzbul2.zzacx();
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.zzcn != null) {
            n2 = n + zzbum.zzf(1, this.zzcn);
        }
        n = n2;
        if (this.zzcx != null) {
            n = n2 + zzbum.zzr(3, this.zzcx);
        }
        n2 = n;
        if (this.zzcy != null) {
            n2 = n + zzbum.zzc(4, this.zzcy);
        }
        return n2;
    }
}
