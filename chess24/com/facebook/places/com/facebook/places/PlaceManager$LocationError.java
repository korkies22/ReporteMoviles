/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places;

import com.facebook.places.PlaceManager;

public static enum PlaceManager.LocationError {
    LOCATION_PERMISSION_DENIED,
    LOCATION_SERVICES_DISABLED,
    LOCATION_TIMEOUT,
    UNKNOWN_ERROR;
    

    private PlaceManager.LocationError() {
    }
}
