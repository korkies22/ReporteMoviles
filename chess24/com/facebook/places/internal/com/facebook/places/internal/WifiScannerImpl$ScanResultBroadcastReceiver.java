/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 */
package com.facebook.places.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.facebook.places.internal.WifiScannerImpl;

private class WifiScannerImpl.ScanResultBroadcastReceiver
extends BroadcastReceiver {
    private WifiScannerImpl.ScanResultBroadcastReceiver() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onReceive(Context object, Intent intent) {
        if (intent != null && "android.net.wifi.SCAN_RESULTS".equals(intent.getAction())) {
            object = WifiScannerImpl.this.scanLock;
            synchronized (object) {
                WifiScannerImpl.this.scanLock.notify();
            }
            WifiScannerImpl.this.unregisterBroadcastReceiver();
            return;
        }
    }
}
