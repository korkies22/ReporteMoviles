/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.tagmanager.zzdd;
import java.util.Random;

public class zzq {
    private final Context mContext;
    private final String zzbCS;
    private final Random zzzI;

    public zzq(Context context, String string) {
        this(context, string, new Random());
    }

    @VisibleForTesting
    zzq(Context context, String string, Random random) {
        this.mContext = zzac.zzw(context);
        this.zzbCS = zzac.zzw(string);
        this.zzzI = random;
    }

    private SharedPreferences zzOP() {
        Context context = this.mContext;
        String string = String.valueOf("_gtmContainerRefreshPolicy_");
        String string2 = String.valueOf(this.zzbCS);
        string = string2.length() != 0 ? string.concat(string2) : new String(string);
        return context.getSharedPreferences(string, 0);
    }

    private long zzg(long l, long l2) {
        SharedPreferences sharedPreferences = this.zzOP();
        long l3 = Math.max(0L, sharedPreferences.getLong("FORBIDDEN_COUNT", 0L));
        long l4 = Math.max(0L, sharedPreferences.getLong("SUCCESSFUL_COUNT", 0L));
        l2 = (long)((float)l3 / (float)(l3 + l4 + 1L) * (float)(l2 - l));
        return (long)(this.zzzI.nextFloat() * (float)(l + l2));
    }

    public long zzOL() {
        return 43200000L + this.zzg(7200000L, 259200000L);
    }

    public long zzOM() {
        return 3600000L + this.zzg(600000L, 86400000L);
    }

    @SuppressLint(value={"CommitPrefEdits"})
    public void zzON() {
        SharedPreferences sharedPreferences = this.zzOP();
        long l = sharedPreferences.getLong("FORBIDDEN_COUNT", 0L);
        long l2 = sharedPreferences.getLong("SUCCESSFUL_COUNT", 0L);
        sharedPreferences = sharedPreferences.edit();
        l = l == 0L ? 3L : Math.min(10L, l + 1L);
        l2 = Math.max(0L, Math.min(l2, 10L - l));
        sharedPreferences.putLong("FORBIDDEN_COUNT", l);
        sharedPreferences.putLong("SUCCESSFUL_COUNT", l2);
        zzdd.zza((SharedPreferences.Editor)sharedPreferences);
    }

    @SuppressLint(value={"CommitPrefEdits"})
    public void zzOO() {
        SharedPreferences sharedPreferences = this.zzOP();
        long l = sharedPreferences.getLong("SUCCESSFUL_COUNT", 0L);
        long l2 = sharedPreferences.getLong("FORBIDDEN_COUNT", 0L);
        l = Math.min(10L, l + 1L);
        l2 = Math.max(0L, Math.min(l2, 10L - l));
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.putLong("SUCCESSFUL_COUNT", l);
        sharedPreferences.putLong("FORBIDDEN_COUNT", l2);
        zzdd.zza((SharedPreferences.Editor)sharedPreferences);
    }
}
