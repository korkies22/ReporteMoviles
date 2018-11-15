/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzacx;

public final class zzx {
    @TargetApi(value=19)
    public static boolean zzc(Context context, int n, String string) {
        return zzacx.zzaQ(context).zzg(n, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean zzf(Context context, int n) {
        if (!zzx.zzc(context, n, "com.google.android.gms")) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager = packageManager.getPackageInfo("com.google.android.gms", 64);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        return zzf.zzav(context).zzb(context.getPackageManager(), (PackageInfo)packageManager);
        if (Log.isLoggable((String)"UidVerifier", (int)3)) {
            Log.d((String)"UidVerifier", (String)"Package manager can't find google play services package, defaulting to false");
        }
        return false;
    }
}
