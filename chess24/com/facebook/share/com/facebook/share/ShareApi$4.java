/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.share;

import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.internal.ShareInternalUtility;
import org.json.JSONObject;

class ShareApi
implements GraphRequest.Callback {
    final /* synthetic */ FacebookCallback val$callback;

    ShareApi(FacebookCallback facebookCallback) {
        this.val$callback = facebookCallback;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        Object object = graphResponse.getJSONObject();
        object = object == null ? null : object.optString("id");
        ShareInternalUtility.invokeCallbackWithResults(this.val$callback, (String)object, graphResponse);
    }
}
