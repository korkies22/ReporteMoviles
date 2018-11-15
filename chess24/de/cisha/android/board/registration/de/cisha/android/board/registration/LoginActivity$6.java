/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import android.content.res.Resources;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
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
    protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        LoginActivity.this.hideDialogWaiting();
        switch (LoginActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[aPIStatusCode.ordinal()]) {
            default: {
                LoginActivity.this.displayErrors(null, LoginActivity.this.getResources().getString(2131690028));
                return;
            }
            case 5: {
                LoginActivity.this.runOnUiThreadWaitForResumed(new Runnable(){

                    @Override
                    public void run() {
                        StatusCodeErrorHelper.handleErrorCode(LoginActivity.this, aPIStatusCode, new IErrorPresenter.IReloadAction(){

                            @Override
                            public void performReload() {
                                LoginActivity.this.confirmFormAction();
                            }
                        }, null);
                    }

                });
                return;
            }
            case 4: {
                LoginActivity.this.displayErrors(list, null);
                return;
            }
            case 3: {
                LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690039));
                return;
            }
            case 2: {
                LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690037));
                return;
            }
            case 1: 
        }
        LoginActivity.this.resetDuuid();
        LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690030));
    }

    @Override
    protected void succeded(UserLoginData userLoginData) {
        LoginActivity.this.hideDialogWaiting();
        LoginActivity.this.loginSucceeded();
    }

}
