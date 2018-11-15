/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

import com.facebook.places.internal.ScannerException;
import com.facebook.places.internal.WifiScanResult;
import java.util.List;

public interface WifiScanner {
    public WifiScanResult getConnectedWifi() throws ScannerException;

    public List<WifiScanResult> getWifiScans() throws ScannerException;

    public void initAndCheckEligibility() throws ScannerException;

    public boolean isWifiScanningEnabled();
}
