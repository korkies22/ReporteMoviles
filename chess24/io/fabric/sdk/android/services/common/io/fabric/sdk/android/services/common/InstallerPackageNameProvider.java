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
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.cache.ValueLoader;

public class InstallerPackageNameProvider {
    private static final String NO_INSTALLER_PACKAGE_NAME = "";
    private final MemoryValueCache<String> installerPackageNameCache = new MemoryValueCache();
    private final ValueLoader<String> installerPackageNameLoader = new ValueLoader<String>(){

        @Override
        public String load(Context object) throws Exception {
            String string = object.getPackageManager().getInstallerPackageName(object.getPackageName());
            object = string;
            if (string == null) {
                object = InstallerPackageNameProvider.NO_INSTALLER_PACKAGE_NAME;
            }
            return object;
        }
    };

    public String getInstallerPackageName(Context object) {
        try {
            object = this.installerPackageNameCache.get((Context)object, this.installerPackageNameLoader);
            boolean bl = NO_INSTALLER_PACKAGE_NAME.equals(object);
            if (bl) {
                object = null;
            }
            return object;
        }
        catch (Exception exception) {
            Fabric.getLogger().e("Fabric", "Failed to determine installer package name", exception);
            return null;
        }
    }

}
