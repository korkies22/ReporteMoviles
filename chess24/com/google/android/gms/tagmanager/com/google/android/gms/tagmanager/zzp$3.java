/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.zzp;
import com.google.android.gms.tagmanager.zzq;

class zzp
implements zzp.zza {
    private Long zzbDu;
    final /* synthetic */ boolean zzbDv;

    zzp(boolean bl) {
        this.zzbDv = bl;
    }

    private long zzOJ() {
        if (this.zzbDu == null) {
            this.zzbDu = zzp.this.zzbDk.zzOL();
        }
        return this.zzbDu;
    }

    @Override
    public boolean zzb(Container container) {
        if (this.zzbDv) {
            if (container.getLastRefreshTime() + this.zzOJ() >= zzp.this.zzuI.currentTimeMillis()) {
                return true;
            }
            return false;
        }
        return container.isDefault() ^ true;
    }
}
