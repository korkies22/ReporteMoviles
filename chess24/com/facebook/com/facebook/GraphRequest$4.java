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
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

class GraphRequest
implements GraphRequest.Callback {
    final /* synthetic */ GraphRequest.Callback val$callback;

    GraphRequest(GraphRequest.Callback callback) {
        this.val$callback = callback;
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        Object object = graphResponse.getJSONObject();
        object = object != null ? object.optJSONObject(com.facebook.GraphRequest.DEBUG_KEY) : null;
        JSONArray jSONArray = object != null ? object.optJSONArray(com.facebook.GraphRequest.DEBUG_MESSAGES_KEY) : null;
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); ++i) {
                Object object2 = jSONArray.optJSONObject(i);
                object = object2 != null ? object2.optString(com.facebook.GraphRequest.DEBUG_MESSAGE_KEY) : null;
                CharSequence charSequence = object2 != null ? object2.optString(com.facebook.GraphRequest.DEBUG_MESSAGE_TYPE_KEY) : null;
                object2 = object2 != null ? object2.optString(com.facebook.GraphRequest.DEBUG_MESSAGE_LINK_KEY) : null;
                if (object == null || charSequence == null) continue;
                LoggingBehavior loggingBehavior = LoggingBehavior.GRAPH_API_DEBUG_INFO;
                if (charSequence.equals(com.facebook.GraphRequest.DEBUG_SEVERITY_WARNING)) {
                    loggingBehavior = LoggingBehavior.GRAPH_API_DEBUG_WARNING;
                }
                charSequence = object;
                if (!Utility.isNullOrEmpty((String)object2)) {
                    charSequence = new StringBuilder();
                    charSequence.append((String)object);
                    charSequence.append(" Link: ");
                    charSequence.append((String)object2);
                    charSequence = charSequence.toString();
                }
                Logger.log(loggingBehavior, com.facebook.GraphRequest.TAG, (String)charSequence);
            }
        }
        if (this.val$callback != null) {
            this.val$callback.onCompleted(graphResponse);
        }
    }
}
