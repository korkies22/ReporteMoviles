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

public static class zza.zzb
extends zza<Integer> {
    public static Integer zza(SharedPreferences sharedPreferences, final String string, final Integer n) {
        return (Integer)zzaps.zzb(new Callable<Integer>(){

            @Override
            public /* synthetic */ Object call() throws Exception {
                return this.zzCS();
            }

            public Integer zzCS() {
                return SharedPreferences.this.getInt(string, n.intValue());
            }
        });
    }

}
