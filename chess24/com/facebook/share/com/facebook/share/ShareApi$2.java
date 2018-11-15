/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.facebook.share;

import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareOpenGraphAction;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

class ShareApi
implements CollectionMapper.OnMapperCompleteListener {
    final /* synthetic */ ShareOpenGraphAction val$action;
    final /* synthetic */ FacebookCallback val$callback;
    final /* synthetic */ Bundle val$parameters;
    final /* synthetic */ GraphRequest.Callback val$requestCallback;

    ShareApi(Bundle bundle, ShareOpenGraphAction shareOpenGraphAction, GraphRequest.Callback callback, FacebookCallback facebookCallback) {
        this.val$parameters = bundle;
        this.val$action = shareOpenGraphAction;
        this.val$requestCallback = callback;
        this.val$callback = facebookCallback;
    }

    @Override
    public void onComplete() {
        try {
            com.facebook.share.ShareApi.handleImagesOnAction(this.val$parameters);
            new GraphRequest(AccessToken.getCurrentAccessToken(), ShareApi.this.getGraphPath(URLEncoder.encode(this.val$action.getActionType(), com.facebook.share.ShareApi.DEFAULT_CHARSET)), this.val$parameters, HttpMethod.POST, this.val$requestCallback).executeAsync();
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            ShareInternalUtility.invokeCallbackWithException(this.val$callback, unsupportedEncodingException);
            return;
        }
    }

    @Override
    public void onError(FacebookException facebookException) {
        ShareInternalUtility.invokeCallbackWithException(this.val$callback, facebookException);
    }
}
