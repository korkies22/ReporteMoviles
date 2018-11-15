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
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.SmartLoginOption;
import com.facebook.internal.Utility;
import com.facebook.login.DeviceAuthDialog;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

class DeviceAuthDialog
implements GraphRequest.Callback {
    final /* synthetic */ String val$accessToken;

    DeviceAuthDialog(String string) {
        this.val$accessToken = string;
    }

    @Override
    public void onCompleted(GraphResponse object) {
        Utility.PermissionsPair permissionsPair;
        Object object2;
        if (DeviceAuthDialog.this.completed.get()) {
            return;
        }
        if (object.getError() != null) {
            DeviceAuthDialog.this.onError(object.getError().getException());
            return;
        }
        try {
            object2 = object.getJSONObject();
            object = object2.getString("id");
            permissionsPair = Utility.handlePermissionResponse(object2);
            object2 = object2.getString("name");
        }
        catch (JSONException jSONException) {
            DeviceAuthDialog.this.onError(new FacebookException((Throwable)jSONException));
            return;
        }
        DeviceRequestsHelper.cleanUpAdvertisementService(DeviceAuthDialog.this.currentRequestState.getUserCode());
        if (FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId()).getSmartLoginOptions().contains((Object)SmartLoginOption.RequireConfirm) && !DeviceAuthDialog.this.isRetry) {
            DeviceAuthDialog.this.isRetry = true;
            DeviceAuthDialog.this.presentConfirmation((String)object, permissionsPair, this.val$accessToken, (String)object2);
            return;
        }
        DeviceAuthDialog.this.completeLogin((String)object, permissionsPair, this.val$accessToken);
    }
}
