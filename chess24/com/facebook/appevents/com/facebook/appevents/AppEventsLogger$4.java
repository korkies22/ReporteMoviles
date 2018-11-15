/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONArray
 *  org.json.JSONException
 */
package com.facebook.appevents;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.HttpMethod;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.BundleJSONConverter;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

static final class AppEventsLogger
implements Runnable {
    final /* synthetic */ String val$applicationID;
    final /* synthetic */ GraphRequest.Callback val$callback;
    final /* synthetic */ Bundle val$parameters;
    final /* synthetic */ String val$userID;

    AppEventsLogger(String string, Bundle bundle, String string2, GraphRequest.Callback callback) {
        this.val$userID = string;
        this.val$parameters = bundle;
        this.val$applicationID = string2;
        this.val$callback = callback;
    }

    @Override
    public void run() {
        Bundle bundle = new Bundle();
        bundle.putString("user_unique_id", this.val$userID);
        bundle.putBundle("custom_data", this.val$parameters);
        Object object = AttributionIdentifiers.getAttributionIdentifiers(FacebookSdk.getApplicationContext());
        if (object != null && object.getAndroidAdvertiserId() != null) {
            bundle.putString("advertiser_id", object.getAndroidAdvertiserId());
        }
        object = new Bundle();
        try {
            bundle = BundleJSONConverter.convertToJSON(bundle);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put((Object)bundle);
            object.putString("data", jSONArray.toString());
        }
        catch (JSONException jSONException) {
            throw new FacebookException("Failed to construct request", (Throwable)jSONException);
        }
        object = new GraphRequest(AccessToken.getCurrentAccessToken(), String.format(Locale.US, "%s/user_properties", this.val$applicationID), (Bundle)object, HttpMethod.POST, this.val$callback);
        object.setSkipClientToken(true);
        object.executeAsync();
    }
}
