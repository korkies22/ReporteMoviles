/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class zzg<T extends zzg> {
    private final zzh zzabd;
    protected final zze zzabe;
    private final List<Object> zzabf;

    protected zzg(zzh object, com.google.android.gms.common.util.zze zze2) {
        zzac.zzw(object);
        this.zzabd = object;
        this.zzabf = new ArrayList<Object>();
        object = new zze(this, zze2);
        object.zzml();
        this.zzabe = object;
    }

    protected void zza(zze zze2) {
    }

    protected void zzd(zze object) {
        object = this.zzabf.iterator();
        while (object.hasNext()) {
            object.next();
        }
    }

    public zze zzlN() {
        zze zze2 = this.zzabe.zzmb();
        this.zzd(zze2);
        return zze2;
    }

    protected zzh zzmj() {
        return this.zzabd;
    }

    public zze zzmm() {
        return this.zzabe;
    }

    public List<zzi> zzmn() {
        return this.zzabe.zzmd();
    }
}
