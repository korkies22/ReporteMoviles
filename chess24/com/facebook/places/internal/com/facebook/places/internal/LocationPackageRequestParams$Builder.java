/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

import com.facebook.places.internal.LocationPackageRequestParams;

public static class LocationPackageRequestParams.Builder {
    private long bluetoothFlushResultsTimeoutMs = 300L;
    private int bluetoothMaxScanResults = 25;
    private long bluetoothScanDurationMs = 500L;
    private boolean isBluetoothScanEnabled = true;
    private boolean isLocationScanEnabled = true;
    private boolean isWifiActiveScanAllowed = true;
    private boolean isWifiActiveScanForced = false;
    private boolean isWifiScanEnabled = true;
    private long lastLocationMaxAgeMs = 60000L;
    private float locationMaxAccuracyMeters = 100.0f;
    private String[] locationProviders = LocationPackageRequestParams.access$1500();
    private long locationRequestTimeoutMs = 30000L;
    private int wifiMaxScanResults = 25;
    private long wifiScanMaxAgeMs = 30000L;
    private long wifiScanTimeoutMs = 6000L;

    static /* synthetic */ boolean access$000(LocationPackageRequestParams.Builder builder) {
        return builder.isLocationScanEnabled;
    }

    static /* synthetic */ String[] access$100(LocationPackageRequestParams.Builder builder) {
        return builder.locationProviders;
    }

    static /* synthetic */ boolean access$1000(LocationPackageRequestParams.Builder builder) {
        return builder.isWifiActiveScanForced;
    }

    static /* synthetic */ boolean access$1100(LocationPackageRequestParams.Builder builder) {
        return builder.isBluetoothScanEnabled;
    }

    static /* synthetic */ long access$1200(LocationPackageRequestParams.Builder builder) {
        return builder.bluetoothScanDurationMs;
    }

    static /* synthetic */ int access$1300(LocationPackageRequestParams.Builder builder) {
        return builder.bluetoothMaxScanResults;
    }

    static /* synthetic */ long access$1400(LocationPackageRequestParams.Builder builder) {
        return builder.bluetoothFlushResultsTimeoutMs;
    }

    static /* synthetic */ float access$200(LocationPackageRequestParams.Builder builder) {
        return builder.locationMaxAccuracyMeters;
    }

    static /* synthetic */ long access$300(LocationPackageRequestParams.Builder builder) {
        return builder.locationRequestTimeoutMs;
    }

    static /* synthetic */ long access$400(LocationPackageRequestParams.Builder builder) {
        return builder.lastLocationMaxAgeMs;
    }

    static /* synthetic */ boolean access$500(LocationPackageRequestParams.Builder builder) {
        return builder.isWifiScanEnabled;
    }

    static /* synthetic */ long access$600(LocationPackageRequestParams.Builder builder) {
        return builder.wifiScanMaxAgeMs;
    }

    static /* synthetic */ int access$700(LocationPackageRequestParams.Builder builder) {
        return builder.wifiMaxScanResults;
    }

    static /* synthetic */ long access$800(LocationPackageRequestParams.Builder builder) {
        return builder.wifiScanTimeoutMs;
    }

    static /* synthetic */ boolean access$900(LocationPackageRequestParams.Builder builder) {
        return builder.isWifiActiveScanAllowed;
    }

    public LocationPackageRequestParams build() {
        return new LocationPackageRequestParams(this, null);
    }

    public LocationPackageRequestParams.Builder setBluetoothFlushResultsTimeoutMs(long l) {
        this.bluetoothFlushResultsTimeoutMs = l;
        return this;
    }

    public LocationPackageRequestParams.Builder setBluetoothMaxScanResults(int n) {
        this.bluetoothMaxScanResults = n;
        return this;
    }

    public LocationPackageRequestParams.Builder setBluetoothScanDurationMs(long l) {
        this.bluetoothScanDurationMs = l;
        return this;
    }

    public LocationPackageRequestParams.Builder setBluetoothScanEnabled(boolean bl) {
        this.isBluetoothScanEnabled = bl;
        return this;
    }

    public LocationPackageRequestParams.Builder setLastLocationMaxAgeMs(long l) {
        this.lastLocationMaxAgeMs = l;
        return this;
    }

    public LocationPackageRequestParams.Builder setLocationMaxAccuracyMeters(float f) {
        this.locationMaxAccuracyMeters = f;
        return this;
    }

    public LocationPackageRequestParams.Builder setLocationProviders(String[] arrstring) {
        this.locationProviders = arrstring;
        return this;
    }

    public LocationPackageRequestParams.Builder setLocationRequestTimeoutMs(long l) {
        this.locationRequestTimeoutMs = l;
        return this;
    }

    public LocationPackageRequestParams.Builder setLocationScanEnabled(boolean bl) {
        this.isLocationScanEnabled = bl;
        return this;
    }

    public LocationPackageRequestParams.Builder setWifiActiveScanAllowed(boolean bl) {
        this.isWifiActiveScanAllowed = bl;
        return this;
    }

    public LocationPackageRequestParams.Builder setWifiActiveScanForced(boolean bl) {
        this.isWifiActiveScanForced = bl;
        return this;
    }

    public LocationPackageRequestParams.Builder setWifiMaxScanResults(int n) {
        this.wifiMaxScanResults = n;
        return this;
    }

    public LocationPackageRequestParams.Builder setWifiScanEnabled(boolean bl) {
        this.isWifiScanEnabled = bl;
        return this;
    }

    public LocationPackageRequestParams.Builder setWifiScanMaxAgeMs(long l) {
        this.wifiScanMaxAgeMs = l;
        return this;
    }

    public LocationPackageRequestParams.Builder setWifiScanTimeoutMs(long l) {
        this.wifiScanTimeoutMs = l;
        return this;
    }
}
