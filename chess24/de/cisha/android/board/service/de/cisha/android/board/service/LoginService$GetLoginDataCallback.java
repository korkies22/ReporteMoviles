/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.IProfileDataService;
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

private class LoginService.GetLoginDataCallback
extends LoadCommandCallbackWithTimeout<UserLoginData> {
    private LoadCommandCallback<UserLoginData> _delegateCallback;
    private String _optionalTrackingLabel;

    public LoginService.GetLoginDataCallback(LoadCommandCallback<UserLoginData> loadCommandCallback, String string) {
        this._delegateCallback = loadCommandCallback;
        this._optionalTrackingLabel = string;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        LoginService.this._loginInfo._authToken = null;
        this._delegateCallback.loadingFailed(aPIStatusCode, string, list, null);
        LoginService.this.saveLoginInformation();
        LoginService.this.notifyLoginObserver();
    }

    @Override
    protected void succeded(final UserLoginData userLoginData) {
        LoginService.this._loginInfo.resetUser();
        LoginService.this._loginInfo._authToken = userLoginData.getAuthToken();
        LoginService.this._loginInfo._uuid = userLoginData.getUserId();
        LoginService.this.saveLoginInformation();
        ServiceProvider.getInstance().getProfileDataService().getUserData((LoadCommandCallback<User>)new LoadCommandCallbackWithTimeout<User>(){

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
                GetLoginDataCallback.this._delegateCallback.loadingSucceded(userLoginData);
                ServiceProvider.getInstance().getTrackingService().trackUserLogin(userLoginData.getUserId(), GetLoginDataCallback.this._optionalTrackingLabel);
            }
        });
    }

}
