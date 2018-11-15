/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.share;

import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.CollectionMapper;
import org.json.JSONObject;

class ShareApi
implements GraphRequest.Callback {
    final /* synthetic */ CollectionMapper.OnMapValueCompleteListener val$onOpenGraphObjectStagedListener;

    ShareApi(CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        this.val$onOpenGraphObjectStagedListener = onMapValueCompleteListener;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        Object object = graphResponse.getError();
        if (object != null) {
            String string = object.getErrorMessage();
            object = string;
            if (string == null) {
                object = "Error staging Open Graph object.";
            }
            this.val$onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(graphResponse, (String)object));
            return;
        }
        object = graphResponse.getJSONObject();
        if (object == null) {
            this.val$onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
            return;
        }
        if ((object = object.optString("id")) == null) {
            this.val$onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(graphResponse, "Error staging Open Graph object."));
            return;
        }
        this.val$onOpenGraphObjectStagedListener.onComplete(object);
    }
}
