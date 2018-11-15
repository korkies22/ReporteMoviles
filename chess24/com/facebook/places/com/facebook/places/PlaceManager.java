/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.Bundle
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.places;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.internal.Utility;
import com.facebook.places.internal.BluetoothScanResult;
import com.facebook.places.internal.LocationPackage;
import com.facebook.places.internal.LocationPackageManager;
import com.facebook.places.internal.LocationPackageRequestParams;
import com.facebook.places.internal.ScannerException;
import com.facebook.places.internal.WifiScanResult;
import com.facebook.places.model.CurrentPlaceFeedbackRequestParams;
import com.facebook.places.model.CurrentPlaceRequestParams;
import com.facebook.places.model.PlaceInfoRequestParams;
import com.facebook.places.model.PlaceSearchRequestParams;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceManager {
    private static final String CURRENT_PLACE_FEEDBACK = "current_place/feedback";
    private static final String CURRENT_PLACE_RESULTS = "current_place/results";
    private static final String PARAM_ACCESS_POINTS = "access_points";
    private static final String PARAM_ACCURACY = "accuracy";
    private static final String PARAM_ALTITUDE = "altitude";
    private static final String PARAM_BLUETOOTH = "bluetooth";
    private static final String PARAM_CATEGORIES = "categories";
    private static final String PARAM_CENTER = "center";
    private static final String PARAM_COORDINATES = "coordinates";
    private static final String PARAM_CURRENT_CONNECTION = "current_connection";
    private static final String PARAM_DISTANCE = "distance";
    private static final String PARAM_ENABLED = "enabled";
    private static final String PARAM_FIELDS = "fields";
    private static final String PARAM_FREQUENCY = "frequency";
    private static final String PARAM_HEADING = "heading";
    private static final String PARAM_LATITUDE = "latitude";
    private static final String PARAM_LIMIT = "limit";
    private static final String PARAM_LONGITUDE = "longitude";
    private static final String PARAM_MAC_ADDRESS = "mac_address";
    private static final String PARAM_MIN_CONFIDENCE_LEVEL = "min_confidence_level";
    private static final String PARAM_PAYLOAD = "payload";
    private static final String PARAM_PLACE_ID = "place_id";
    private static final String PARAM_Q = "q";
    private static final String PARAM_RSSI = "rssi";
    private static final String PARAM_SCANS = "scans";
    private static final String PARAM_SIGNAL_STRENGTH = "signal_strength";
    private static final String PARAM_SPEED = "speed";
    private static final String PARAM_SSID = "ssid";
    private static final String PARAM_SUMMARY = "summary";
    private static final String PARAM_TRACKING = "tracking";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_WAS_HERE = "was_here";
    private static final String PARAM_WIFI = "wifi";
    private static final String SEARCH = "search";

    private PlaceManager() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Bundle getCurrentPlaceParameters(CurrentPlaceRequestParams object, LocationPackage locationPackage) throws FacebookException {
        if (object == null) {
            throw new FacebookException("Request and location must be specified.");
        }
        LocationPackage locationPackage2 = locationPackage;
        if (locationPackage == null) {
            locationPackage2 = new LocationPackage();
        }
        if (locationPackage2.location == null) {
            locationPackage2.location = object.getLocation();
        }
        if (locationPackage2.location == null) {
            throw new FacebookException("A location must be specified");
        }
        try {
            Object object2;
            locationPackage = new Bundle(6);
            locationPackage.putString(PARAM_SUMMARY, PARAM_TRACKING);
            int n = object.getLimit();
            if (n > 0) {
                locationPackage.putInt(PARAM_LIMIT, n);
            }
            if ((object2 = object.getFields()) != null && !object2.isEmpty()) {
                locationPackage.putString(PARAM_FIELDS, TextUtils.join((CharSequence)",", (Iterable)object2));
            }
            object2 = locationPackage2.location;
            Object object3 = new JSONObject();
            object3.put(PARAM_LATITUDE, object2.getLatitude());
            object3.put(PARAM_LONGITUDE, object2.getLongitude());
            if (object2.hasAccuracy()) {
                object3.put(PARAM_ACCURACY, (double)object2.getAccuracy());
            }
            if (object2.hasAltitude()) {
                object3.put(PARAM_ALTITUDE, object2.getAltitude());
            }
            if (object2.hasBearing()) {
                object3.put(PARAM_HEADING, (double)object2.getBearing());
            }
            if (object2.hasSpeed()) {
                object3.put(PARAM_SPEED, (double)object2.getSpeed());
            }
            locationPackage.putString(PARAM_COORDINATES, object3.toString());
            object = object.getMinConfidenceLevel();
            if (object == CurrentPlaceRequestParams.ConfidenceLevel.LOW || object == CurrentPlaceRequestParams.ConfidenceLevel.MEDIUM || object == CurrentPlaceRequestParams.ConfidenceLevel.HIGH) {
                locationPackage.putString(PARAM_MIN_CONFIDENCE_LEVEL, object.toString().toLowerCase(Locale.US));
            }
            if (locationPackage2 != null) {
                object = new JSONObject();
                object.put(PARAM_ENABLED, locationPackage2.isWifiScanningEnabled);
                object2 = locationPackage2.connectedWifi;
                if (object2 != null) {
                    object.put(PARAM_CURRENT_CONNECTION, (Object)PlaceManager.getWifiScanJson((WifiScanResult)object2));
                }
                if ((object3 = locationPackage2.ambientWifi) != null) {
                    object2 = new JSONArray();
                    object3 = object3.iterator();
                    while (object3.hasNext()) {
                        object2.put((Object)PlaceManager.getWifiScanJson((WifiScanResult)object3.next()));
                    }
                    object.put(PARAM_ACCESS_POINTS, object2);
                }
                locationPackage.putString(PARAM_WIFI, object.toString());
                object = new JSONObject();
                object.put(PARAM_ENABLED, locationPackage2.isBluetoothScanningEnabled);
                object2 = locationPackage2.ambientBluetoothLe;
                if (object2 != null) {
                    locationPackage2 = new JSONArray();
                    object2 = object2.iterator();
                    while (object2.hasNext()) {
                        object3 = (BluetoothScanResult)object2.next();
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(PARAM_PAYLOAD, (Object)object3.payload);
                        jSONObject.put(PARAM_RSSI, object3.rssi);
                        locationPackage2.put((Object)jSONObject);
                    }
                    object.put(PARAM_SCANS, (Object)locationPackage2);
                }
                locationPackage.putString(PARAM_BLUETOOTH, object.toString());
            }
            return locationPackage;
        }
        catch (JSONException jSONException) {
            throw new FacebookException((Throwable)jSONException);
        }
    }

    private static LocationError getLocationError(ScannerException.Type type) {
        if (type == ScannerException.Type.PERMISSION_DENIED) {
            return LocationError.LOCATION_PERMISSION_DENIED;
        }
        if (type == ScannerException.Type.DISABLED) {
            return LocationError.LOCATION_SERVICES_DISABLED;
        }
        if (type == ScannerException.Type.TIMEOUT) {
            return LocationError.LOCATION_TIMEOUT;
        }
        return LocationError.UNKNOWN_ERROR;
    }

    private static JSONObject getWifiScanJson(WifiScanResult wifiScanResult) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(PARAM_MAC_ADDRESS, (Object)wifiScanResult.bssid);
        jSONObject.put(PARAM_SSID, (Object)wifiScanResult.ssid);
        jSONObject.put(PARAM_SIGNAL_STRENGTH, wifiScanResult.rssi);
        jSONObject.put(PARAM_FREQUENCY, wifiScanResult.frequency);
        return jSONObject;
    }

    public static GraphRequest newCurrentPlaceFeedbackRequest(CurrentPlaceFeedbackRequestParams object) {
        String string = object.getPlaceId();
        String string2 = object.getTracking();
        object = object.wasHere();
        if (string2 != null && string != null && object != null) {
            Bundle bundle = new Bundle(3);
            bundle.putString(PARAM_TRACKING, string2);
            bundle.putString(PARAM_PLACE_ID, string);
            bundle.putBoolean(PARAM_WAS_HERE, object.booleanValue());
            return new GraphRequest(AccessToken.getCurrentAccessToken(), CURRENT_PLACE_FEEDBACK, bundle, HttpMethod.POST);
        }
        throw new FacebookException("tracking, placeId and wasHere must be specified.");
    }

    public static void newCurrentPlaceRequest(final CurrentPlaceRequestParams currentPlaceRequestParams, final OnRequestReadyCallback onRequestReadyCallback) {
        Location location = currentPlaceRequestParams.getLocation();
        CurrentPlaceRequestParams.ScanMode scanMode = currentPlaceRequestParams.getScanMode();
        LocationPackageRequestParams.Builder builder = new LocationPackageRequestParams.Builder();
        boolean bl = location == null;
        builder.setLocationScanEnabled(bl);
        if (scanMode != null && scanMode == CurrentPlaceRequestParams.ScanMode.LOW_LATENCY) {
            builder.setWifiActiveScanAllowed(false);
        }
        LocationPackageManager.requestLocationPackage(builder.build(), new LocationPackageManager.Listener(){

            @Override
            public void onLocationPackage(LocationPackage object) {
                if (object.locationError != null) {
                    onRequestReadyCallback.onLocationError(PlaceManager.getLocationError(object.locationError));
                    return;
                }
                object = PlaceManager.getCurrentPlaceParameters(currentPlaceRequestParams, (LocationPackage)object);
                object = new GraphRequest(AccessToken.getCurrentAccessToken(), PlaceManager.CURRENT_PLACE_RESULTS, (Bundle)object, HttpMethod.GET);
                onRequestReadyCallback.onRequestReady((GraphRequest)object);
            }
        });
    }

    public static GraphRequest newPlaceInfoRequest(PlaceInfoRequestParams object) {
        String string = object.getPlaceId();
        if (string == null) {
            throw new FacebookException("placeId must be specified.");
        }
        Bundle bundle = new Bundle(1);
        if ((object = object.getFields()) != null && !object.isEmpty()) {
            bundle.putString(PARAM_FIELDS, TextUtils.join((CharSequence)",", (Iterable)object));
        }
        return new GraphRequest(AccessToken.getCurrentAccessToken(), string, bundle, HttpMethod.GET);
    }

    public static void newPlaceSearchRequest(final PlaceSearchRequestParams placeSearchRequestParams, final OnRequestReadyCallback onRequestReadyCallback) {
        LocationPackageRequestParams.Builder builder = new LocationPackageRequestParams.Builder();
        builder.setWifiScanEnabled(false);
        builder.setBluetoothScanEnabled(false);
        LocationPackageManager.requestLocationPackage(builder.build(), new LocationPackageManager.Listener(){

            @Override
            public void onLocationPackage(LocationPackage object) {
                if (object.locationError == null) {
                    object = PlaceManager.newPlaceSearchRequestForLocation(placeSearchRequestParams, object.location);
                    onRequestReadyCallback.onRequestReady((GraphRequest)object);
                    return;
                }
                onRequestReadyCallback.onLocationError(PlaceManager.getLocationError(object.locationError));
            }
        });
    }

    public static GraphRequest newPlaceSearchRequestForLocation(PlaceSearchRequestParams placeSearchRequestParams, Location object) {
        String string = placeSearchRequestParams.getSearchText();
        if (object == null && string == null) {
            throw new FacebookException("Either location or searchText must be specified.");
        }
        int n = placeSearchRequestParams.getLimit();
        Set<String> set = placeSearchRequestParams.getFields();
        Set<String> set2 = placeSearchRequestParams.getCategories();
        Bundle bundle = new Bundle(7);
        bundle.putString(PARAM_TYPE, "place");
        if (object != null) {
            bundle.putString(PARAM_CENTER, String.format(Locale.US, "%f,%f", object.getLatitude(), object.getLongitude()));
            int n2 = placeSearchRequestParams.getDistance();
            if (n2 > 0) {
                bundle.putInt(PARAM_DISTANCE, n2);
            }
        }
        if (n > 0) {
            bundle.putInt(PARAM_LIMIT, n);
        }
        if (!Utility.isNullOrEmpty(string)) {
            bundle.putString(PARAM_Q, string);
        }
        if (set2 != null && !set2.isEmpty()) {
            placeSearchRequestParams = new JSONArray();
            object = set2.iterator();
            while (object.hasNext()) {
                placeSearchRequestParams.put((Object)((String)object.next()));
            }
            bundle.putString(PARAM_CATEGORIES, placeSearchRequestParams.toString());
        }
        if (set != null && !set.isEmpty()) {
            bundle.putString(PARAM_FIELDS, TextUtils.join((CharSequence)",", set));
        }
        return new GraphRequest(AccessToken.getCurrentAccessToken(), SEARCH, bundle, HttpMethod.GET);
    }

    public static enum LocationError {
        LOCATION_PERMISSION_DENIED,
        LOCATION_SERVICES_DISABLED,
        LOCATION_TIMEOUT,
        UNKNOWN_ERROR;
        

        private LocationError() {
        }
    }

    public static interface OnRequestReadyCallback {
        public void onLocationError(LocationError var1);

        public void onRequestReady(GraphRequest var1);
    }

}
