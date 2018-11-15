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
import de.cisha.android.board.user.User;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import org.json.JSONObject;

class LoginService.GetLoginDataCallback
extends LoadCommandCallbackWithTimeout<User> {
    final /* synthetic */ UserLoginData val$result;

    LoginService.GetLoginDataCallback(UserLoginData userLoginData) {
        this.val$result = userLoginData;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        LoadCommandCallback loadCommandCallback = GetLoginDataCallback.this._delegateCallback;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Loading own userData failed with message:");
        stringBuilder.append(string);
        loadCommandCallback.loadingFailed(aPIStatusCode, stringBuilder.toString(), list, jSONObject);
    }

    @Override
    protected void succeded(User user) {
        GetLoginDataCallback.this._delegateCallback.loadingSucceded(this.val$result);
        ServiceProvider.getInstance().getTrackingService().trackUserLogin(this.val$result.getUserId(), GetLoginDataCallback.this._optionalTrackingLabel);
    }
}
