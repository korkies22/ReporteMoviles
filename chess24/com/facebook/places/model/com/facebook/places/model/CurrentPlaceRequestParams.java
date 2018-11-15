/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.facebook.places.model;

import android.location.Location;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CurrentPlaceRequestParams {
    private final Set<String> fields = new HashSet<String>();
    private final int limit;
    private final Location location;
    private final ConfidenceLevel minConfidenceLevel;
    private final ScanMode scanMode;

    private CurrentPlaceRequestParams(Builder builder) {
        this.location = builder.location;
        this.scanMode = builder.scanMode;
        this.minConfidenceLevel = builder.minConfidenceLevel;
        this.limit = builder.limit;
        this.fields.addAll(builder.fields);
    }

    public Set<String> getFields() {
        return this.fields;
    }

    public int getLimit() {
        return this.limit;
    }

    public Location getLocation() {
        return this.location;
    }

    public ConfidenceLevel getMinConfidenceLevel() {
        return this.minConfidenceLevel;
    }

    public ScanMode getScanMode() {
        return this.scanMode;
    }

    public static class Builder {
        private final Set<String> fields = new HashSet<String>();
        private int limit;
        private Location location;
        private ConfidenceLevel minConfidenceLevel;
        private ScanMode scanMode = ScanMode.HIGH_ACCURACY;

        public Builder addField(String string) {
            this.fields.add(string);
            return this;
        }

        public CurrentPlaceRequestParams build() {
            return new CurrentPlaceRequestParams(this);
        }

        public Builder setLimit(int n) {
            this.limit = n;
            return this;
        }

        public Builder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder setMinConfidenceLevel(ConfidenceLevel confidenceLevel) {
            this.minConfidenceLevel = confidenceLevel;
            return this;
        }

        public Builder setScanMode(ScanMode scanMode) {
            this.scanMode = scanMode;
            return this;
        }
    }

    public static enum ConfidenceLevel {
        LOW,
        MEDIUM,
        HIGH;
        

        private ConfidenceLevel() {
        }
    }

    public static enum ScanMode {
        HIGH_ACCURACY,
        LOW_LATENCY;
        

        private ScanMode() {
        }
    }

}
