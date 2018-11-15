/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.content.res.Resources
 */
package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.zze;

public final class GooglePlayServicesUtil
extends zze {
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    @Deprecated
    public static Dialog getErrorDialog(int n, Activity activity, int n2) {
        return GooglePlayServicesUtil.getErrorDialog(n, activity, n2, null);
    }

    @Deprecated
    public static Dialog getErrorDialog(int n, Activity activity, int n2, DialogInterface.OnCancelListener onCancelListener) {
        int n3 = n;
        if (GooglePlayServicesUtil.zzd((Context)activity, n)) {
            n3 = 18;
        }
        return GoogleApiAvailability.getInstance().getErrorDialog(activity, n3, n2, onCancelListener);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int n, Context context, int n2) {
        return zze.getErrorPendingIntent(n, context, n2);
    }

    @Deprecated
    public static String getErrorString(int n) {
        return zze.getErrorString(n);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        return zze.getOpenSourceSoftwareLicenseInfo(context);
    }

    public static Context getRemoteContext(Context context) {
        return zze.getRemoteContext(context);
    }

    public static Resources getRemoteResource(Context context) {
        return zze.getRemoteResource(context);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return zze.isGooglePlayServicesAvailable(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int n) {
        return zze.isUserRecoverableError(n);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int n, Activity activity, int n2) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n, activity, n2, null);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int n, Activity activity, int n2, DialogInterface.OnCancelListener onCancelListener) {
        return GooglePlayServicesUtil.showErrorDialogFragment(n, activity, null, n2, onCancelListener);
    }

    public static boolean showErrorDialogFragment(int n, Activity activity, Fragment fragment, int n2, DialogInterface.OnCancelListener onCancelListener) {
        int n3 = n;
        if (GooglePlayServicesUtil.zzd((Context)activity, n)) {
            n3 = 18;
        }
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        if (fragment == null) {
            return googleApiAvailability.showErrorDialogFragment(activity, n3, n2, onCancelListener);
        }
        if ((fragment = googleApiAvailability.zza((Context)activity, n3, zzi.zza(fragment, GoogleApiAvailability.getInstance().zzb((Context)activity, n3, "d"), n2), onCancelListener)) == null) {
            return false;
        }
        googleApiAvailability.zza(activity, (Dialog)fragment, GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    @Deprecated
    public static void showErrorNotification(int n, Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        if (!GooglePlayServicesUtil.zzd(context, n) && !GooglePlayServicesUtil.zze(context, n)) {
            googleApiAvailability.showErrorNotification(context, n);
            return;
        }
        googleApiAvailability.zzal(context);
    }

    @Deprecated
    public static boolean zzd(Context context, int n) {
        return zze.zzd(context, n);
    }

    @Deprecated
    public static boolean zze(Context context, int n) {
        return zze.zze(context, n);
    }
}
