/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.share.internal;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.share.internal.DeviceShareDialogFragment;
import org.json.JSONException;
import org.json.JSONObject;

class DeviceShareDialogFragment
implements GraphRequest.Callback {
    DeviceShareDialogFragment() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onCompleted(GraphResponse graphResponse) {
        Object object = graphResponse.getError();
        if (object != null) {
            DeviceShareDialogFragment.this.finishActivityWithError((FacebookRequestError)object);
            return;
        }
        graphResponse = graphResponse.getJSONObject();
        object = new DeviceShareDialogFragment.RequestState();
        try {
            object.setUserCode(graphResponse.getString("user_code"));
            object.setExpiresIn(graphResponse.getLong("expires_in"));
        }
        catch (JSONException jSONException) {}
        DeviceShareDialogFragment.this.setCurrentRequestState((DeviceShareDialogFragment.RequestState)object);
        return;
        DeviceShareDialogFragment.this.finishActivityWithError(new FacebookRequestError(0, "", "Malformed server response"));
    }
}
