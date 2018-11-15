/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.os.Bundle
 */
package com.crashlytics.android.core;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.crashlytics.android.core.UnityVersionProvider;

class ManifestUnityVersionProvider
implements UnityVersionProvider {
    static final String FABRIC_UNITY_CRASHLYTICS_VERSION_KEY = "io.fabric.unity.crashlytics.version";
    private final Context context;
    private final String packageName;

    public ManifestUnityVersionProvider(Context context, String string) {
        this.context = context;
        this.packageName = string;
    }

    @Override
    public String getUnityVersion() {
        String string;
        block3 : {
            PackageManager packageManager = this.context.getPackageManager();
            string = null;
            try {
                packageManager = packageManager.getApplicationInfo((String)this.packageName, (int)128).metaData;
                if (packageManager == null) break block3;
            }
            catch (Exception exception) {
                return null;
            }
            string = packageManager.getString(FABRIC_UNITY_CRASHLYTICS_VERSION_KEY);
        }
        return string;
    }
}
