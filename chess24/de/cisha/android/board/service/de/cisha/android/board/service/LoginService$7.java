/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.LoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWrapper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class LoginService
extends LoadCommandCallbackWrapper<Boolean> {
    LoginService(LoadCommandCallback loadCommandCallback) {
        super(loadCommandCallback);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        super.failed(aPIStatusCode, string, list, jSONObject);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "User registered", "error");
    }

    @Override
    protected void succeded(Boolean bl) {
        super.succeded(bl);
        ServiceProvider.getInstance().getTrackingService().trackEvent(ITrackingService.TrackingCategory.USER, "User registered", "success");
        LoginService.this._loginInfo.resetGuest();
    }
}
