/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package com.crashlytics.android.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.services.common.IdManager;

class AppData {
    public final String apiKey;
    public final String buildId;
    public final String installerPackageName;
    public final String packageName;
    public final String versionCode;
    public final String versionName;

    AppData(String string, String string2, String string3, String string4, String string5, String string6) {
        this.apiKey = string;
        this.buildId = string2;
        this.installerPackageName = string3;
        this.packageName = string4;
        this.versionCode = string5;
        this.versionName = string6;
    }

    public static AppData create(Context object, IdManager object2, String string, String string2) throws PackageManager.NameNotFoundException {
        String string3;
        String string4;
        string3 = object.getPackageName();
        object2 = object2.getInstallerPackageName();
        object = object.getPackageManager().getPackageInfo(string3, 0);
        string4 = Integer.toString(object.versionCode);
        object = object.versionName == null ? "0.0" : object.versionName;
        return new AppData(string, string2, (String)object2, string3, string4, (String)object);
    }
}
