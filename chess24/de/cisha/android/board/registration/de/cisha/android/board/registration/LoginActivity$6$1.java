/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.registration.LoginActivity;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

class LoginActivity
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    LoginActivity(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        StatusCodeErrorHelper.handleErrorCode(6.this.this$0, this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                6.this.this$0.confirmFormAction();
            }
        }, null);
    }

}
