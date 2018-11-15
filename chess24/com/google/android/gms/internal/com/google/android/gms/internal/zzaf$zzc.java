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

public static final class zzaf.zzc
extends zzbun<zzaf.zzc> {
    public byte[] zzcs = null;
    public byte[] zzct = null;

    public zzaf.zzc() {
        this.zzcsg = -1;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzcs != null) {
            zzbum2.zzb(1, this.zzcs);
        }
        if (this.zzct != null) {
            zzbum2.zzb(2, this.zzct);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzj(zzbul2);
    }

    public zzaf.zzc zzj(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 10) {
                if (n != 18) {
                    if (super.zza(zzbul2, n)) continue;
                    return this;
                }
                this.zzct = zzbul2.readBytes();
                continue;
            }
            this.zzcs = zzbul2.readBytes();
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.zzcs != null) {
            n2 = n + zzbum.zzc(1, this.zzcs);
        }
        n = n2;
        if (this.zzct != null) {
            n = n2 + zzbum.zzc(2, this.zzct);
        }
        return n;
    }
}
