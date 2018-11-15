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

private class LikeActionController.PublishLikeRequestWrapper
extends LikeActionController.AbstractRequestWrapper {
    String unlikeToken;

    LikeActionController.PublishLikeRequestWrapper(String string, LikeView.ObjectType objectType) {
        super(LikeActionController.this, string, objectType);
        LikeActionController.this = new Bundle();
        LikeActionController.this.putString("object", string);
        this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", (Bundle)LikeActionController.this, HttpMethod.POST));
    }

    @Override
    protected void processError(FacebookRequestError facebookRequestError) {
        if (facebookRequestError.getErrorCode() == 3501) {
            this.error = null;
            return;
        }
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Error liking object '%s' with type '%s' : %s", new Object[]{this.objectId, this.objectType, facebookRequestError});
        LikeActionController.this.logAppEventForError("publish_like", facebookRequestError);
    }

    @Override
    protected void processSuccess(GraphResponse graphResponse) {
        this.unlikeToken = Utility.safeGetStringFromResponse(graphResponse.getJSONObject(), "id");
    }
}
