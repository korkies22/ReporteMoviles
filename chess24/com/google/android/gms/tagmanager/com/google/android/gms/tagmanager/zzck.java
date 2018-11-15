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

class zzck
extends zzam {
    private static final String ID = zzag.zzdB.toString();
    private static final String zzbFj = zzah.zzii.toString();
    private static final String zzbFk = zzah.zzig.toString();

    public zzck() {
        super(ID, new String[0]);
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        double d;
        double d2;
        block3 : {
            block2 : {
                Object object2 = object.get(zzbFj);
                object = object.get(zzbFk);
                if (object2 == null || object2 == zzdm.zzQm() || object == null || object == zzdm.zzQm()) break block2;
                object2 = zzdm.zzf((zzaj.zza)object2);
                object = zzdm.zzf((zzaj.zza)object);
                if (object2 != zzdm.zzQk() && object != zzdm.zzQk() && (d = object2.doubleValue()) <= (d2 = object.doubleValue())) break block3;
            }
            d = 0.0;
            d2 = 2.147483647E9;
        }
        return zzdm.zzR(Math.round(Math.random() * (d2 - d) + d));
    }
}
