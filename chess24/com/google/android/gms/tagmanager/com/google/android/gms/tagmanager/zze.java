/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzbf;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zze
extends zzam {
    private static final String ID = zzag.zzdJ.toString();
    private static final String zzbCK = zzah.zzgo.toString();
    private static final String zzbCL = zzah.zzgr.toString();
    private final Context zzqr;

    public zze(Context context) {
        super(ID, zzbCL);
        this.zzqr = context;
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        Object object2 = object.get(zzbCL);
        if (object2 == null) {
            return zzdm.zzQm();
        }
        object2 = zzdm.zze((zzaj.zza)object2);
        object = (object = object.get(zzbCK)) != null ? zzdm.zze((zzaj.zza)object) : null;
        if ((object = zzbf.zzj(this.zzqr, (String)object2, (String)object)) != null) {
            return zzdm.zzR(object);
        }
        return zzdm.zzQm();
    }
}
