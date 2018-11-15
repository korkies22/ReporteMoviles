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

class zzb
extends zzam {
    private static final String ID = zzag.zzdc.toString();
    private final zza zzbCJ;

    public zzb(Context context) {
        this(zza.zzbA(context));
    }

    zzb(zza zza2) {
        super(ID, new String[0]);
        this.zzbCJ = zza2;
        this.zzbCJ.zzOq();
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        object = this.zzbCJ.zzOq();
        if (object == null) {
            return zzdm.zzQm();
        }
        return zzdm.zzR(object);
    }
}
