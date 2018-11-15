/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;
import org.json.JSONObject;

private class LikeActionController.GetPageIdRequestWrapper
extends LikeActionController.AbstractRequestWrapper {
    boolean objectIsPage;
    String verifiedObjectId;

    LikeActionController.GetPageIdRequestWrapper(String string, LikeView.ObjectType objectType) {
        super(LikeActionController.this, string, objectType);
        LikeActionController.this = new Bundle();
        LikeActionController.this.putString("fields", "id");
        LikeActionController.this.putString("ids", string);
        this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "", (Bundle)LikeActionController.this, HttpMethod.GET));
    }

    @Override
    protected void processError(FacebookRequestError facebookRequestError) {
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Error getting the FB id for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
    }

    @Override
    protected void processSuccess(GraphResponse graphResponse) {
        if ((graphResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), this.objectId)) != null) {
            this.verifiedObjectId = graphResponse.optString("id");
            this.objectIsPage = Utility.isNullOrEmpty(this.verifiedObjectId) ^ true;
        }
    }
}
