/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.places;

import com.facebook.GraphRequest;
import com.facebook.places.PlaceManager;

public static interface PlaceManager.OnRequestReadyCallback {
    public void onLocationError(PlaceManager.LocationError var1);

    public void onRequestReady(GraphRequest var1);
}
