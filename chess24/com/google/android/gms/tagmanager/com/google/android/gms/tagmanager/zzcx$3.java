/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbgi;
import com.google.android.gms.tagmanager.zzcq;
import com.google.android.gms.tagmanager.zzcs;
import com.google.android.gms.tagmanager.zzcx;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

class zzcx
implements zzcx.zza {
    final /* synthetic */ Map zzbFN;
    final /* synthetic */ Map zzbFO;
    final /* synthetic */ Map zzbFP;
    final /* synthetic */ Map zzbFQ;

    zzcx(com.google.android.gms.tagmanager.zzcx zzcx2, Map map, Map map2, Map map3, Map map4) {
        this.zzbFN = map;
        this.zzbFO = map2;
        this.zzbFP = map3;
        this.zzbFQ = map4;
    }

    @Override
    public void zza(zzbgi.zze zze2, Set<zzbgi.zza> collection, Set<zzbgi.zza> set, zzcs zzcs2) {
        List list = (List)this.zzbFN.get(zze2);
        this.zzbFO.get(zze2);
        if (list != null) {
            collection.addAll(list);
            zzcs2.zzPo();
        }
        collection = (List)this.zzbFP.get(zze2);
        this.zzbFQ.get(zze2);
        if (collection != null) {
            set.addAll(collection);
            zzcs2.zzPp();
        }
    }
}
