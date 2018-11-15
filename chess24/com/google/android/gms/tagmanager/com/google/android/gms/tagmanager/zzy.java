/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.zzdk;
import com.google.android.gms.tagmanager.zzdm;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class zzy
extends zzdk {
    private static final String ID = zzag.zzdS.toString();
    private static final String VALUE = zzah.zzkh.toString();
    private static final String zzbDT = zzah.zzgj.toString();
    private final DataLayer zzbCT;

    public zzy(DataLayer dataLayer) {
        super(ID, VALUE);
        this.zzbCT = dataLayer;
    }

    private void zza(zzaj.zza object) {
        if (object != null) {
            if (object == zzdm.zzQg()) {
                return;
            }
            if ((object = zzdm.zze((zzaj.zza)object)) == zzdm.zzQl()) {
                return;
            }
            this.zzbCT.zzhd((String)object);
        }
    }

    private void zzb(zzaj.zza iterator) {
        if (iterator != null) {
            if (iterator == zzdm.zzQg()) {
                return;
            }
            if (!((iterator = zzdm.zzj((zzaj.zza)((Object)iterator))) instanceof List)) {
                return;
            }
            for (Object object : (List)((Object)iterator)) {
                if (!(object instanceof Map)) continue;
                object = (Map)object;
                this.zzbCT.push((Map<String, Object>)object);
            }
        }
    }

    @Override
    public void zzaa(Map<String, zzaj.zza> map) {
        this.zzb(map.get(VALUE));
        this.zza(map.get(zzbDT));
    }
}
