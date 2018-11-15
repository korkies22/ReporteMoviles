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

final class zza.zzb
implements Callable<Integer> {
    final /* synthetic */ String zzaWN;
    final /* synthetic */ Integer zzaWP;

    zza.zzb(String string, Integer n) {
        this.zzaWN = string;
        this.zzaWP = n;
    }

    @Override
    public /* synthetic */ Object call() throws Exception {
        return this.zzCS();
    }

    public Integer zzCS() {
        return SharedPreferences.this.getInt(this.zzaWN, this.zzaWP.intValue());
    }
}
