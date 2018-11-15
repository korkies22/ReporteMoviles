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

private class LikeActionController.GetPageLikesRequestWrapper
extends LikeActionController.AbstractRequestWrapper
implements LikeActionController.LikeRequestWrapper {
    private boolean objectIsLiked;
    private String pageId;

    LikeActionController.GetPageLikesRequestWrapper(String string) {
        super(LikeActionController.this, string, LikeView.ObjectType.PAGE);
        this.objectIsLiked = LikeActionController.this.isObjectLiked;
        this.pageId = string;
        LikeActionController.this = new Bundle();
        LikeActionController.this.putString("fields", "id");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("me/likes/");
        stringBuilder.append(string);
        this.setRequest(new GraphRequest(accessToken, stringBuilder.toString(), (Bundle)LikeActionController.this, HttpMethod.GET));
    }

    @Override
    public String getUnlikeToken() {
        return null;
    }

    @Override
    public boolean isObjectLiked() {
        return this.objectIsLiked;
    }

    @Override
    protected void processError(FacebookRequestError facebookRequestError) {
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Error fetching like status for page id '%s': %s", this.pageId, facebookRequestError);
        LikeActionController.this.logAppEventForError("get_page_like", facebookRequestError);
    }

    @Override
    protected void processSuccess(GraphResponse graphResponse) {
        if ((graphResponse = Utility.tryGetJSONArrayFromResponse(graphResponse.getJSONObject(), "data")) != null && graphResponse.length() > 0) {
            this.objectIsLiked = true;
        }
    }
}
