/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzabs;
import com.google.android.gms.internal.zzsq;

public static final class zzsq.zza<V> {
    private final V zzaeZ;
    private final zzabs<V> zzafa;

    private zzsq.zza(zzabs<V> zzabs2, V v) {
        zzac.zzw(zzabs2);
        this.zzafa = zzabs2;
        this.zzaeZ = v;
    }

    static zzsq.zza<Float> zza(String string, float f) {
        return zzsq.zza.zza(string, f, f);
    }

    static zzsq.zza<Float> zza(String string, float f, float f2) {
        return new zzsq.zza<Float>(zzabs.zza(string, Float.valueOf(f2)), Float.valueOf(f));
    }

    static zzsq.zza<Integer> zza(String string, int n, int n2) {
        return new zzsq.zza<Integer>(zzabs.zza(string, n2), n);
    }

    static zzsq.zza<Long> zza(String string, long l, long l2) {
        return new zzsq.zza<Long>(zzabs.zza(string, l2), l);
    }

    static zzsq.zza<Boolean> zza(String string, boolean bl, boolean bl2) {
        return new zzsq.zza<Boolean>(zzabs.zzj(string, bl2), bl);
    }

    static zzsq.zza<Long> zzb(String string, long l) {
        return zzsq.zza.zza(string, l, l);
    }

    static zzsq.zza<String> zzd(String string, String string2, String string3) {
        return new zzsq.zza<String>(zzabs.zzA(string, string3), string2);
    }

    static zzsq.zza<Integer> zze(String string, int n) {
        return zzsq.zza.zza(string, n, n);
    }

    static zzsq.zza<Boolean> zzf(String string, boolean bl) {
        return zzsq.zza.zza(string, bl, bl);
    }

    static zzsq.zza<String> zzq(String string, String string2) {
        return zzsq.zza.zzd(string, string2, string2);
    }

    public V get() {
        return this.zzaeZ;
    }
}
