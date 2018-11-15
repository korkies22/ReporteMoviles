/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class LoginActivity
extends LoadCommandCallbackWithTimeout<UserLoginData> {
    LoginActivity() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        5.this.this$0.hideDialogWaiting();
        5.this.this$0.displayErrors(list, 5.this.this$0.getString(2131690031));
    }

    @Override
    protected void succeded(UserLoginData userLoginData) {
        5.this.this$0.hideDialogWaiting();
        5.this.this$0.loginSucceeded();
    }
}
