/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 */
package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.content.pm.PackageManager;
import io.fabric.sdk.android.services.cache.ValueLoader;

class InstallerPackageNameProvider
implements ValueLoader<String> {
    InstallerPackageNameProvider() {
    }

    @Override
    public String load(Context object) throws Exception {
        String string = object.getPackageManager().getInstallerPackageName(object.getPackageName());
        object = string;
        if (string == null) {
            object = io.fabric.sdk.android.services.common.InstallerPackageNameProvider.NO_INSTALLER_PACKAGE_NAME;
        }
        return object;
    }
}
