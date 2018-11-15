/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

public class BluetoothScanResult {
    public String payload;
    public int rssi;

    public BluetoothScanResult(String string, int n) {
        this.payload = string;
        this.rssi = n;
    }
}
