/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 */
package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.zze;

public final class zzi {
    private static Boolean zzaGL;
    private static Boolean zzaGM;
    private static Boolean zzaGN;
    private static Boolean zzaGO;
    private static Boolean zzaGP;

    @TargetApi(value=20)
    public static boolean zzaI(Context context) {
        if (zzaGN == null) {
            boolean bl = zzs.zzyG() && context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
            zzaGN = bl;
        }
        return zzaGN;
    }

    @TargetApi(value=24)
    public static boolean zzaJ(Context context) {
        if (!zzs.isAtLeastN() && zzi.zzaI(context)) {
            return true;
        }
        return false;
    }

    @TargetApi(value=21)
    public static boolean zzaK(Context context) {
        if (zzaGO == null) {
            boolean bl = zzs.zzyI() && context.getPackageManager().hasSystemFeature("cn.google");
            zzaGO = bl;
        }
        return zzaGO;
    }

    public static boolean zzaL(Context context) {
        if (zzaGP == null) {
            zzaGP = context.getPackageManager().hasSystemFeature("android.hardware.type.iot");
        }
        return zzaGP;
    }

    public static boolean zzb(Resources resources) {
        boolean bl = false;
        if (resources == null) {
            return false;
        }
        if (zzaGL == null) {
            boolean bl2 = (resources.getConfiguration().screenLayout & 15) > 3;
            if (zzs.zzyx() && bl2 || zzi.zzc(resources)) {
                bl = true;
            }
            zzaGL = bl;
        }
        return zzaGL;
    }

    @TargetApi(value=13)
    private static boolean zzc(Resources resources) {
        if (zzaGM == null) {
            resources = resources.getConfiguration();
            boolean bl = zzs.zzyz() && (resources.screenLayout & 15) <= 3 && resources.smallestScreenWidthDp >= 600;
            zzaGM = bl;
        }
        return zzaGM;
    }

    public static boolean zzyw() {
        boolean bl = zze.zzaxl;
        return "user".equals(Build.TYPE);
    }
}
