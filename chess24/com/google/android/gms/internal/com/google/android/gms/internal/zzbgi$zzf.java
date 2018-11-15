/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbgi;
import java.util.ArrayList;
import java.util.List;

public static class zzbgi.zzf {
    private final List<zzbgi.zza> zzbKC = new ArrayList<zzbgi.zza>();
    private final List<zzbgi.zza> zzbKD = new ArrayList<zzbgi.zza>();
    private final List<zzbgi.zza> zzbKE = new ArrayList<zzbgi.zza>();
    private final List<zzbgi.zza> zzbKF = new ArrayList<zzbgi.zza>();
    private final List<zzbgi.zza> zzbLk = new ArrayList<zzbgi.zza>();
    private final List<zzbgi.zza> zzbLl = new ArrayList<zzbgi.zza>();
    private final List<String> zzbLm = new ArrayList<String>();
    private final List<String> zzbLn = new ArrayList<String>();
    private final List<String> zzbLo = new ArrayList<String>();
    private final List<String> zzbLp = new ArrayList<String>();

    private zzbgi.zzf() {
    }

    public zzbgi.zze zzSc() {
        return new zzbgi.zze(this.zzbKC, this.zzbKD, this.zzbKE, this.zzbKF, this.zzbLk, this.zzbLl, this.zzbLm, this.zzbLn, this.zzbLo, this.zzbLp, null);
    }

    public zzbgi.zzf zzd(zzbgi.zza zza2) {
        this.zzbKC.add(zza2);
        return this;
    }

    public zzbgi.zzf zze(zzbgi.zza zza2) {
        this.zzbKD.add(zza2);
        return this;
    }

    public zzbgi.zzf zzf(zzbgi.zza zza2) {
        this.zzbKE.add(zza2);
        return this;
    }

    public zzbgi.zzf zzg(zzbgi.zza zza2) {
        this.zzbKF.add(zza2);
        return this;
    }

    public zzbgi.zzf zzh(zzbgi.zza zza2) {
        this.zzbLk.add(zza2);
        return this;
    }

    public zzbgi.zzf zzi(zzbgi.zza zza2) {
        this.zzbLl.add(zza2);
        return this;
    }

    public zzbgi.zzf zzil(String string) {
        this.zzbLo.add(string);
        return this;
    }

    public zzbgi.zzf zzim(String string) {
        this.zzbLp.add(string);
        return this;
    }

    public zzbgi.zzf zzin(String string) {
        this.zzbLm.add(string);
        return this;
    }

    public zzbgi.zzf zzio(String string) {
        this.zzbLn.add(string);
        return this;
    }
}
