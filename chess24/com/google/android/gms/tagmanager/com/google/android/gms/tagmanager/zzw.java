/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzam;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Map;

class zzw
extends zzam {
    private static final String ID = zzag.zzdk.toString();
    private static final String NAME = zzah.zzik.toString();
    private static final String zzbDI = zzah.zzgP.toString();
    private final DataLayer zzbCT;

    public zzw(DataLayer dataLayer) {
        super(ID, NAME);
        this.zzbCT = dataLayer;
    }

    @Override
    public boolean zzOw() {
        return false;
    }

    @Override
    public zzaj.zza zzY(Map<String, zzaj.zza> object) {
        Object object2 = this.zzbCT.get(zzdm.zze(object.get(NAME)));
        if (object2 == null) {
            if ((object = object.get(zzbDI)) != null) {
                return object;
            }
            return zzdm.zzQm();
        }
        return zzdm.zzR(object2);
    }
}
