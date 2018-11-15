/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import com.google.android.gms.flags.impl.zza;
import com.google.android.gms.internal.zzaps;
import java.util.concurrent.Callable;

public static class zza.zzc
extends zza<Long> {
    public static Long zza(SharedPreferences sharedPreferences, final String string, final Long l) {
        return (Long)zzaps.zzb(new Callable<Long>(){

            @Override
            public /* synthetic */ Object call() throws Exception {
                return this.zzCT();
            }

            public Long zzCT() {
                return SharedPreferences.this.getLong(string, l.longValue());
            }
        });
    }

}
