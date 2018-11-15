/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.facebook;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONArray;
import org.json.JSONObject;

static final class GraphRequest
implements GraphRequest.Callback {
    final /* synthetic */ GraphRequest.GraphJSONArrayCallback val$callback;

    GraphRequest(GraphRequest.GraphJSONArrayCallback graphJSONArrayCallback) {
        this.val$callback = graphJSONArrayCallback;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        if (this.val$callback != null) {
            Object object = graphResponse.getJSONObject();
            object = object != null ? object.optJSONArray("data") : null;
            this.val$callback.onCompleted((JSONArray)object, graphResponse);
        }
    }
}
