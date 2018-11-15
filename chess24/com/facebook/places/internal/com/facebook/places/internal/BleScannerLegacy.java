/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

import com.facebook.places.internal.BleScanner;
import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.ScannerException;
import java.util.List;

public class BleScannerLegacy
implements BleScanner {
    BleScannerLegacy() {
    }

    @Override
    public int getErrorCode() {
        return -1;
    }

    @Override
    public List<BluetoothScanResult> getScanResults() {
        return null;
    }

    @Override
    public void initAndCheckEligibility() throws ScannerException {
        throw new ScannerException(ScannerException.Type.NOT_SUPPORTED);
    }

    @Override
    public void startScanning() throws ScannerException {
        throw new ScannerException(ScannerException.Type.NOT_SUPPORTED);
    }

    @Override
    public void stopScanning() throws ScannerException {
        throw new ScannerException(ScannerException.Type.NOT_SUPPORTED);
    }
}
