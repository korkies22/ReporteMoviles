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
import de.cisha.android.board.registration.RegistrationActivity;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import org.json.JSONObject;

class RegistrationActivity
extends LoadCommandCallbackWithTimeout<Boolean> {
    final /* synthetic */ String val$email;

    RegistrationActivity(String string) {
        this.val$email = string;
    }

    @Override
    protected void failed(APIStatusCode object, String string, List<LoadFieldError> list, JSONObject jSONObject) {
        if (list != null) {
            object = StatusCodeErrorHelper.createErrorMessageFrom(list);
            RegistrationActivity.this.showErrorText((String)object);
        } else {
            switch (RegistrationActivity.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[object.ordinal()]) {
                default: {
                    RegistrationActivity.this.showErrorText(RegistrationActivity.this.getResources().getString(2131690248));
                    break;
                }
                case 3: {
                    RegistrationActivity.this.runOnUiThreadWaitForResumed(new Runnable((APIStatusCode)((Object)object)){
                        final /* synthetic */ APIStatusCode val$errorCode;
                        {
                            this.val$errorCode = aPIStatusCode;
                        }

                        @Override
                        public void run() {
                            StatusCodeErrorHelper.handleErrorCode(RegistrationActivity.this, this.val$errorCode, new IErrorPresenter.IReloadAction(){

                                @Override
                                public void performReload() {
                                    RegistrationActivity.this.confirmForm();
                                }
                            }, null);
                        }

                    });
                    break;
                }
                case 2: {
                    RegistrationActivity.this.showErrorText(StatusCodeErrorHelper.createErrorMessageFrom(list));
                    break;
                }
                case 1: {
                    RegistrationActivity.this.showErrorText(RegistrationActivity.this.getString(2131690030));
                    ServiceProvider.getInstance().getDUUIDService().renewDuuid();
                }
            }
        }
        RegistrationActivity.this.hideDialogWaiting();
    }

    @Override
    protected void succeded(Boolean bl) {
        RegistrationActivity.this.findViewById(2131296907).setVisibility(8);
        RegistrationActivity.this._confirmationContainer.setVisibility(0);
        RegistrationActivity.this._emailConfirmationText.setText((CharSequence)this.val$email);
        RegistrationActivity.this.hideDialogWaiting();
    }

}
