/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.registration;

import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.registration.RegistrationActivity;
import de.cisha.android.board.service.jsonparser.APIStatusCode;

class RegistrationActivity
implements Runnable {
    final /* synthetic */ APIStatusCode val$errorCode;

    RegistrationActivity(APIStatusCode aPIStatusCode) {
        this.val$errorCode = aPIStatusCode;
    }

    @Override
    public void run() {
        StatusCodeErrorHelper.handleErrorCode(5.this.this$0, this.val$errorCode, new IErrorPresenter.IReloadAction(){

            @Override
            public void performReload() {
                5.this.this$0.confirmForm();
            }
        }, null);
    }

}
