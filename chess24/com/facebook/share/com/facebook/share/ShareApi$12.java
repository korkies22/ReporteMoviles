/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share;

import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.CollectionMapper;
import com.facebook.share.model.SharePhoto;
import org.json.JSONException;
import org.json.JSONObject;

class ShareApi
implements GraphRequest.Callback {
    final /* synthetic */ CollectionMapper.OnMapValueCompleteListener val$onPhotoStagedListener;
    final /* synthetic */ SharePhoto val$photo;

    ShareApi(CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener, SharePhoto sharePhoto) {
        this.val$onPhotoStagedListener = onMapValueCompleteListener;
        this.val$photo = sharePhoto;
    }

    @Override
    public void onCompleted(GraphResponse object) {
        Object object2 = object.getError();
        if (object2 != null) {
            String string = object2.getErrorMessage();
            object2 = string;
            if (string == null) {
                object2 = "Error staging photo.";
            }
            this.val$onPhotoStagedListener.onError(new FacebookGraphResponseException((GraphResponse)object, (String)object2));
            return;
        }
        if ((object = object.getJSONObject()) == null) {
            this.val$onPhotoStagedListener.onError(new FacebookException("Error staging photo."));
            return;
        }
        if ((object = object.optString("uri")) == null) {
            this.val$onPhotoStagedListener.onError(new FacebookException("Error staging photo."));
            return;
        }
        object2 = new JSONObject();
        try {
            object2.put("url", object);
            object2.put("user_generated", this.val$photo.getUserGenerated());
        }
        catch (JSONException jSONException) {
            Object object3 = object2 = jSONException.getLocalizedMessage();
            if (object2 == null) {
                object3 = "Error staging photo.";
            }
            this.val$onPhotoStagedListener.onError(new FacebookException((String)object3));
            return;
        }
        this.val$onPhotoStagedListener.onComplete(object2);
    }
}
