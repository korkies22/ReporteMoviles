/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zza;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzc
extends zzam {
    private static final String ID = zzag.zzdd.toString();
    private final zza zzbCJ;

    public zzc(Context context) {
        this(zza.zzbA(context));
    }

    zzc(zza zza2) {
        super(ID, new String[0]);
        this.zzbCJ = zza2;
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> map) {
        return zzdm.zzR(this.zzbCJ.isLimitAdTrackingEnabled() ^ true);
    }
}
