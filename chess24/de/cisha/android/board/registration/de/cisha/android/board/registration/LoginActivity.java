/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.registration.FacebookButtonActivity;
import de.cisha.android.board.registration.ForgotLoginDataActivity;
import de.cisha.android.board.registration.RegistrationActivity;
import de.cisha.android.board.service.IDUUIDService;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ITrackingService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.android.ui.patterns.text.CustomTextView;
import java.util.List;
import org.json.JSONObject;

public class LoginActivity
extends FacebookButtonActivity
implements TextWatcher {
    private static final String LOG_TAG = "LoginActivity";
    private View _createAccountButton;
    private CustomEditPassword _editPassword;
    private CustomEditText _editTextUser;
    private TextView _errorTextView;
    private CustomTextView _facebookLoginButton;

    private void displayErrors(final List<LoadFieldError> list, final String string) {
        this.runOnUiThread(new Runnable(){

            @Override
            public void run() {
                String string2 = string;
                if (list != null) {
                    string2 = StatusCodeErrorHelper.createErrorMessageFrom(list);
                }
                TextView textView = LoginActivity.this._errorTextView;
                int n = string2.length() > 0 ? 0 : 8;
                textView.setVisibility(n);
                LoginActivity.this._errorTextView.setText((CharSequence)string2);
            }
        });
    }

    private void initLoginGuestButton() {
        this.findViewById(2131296583).setOnClickListener(new View.OnClickListener(){

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

        });
    }

    private void loginSucceeded() {
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.setFlags(2097152);
        this.startActivity(intent);
        this.finish();
    }

    private void resetDuuid() {
        ServiceProvider.getInstance().getDUUIDService().renewDuuid();
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        this._errorTextView.setVisibility(8);
    }

    protected void confirmFormAction() {
        ILoginService iLoginService = ServiceProvider.getInstance().getLoginService();
        this.showDialogWaiting(false);
        iLoginService.authenticateByLogin(this._editTextUser.getEditText().getText().toString(), this._editPassword.getEditText().getEditableText().toString(), (LoadCommandCallback<UserLoginData>)new LoadCommandCallbackWithTimeout<UserLoginData>(){

            @Override
            protected void failed(final APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                LoginActivity.this.hideDialogWaiting();
                switch (.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[aPIStatusCode.ordinal()]) {
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

        });
    }

    @Override
    protected void facebookLoginFailed(APIStatusCode object, String string, List<LoadFieldError> list) {
        if (.$SwitchMap$de$cisha$android$board$service$jsonparser$APIStatusCode[object.ordinal()] != 1) {
            object = this.getResources().getString(2131689977);
        } else {
            object = this.getResources().getString(2131690030);
            this.resetDuuid();
        }
        this.displayErrors(null, (String)object);
    }

    @Override
    protected void facebookLoginSucceeded() {
        this.loginSucceeded();
    }

    @Override
    protected View getFacebookButton() {
        return this._facebookLoginButton;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427360);
        this._editTextUser = (CustomEditText)this.findViewById(2131296581);
        this._editTextUser.getEditText().setSingleLine();
        this._editTextUser.getEditText().setImeOptions(5);
        this._editTextUser.getEditText().addTextChangedListener((TextWatcher)this);
        this._editTextUser.getEditText().setInputType(524288);
        this._editPassword = (CustomEditPassword)this.findViewById(2131296582);
        this._editPassword.getEditText().setImeOptions(4);
        this._editPassword.getEditText().addTextChangedListener((TextWatcher)this);
        this._editPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
                if (n == 4) {
                    LoginActivity.this.confirmFormAction();
                    return true;
                }
                return false;
            }
        });
        this.findViewById(2131296578).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                LoginActivity.this.confirmFormAction();
            }
        });
        this._errorTextView = (TextView)this.findViewById(2131296579);
        this._createAccountButton = this.findViewById(2131296580);
        this._createAccountButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = new Intent(LoginActivity.this.getApplicationContext(), RegistrationActivity.class);
                LoginActivity.this.startActivity((Intent)view);
            }
        });
        this.findViewById(2131296585).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                view = new Intent(LoginActivity.this.getApplicationContext(), ForgotLoginDataActivity.class);
                LoginActivity.this.startActivity((Intent)view);
            }
        });
        this._facebookLoginButton = (CustomTextView)this.findViewById(2131296577);
        this.initLoginGuestButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ServiceProvider.getInstance().getTrackingService().trackScreenOpen("Login");
    }

    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
    }

}
