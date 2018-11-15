/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONObject
 */
package com.facebook.share;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONObject;

class ShareApi
implements CollectionMapper.OnMapperCompleteListener {
    final /* synthetic */ String val$ogType;
    final /* synthetic */ CollectionMapper.OnMapValueCompleteListener val$onOpenGraphObjectStagedListener;
    final /* synthetic */ GraphRequest.Callback val$requestCallback;
    final /* synthetic */ JSONObject val$stagedObject;

    ShareApi(JSONObject jSONObject, String string, GraphRequest.Callback callback, CollectionMapper.OnMapValueCompleteListener onMapValueCompleteListener) {
        this.val$stagedObject = jSONObject;
        this.val$ogType = string;
        this.val$requestCallback = callback;
        this.val$onOpenGraphObjectStagedListener = onMapValueCompleteListener;
    }

    @Override
    public void onComplete() {
        Object object = this.val$stagedObject.toString();
        Bundle bundle = new Bundle();
        bundle.putString("object", (String)object);
        try {
            object = AccessToken.getCurrentAccessToken();
            com.facebook.share.ShareApi shareApi = ShareApi.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("objects/");
            stringBuilder.append(URLEncoder.encode(this.val$ogType, com.facebook.share.ShareApi.DEFAULT_CHARSET));
            new GraphRequest((AccessToken)object, shareApi.getGraphPath(stringBuilder.toString()), bundle, HttpMethod.POST, this.val$requestCallback).executeAsync();
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Object object2 = object = unsupportedEncodingException.getLocalizedMessage();
            if (object == null) {
                object2 = "Error staging Open Graph object.";
            }
            this.val$onOpenGraphObjectStagedListener.onError(new FacebookException((String)object2));
            return;
        }
    }

    @Override
    public void onError(FacebookException facebookException) {
        this.val$onOpenGraphObjectStagedListener.onError(facebookException);
    }
}
