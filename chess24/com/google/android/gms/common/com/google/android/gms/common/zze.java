/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageInstaller
 *  android.content.pm.PackageInstaller$SessionInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.os.UserManager
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzx;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zzd;
import com.google.android.gms.common.zzf;
import com.google.android.gms.internal.zzacx;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class zze {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 10084000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    public static boolean zzaxl = false;
    public static boolean zzaxm = false;
    static boolean zzaxn = false;
    private static boolean zzaxo = false;
    static final AtomicBoolean zzaxp = new AtomicBoolean();
    private static final AtomicBoolean zzaxq = new AtomicBoolean();

    zze() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int n, Context context, int n2) {
        return zzc.zzuz().getErrorResolutionPendingIntent(context, n, n2);
    }

    @Deprecated
    public static String getErrorString(int n) {
        return ConnectionResult.getStatusString(n);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context object) {
        Uri uri = new Uri.Builder().scheme("android.resource").authority(GOOGLE_PLAY_SERVICES_PACKAGE).appendPath("raw").appendPath("oss_notice").build();
        try {
            object = object.getContentResolver().openInputStream(uri);
        }
        catch (Exception exception) {
            return null;
        }
        String string = new Scanner((InputStream)object).useDelimiter("\\A").next();
        if (object == null) return string;
        object.close();
        return string;
        catch (Throwable throwable) {
            if (object == null) throw throwable;
            object.close();
            throw throwable;
        }
        catch (NoSuchElementException noSuchElementException) {}
        if (object == null) return null;
        object.close();
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext(GOOGLE_PLAY_SERVICES_PACKAGE, 3);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication(GOOGLE_PLAY_SERVICES_PACKAGE);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Deprecated
    public static int isGooglePlayServicesAvailable(Context var0) {
        block17 : {
            block15 : {
                block16 : {
                    block14 : {
                        var4_4 = var0.getPackageManager();
                        try {
                            var0.getResources().getString(R.string.common_google_play_services_unknown_issue);
                            break block14;
                        }
                        catch (Throwable var3_5) {}
                        Log.e((String)"GooglePlayServicesUtil", (String)"The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
                    }
                    if (!"com.google.android.gms".equals(var0.getPackageName())) {
                        zze.zzap((Context)var0);
                    }
                    var1_12 = zzi.zzaJ((Context)var0) == false && zzi.zzaL((Context)var0) == false ? 1 : 0;
                    var3_6 = null;
                    if (var1_12 != 0) {
                        var3_7 = var4_4.getPackageInfo("com.android.vending", 8256);
                    }
                    try {
                        var5_13 = var4_4.getPackageInfo("com.google.android.gms", 64);
                    }
                    catch (PackageManager.NameNotFoundException var0_3) {}
                    var0 = zzf.zzav((Context)var0);
                    if (var1_12 == 0) break block15;
                    var3_9 = var0.zza((PackageInfo)var3_8, zzd.zzd.zzaxk);
                    if (var3_9 != null) break block16;
                    var0 = "Google Play Store signature invalid.";
                    break block17;
                }
                if (var0.zza(var5_13, new zzd.zza[]{var3_9}) != null) ** GOTO lbl-1000
                ** GOTO lbl-1000
            }
            if (var0.zza(var5_13, zzd.zzd.zzaxk) == null) lbl-1000: // 2 sources:
            {
                var0 = "Google Play services signature invalid.";
            } else lbl-1000: // 2 sources:
            {
                var1_12 = zzl.zzdj(zze.GOOGLE_PLAY_SERVICES_VERSION_CODE);
                if (zzl.zzdj(var5_13.versionCode) < var1_12) {
                    var1_12 = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
                    var2_14 = var5_13.versionCode;
                    var0 = new StringBuilder(77);
                    var0.append("Google Play services out of date.  Requires ");
                    var0.append(var1_12);
                    var0.append(" but found ");
                    var0.append(var2_14);
                    Log.w((String)"GooglePlayServicesUtil", (String)var0.toString());
                    return 2;
                }
                var3_11 = var5_13.applicationInfo;
                var0 = var3_11;
                if (var3_11 == null) {
                    try {
                        var0 = var4_4.getApplicationInfo("com.google.android.gms", 0);
                    }
                    catch (PackageManager.NameNotFoundException var0_2) {
                        Log.wtf((String)"GooglePlayServicesUtil", (String)"Google Play services missing when getting application info.", (Throwable)var0_2);
                        return 1;
                    }
                }
                if (var0.enabled != false) return 0;
                return 3;
                catch (PackageManager.NameNotFoundException var0_1) {}
                var0 = "Google Play Store is missing.";
            }
        }
        Log.w((String)"GooglePlayServicesUtil", (String)var0);
        return 9;
        Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services is missing.");
        return 1;
    }

    @Deprecated
    public static boolean isUserRecoverableError(int n) {
        if (n != 9) {
            switch (n) {
                default: {
                    return false;
                }
                case 1: 
                case 2: 
                case 3: 
            }
        }
        return true;
    }

    @Deprecated
    public static void zzZ(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int n = zzc.zzuz().isGooglePlayServicesAvailable(context);
        if (n != 0) {
            context = zzc.zzuz().zzb(context, n, "e");
            StringBuilder stringBuilder = new StringBuilder(57);
            stringBuilder.append("GooglePlayServices not available due to error ");
            stringBuilder.append(n);
            Log.e((String)"GooglePlayServicesUtil", (String)stringBuilder.toString());
            if (context == null) {
                throw new GooglePlayServicesNotAvailableException(n);
            }
            throw new GooglePlayServicesRepairableException(n, "Google Play Services not available", (Intent)context);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static int zzak(Context context) {
        try {
            context = context.getPackageManager().getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 0);
            return context.versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        Log.w((String)"GooglePlayServicesUtil", (String)"Google Play services is missing.");
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Deprecated
    public static void zzan(Context context) {
        if (zzaxp.getAndSet(true)) {
            return;
        }
        try {
            context = (NotificationManager)context.getSystemService("notification");
            if (context == null) return;
        }
        catch (SecurityException securityException) {
            return;
        }
        context.cancel(10436);
    }

    private static void zzap(Context object) {
        if (zzaxq.get()) {
            return;
        }
        int n = zzz.zzaE(object);
        if (n == 0) {
            throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
        }
        if (n != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
            int n2 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
            object = String.valueOf("com.google.android.gms.version");
            StringBuilder stringBuilder = new StringBuilder(290 + String.valueOf(object).length());
            stringBuilder.append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ");
            stringBuilder.append(n2);
            stringBuilder.append(" but found ");
            stringBuilder.append(n);
            stringBuilder.append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"");
            stringBuilder.append((String)object);
            stringBuilder.append("\" android:value=\"@integer/google_play_services_version\" />");
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public static boolean zzaq(Context context) {
        zze.zzat(context);
        return zzaxn;
    }

    public static boolean zzar(Context context) {
        if (!zze.zzaq(context) && zze.zzuE()) {
            return false;
        }
        return true;
    }

    @TargetApi(value=18)
    public static boolean zzas(Context context) {
        if (zzs.zzyE() && (context = ((UserManager)context.getSystemService("user")).getApplicationRestrictions(context.getPackageName())) != null && "true".equals(context.getString("restricted_profile"))) {
            return true;
        }
        return false;
    }

    private static void zzat(Context context) {
        if (!zzaxo) {
            zze.zzau(context);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void zzau(Context context) {
        Throwable throwable22222;
        block5 : {
            block4 : {
                try {
                    PackageInfo packageInfo = zzacx.zzaQ(context).getPackageInfo(GOOGLE_PLAY_SERVICES_PACKAGE, 64);
                    zzaxn = packageInfo != null && zzf.zzav(context).zza(packageInfo, zzd.zzd.zzaxk[1]) != null;
                    break block4;
                }
                catch (Throwable throwable22222) {
                    break block5;
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    Log.w((String)"GooglePlayServicesUtil", (String)"Cannot find Google Play services package name.", (Throwable)nameNotFoundException);
                }
            }
            zzaxo = true;
            return;
        }
        zzaxo = true;
        throw throwable22222;
    }

    @Deprecated
    @TargetApi(value=19)
    public static boolean zzc(Context context, int n, String string2) {
        return zzx.zzc(context, n, string2);
    }

    @Deprecated
    public static boolean zzd(Context context, int n) {
        if (n == 18) {
            return true;
        }
        if (n == 1) {
            return zze.zzs(context, GOOGLE_PLAY_SERVICES_PACKAGE);
        }
        return false;
    }

    @Deprecated
    public static boolean zze(Context context, int n) {
        if (n == 9) {
            return zze.zzs(context, GOOGLE_PLAY_STORE_PACKAGE);
        }
        return false;
    }

    @Deprecated
    public static boolean zzf(Context context, int n) {
        return zzx.zzf(context, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @TargetApi(value=21)
    static boolean zzs(Context context, String string2) {
        Object object;
        boolean bl = string2.equals(GOOGLE_PLAY_SERVICES_PACKAGE);
        if (zzs.zzyI()) {
            object = context.getPackageManager().getPackageInstaller().getAllSessions().iterator();
            while (object.hasNext()) {
                if (!string2.equals(((PackageInstaller.SessionInfo)object.next()).getAppPackageName())) continue;
                return true;
            }
        }
        object = context.getPackageManager();
        try {
            string2 = object.getApplicationInfo(string2, 8192);
            if (bl) {
                return string2.enabled;
            }
            if (string2.enabled && !(bl = zze.zzas(context))) {
                return true;
            }
            return false;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    @Deprecated
    public static boolean zzuE() {
        return zzi.zzyw();
    }
}
