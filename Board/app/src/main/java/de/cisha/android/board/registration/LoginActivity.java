// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.registration;

import android.view.KeyEvent;
import android.widget.TextView.OnEditorActionListener;
import android.os.Bundle;
import de.cisha.android.board.service.ILoginService;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.board.IErrorPresenter;
import android.text.Editable;
import android.content.Intent;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.view.View.OnClickListener;
import de.cisha.android.board.StatusCodeErrorHelper;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.ui.patterns.text.CustomTextView;
import android.widget.TextView;
import de.cisha.android.ui.patterns.input.CustomEditText;
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import android.view.View;
import android.text.TextWatcher;

public class LoginActivity extends FacebookButtonActivity implements TextWatcher
{
    private static final String LOG_TAG = "LoginActivity";
    private View _createAccountButton;
    private CustomEditPassword _editPassword;
    private CustomEditText _editTextUser;
    private TextView _errorTextView;
    private CustomTextView _facebookLoginButton;
    
    private void displayErrors(final List<LoadFieldError> list, final String s) {
        this.runOnUiThread((Runnable)new Runnable() {
            @Override
            public void run() {
                String text = s;
                if (list != null) {
                    text = StatusCodeErrorHelper.createErrorMessageFrom(list);
                }
                final TextView access.300 = LoginActivity.this._errorTextView;
                int visibility;
                if (text.length() > 0) {
                    visibility = 0;
                }
                else {
                    visibility = 8;
                }
                access.300.setVisibility(visibility);
                LoginActivity.this._errorTextView.setText((CharSequence)text);
            }
        });
    }
    
    private void initLoginGuestButton() {
        this.findViewById(2131296583).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LoginActivity.this.showDialogWaiting(false);
                ServiceProvider.getInstance().getLoginService().authenticateAsGuest(new LoadCommandCallbackWithTimeout<UserLoginData>() {
                    @Override
                    protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                        LoginActivity.this.hideDialogWaiting();
                        LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690031));
                    }
                    
                    @Override
                    protected void succeded(final UserLoginData userLoginData) {
                        LoginActivity.this.hideDialogWaiting();
                        LoginActivity.this.loginSucceeded();
                    }
                });
            }
        });
    }
    
    private void loginSucceeded() {
        final Intent intent = new Intent(this.getApplicationContext(), (Class)MainActivity.class);
        intent.setFlags(2097152);
        this.startActivity(intent);
        this.finish();
    }
    
    private void resetDuuid() {
        ServiceProvider.getInstance().getDUUIDService().renewDuuid();
    }
    
    public void afterTextChanged(final Editable editable) {
    }
    
    public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
        this._errorTextView.setVisibility(8);
    }
    
    protected void confirmFormAction() {
        final ILoginService loginService = ServiceProvider.getInstance().getLoginService();
        this.showDialogWaiting(false);
        loginService.authenticateByLogin(this._editTextUser.getEditText().getText().toString(), this._editPassword.getEditText().getEditableText().toString(), new LoadCommandCallbackWithTimeout<UserLoginData>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                LoginActivity.this.hideDialogWaiting();
                switch (LoginActivity.8..SwitchMap.de.cisha.android.board.service.jsonparser.APIStatusCode[apiStatusCode.ordinal()]) {
                    default: {
                        LoginActivity.this.displayErrors(null, LoginActivity.this.getResources().getString(2131690028));
                    }
                    case 5: {
                        LoginActivity.this.runOnUiThreadWaitForResumed(new Runnable() {
                            @Override
                            public void run() {
                                StatusCodeErrorHelper.handleErrorCode(LoginActivity.this, apiStatusCode, new IErrorPresenter.IReloadAction() {
                                    @Override
                                    public void performReload() {
                                        LoginActivity.this.confirmFormAction();
                                    }
                                }, null);
                            }
                        });
                    }
                    case 4: {
                        LoginActivity.this.displayErrors(list, null);
                    }
                    case 3: {
                        LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690039));
                    }
                    case 2: {
                        LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690037));
                    }
                    case 1: {
                        LoginActivity.this.resetDuuid();
                        LoginActivity.this.displayErrors(list, LoginActivity.this.getString(2131690030));
                    }
                }
            }
            
            @Override
            protected void succeded(final UserLoginData userLoginData) {
                LoginActivity.this.hideDialogWaiting();
                LoginActivity.this.loginSucceeded();
            }
        });
    }
    
    @Override
    protected void facebookLoginFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list) {
        String s2;
        if (LoginActivity.8..SwitchMap.de.cisha.android.board.service.jsonparser.APIStatusCode[apiStatusCode.ordinal()] != 1) {
            s2 = this.getResources().getString(2131689977);
        }
        else {
            s2 = this.getResources().getString(2131690030);
            this.resetDuuid();
        }
        this.displayErrors(null, s2);
    }
    
    @Override
    protected void facebookLoginSucceeded() {
        this.loginSucceeded();
    }
    
    @Override
    protected View getFacebookButton() {
        return (View)this._facebookLoginButton;
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
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
        this._editPassword.getEditText().setOnEditorActionListener((TextView.OnEditorActionListener)new TextView.OnEditorActionListener() {
            public boolean onEditorAction(final TextView textView, final int n, final KeyEvent keyEvent) {
                if (n == 4) {
                    LoginActivity.this.confirmFormAction();
                    return true;
                }
                return false;
            }
        });
        this.findViewById(2131296578).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LoginActivity.this.confirmFormAction();
            }
        });
        this._errorTextView = (TextView)this.findViewById(2131296579);
        (this._createAccountButton = this.findViewById(2131296580)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), (Class)RegistrationActivity.class));
            }
        });
        this.findViewById(2131296585).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), (Class)ForgotLoginDataActivity.class));
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
    
    public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
    }
}
