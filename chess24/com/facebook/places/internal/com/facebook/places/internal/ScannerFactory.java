/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.facebook.places.internal;

import android.content.Context;
import android.os.Build;
import com.facebook.places.internal.BleScanner;
import com.facebook.places.internal.BleScannerImpl;
import com.facebook.places.internal.BleScannerLegacy;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.LocationScanner;
import com.facebook.places.internal.LocationScannerImpl;
import com.facebook.places.internal.WifiScanner;
import com.facebook.places.internal.WifiScannerImpl;

public class ScannerFactory {
    public static final int OS_VERSION_JELLY_BEAN_MR1 = 17;
    public static final int OS_VERSION_JELLY_BEAN_MR2 = 18;
    public static final int OS_VERSION_LOLLIPOP = 21;

    public static BleScanner newBleScanner(Context context, LocationPackageRequestParams locationPackageRequestParams) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new BleScannerImpl(context, locationPackageRequestParams);
        }
        return new BleScannerLegacy();
    }

    public static LocationScanner newLocationScanner(Context context, LocationPackageRequestParams locationPackageRequestParams) {
        return new LocationScannerImpl(context, locationPackageRequestParams);
    }

    public static WifiScanner newWifiScanner(Context context, LocationPackageRequestParams locationPackageRequestParams) {
        return new WifiScannerImpl(context, locationPackageRequestParams);
    }
}
