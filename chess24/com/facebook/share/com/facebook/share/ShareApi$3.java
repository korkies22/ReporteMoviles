/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook.share;

import com.facebook.FacebookCallback;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.Mutable;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.ArrayList;
import org.json.JSONObject;

class ShareApi
implements GraphRequest.Callback {
    final /* synthetic */ FacebookCallback val$callback;
    final /* synthetic */ ArrayList val$errorResponses;
    final /* synthetic */ Mutable val$requestCount;
    final /* synthetic */ ArrayList val$results;

    ShareApi(ArrayList arrayList, ArrayList arrayList2, Mutable mutable, FacebookCallback facebookCallback) {
        this.val$results = arrayList;
        this.val$errorResponses = arrayList2;
        this.val$requestCount = mutable;
        this.val$callback = facebookCallback;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        Object object = graphResponse.getJSONObject();
        if (object != null) {
            this.val$results.add(object);
        }
        if (graphResponse.getError() != null) {
            this.val$errorResponses.add(graphResponse);
        }
        this.val$requestCount.value = (Integer)this.val$requestCount.value - 1;
        if ((Integer)this.val$requestCount.value == 0) {
            if (!this.val$errorResponses.isEmpty()) {
                ShareInternalUtility.invokeCallbackWithResults(this.val$callback, null, (GraphResponse)this.val$errorResponses.get(0));
                return;
            }
            if (!this.val$results.isEmpty()) {
                object = ((JSONObject)this.val$results.get(0)).optString("id");
                ShareInternalUtility.invokeCallbackWithResults(this.val$callback, (String)object, graphResponse);
            }
        }
    }
}
