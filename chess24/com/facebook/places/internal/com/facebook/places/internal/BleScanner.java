/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.ScannerException;
import java.util.List;

public interface BleScanner {
    public int getErrorCode();

    public List<BluetoothScanResult> getScanResults();

    public void initAndCheckEligibility() throws ScannerException;

    public void startScanning() throws ScannerException;

    public void stopScanning() throws ScannerException;
}
