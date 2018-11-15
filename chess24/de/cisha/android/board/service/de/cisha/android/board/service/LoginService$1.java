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
    final /* synthetic */ String val$email;
    final /* synthetic */ String val$password;

    LoginService(LoadCommandCallback loadCommandCallback, String string, String string2) {
        this.val$callback = loadCommandCallback;
        this.val$email = string;
        this.val$password = string2;
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> object, JSONObject object2) {
        object = this.val$callback;
        object2 = new StringBuilder();
        object2.append("Could not login by user name and password because DUUID could not be retrieved. Message was:");
        object2.append(string);
        object.loadingFailed(aPIStatusCode, object2.toString(), null, null);
    }

    @Override
    protected void succeded(CishaUUID cishaUUID) {
        if (LoginService.this.checkInternetAndDuuid(cishaUUID, this.val$callback)) {
            return;
        }
        GeneralJSONAPICommandLoader generalJSONAPICommandLoader = new GeneralJSONAPICommandLoader();
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("duuid", cishaUUID.getUuid());
        treeMap.put(de.cisha.android.board.service.LoginService.FACEBOOK_PERMISSION_EMAIL, this.val$email);
        treeMap.put("password", this.val$password);
        generalJSONAPICommandLoader.loadApiCommandPost(new LoginService.GetLoginDataCallback(LoginService.this, this.val$callback, "Credentials"), "mobileAPI/AuthenticateByLogin", treeMap, new JSONUserLoginDataParser(), false);
    }
}
