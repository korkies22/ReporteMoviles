/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.view.View
 *  android.widget.TextView
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.registration.ForgotLoginDataActivity;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.input.CustomEditText;
import java.util.List;
import org.json.JSONObject;

class ForgotLoginDataActivity
extends LoadCommandCallbackWithTimeout<Boolean> {
    ForgotLoginDataActivity() {
    }

    @Override
    protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        ForgotLoginDataActivity.this.hideDialogWaiting();
        if (list != null) {
            ForgotLoginDataActivity.this.showErrorText(StatusCodeErrorHelper.createErrorMessageFrom(list));
            return;
        }
        switch (ForgotLoginDataActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[aPIStatusCode.ordinal()]) {
            default: {
                ForgotLoginDataActivity.this.showErrorText(ForgotLoginDataActivity.this.getResources().getString(2131690030));
                return;
            }
            case 2: {
                if (ForgotLoginDataActivity.this._activityDestroyed) break;
                StatusCodeErrorHelper.handleErrorCode(ForgotLoginDataActivity.this, aPIStatusCode, new IErrorPresenter.IReloadAction(){

                    @Override
                    public void performReload() {
                        ForgotLoginDataActivity.this.confirmForm();
                    }
                }, null);
                return;
            }
            case 1: {
                ForgotLoginDataActivity.this.showErrorText(ForgotLoginDataActivity.this.getResources().getString(2131690030));
                ServiceProvider.getInstance().getDUUIDService().renewDuuid();
            }
        }
    }

    @Override
    protected void succeded(Boolean bl) {
        ForgotLoginDataActivity.this.hideDialogWaiting();
        ForgotLoginDataActivity.this._confirmLostPasswordButton.setVisibility(8);
        ForgotLoginDataActivity.this._editEmail.setVisibility(8);
        ForgotLoginDataActivity.this._okButton.setVisibility(0);
        ForgotLoginDataActivity.this._confirmationText.setVisibility(0);
    }

}
