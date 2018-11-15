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
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

class DeviceAuthDialog
implements GraphRequest.Callback {
    DeviceAuthDialog() {
    }

    @Override
    public void onCompleted(GraphResponse graphResponse) {
        if (DeviceAuthDialog.this.completed.get()) {
            return;
        }
        FacebookRequestError facebookRequestError = graphResponse.getError();
        if (facebookRequestError != null) {
            int n = facebookRequestError.getSubErrorCode();
            if (n != 1349152) {
                switch (n) {
                    default: {
                        DeviceAuthDialog.this.onError(graphResponse.getError().getException());
                        return;
                    }
                    case 1349172: 
                    case 1349174: {
                        DeviceAuthDialog.this.schedulePoll();
                        return;
                    }
                    case 1349173: 
                }
            }
            DeviceAuthDialog.this.onCancel();
            return;
        }
        try {
            graphResponse = graphResponse.getJSONObject();
            DeviceAuthDialog.this.onSuccess(graphResponse.getString("access_token"));
            return;
        }
        catch (JSONException jSONException) {
            DeviceAuthDialog.this.onError(new FacebookException((Throwable)jSONException));
            return;
        }
    }
}
