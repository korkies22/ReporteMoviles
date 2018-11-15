/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class LoginService
implements LoadCommandCallback<Void> {
    final /* synthetic */ ILoginService.LogoutCallback val$callback;

    LoginService(ILoginService.LogoutCallback logoutCallback) {
        this.val$callback = logoutCallback;
    }

    @Override
    public void loadingCancelled() {
        this.val$callback.logoutFailed("logout was cancelled");
    }

    @Override
    public void loadingFailed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        this.val$callback.logoutFailed(string);
    }

    @Override
    public void loadingSucceded(Void void_) {
        this.val$callback.logoutSucceeded();
    }
}
