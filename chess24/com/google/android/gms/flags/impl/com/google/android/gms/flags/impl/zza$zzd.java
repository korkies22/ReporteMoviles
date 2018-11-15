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

public static class zza.zzd
extends zza<String> {
    public static String zza(SharedPreferences sharedPreferences, final String string, final String string2) {
        return (String)zzaps.zzb(new Callable<String>(){

            @Override
            public /* synthetic */ Object call() throws Exception {
                return this.zzou();
            }

            public String zzou() {
                return SharedPreferences.this.getString(string, string2);
            }
        });
    }

}
