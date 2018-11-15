/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.net.wifi.ScanResult
 */
package com.facebook.places.internal;

import android.net.wifi.ScanResult;
import java.util.Comparator;

static final class WifiScannerImpl
implements Comparator<ScanResult> {
    WifiScannerImpl() {
    }

    @Override
    public int compare(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult2.level - scanResult.level;
    }
}
