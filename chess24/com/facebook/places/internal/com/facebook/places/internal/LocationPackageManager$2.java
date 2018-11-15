/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.facebook.places.internal;

import android.location.Location;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationScanner;
import com.facebook.places.internal.ScannerException;
import java.util.concurrent.Callable;

static final class LocationPackageManager
implements Callable<LocationPackage> {
    final /* synthetic */ LocationScanner val$locationScanner;

    LocationPackageManager(LocationScanner locationScanner) {
        this.val$locationScanner = locationScanner;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public LocationPackage call() throws Exception {
        LocationPackage locationPackage = new LocationPackage();
        try {
            locationPackage.location = this.val$locationScanner.getLocation();
            return locationPackage;
        }
        catch (ScannerException scannerException) {
            locationPackage.locationError = scannerException.type;
            com.facebook.places.internal.LocationPackageManager.logException("Exception while getting location", scannerException);
            return locationPackage;
        }
        catch (Exception exception) {}
        locationPackage.locationError = ScannerException.Type.UNKNOWN_ERROR;
        return locationPackage;
    }
}
