/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.flags.impl.zza;
import com.google.android.gms.flags.impl.zzb;
import com.google.android.gms.internal.zzapq;

@DynamiteApi
public class FlagProviderImpl
extends zzapq.zza {
    private SharedPreferences zzAN;
    private boolean zztW = false;

    @Override
    public boolean getBooleanFlagValue(String string, boolean bl, int n) {
        if (!this.zztW) {
            return bl;
        }
        return zza.zza.zza(this.zzAN, string, bl);
    }

    @Override
    public int getIntFlagValue(String string, int n, int n2) {
        if (!this.zztW) {
            return n;
        }
        return zza.zzb.zza(this.zzAN, string, n);
    }

    @Override
    public long getLongFlagValue(String string, long l, int n) {
        if (!this.zztW) {
            return l;
        }
        return zza.zzc.zza(this.zzAN, string, l);
    }

    @Override
    public String getStringFlagValue(String string, String string2, int n) {
        if (!this.zztW) {
            return string2;
        }
        return zza.zzd.zza(this.zzAN, string, string2);
    }

    @Override
    public void init(zzd zzd2) {
        zzd2 = (Context)zze.zzE(zzd2);
        if (this.zztW) {
            return;
        }
        try {
            this.zzAN = zzb.zzm(zzd2.createPackageContext("com.google.android.gms", 0));
            this.zztW = true;
            return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
    }
}
