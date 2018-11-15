/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.dynamic;

import android.os.Bundle;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;

class zza
implements zza.zza {
    final /* synthetic */ Bundle zzxd;

    zza(Bundle bundle) {
        this.zzxd = bundle;
    }

    @Override
    public int getState() {
        return 1;
    }

    @Override
    public void zzb(LifecycleDelegate lifecycleDelegate) {
        zza.this.zzaQd.onCreate(this.zzxd);
    }
}
