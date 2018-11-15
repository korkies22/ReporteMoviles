/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places.internal;

public class LocationPackageRequestParams {
    private static final boolean DEFAULT_BLUETOOTH_ENABLED = true;
    private static final long DEFAULT_BLUETOOTH_FLUSH_RESULTS_TIMEOUT_MS = 300L;
    private static final int DEFAULT_BLUETOOTH_MAX_SCAN_RESULTS = 25;
    private static final long DEFAULT_BLUETOOTH_SCAN_DURATION_MS = 500L;
    private static final long DEFAULT_LAST_LOCATION_MAX_AGE_MS = 60000L;
    private static final boolean DEFAULT_LOCATION_ENABLED = true;
    private static final float DEFAULT_LOCATION_MAX_ACCURACY_METERS = 100.0f;
    private static final String[] DEFAULT_LOCATION_PROVIDERS = new String[]{"network", "gps"};
    private static final long DEFAULT_LOCATION_REQUEST_TIMEOUT_MS = 30000L;
    private static final boolean DEFAULT_WIFI_ACTIVE_SCAN_ALLOWED = true;
    private static final boolean DEFAULT_WIFI_ACTIVE_SCAN_FORCED = false;
    private static final boolean DEFAULT_WIFI_ENABLED = true;
    private static final int DEFAULT_WIFI_MAX_SCAN_RESULTS = 25;
    private static final long DEFAULT_WIFI_SCAN_MAX_AGE_MS = 30000L;
    private static final long DEFAULT_WIFI_SCAN_TIMEOUT_MS = 6000L;
    private long bluetoothFlushResultsTimeoutMs;
    private int bluetoothMaxScanResults;
    private long bluetoothScanDurationMs;
    private boolean isBluetoothScanEnabled;
    private boolean isLocationScanEnabled;
    private boolean isWifiActiveScanAllowed;
    private boolean isWifiActiveScanForced;
    private boolean isWifiScanEnabled;
    private long lastLocationMaxAgeMs;
    private float locationMaxAccuracyMeters;
    private final String[] locationProviders;
    private long locationRequestTimeoutMs;
    private int wifiMaxScanResults;
    private long wifiScanMaxAgeMs;
    private long wifiScanTimeoutMs;

    private LocationPackageRequestParams(Builder builder) {
        this.isLocationScanEnabled = builder.isLocationScanEnabled;
        this.locationProviders = builder.locationProviders;
        this.locationMaxAccuracyMeters = builder.locationMaxAccuracyMeters;
        this.locationRequestTimeoutMs = builder.locationRequestTimeoutMs;
        this.lastLocationMaxAgeMs = builder.lastLocationMaxAgeMs;
        this.isWifiScanEnabled = builder.isWifiScanEnabled;
        this.wifiScanMaxAgeMs = builder.wifiScanMaxAgeMs;
        this.wifiMaxScanResults = builder.wifiMaxScanResults;
        this.wifiScanTimeoutMs = builder.wifiScanTimeoutMs;
        this.isWifiActiveScanAllowed = builder.isWifiActiveScanAllowed;
        this.isWifiActiveScanForced = builder.isWifiActiveScanForced;
        this.isBluetoothScanEnabled = builder.isBluetoothScanEnabled;
        this.bluetoothScanDurationMs = builder.bluetoothScanDurationMs;
        this.bluetoothMaxScanResults = builder.bluetoothMaxScanResults;
        this.bluetoothFlushResultsTimeoutMs = builder.bluetoothFlushResultsTimeoutMs;
    }

    static /* synthetic */ String[] access$1500() {
        return DEFAULT_LOCATION_PROVIDERS;
    }

    public long getBluetoothFlushResultsTimeoutMs() {
        return this.bluetoothFlushResultsTimeoutMs;
    }

    public int getBluetoothMaxScanResults() {
        return this.bluetoothMaxScanResults;
    }

    public long getBluetoothScanDurationMs() {
        return this.bluetoothScanDurationMs;
    }

    public long getLastLocationMaxAgeMs() {
        return this.lastLocationMaxAgeMs;
    }

    public float getLocationMaxAccuracyMeters() {
        return this.locationMaxAccuracyMeters;
    }

    public String[] getLocationProviders() {
        return this.locationProviders;
    }

    public long getLocationRequestTimeoutMs() {
        return this.locationRequestTimeoutMs;
    }

    public int getWifiMaxScanResults() {
        return this.wifiMaxScanResults;
    }

    public long getWifiScanMaxAgeMs() {
        return this.wifiScanMaxAgeMs;
    }

    public long getWifiScanTimeoutMs() {
        return this.wifiScanTimeoutMs;
    }

    public boolean isBluetoothScanEnabled() {
        return this.isBluetoothScanEnabled;
    }

    public boolean isLocationScanEnabled() {
        return this.isLocationScanEnabled;
    }

    public boolean isWifiActiveScanAllowed() {
        return this.isWifiActiveScanAllowed;
    }

    public boolean isWifiActiveScanForced() {
        return this.isWifiActiveScanForced;
    }

    public boolean isWifiScanEnabled() {
        return this.isWifiScanEnabled;
    }

    public static class Builder {
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

        public LocationPackageRequestParams build() {
            return new LocationPackageRequestParams(this);
        }

        public Builder setBluetoothFlushResultsTimeoutMs(long l) {
            this.bluetoothFlushResultsTimeoutMs = l;
            return this;
        }

        public Builder setBluetoothMaxScanResults(int n) {
            this.bluetoothMaxScanResults = n;
            return this;
        }

        public Builder setBluetoothScanDurationMs(long l) {
            this.bluetoothScanDurationMs = l;
            return this;
        }

        public Builder setBluetoothScanEnabled(boolean bl) {
            this.isBluetoothScanEnabled = bl;
            return this;
        }

        public Builder setLastLocationMaxAgeMs(long l) {
            this.lastLocationMaxAgeMs = l;
            return this;
        }

        public Builder setLocationMaxAccuracyMeters(float f) {
            this.locationMaxAccuracyMeters = f;
            return this;
        }

        public Builder setLocationProviders(String[] arrstring) {
            this.locationProviders = arrstring;
            return this;
        }

        public Builder setLocationRequestTimeoutMs(long l) {
            this.locationRequestTimeoutMs = l;
            return this;
        }

        public Builder setLocationScanEnabled(boolean bl) {
            this.isLocationScanEnabled = bl;
            return this;
        }

        public Builder setWifiActiveScanAllowed(boolean bl) {
            this.isWifiActiveScanAllowed = bl;
            return this;
        }

        public Builder setWifiActiveScanForced(boolean bl) {
            this.isWifiActiveScanForced = bl;
            return this;
        }

        public Builder setWifiMaxScanResults(int n) {
            this.wifiMaxScanResults = n;
            return this;
        }

        public Builder setWifiScanEnabled(boolean bl) {
            this.isWifiScanEnabled = bl;
            return this;
        }

        public Builder setWifiScanMaxAgeMs(long l) {
            this.wifiScanMaxAgeMs = l;
            return this;
        }

        public Builder setWifiScanTimeoutMs(long l) {
            this.wifiScanTimeoutMs = l;
            return this;
        }
    }

}
