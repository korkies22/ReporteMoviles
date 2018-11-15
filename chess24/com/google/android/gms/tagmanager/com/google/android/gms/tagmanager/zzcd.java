/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzci;
import com.google.android.gms.tagmanager.zzdl;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

abstract class zzcd
extends zzci {
    public zzcd(String string) {
        super(string);
    }

    @Override
    protected boolean zza(zzaj.zza object, zzaj.zza object2, Map<String, zzaj.zza> map) {
        object = zzdm.zzf((zzaj.zza)object);
        object2 = zzdm.zzf((zzaj.zza)object2);
        if (object != zzdm.zzQk() && object2 != zzdm.zzQk()) {
            return this.zza((zzdl)object, (zzdl)object2, map);
        }
        return false;
    }

    protected abstract boolean zza(zzdl var1, zzdl var2, Map<String, zzaj.zza> var3);
}
