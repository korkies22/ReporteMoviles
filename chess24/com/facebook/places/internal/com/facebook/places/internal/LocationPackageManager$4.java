/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

import com.facebook.FacebookSdk;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.ScannerFactory;
import com.facebook.places.internal.WifiScanResult;
import com.facebook.places.internal.WifiScanner;
import java.util.List;
import java.util.concurrent.Callable;

static final class LocationPackageManager
implements Callable<LocationPackage> {
    final /* synthetic */ LocationPackageRequestParams val$requestParams;

    LocationPackageManager(LocationPackageRequestParams locationPackageRequestParams) {
        this.val$requestParams = locationPackageRequestParams;
    }

    @Override
    public LocationPackage call() throws Exception {
        LocationPackage locationPackage = new LocationPackage();
        try {
            WifiScanner wifiScanner = ScannerFactory.newWifiScanner(FacebookSdk.getApplicationContext(), this.val$requestParams);
            wifiScanner.initAndCheckEligibility();
            locationPackage.connectedWifi = wifiScanner.getConnectedWifi();
            locationPackage.isWifiScanningEnabled = wifiScanner.isWifiScanningEnabled();
            if (locationPackage.isWifiScanningEnabled) {
                locationPackage.ambientWifi = wifiScanner.getWifiScans();
                return locationPackage;
            }
        }
        catch (Exception exception) {
            com.facebook.places.internal.LocationPackageManager.logException("Exception scanning for wifi access points", exception);
            locationPackage.isWifiScanningEnabled = false;
        }
        return locationPackage;
    }
}
