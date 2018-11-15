/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

abstract class zzdk
extends zzam {
    public /* varargs */ zzdk(String string, String ... arrstring) {
        super(string, arrstring);
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        this.zzaa(map);
        return zzdm.zzQm();
    }

    public abstract void zzaa(Map<String, zzaj.zza> var1);
}
