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

final class zza.zzc
implements Callable<Long> {
    final /* synthetic */ String zzaWN;
    final /* synthetic */ Long zzaWQ;

    zza.zzc(String string, Long l) {
        this.zzaWN = string;
        this.zzaWQ = l;
    }

    @Override
    public /* synthetic */ Object call() throws Exception {
        return this.zzCT();
    }

    public Long zzCT() {
        return SharedPreferences.this.getLong(this.zzaWN, this.zzaWQ.longValue());
    }
}
