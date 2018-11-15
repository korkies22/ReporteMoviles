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

class zzbe
extends zzam {
    private static final String ID = zzag.zzdO.toString();
    private static final String zzbCK = zzah.zzgo.toString();
    private final Context zzqr;

    public zzbe(Context context) {
        super(ID, new String[0]);
        this.zzqr = context;
    }

    @Override
    public boolean zzOw() {
        return true;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        object = object.get(zzbCK) != null ? zzdm.zze(object.get(zzbCK)) : null;
        if ((object = zzbf.zzH(this.zzqr, object)) != null) {
            return zzdm.zzR(object);
        }
        return zzdm.zzQm();
    }
}
