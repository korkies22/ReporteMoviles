/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzx;
import java.util.List;

class zzx
implements Runnable {
    final /* synthetic */ List zzbDN;
    final /* synthetic */ long zzbDO;

    zzx(List list, long l) {
        this.zzbDN = list;
        this.zzbDO = l;
    }

    @Override
    public void run() {
        zzx.this.zzb(this.zzbDN, this.zzbDO);
    }
}
