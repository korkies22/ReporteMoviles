/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 */
package com.facebook.places;

import android.location.Location;
import com.facebook.GraphRequest;
import com.facebook.places.PlaceManager;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationPackageManager;
import com.facebook.places.internal.ScannerException;
import com.facebook.places.model.PlaceSearchRequestParams;

static final class PlaceManager
implements LocationPackageManager.Listener {
    final /* synthetic */ PlaceManager.OnRequestReadyCallback val$callback;
    final /* synthetic */ PlaceSearchRequestParams val$requestParams;

    PlaceManager(PlaceSearchRequestParams placeSearchRequestParams, PlaceManager.OnRequestReadyCallback onRequestReadyCallback) {
        this.val$requestParams = placeSearchRequestParams;
        this.val$callback = onRequestReadyCallback;
    }

    @Override
    public void onLocationPackage(LocationPackage object) {
        if (object.locationError == null) {
            object = com.facebook.places.PlaceManager.newPlaceSearchRequestForLocation(this.val$requestParams, object.location);
            this.val$callback.onRequestReady((GraphRequest)object);
            return;
        }
        this.val$callback.onLocationError(com.facebook.places.PlaceManager.getLocationError(object.locationError));
    }
}
