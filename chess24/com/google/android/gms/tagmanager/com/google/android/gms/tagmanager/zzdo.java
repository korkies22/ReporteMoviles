/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzdo
extends zzam {
    private static final String ID = zzag.zzdU.toString();
    private static final String zzbEd = zzah.zzfL.toString();

    public zzdo() {
        super(ID, zzbEd);
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        return zzdm.zzR(zzdm.zze(map.get(zzbEd)).toUpperCase());
    }
}
