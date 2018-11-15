/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzai;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbup;
import com.google.android.gms.internal.zzbur;
import com.google.android.gms.internal.zzbut;
import java.io.IOException;

public static final class zzai.zzi
extends zzbun<zzai.zzi> {
    private static volatile zzai.zzi[] zzlq;
    public String name;
    public zzaj.zza zzlr;
    public zzai.zzd zzls;

    public zzai.zzi() {
        this.zzM();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static zzai.zzi[] zzL() {
        if (zzlq != null) return zzlq;
        Object object = zzbur.zzcsf;
        synchronized (object) {
            if (zzlq != null) return zzlq;
            zzlq = new zzai.zzi[0];
            return zzlq;
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzai.zzi)) {
            return false;
        }
        object = (zzai.zzi)object;
        if (this.name == null ? object.name != null : !this.name.equals(object.name)) {
            return false;
        }
        if (this.zzlr == null ? object.zzlr != null : !this.zzlr.equals(object.zzlr)) {
            return false;
        }
        if (this.zzls == null ? object.zzls != null : !this.zzls.equals(object.zzls)) {
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
        String string = this.name;
        int n2 = 0;
        int n3 = string == null ? 0 : this.name.hashCode();
        int n4 = this.zzlr == null ? 0 : this.zzlr.hashCode();
        int n5 = this.zzls == null ? 0 : this.zzls.hashCode();
        int n6 = n2;
        if (this.zzcrX != null) {
            n6 = this.zzcrX.isEmpty() ? n2 : this.zzcrX.hashCode();
        }
        return 31 * ((((527 + n) * 31 + n3) * 31 + n4) * 31 + n5) + n6;
    }

    public zzai.zzi zzM() {
        this.name = "";
        this.zzlr = null;
        this.zzls = null;
        this.zzcrX = null;
        this.zzcsg = -1;
        return this;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.name != null && !this.name.equals("")) {
            zzbum2.zzq(1, this.name);
        }
        if (this.zzlr != null) {
            zzbum2.zza(2, this.zzlr);
        }
        if (this.zzls != null) {
            zzbum2.zza(3, this.zzls);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzv(zzbul2);
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.name != null) {
            n2 = n;
            if (!this.name.equals("")) {
                n2 = n + zzbum.zzr(1, this.name);
            }
        }
        n = n2;
        if (this.zzlr != null) {
            n = n2 + zzbum.zzc(2, this.zzlr);
        }
        n2 = n;
        if (this.zzls != null) {
            n2 = n + zzbum.zzc(3, this.zzls);
        }
        return n2;
    }

    public zzai.zzi zzv(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 10) {
                zzbun zzbun2;
                if (n != 18) {
                    if (n != 26) {
                        if (super.zza(zzbul2, n)) continue;
                        return this;
                    }
                    if (this.zzls == null) {
                        this.zzls = new zzai.zzd();
                    }
                    zzbun2 = this.zzls;
                } else {
                    if (this.zzlr == null) {
                        this.zzlr = new zzaj.zza();
                    }
                    zzbun2 = this.zzlr;
                }
                zzbul2.zza(zzbun2);
                continue;
            }
            this.name = zzbul2.readString();
        }
        return this;
    }
}
