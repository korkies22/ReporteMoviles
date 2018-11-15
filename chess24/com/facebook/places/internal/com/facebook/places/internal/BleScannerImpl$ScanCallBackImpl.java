/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.bluetooth.le.ScanCallback
 *  android.bluetooth.le.ScanResult
 */
package com.facebook.places.internal;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import com.facebook.places.internal.BleScannerImpl;
import com.facebook.places.internal.BluetoothScanResult;
import java.util.Iterator;
import java.util.List;

private class BleScannerImpl.ScanCallBackImpl
extends ScanCallback {
    private BleScannerImpl.ScanCallBackImpl() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onBatchScanResults(List<ScanResult> iterator) {
        super.onBatchScanResults(iterator);
        try {
            List list = BleScannerImpl.this.scanResults;
            // MONITORENTER : list
            iterator = iterator.iterator();
        }
        catch (Exception exception) {
            BleScannerImpl.logException("Exception in ble scan callback", exception);
            return;
        }
        do {
            if (!iterator.hasNext()) {
                // MONITOREXIT : list
                return;
            }
            BluetoothScanResult bluetoothScanResult = BleScannerImpl.newBluetoothScanResult((ScanResult)iterator.next());
            if (bluetoothScanResult == null) continue;
            BleScannerImpl.this.scanResults.add(bluetoothScanResult);
        } while (true);
    }

    public void onScanFailed(int n) {
        super.onScanFailed(n);
        BleScannerImpl.this.errorCode = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void onScanResult(int n, ScanResult object) {
        super.onScanResult(n, object);
        try {
            List list = BleScannerImpl.this.scanResults;
            // MONITORENTER : list
        }
        catch (Exception exception) {
            BleScannerImpl.logException("Exception in ble scan callback", exception);
            return;
        }
        object = BleScannerImpl.newBluetoothScanResult(object);
        if (object != null) {
            BleScannerImpl.this.scanResults.add(object);
        }
        // MONITOREXIT : list
    }
}
