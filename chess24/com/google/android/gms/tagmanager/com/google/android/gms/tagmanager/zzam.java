/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzam {
    private final Set<String> zzbEh;
    private final String zzbEi;

    public /* varargs */ zzam(String string2, String ... arrstring) {
        this.zzbEi = string2;
        this.zzbEh = new HashSet<String>(arrstring.length);
        for (String string2 : arrstring) {
            this.zzbEh.add(string2);
        }
    }

    public abstract boolean zzOw();

    public String zzPg() {
        return this.zzbEi;
    }

    public Set<String> zzPh() {
        return this.zzbEh;
    }

    public abstract zzaj.zza zzY(Map<String, zzaj.zza> var1);

    boolean zzf(Set<String> set) {
        return set.containsAll(this.zzbEh);
    }
}
