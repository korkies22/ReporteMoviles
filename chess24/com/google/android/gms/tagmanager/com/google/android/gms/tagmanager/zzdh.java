/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzci;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

abstract class zzdh
extends zzci {
    public zzdh(String string) {
        super(string);
    }

    @Override
    protected boolean zza(zzaj.zza object, zzaj.zza object2, Map<String, zzaj.zza> map) {
        object = zzdm.zze((zzaj.zza)object);
        object2 = zzdm.zze((zzaj.zza)object2);
        if (object != zzdm.zzQl() && object2 != zzdm.zzQl()) {
            return this.zza((String)object, (String)object2, map);
        }
        return false;
    }

    protected abstract boolean zza(String var1, String var2, Map<String, zzaj.zza> var3);
}
