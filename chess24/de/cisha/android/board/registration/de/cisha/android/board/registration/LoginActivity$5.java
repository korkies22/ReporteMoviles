/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import android.view.View;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class LoginActivity
implements View.OnClickListener {
    LoginActivity() {
    }

    public void onClick(View view) {
        LoginActivity.this.showDialogWaiting(false);
        ServiceProvider.getInstance().getLoginService().authenticateAsGuest((LoadCommandCallback<UserLoginData>)new LoadCommandCallbackWithTimeout<UserLoginData>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                LoginActivity.this.hideDialogWaiting();
                LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690031));
            }

            @Override
            protected void succeded(UserLoginData userLoginData) {
                LoginActivity.this.hideDialogWaiting();
                LoginActivity.this.loginSucceeded();
            }
        });
    }

}
