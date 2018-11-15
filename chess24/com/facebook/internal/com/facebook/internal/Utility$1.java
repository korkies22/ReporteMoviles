/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.internal;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.ProfileInformationCache;
import com.facebook.internal.Utility;
import org.json.JSONObject;

static final class Utility
implements GraphRequest.Callback {
    final /* synthetic */ String val$accessToken;
    final /* synthetic */ Utility.GraphMeRequestWithCacheCallback val$callback;

    Utility(Utility.GraphMeRequestWithCacheCallback graphMeRequestWithCacheCallback, String string) {
        this.val$callback = graphMeRequestWithCacheCallback;
        this.val$accessToken = string;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        if (graphResponse.getError() != null) {
            this.val$callback.onFailure(graphResponse.getError().getException());
            return;
        }
        ProfileInformationCache.putProfileInformation(this.val$accessToken, graphResponse.getJSONObject());
        this.val$callback.onSuccess(graphResponse.getJSONObject());
    }
}
