/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzacx;

public class zzd {
    public static int zza(PackageInfo packageInfo) {
        if (packageInfo != null) {
            if (packageInfo.applicationInfo == null) {
                return -1;
            }
            packageInfo = packageInfo.applicationInfo.metaData;
            if (packageInfo == null) {
                return -1;
            }
            return packageInfo.getInt("com.google.android.gms.version", -1);
        }
        return -1;
    }

    public static int zzv(Context context, String string) {
        return zzd.zza(zzd.zzw(context, string));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Nullable
    public static PackageInfo zzw(Context context, String string) {
        try {
            return zzacx.zzaQ(context).getPackageInfo(string, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    @TargetApi(value=12)
    public static boolean zzx(Context context, String string) {
        boolean bl = zzs.zzyy();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        "com.google.android.gms".equals(string);
        try {
            int n = zzacx.zzaQ((Context)context).getApplicationInfo((String)string, (int)0).flags;
            if ((n & 2097152) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }
}
