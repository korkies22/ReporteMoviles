/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 */
package com.crashlytics.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.crashlytics.android.CrashlyticsInitProvider;

class ManifestEnabledCheckStrategy
implements CrashlyticsInitProvider.EnabledCheckStrategy {
    ManifestEnabledCheckStrategy() {
    }

    @Override
    public boolean isCrashlyticsEnabled(Context context) {
        boolean bl;
        block4 : {
            bl = true;
            try {
                String string = context.getPackageName();
                context = context.getPackageManager().getApplicationInfo((String)string, (int)128).metaData;
                if (context == null) break block4;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                return true;
            }
            bl = context.getBoolean("firebase_crashlytics_collection_enabled", true);
            if (bl) {
                return true;
            }
            bl = false;
        }
        return bl;
    }
}
