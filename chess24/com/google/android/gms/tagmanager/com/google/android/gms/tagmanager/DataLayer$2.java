/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.DataLayer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

class DataLayer
implements DataLayer.zzc.zza {
    DataLayer() {
    }

    @Override
    public void zzJ(List<DataLayer.zza> object) {
        object = object.iterator();
        while (object.hasNext()) {
            DataLayer.zza zza2 = (DataLayer.zza)object.next();
            DataLayer.this.zzab(DataLayer.this.zzo(zza2.zzAH, zza2.zzYe));
        }
        DataLayer.this.zzbDG.countDown();
    }
}
