/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
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
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.widget.LikeView;

private class LikeActionController.PublishUnlikeRequestWrapper
extends LikeActionController.AbstractRequestWrapper {
    private String unlikeToken;

    LikeActionController.PublishUnlikeRequestWrapper(String string) {
        super(LikeActionController.this, null, null);
        this.unlikeToken = string;
        this.setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), string, null, HttpMethod.DELETE));
    }

    @Override
    protected void processError(FacebookRequestError facebookRequestError) {
        Logger.log(LoggingBehavior.REQUESTS, TAG, "Error unliking object with unlike token '%s' : %s", this.unlikeToken, facebookRequestError);
        LikeActionController.this.logAppEventForError("publish_unlike", facebookRequestError);
    }

    @Override
    protected void processSuccess(GraphResponse graphResponse) {
    }
}
