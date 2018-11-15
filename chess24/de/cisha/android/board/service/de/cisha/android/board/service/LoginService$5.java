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
    final /* synthetic */ String val$accessToken;
    final /* synthetic */ LoadCommandCallback val$callback;

    LoginService(String string, LoadCommandCallback loadCommandCallback) {
        this.val$accessToken = string;
        this.val$callback = loadCommandCallback;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.loadingFailed(aPIStatusCode, string, list, null);
    }

    @Override
    protected void succeded(CishaUUID object) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("duuid", object.getUuid());
        treeMap.put("fbAuthToken", this.val$accessToken);
        if (LoginService.this._loginInfo._lastGuestAuthToken != null) {
            treeMap.put(de.cisha.android.board.service.LoginService.PREF_STRING_AUTH_TOKEN, LoginService.this._loginInfo._lastGuestAuthToken.getUuid());
        }
        object = new LoginService.GetLoginDataCallback(this.val$callback, "Facebook"){

            @Override
            protected void succeded(UserLoginData userLoginData) {
                LoginService.this._loginInfo.resetGuest();
                super.succeded(userLoginData);
            }
        };
        new GeneralJSONAPICommandLoader().loadApiCommandPost(object, "mobileAPI/AuthenticateByFacebook", (Map<String, String>)treeMap, new JSONUserLoginDataParser(), false);
    }

}
