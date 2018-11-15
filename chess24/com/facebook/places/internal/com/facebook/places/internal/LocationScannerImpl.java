/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.location.Location
 *  android.location.LocationListener
 *  android.location.LocationManager
 *  android.os.Bundle
 *  android.os.HandlerThread
 *  android.os.Looper
 */
package com.facebook.places.internal;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import com.facebook.internal.Validate;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.LocationScanner;
import com.facebook.places.internal.ScannerException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocationScannerImpl
implements LocationScanner,
LocationListener {
    private static final float MIN_DISTANCE_BETWEEN_UPDATES = 0.0f;
    private static final long MIN_TIME_BETWEEN_UPDATES = 100L;
    private Context context;
    private List<String> enabledProviders;
    private Location freshLocation;
    private LocationManager locationManager;
    private LocationPackageRequestParams params;
    private final Object scanLock = new Object();

    public LocationScannerImpl(Context context, LocationPackageRequestParams locationPackageRequestParams) {
        this.context = context;
        this.params = locationPackageRequestParams;
        this.locationManager = (LocationManager)context.getSystemService("location");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private Location getFreshLocation() throws ScannerException {
        HandlerThread handlerThread;
        block8 : {
            this.freshLocation = null;
            handlerThread = new HandlerThread("LocationScanner");
            try {
                handlerThread.start();
                for (String string : this.enabledProviders) {
                    this.locationManager.requestLocationUpdates(string, 100L, 0.0f, (LocationListener)this, handlerThread.getLooper());
                }
                Object object = this.scanLock;
                // MONITORENTER : object
            }
            catch (Throwable throwable) {
                this.locationManager.removeUpdates((LocationListener)this);
                handlerThread.quit();
                throw throwable;
            }
            this.scanLock.wait(this.params.getLocationRequestTimeoutMs());
            // MONITOREXIT : object
            break block8;
            catch (Exception exception) {}
        }
        this.locationManager.removeUpdates((LocationListener)this);
        handlerThread.quit();
        if (this.freshLocation != null) return this.freshLocation;
        throw new ScannerException(ScannerException.Type.TIMEOUT);
    }

    private Location getLastLocation(String string) {
        if ((string = this.locationManager.getLastKnownLocation(string)) != null) {
            long l = string.getTime();
            if (System.currentTimeMillis() - l < this.params.getLastLocationMaxAgeMs()) {
                return string;
            }
        }
        return null;
    }

    @Override
    public Location getLocation() throws ScannerException {
        Iterator<String> iterator = this.enabledProviders.iterator();
        while (iterator.hasNext()) {
            Location location = this.getLastLocation(iterator.next());
            if (location == null) continue;
            return location;
        }
        return this.getFreshLocation();
    }

    @Override
    public void initAndCheckEligibility() throws ScannerException {
        if (!Validate.hasLocationPermission(this.context)) {
            throw new ScannerException(ScannerException.Type.PERMISSION_DENIED);
        }
        this.enabledProviders = new ArrayList<String>(this.params.getLocationProviders().length);
        for (String string : this.params.getLocationProviders()) {
            if (!this.locationManager.isProviderEnabled(string)) continue;
            this.enabledProviders.add(string);
        }
        if (this.enabledProviders.isEmpty()) {
            throw new ScannerException(ScannerException.Type.DISABLED);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onLocationChanged(Location location) {
        if (this.freshLocation == null && location.getAccuracy() < this.params.getLocationMaxAccuracyMeters()) {
            Object object = this.scanLock;
            synchronized (object) {
                this.freshLocation = location;
                this.scanLock.notify();
                return;
            }
        }
    }

    public void onProviderDisabled(String string) {
    }

    public void onProviderEnabled(String string) {
    }

    public void onStatusChanged(String string, int n, Bundle bundle) {
    }
}
