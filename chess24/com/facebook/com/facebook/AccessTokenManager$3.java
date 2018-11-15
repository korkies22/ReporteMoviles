/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook;

import com.facebook.AccessTokenManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONObject;

class AccessTokenManager
implements GraphRequest.Callback {
    final /* synthetic */ AccessTokenManager.RefreshResult val$refreshResult;

    AccessTokenManager(AccessTokenManager.RefreshResult refreshResult) {
        this.val$refreshResult = refreshResult;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        if ((graphResponse = graphResponse.getJSONObject()) == null) {
            return;
        }
        this.val$refreshResult.accessToken = graphResponse.optString("access_token");
        this.val$refreshResult.expiresAt = graphResponse.optInt("expires_at");
    }
}
