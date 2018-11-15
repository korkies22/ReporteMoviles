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

final class zza.zzd
implements Callable<String> {
    final /* synthetic */ String zzaWN;
    final /* synthetic */ String zzaWR;

    zza.zzd(String string, String string2) {
        this.zzaWN = string;
        this.zzaWR = string2;
    }

    @Override
    public /* synthetic */ Object call() throws Exception {
        return this.zzou();
    }

    public String zzou() {
        return SharedPreferences.this.getString(this.zzaWN, this.zzaWR);
    }
}
