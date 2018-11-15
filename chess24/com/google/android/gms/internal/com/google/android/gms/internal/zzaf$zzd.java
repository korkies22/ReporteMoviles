/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbus;
import com.google.android.gms.internal.zzbut;
import java.io.IOException;

public static final class zzaf.zzd
extends zzbun<zzaf.zzd> {
    public byte[] data = null;
    public byte[] zzcu = null;
    public byte[] zzcv = null;
    public byte[] zzcw = null;

    public zzaf.zzd() {
        this.zzcsg = -1;
    }

    public static zzaf.zzd zze(byte[] arrby) throws zzbus {
        return zzbut.zza(new zzaf.zzd(), arrby);
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.data != null) {
            zzbum2.zzb(1, this.data);
        }
        if (this.zzcu != null) {
            zzbum2.zzb(2, this.zzcu);
        }
        if (this.zzcv != null) {
            zzbum2.zzb(3, this.zzcv);
        }
        if (this.zzcw != null) {
            zzbum2.zzb(4, this.zzcw);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzk(zzbul2);
    }

    public zzaf.zzd zzk(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 10) {
                if (n != 18) {
                    if (n != 26) {
                        if (n != 34) {
                            if (super.zza(zzbul2, n)) continue;
                            return this;
                        }
                        this.zzcw = zzbul2.readBytes();
                        continue;
                    }
                    this.zzcv = zzbul2.readBytes();
                    continue;
                }
                this.zzcu = zzbul2.readBytes();
                continue;
            }
            this.data = zzbul2.readBytes();
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.data != null) {
            n2 = n + zzbum.zzc(1, this.data);
        }
        n = n2;
        if (this.zzcu != null) {
            n = n2 + zzbum.zzc(2, this.zzcu);
        }
        n2 = n;
        if (this.zzcv != null) {
            n2 = n + zzbum.zzc(3, this.zzcv);
        }
        n = n2;
        if (this.zzcw != null) {
            n = n2 + zzbum.zzc(4, this.zzcw);
        }
        return n;
    }
}
