/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzad.zzb
extends zzbut {
    public Integer zzaL;

    public zzad.zzb() {
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

    public zzad.zzb zzc(zzbul zzbul2) throws IOException {
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

    public zzad.zzb zzw() {
        this.zzcsg = -1;
        return this;
    }
}
