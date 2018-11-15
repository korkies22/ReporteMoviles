/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzad.zza
extends zzbut {
    public zzad.zzb zzaJ;
    public zzad.zzc zzaK;

    public zzad.zza() {
        this.zzu();
    }

    public static zzad.zza zzc(byte[] arrby) throws zzbus {
        return zzbut.zza(new zzad.zza(), arrby);
    }

    public zzad.zza zza(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            zzbut zzbut2;
            if (n != 10) {
                if (n != 18) {
                    if (zzbuw.zzb(zzbul2, n)) continue;
                    return this;
                }
                if (this.zzaK == null) {
                    this.zzaK = new zzad.zzc();
                }
                zzbut2 = this.zzaK;
            } else {
                if (this.zzaJ == null) {
                    this.zzaJ = new zzad.zzb();
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

    public zzad.zza zzu() {
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
