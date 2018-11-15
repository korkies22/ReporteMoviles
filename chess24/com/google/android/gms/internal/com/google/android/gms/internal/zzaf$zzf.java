/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzaf.zzf
extends zzbun<zzaf.zzf> {
    public Integer zzcA = null;
    public Integer zzcB = null;
    public byte[] zzcu = null;
    public byte[][] zzcz = zzbuw.zzcso;

    public zzaf.zzf() {
        this.zzcsg = -1;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzcz != null && this.zzcz.length > 0) {
            for (int i = 0; i < this.zzcz.length; ++i) {
                byte[] arrby = this.zzcz[i];
                if (arrby == null) continue;
                zzbum2.zzb(1, arrby);
            }
        }
        if (this.zzcu != null) {
            zzbum2.zzb(2, this.zzcu);
        }
        if (this.zzcA != null) {
            zzbum2.zzF(3, this.zzcA);
        }
        if (this.zzcB != null) {
            zzbum2.zzF(4, this.zzcB);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzm(zzbul2);
    }

    public zzaf.zzf zzm(zzbul zzbul2) throws IOException {
        int n;
        block6 : while ((n = zzbul2.zzacu()) != 0) {
            if (n != 10) {
                if (n != 18) {
                    if (n != 24) {
                        if (n != 32) {
                            if (super.zza(zzbul2, n)) continue;
                            return this;
                        }
                        n = zzbul2.zzacy();
                        switch (n) {
                            default: {
                                continue block6;
                            }
                            case 0: 
                            case 1: 
                        }
                        this.zzcB = n;
                        continue;
                    }
                    n = zzbul2.zzacy();
                    switch (n) {
                        default: {
                            continue block6;
                        }
                        case 0: 
                        case 1: 
                    }
                    this.zzcA = n;
                    continue;
                }
                this.zzcu = zzbul2.readBytes();
                continue;
            }
            int n2 = zzbuw.zzc(zzbul2, 10);
            n = this.zzcz == null ? 0 : this.zzcz.length;
            byte[][] arrarrby = new byte[n2 + n][];
            n2 = n;
            if (n != 0) {
                System.arraycopy(this.zzcz, 0, arrarrby, 0, n);
                n2 = n;
            }
            while (n2 < arrarrby.length - 1) {
                arrarrby[n2] = zzbul2.readBytes();
                zzbul2.zzacu();
                ++n2;
            }
            arrarrby[n2] = zzbul2.readBytes();
            this.zzcz = arrarrby;
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2;
        int n3 = n = super.zzv();
        if (this.zzcz != null) {
            n3 = n;
            if (this.zzcz.length > 0) {
                int n4 = n2 = 0;
                for (n3 = 0; n3 < this.zzcz.length; ++n3) {
                    byte[] arrby = this.zzcz[n3];
                    int n5 = n2;
                    int n6 = n4;
                    if (arrby != null) {
                        n6 = n4 + 1;
                        n5 = n2 + zzbum.zzag(arrby);
                    }
                    n2 = n5;
                    n4 = n6;
                }
                n3 = n + n2 + 1 * n4;
            }
        }
        n2 = n3;
        if (this.zzcu != null) {
            n2 = n3 + zzbum.zzc(2, this.zzcu);
        }
        n3 = n2;
        if (this.zzcA != null) {
            n3 = n2 + zzbum.zzH(3, this.zzcA);
        }
        n2 = n3;
        if (this.zzcB != null) {
            n2 = n3 + zzbum.zzH(4, this.zzcB);
        }
        return n2;
    }
}
