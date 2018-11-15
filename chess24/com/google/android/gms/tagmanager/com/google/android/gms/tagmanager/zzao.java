/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzcd;
import com.google.android.gms.tagmanager.zzdl;
import java.util.Map;

class zzao
extends zzcd {
    private static final String ID = zzag.zzet.toString();

    public zzao() {
        super(ID);
    }

    @Override
    protected boolean zza(zzdl zzdl2, zzdl zzdl3, Map<String, zzaj.zza> map) {
        if (zzdl2.zza(zzdl3) >= 0) {
            return true;
        }
        return false;
    }
}
