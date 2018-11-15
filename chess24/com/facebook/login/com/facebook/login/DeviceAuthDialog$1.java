/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.login;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.DeviceAuthDialog;
import org.json.JSONException;
import org.json.JSONObject;

class DeviceAuthDialog
implements GraphRequest.Callback {
    DeviceAuthDialog() {
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        if (DeviceAuthDialog.this.isBeingDestroyed) {
            return;
        }
        if (graphResponse.getError() != null) {
            DeviceAuthDialog.this.onError(graphResponse.getError().getException());
            return;
        }
        graphResponse = graphResponse.getJSONObject();
        DeviceAuthDialog.RequestState requestState = new DeviceAuthDialog.RequestState();
        try {
            requestState.setUserCode(graphResponse.getString("user_code"));
            requestState.setRequestCode(graphResponse.getString("code"));
            requestState.setInterval(graphResponse.getLong("interval"));
        }
        catch (JSONException jSONException) {
            DeviceAuthDialog.this.onError(new FacebookException((Throwable)jSONException));
            return;
        }
        DeviceAuthDialog.this.setCurrentRequestState(requestState);
    }
}
