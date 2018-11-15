/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.LoginService;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class LoginService
extends LoginService.GetLoginDataCallback {
    LoginService(LoadCommandCallback loadCommandCallback, String string) {
        super(4.this.this$0, loadCommandCallback, string);
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        4.this.this$0._loginInfo.resetUser();
        4.this.this$0._loginInfo.resetGuest();
        4.this.this$0.saveLoginInformation();
        4.this.this$0.authenticateAsGuest(4.this.val$callback);
    }
}
