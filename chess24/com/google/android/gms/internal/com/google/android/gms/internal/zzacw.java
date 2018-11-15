/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.common.util.zzs;

public class zzacw {
    protected final Context mContext;

    public zzacw(Context context) {
        this.mContext = context;
    }

    public int checkCallingOrSelfPermission(String string) {
        return this.mContext.checkCallingOrSelfPermission(string);
    }

    public int checkPermission(String string, String string2) {
        return this.mContext.getPackageManager().checkPermission(string, string2);
    }

    public ApplicationInfo getApplicationInfo(String string, int n) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationInfo(string, n);
    }

    public PackageInfo getPackageInfo(String string, int n) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getPackageInfo(string, n);
    }

    public String[] getPackagesForUid(int n) {
        return this.mContext.getPackageManager().getPackagesForUid(n);
    }

    public CharSequence zzdE(String string) throws PackageManager.NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationLabel(this.mContext.getPackageManager().getApplicationInfo(string, 0));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @TargetApi(value=19)
    public boolean zzg(int n, String string) {
        if (zzs.zzyF()) {
            try {
                ((AppOpsManager)this.mContext.getSystemService("appops")).checkPackage(n, string);
                return true;
            }
            catch (SecurityException securityException) {
                return false;
            }
        }
        String[] arrstring = this.mContext.getPackageManager().getPackagesForUid(n);
        if (string == null) return false;
        if (arrstring == null) return false;
        n = 0;
        while (n < arrstring.length) {
            if (string.equals(arrstring[n])) {
                return true;
            }
            ++n;
        }
        return false;
    }
}
