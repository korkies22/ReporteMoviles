/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import com.google.android.gms.flags.impl.zza;
import java.util.concurrent.Callable;

final class zza.zza
implements Callable<Boolean> {
    final /* synthetic */ String zzaWN;
    final /* synthetic */ Boolean zzaWO;

    zza.zza(String string, Boolean bl) {
        this.zzaWN = string;
        this.zzaWO = bl;
    }

    @Override
    public /* synthetic */ Object call() throws Exception {
        return this.zzkt();
    }

    public Boolean zzkt() {
        return SharedPreferences.this.getBoolean(this.zzaWN, this.zzaWO.booleanValue());
    }
}
