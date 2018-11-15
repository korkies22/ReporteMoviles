/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

public class WifiScanResult {
    public String bssid;
    public int frequency;
    public int rssi;
    public String ssid;

    public WifiScanResult() {
    }

    public WifiScanResult(String string, String string2, int n, int n2) {
        this.ssid = string;
        this.bssid = string2;
        this.rssi = n;
        this.frequency = n2;
    }
}
