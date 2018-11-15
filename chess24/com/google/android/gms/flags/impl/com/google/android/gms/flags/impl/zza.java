/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 */
package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import com.google.android.gms.internal.zzaps;
import java.util.concurrent.Callable;

public abstract class zza<T> {

    public static class zza
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

    public static class zzb
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

    public static class zzc
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

    public static class zzd
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

}
