/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.facebook.places.model;

import android.location.Location;
import com.facebook.places.model.CurrentPlaceRequestParams;
import java.util.HashSet;
import java.util.Set;

public static class CurrentPlaceRequestParams.Builder {
    private final Set<String> fields = new HashSet<String>();
    private int limit;
    private Location location;
    private CurrentPlaceRequestParams.ConfidenceLevel minConfidenceLevel;
    private CurrentPlaceRequestParams.ScanMode scanMode = CurrentPlaceRequestParams.ScanMode.HIGH_ACCURACY;

    static /* synthetic */ Location access$000(CurrentPlaceRequestParams.Builder builder) {
        return builder.location;
    }

    static /* synthetic */ CurrentPlaceRequestParams.ScanMode access$100(CurrentPlaceRequestParams.Builder builder) {
        return builder.scanMode;
    }

    static /* synthetic */ CurrentPlaceRequestParams.ConfidenceLevel access$200(CurrentPlaceRequestParams.Builder builder) {
        return builder.minConfidenceLevel;
    }

    static /* synthetic */ int access$300(CurrentPlaceRequestParams.Builder builder) {
        return builder.limit;
    }

    static /* synthetic */ Set access$400(CurrentPlaceRequestParams.Builder builder) {
        return builder.fields;
    }

    public CurrentPlaceRequestParams.Builder addField(String string) {
        this.fields.add(string);
        return this;
    }

    public CurrentPlaceRequestParams build() {
        return new CurrentPlaceRequestParams(this, null);
    }

    public CurrentPlaceRequestParams.Builder setLimit(int n) {
        this.limit = n;
        return this;
    }

    public CurrentPlaceRequestParams.Builder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public CurrentPlaceRequestParams.Builder setMinConfidenceLevel(CurrentPlaceRequestParams.ConfidenceLevel confidenceLevel) {
        this.minConfidenceLevel = confidenceLevel;
        return this;
    }

    public CurrentPlaceRequestParams.Builder setScanMode(CurrentPlaceRequestParams.ScanMode scanMode) {
        this.scanMode = scanMode;
        return this;
    }
}
