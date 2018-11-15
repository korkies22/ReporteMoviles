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

private class LikeActionController.GetOGObjectLikesRequestWrapper
extends LikeActionController.AbstractRequestWrapper
implements LikeActionController.LikeRequestWrapper {
    private final String objectId;
    private boolean objectIsLiked;
    private final LikeView.ObjectType objectType;
    private String unlikeToken;

    LikeActionController.GetOGObjectLikesRequestWrapper(String string, LikeView.ObjectType objectType) {
        super(LikeActionController.this, string, objectType);
        this.objectIsLiked = LikeActionController.this.isObjectLiked;
        this.objectId = string;
        this.objectType = objectType;
        LikeActionController.this = new Bundle();
        LikeActionController.this.putString("fields", "id,application");
        LikeActionController.this.putString("object", this.objectId);
        this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", (Bundle)LikeActionController.this, HttpMethod.GET));
    }

    @Override
    public String getUnlikeToken() {
        return this.unlikeToken;
    }

    @Override
    public boolean isObjectLiked() {
        return this.objectIsLiked;
    }

    @Override
    protected void processError(FacebookRequestError facebookRequestError) {
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Error fetching like status for object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
        LikeActionController.this.logAppEventForError("get_og_object_like", facebookRequestError);
    }

    @Override
    protected void processSuccess(GraphResponse graphResponse) {
        if ((graphResponse = Utility.tryGetJSONArrayFromResponse(graphResponse.getJSONObject(), "data")) != null) {
            for (int i = 0; i < graphResponse.length(); ++i) {
                JSONObject jSONObject = graphResponse.optJSONObject(i);
                if (jSONObject == null) continue;
                this.objectIsLiked = true;
                JSONObject jSONObject2 = jSONObject.optJSONObject("application");
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (jSONObject2 == null || accessToken == null || !Utility.areObjectsEqual(accessToken.getApplicationId(), jSONObject2.optString("id"))) continue;
                this.unlikeToken = jSONObject.optString("id");
            }
        }
    }
}
