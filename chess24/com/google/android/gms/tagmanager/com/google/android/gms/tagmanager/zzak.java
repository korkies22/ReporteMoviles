/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzcx;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzak
extends zzam {
    private static final String ID = zzag.zzdt.toString();
    private final zzcx zzbCU;

    public zzak(zzcx zzcx2) {
        super(ID, new String[0]);
        this.zzbCU = zzcx2;
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        object = this.zzbCU.zzPJ();
        if (object == null) {
            return zzdm.zzQm();
        }
        return zzdm.zzR(object);
    }
}
