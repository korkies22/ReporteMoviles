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

public static final class zzad.zzc
extends zzbut {
    public String zzaM;
    public String zzaN;
    public String zzaO;
    public String zzaP;
    public String zzaQ;

    public zzad.zzc() {
        this.zzx();
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzaM != null) {
            zzbum2.zzq(1, this.zzaM);
        }
        if (this.zzaN != null) {
            zzbum2.zzq(2, this.zzaN);
        }
        if (this.zzaO != null) {
            zzbum2.zzq(3, this.zzaO);
        }
        if (this.zzaP != null) {
            zzbum2.zzq(4, this.zzaP);
        }
        if (this.zzaQ != null) {
            zzbum2.zzq(5, this.zzaQ);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzd(zzbul2);
    }

    public zzad.zzc zzd(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 10) {
                if (n != 18) {
                    if (n != 26) {
                        if (n != 34) {
                            if (n != 42) {
                                if (zzbuw.zzb(zzbul2, n)) continue;
                                return this;
                            }
                            this.zzaQ = zzbul2.readString();
                            continue;
                        }
                        this.zzaP = zzbul2.readString();
                        continue;
                    }
                    this.zzaO = zzbul2.readString();
                    continue;
                }
                this.zzaN = zzbul2.readString();
                continue;
            }
            this.zzaM = zzbul2.readString();
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.zzaM != null) {
            n2 = n + zzbum.zzr(1, this.zzaM);
        }
        n = n2;
        if (this.zzaN != null) {
            n = n2 + zzbum.zzr(2, this.zzaN);
        }
        n2 = n;
        if (this.zzaO != null) {
            n2 = n + zzbum.zzr(3, this.zzaO);
        }
        n = n2;
        if (this.zzaP != null) {
            n = n2 + zzbum.zzr(4, this.zzaP);
        }
        n2 = n;
        if (this.zzaQ != null) {
            n2 = n + zzbum.zzr(5, this.zzaQ);
        }
        return n2;
    }

    public zzad.zzc zzx() {
        this.zzaM = null;
        this.zzaN = null;
        this.zzaO = null;
        this.zzaP = null;
        this.zzaQ = null;
        this.zzcsg = -1;
        return this;
    }
}
