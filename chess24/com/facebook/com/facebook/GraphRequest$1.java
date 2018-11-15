/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.facebook;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONObject;

static final class GraphRequest
implements GraphRequest.Callback {
    final /* synthetic */ GraphRequest.GraphJSONObjectCallback val$callback;

    GraphRequest(GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback) {
        this.val$callback = graphJSONObjectCallback;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        if (this.val$callback != null) {
            this.val$callback.onCompleted(graphResponse.getJSONObject(), graphResponse);
        }
    }
}
