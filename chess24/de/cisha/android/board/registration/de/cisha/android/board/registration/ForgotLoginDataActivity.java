/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.registration.SingleScreenFragmentActivity;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.input.CustomEditText;
import java.util.List;
import org.json.JSONObject;

public class ForgotLoginDataActivity
extends SingleScreenFragmentActivity
implements TextWatcher {
    private boolean _activityDestroyed;
    private View _confirmLostPasswordButton;
    private TextView _confirmationText;
    private CustomEditText _editEmail;
    private TextView _errorTextView;
    private View _okButton;

    private void confirmForm() {
        String string = this._editEmail.getEditText().getText().toString();
        this.showDialogWaiting(false);
        ServiceProvider.getInstance().getLoginService().lostPassword(string, (LoadCommandCallback<Boolean>)new LoadCommandCallbackWithTimeout<Boolean>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                ForgotLoginDataActivity.this.hideDialogWaiting();
                if (list != null) {
                    ForgotLoginDataActivity.this.showErrorText(StatusCodeErrorHelper.createErrorMessageFrom(list));
                    return;
                }
                switch (.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[aPIStatusCode.ordinal()]) {
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

        });
    }

    private void showErrorText(String string) {
        this._errorTextView.setText((CharSequence)string);
        TextView textView = this._errorTextView;
        int n = string != null && string.length() > 0 ? 0 : 8;
        textView.setVisibility(n);
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        this.showErrorText("");
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427361);
        this._editEmail = (CustomEditText)this.findViewById(2131296902);
        this._editEmail.getEditText().setImeOptions(4);
        this._editEmail.getEditText().setSingleLine();
        this._editEmail.getEditText().setInputType(32);
        this._okButton = this.findViewById(2131296905);
        this._okButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ForgotLoginDataActivity.this.finish();
            }
        });
        this._confirmationText = (TextView)this.findViewById(2131296901);
        this._errorTextView = (TextView)this.findViewById(2131296903);
        this._confirmLostPasswordButton = this.findViewById(2131296904);
        this._confirmLostPasswordButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ForgotLoginDataActivity.this.confirmForm();
            }
        });
        this._editEmail.getEditText().addTextChangedListener((TextWatcher)this);
        this._editEmail.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
                if (n == 4) {
                    ForgotLoginDataActivity.this.confirmForm();
                    return true;
                }
                return false;
            }
        });
        ((ImageView)this.findViewById(2131296536)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ForgotLoginDataActivity.this.onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this._activityDestroyed = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ServiceProvider.getInstance().getTrackingService().trackScreenOpen("ForgotPassword");
    }

    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

}
