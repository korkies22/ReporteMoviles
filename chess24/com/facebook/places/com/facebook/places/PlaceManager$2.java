/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.places;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.places.PlaceManager;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationPackageManager;
import com.facebook.places.internal.ScannerException;
import com.facebook.places.model.CurrentPlaceRequestParams;

static final class PlaceManager
implements LocationPackageManager.Listener {
    final /* synthetic */ PlaceManager.OnRequestReadyCallback val$callback;
    final /* synthetic */ CurrentPlaceRequestParams val$requestParams;

    PlaceManager(PlaceManager.OnRequestReadyCallback onRequestReadyCallback, CurrentPlaceRequestParams currentPlaceRequestParams) {
        this.val$callback = onRequestReadyCallback;
        this.val$requestParams = currentPlaceRequestParams;
    }

    @Override
    public void onLocationPackage(LocationPackage object) {
        if (object.locationError != null) {
            this.val$callback.onLocationError(com.facebook.places.PlaceManager.getLocationError(object.locationError));
            return;
        }
        object = com.facebook.places.PlaceManager.getCurrentPlaceParameters(this.val$requestParams, (LocationPackage)object);
        object = new GraphRequest(AccessToken.getCurrentAccessToken(), com.facebook.places.PlaceManager.CURRENT_PLACE_RESULTS, (Bundle)object, HttpMethod.GET);
        this.val$callback.onRequestReady((GraphRequest)object);
    }
}
