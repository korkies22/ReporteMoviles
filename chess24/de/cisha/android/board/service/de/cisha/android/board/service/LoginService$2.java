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
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import org.json.JSONObject;

class LoginService
extends LoadCommandCallbackWithTimeout<Boolean> {
    final /* synthetic */ LoadCommandCallback val$callback;

    LoginService(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.loadingFailed(aPIStatusCode, string, null, null);
    }

    @Override
    protected void succeded(Boolean bl) {
        if (bl.booleanValue()) {
            this.val$callback.loadingSucceded(new UserLoginData(LoginService.this.getAuthToken(), LoginService.this.getUserUUID()));
            ServiceProvider.getInstance().getTrackingService().trackUserLogin(LoginService.this._loginInfo._uuid, "Authtoken");
            return;
        }
        this.val$callback.loadingFailed(APIStatusCode.API_ERROR_INVALID_AUTHTOKEN, "login not valid anymore", null, null);
    }
}
