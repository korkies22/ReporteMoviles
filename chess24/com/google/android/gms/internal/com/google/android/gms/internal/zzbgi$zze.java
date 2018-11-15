/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzbgi;
import java.util.Collections;
import java.util.List;

public static class zzbgi.zze {
    private final List<zzbgi.zza> zzbKC;
    private final List<zzbgi.zza> zzbKD;
    private final List<zzbgi.zza> zzbKE;
    private final List<zzbgi.zza> zzbKF;
    private final List<zzbgi.zza> zzbLk;
    private final List<zzbgi.zza> zzbLl;
    private final List<String> zzbLm;
    private final List<String> zzbLn;
    private final List<String> zzbLo;
    private final List<String> zzbLp;

    private zzbgi.zze(List<zzbgi.zza> list, List<zzbgi.zza> list2, List<zzbgi.zza> list3, List<zzbgi.zza> list4, List<zzbgi.zza> list5, List<zzbgi.zza> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
        this.zzbKC = Collections.unmodifiableList(list);
        this.zzbKD = Collections.unmodifiableList(list2);
        this.zzbKE = Collections.unmodifiableList(list3);
        this.zzbKF = Collections.unmodifiableList(list4);
        this.zzbLk = Collections.unmodifiableList(list5);
        this.zzbLl = Collections.unmodifiableList(list6);
        this.zzbLm = Collections.unmodifiableList(list7);
        this.zzbLn = Collections.unmodifiableList(list8);
        this.zzbLo = Collections.unmodifiableList(list9);
        this.zzbLp = Collections.unmodifiableList(list10);
    }

    public static zzbgi.zzf zzRZ() {
        return new zzbgi.zzf(null);
    }

    public String toString() {
        String string = String.valueOf(this.zzRv());
        String string2 = String.valueOf(this.zzRw());
        String string3 = String.valueOf(this.zzRx());
        String string4 = String.valueOf(this.zzRy());
        String string5 = String.valueOf(this.zzSa());
        String string6 = String.valueOf(this.zzSb());
        StringBuilder stringBuilder = new StringBuilder(102 + String.valueOf(string).length() + String.valueOf(string2).length() + String.valueOf(string3).length() + String.valueOf(string4).length() + String.valueOf(string5).length() + String.valueOf(string6).length());
        stringBuilder.append("Positive predicates: ");
        stringBuilder.append(string);
        stringBuilder.append("  Negative predicates: ");
        stringBuilder.append(string2);
        stringBuilder.append("  Add tags: ");
        stringBuilder.append(string3);
        stringBuilder.append("  Remove tags: ");
        stringBuilder.append(string4);
        stringBuilder.append("  Add macros: ");
        stringBuilder.append(string5);
        stringBuilder.append("  Remove macros: ");
        stringBuilder.append(string6);
        return stringBuilder.toString();
    }

    public List<zzbgi.zza> zzRv() {
        return this.zzbKC;
    }

    public List<zzbgi.zza> zzRw() {
        return this.zzbKD;
    }

    public List<zzbgi.zza> zzRx() {
        return this.zzbKE;
    }

    public List<zzbgi.zza> zzRy() {
        return this.zzbKF;
    }

    public List<zzbgi.zza> zzSa() {
        return this.zzbLk;
    }

    public List<zzbgi.zza> zzSb() {
        return this.zzbLl;
    }
}
