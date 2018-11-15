/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.android.board.service.LoginService;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;

class LoginService
extends LoginService.GetLoginDataCallback {
    LoginService(LoadCommandCallback loadCommandCallback, String string) {
        super(5.this.this$0, loadCommandCallback, string);
    }

    @Override
    protected void succeded(UserLoginData userLoginData) {
        5.this.this$0._loginInfo.resetGuest();
        super.succeded(userLoginData);
    }
}
