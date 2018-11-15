/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.List;
import org.json.JSONObject;

class FacebookButtonActivity
extends LoadCommandCallbackWithTimeout<UserLoginData> {
    FacebookButtonActivity() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        FacebookButtonActivity.this.hideDialogWaiting();
        Logger.getInstance().debug(this.getClass().getName(), "Logging in with Facebook failed!");
        FacebookButtonActivity.this.facebookLoginFailed(aPIStatusCode, string, list);
    }

    @Override
    protected void succeded(UserLoginData userLoginData) {
        FacebookButtonActivity.this.hideDialogWaiting();
        Logger.getInstance().debug(this.getClass().getName(), "Logging in with Facebook succeeded!");
        FacebookButtonActivity.this.facebookLoginSucceeded();
    }
}
