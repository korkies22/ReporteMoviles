/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

static final class AccessToken
implements Utility.GraphMeRequestWithCacheCallback {
    final /* synthetic */ AccessToken.AccessTokenCreationCallback val$accessTokenCallback;
    final /* synthetic */ String val$applicationId;
    final /* synthetic */ Bundle val$extras;

    AccessToken(Bundle bundle, AccessToken.AccessTokenCreationCallback accessTokenCreationCallback, String string) {
        this.val$extras = bundle;
        this.val$accessTokenCallback = accessTokenCreationCallback;
        this.val$applicationId = string;
    }

    @Override
    public void onFailure(FacebookException facebookException) {
        this.val$accessTokenCallback.onError(facebookException);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onSuccess(JSONObject object) {
        try {
            String string = object.getString("id");
            this.val$extras.putString(com.facebook.AccessToken.USER_ID_KEY, string);
            this.val$accessTokenCallback.onSuccess(com.facebook.AccessToken.createFromBundle(null, this.val$extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), this.val$applicationId));
            return;
        }
        catch (JSONException jSONException) {}
        this.val$accessTokenCallback.onError(new FacebookException("Unable to generate access token due to missing user id"));
    }
}
