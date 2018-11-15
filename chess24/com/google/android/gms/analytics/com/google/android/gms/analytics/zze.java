/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.zzf;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzh;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zze {
    private final zzg zzaaT;
    private boolean zzaaU;
    private long zzaaV;
    private long zzaaW;
    private long zzaaX;
    private long zzaaY;
    private long zzaaZ;
    private boolean zzaba;
    private final Map<Class<? extends zzf>, zzf> zzabb;
    private final List<zzi> zzabc;
    private final com.google.android.gms.common.util.zze zzuI;

    zze(zze object) {
        this.zzaaT = object.zzaaT;
        this.zzuI = object.zzuI;
        this.zzaaV = object.zzaaV;
        this.zzaaW = object.zzaaW;
        this.zzaaX = object.zzaaX;
        this.zzaaY = object.zzaaY;
        this.zzaaZ = object.zzaaZ;
        this.zzabc = new ArrayList<zzi>(object.zzabc);
        this.zzabb = new HashMap<Class<? extends zzf>, zzf>(object.zzabb.size());
        for (Map.Entry<Class<? extends zzf>, zzf> entry : object.zzabb.entrySet()) {
            zzf t = zze.zzc(entry.getKey());
            entry.getValue().zzb(t);
            this.zzabb.put(entry.getKey(), t);
        }
    }

    zze(zzg zzg2, com.google.android.gms.common.util.zze zze2) {
        zzac.zzw(zzg2);
        zzac.zzw(zze2);
        this.zzaaT = zzg2;
        this.zzuI = zze2;
        this.zzaaY = 1800000L;
        this.zzaaZ = 3024000000L;
        this.zzabb = new HashMap<Class<? extends zzf>, zzf>();
        this.zzabc = new ArrayList<zzi>();
    }

    private static <T extends zzf> T zzc(Class<T> object) {
        try {
            object = (zzf)object.newInstance();
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException("dataType default constructor is not accessible", illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new IllegalArgumentException("dataType doesn't have default constructor", instantiationException);
        }
        return (T)object;
    }

    public <T extends zzf> T zza(Class<T> class_) {
        return (T)this.zzabb.get(class_);
    }

    public void zza(zzf zzf2) {
        zzac.zzw(zzf2);
        Class<?> class_ = zzf2.getClass();
        if (class_.getSuperclass() != zzf.class) {
            throw new IllegalArgumentException();
        }
        zzf2.zzb(this.zzb(class_));
    }

    public <T extends zzf> T zzb(Class<T> class_) {
        zzf zzf2;
        zzf zzf3 = zzf2 = this.zzabb.get(class_);
        if (zzf2 == null) {
            zzf3 = zze.zzc(class_);
            this.zzabb.put(class_, zzf3);
        }
        return (T)zzf3;
    }

    public zze zzmb() {
        return new zze(this);
    }

    public Collection<zzf> zzmc() {
        return this.zzabb.values();
    }

    public List<zzi> zzmd() {
        return this.zzabc;
    }

    public long zzme() {
        return this.zzaaV;
    }

    public void zzmf() {
        this.zzmj().zze(this);
    }

    public boolean zzmg() {
        return this.zzaaU;
    }

    /*
     * Enabled aggressive block sorting
     */
    void zzmh() {
        this.zzaaX = this.zzuI.elapsedRealtime();
        long l = this.zzaaW != 0L ? this.zzaaW : this.zzuI.currentTimeMillis();
        this.zzaaV = l;
        this.zzaaU = true;
    }

    zzg zzmi() {
        return this.zzaaT;
    }

    zzh zzmj() {
        return this.zzaaT.zzmj();
    }

    boolean zzmk() {
        return this.zzaba;
    }

    void zzml() {
        this.zzaba = true;
    }

    public void zzp(long l) {
        this.zzaaW = l;
    }
}
