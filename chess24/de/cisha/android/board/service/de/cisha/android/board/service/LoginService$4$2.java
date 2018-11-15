/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.LoginService;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.chess.model.CishaUUID;

class LoginService
extends LoginService.GetLoginDataCallback {
    LoginService(LoadCommandCallback loadCommandCallback, String string) {
        super(4.this.this$0, loadCommandCallback, string);
    }

    @Override
    protected void succeded(UserLoginData userLoginData) {
        4.this.this$0._loginInfo._lastGuestAuthToken = userLoginData.getAuthToken();
        4.this.this$0._loginInfo._lastGuestUuid = userLoginData.getUserId();
        4.this.this$0.saveLoginInformation();
        super.succeded(userLoginData);
    }
}
