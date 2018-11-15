/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzch
extends zzam {
    private static final String ID = zzag.zzdA.toString();
    private static final zzaj.zza zzbEZ = zzdm.zzR("Android");

    public zzch() {
        super(ID, new String[0]);
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        return zzbEZ;
    }
}
