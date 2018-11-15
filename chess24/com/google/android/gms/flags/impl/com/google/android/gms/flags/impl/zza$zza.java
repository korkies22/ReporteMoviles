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

public static class zza.zza
extends zza<Boolean> {
    public static Boolean zza(SharedPreferences sharedPreferences, final String string, final Boolean bl) {
        return (Boolean)zzaps.zzb(new Callable<Boolean>(){

            @Override
            public /* synthetic */ Object call() throws Exception {
                return this.zzkt();
            }

            public Boolean zzkt() {
                return SharedPreferences.this.getBoolean(string, bl.booleanValue());
            }
        });
    }

}
