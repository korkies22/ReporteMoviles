/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

import com.facebook.places.internal.BluetoothScanResult;
import java.util.Comparator;

class BleScannerImpl
implements Comparator<BluetoothScanResult> {
    BleScannerImpl() {
    }

    @Override
    public int compare(BluetoothScanResult bluetoothScanResult, BluetoothScanResult bluetoothScanResult2) {
        return bluetoothScanResult2.rssi - bluetoothScanResult.rssi;
    }
}
