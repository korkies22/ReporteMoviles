/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 */
package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;

class zza
implements zza.zza {
    final /* synthetic */ Activity val$activity;
    final /* synthetic */ Bundle zzaQi;
    final /* synthetic */ Bundle zzxd;

    zza(Activity activity, Bundle bundle, Bundle bundle2) {
        this.val$activity = activity;
        this.zzaQi = bundle;
        this.zzxd = bundle2;
    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public void zzb(LifecycleDelegate lifecycleDelegate) {
        zza.this.zzaQd.onInflate(this.val$activity, this.zzaQi, this.zzxd);
    }
}
