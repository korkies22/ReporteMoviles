/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.text.TextUtils
 */
package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.common.zze;
import com.google.android.gms.internal.zzacx;

public class zzc {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final zzc zzaxc = new zzc();

    zzc() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String zzt(@Nullable Context context, @Nullable String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("gcore_");
        stringBuilder.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        stringBuilder.append("-");
        if (!TextUtils.isEmpty((CharSequence)string)) {
            stringBuilder.append(string);
        }
        stringBuilder.append("-");
        if (context != null) {
            stringBuilder.append(context.getPackageName());
        }
        stringBuilder.append("-");
        if (context == null) return stringBuilder.toString();
        try {
            stringBuilder.append(zzacx.zzaQ((Context)context).getPackageInfo((String)context.getPackageName(), (int)0).versionCode);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return stringBuilder.toString();
        }
        return stringBuilder.toString();
    }

    public static zzc zzuz() {
        return zzaxc;
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int n, int n2) {
        return this.zza(context, n, n2, null);
    }

    public String getErrorString(int n) {
        return zze.getErrorString(n);
    }

    @Nullable
    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return zze.getOpenSourceSoftwareLicenseInfo(context);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        int n;
        int n2 = n = zze.isGooglePlayServicesAvailable(context);
        if (zze.zzd(context, n)) {
            n2 = 18;
        }
        return n2;
    }

    public boolean isUserResolvableError(int n) {
        return zze.isUserRecoverableError(n);
    }

    @Nullable
    public PendingIntent zza(Context context, int n, int n2, @Nullable String string) {
        if ((string = this.zzb(context, n, string)) == null) {
            return null;
        }
        return PendingIntent.getActivity((Context)context, (int)n2, (Intent)string, (int)268435456);
    }

    public int zzak(Context context) {
        return zze.zzak(context);
    }

    public void zzam(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zze.zzZ(context);
    }

    public void zzan(Context context) {
        zze.zzan(context);
    }

    @Nullable
    public Intent zzb(Context context, int n, @Nullable String string) {
        switch (n) {
            default: {
                return null;
            }
            case 3: {
                return zzp.zzdt(GOOGLE_PLAY_SERVICES_PACKAGE);
            }
            case 1: 
            case 2: 
        }
        if (context != null && zzi.zzaJ(context)) {
            return zzp.zzxu();
        }
        return zzp.zzC(GOOGLE_PLAY_SERVICES_PACKAGE, zzc.zzt(context, string));
    }

    @Deprecated
    @Nullable
    public Intent zzcr(int n) {
        return this.zzb(null, n, null);
    }

    public boolean zzd(Context context, int n) {
        return zze.zzd(context, n);
    }

    public boolean zzs(Context context, String string) {
        return zze.zzs(context, string);
    }
}
