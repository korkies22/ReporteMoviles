/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.facebook.places.internal;

import android.location.Location;
import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.ScannerException;
import com.facebook.places.internal.WifiScanResult;
import java.util.List;

public class LocationPackage {
    public List<BluetoothScanResult> ambientBluetoothLe;
    public List<WifiScanResult> ambientWifi;
    public WifiScanResult connectedWifi;
    public boolean isBluetoothScanningEnabled;
    public boolean isWifiScanningEnabled;
    public Location location;
    public ScannerException.Type locationError;
}
