/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.JSONUserLoginDataParser;
import de.cisha.android.board.service.LoginService;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.GeneralJSONAPICommandLoader;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.model.CishaUUID;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

class LoginService
extends LoadCommandCallbackWithTimeout<CishaUUID> {
    final /* synthetic */ LoadCommandCallback val$callback;

    LoginService(LoadCommandCallback loadCommandCallback) {
        this.val$callback = loadCommandCallback;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.loadingFailed(aPIStatusCode, string, null, jSONObject);
    }

    @Override
    protected void succeded(CishaUUID object) {
        if (LoginService.this.checkInternetAndDuuid((CishaUUID)object, this.val$callback)) {
            return;
        }
        if (LoginService.this._loginInfo._lastGuestAuthToken != null) {
            LoginService.this._loginInfo._authToken = LoginService.this._loginInfo._lastGuestAuthToken;
            LoginService.this._loginInfo._uuid = LoginService.this._loginInfo._lastGuestUuid;
            LoginService.this.verifyAuthToken(new LoginService.GetLoginDataCallback(this.val$callback, "Guest"){

                @Override
                protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                    LoginService.this._loginInfo.resetUser();
                    LoginService.this._loginInfo.resetGuest();
                    LoginService.this.saveLoginInformation();
                    LoginService.this.authenticateAsGuest(4.this.val$callback);
                }
            });
            return;
        }
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("duuid", object.getUuid());
        object = new LoginService.GetLoginDataCallback(this.val$callback, "Guest"){

            @Override
            protected void succeded(UserLoginData userLoginData) {
                LoginService.this._loginInfo._lastGuestAuthToken = userLoginData.getAuthToken();
                LoginService.this._loginInfo._lastGuestUuid = userLoginData.getUserId();
                LoginService.this.saveLoginInformation();
                super.succeded(userLoginData);
            }
        };
        new GeneralJSONAPICommandLoader().loadApiCommandPost(object, "mobileAPI/AuthenticateGuest", (Map<String, String>)treeMap, new JSONUserLoginDataParser(), false);
    }

}
