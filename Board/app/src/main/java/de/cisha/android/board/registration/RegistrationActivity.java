// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.registration;

import android.widget.ImageView;
import android.net.Uri;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import de.cisha.android.board.MainActivity;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import android.view.View.OnClickListener;
import de.cisha.android.ui.patterns.input.CustomEditPassword;
import de.cisha.android.ui.patterns.input.CustomEditText;
import android.widget.TextView;
import android.view.View;

public class RegistrationActivity extends FacebookButtonActivity
{
    private View _confirmationContainer;
    private TextView _emailConfirmationText;
    private CustomEditText _emailEditText;
    private TextView _errorTextView;
    private View _facebookButton;
    private View _headerTextView;
    private CustomEditText _nickEditText;
    private CustomEditPassword _passwordConfirmationEditText;
    private CustomEditPassword _passwordEditText;
    private View _termsAndConditionsText;
    
    private void confirmConfirmation() {
        this._headerTextView.setVisibility(8);
        this._confirmationContainer.setVisibility(8);
        this.findViewById(2131296917).setVisibility(0);
        this.findViewById(2131296916).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RegistrationActivity.this.finish();
            }
        });
    }
    
    private void confirmForm() {
        final String string = this._nickEditText.getEditText().getEditableText().toString();
        final String string2 = this._emailEditText.getEditText().getEditableText().toString();
        final String string3 = this._passwordEditText.getEditText().getEditableText().toString();
        final String string4 = this._passwordConfirmationEditText.getEditText().getEditableText().toString();
        if (string3 != null && string3.equals(string4) && string3.length() > 0) {
            this.showDialogWaiting(false);
            ServiceProvider.getInstance().getLoginService().register(string, string2, string3, new LoadCommandCallbackWithTimeout<Boolean>() {
                @Override
                protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                    if (list != null) {
                        RegistrationActivity.this.showErrorText(StatusCodeErrorHelper.createErrorMessageFrom(list));
                    }
                    else {
                        switch (RegistrationActivity.7..SwitchMap.de.cisha.android.board.service.jsonparser.APIStatusCode[apiStatusCode.ordinal()]) {
                            default: {
                                RegistrationActivity.this.showErrorText(RegistrationActivity.this.getResources().getString(2131690248));
                                break;
                            }
                            case 3: {
                                RegistrationActivity.this.runOnUiThreadWaitForResumed(new Runnable() {
                                    @Override
                                    public void run() {
                                        StatusCodeErrorHelper.handleErrorCode(RegistrationActivity.this, apiStatusCode, new IErrorPresenter.IReloadAction() {
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
                                break;
                            }
                        }
                    }
                    RegistrationActivity.this.hideDialogWaiting();
                }
                
                @Override
                protected void succeded(final Boolean b) {
                    RegistrationActivity.this.findViewById(2131296907).setVisibility(8);
                    RegistrationActivity.this._confirmationContainer.setVisibility(0);
                    RegistrationActivity.this._emailConfirmationText.setText((CharSequence)string2);
                    RegistrationActivity.this.hideDialogWaiting();
                }
            });
            return;
        }
        this.showErrorText(this.getString(2131690253));
    }
    
    private void showErrorText(final String text) {
        this._errorTextView.setText((CharSequence)text);
        this._errorTextView.setVisibility(0);
        this._termsAndConditionsText.setVisibility(8);
    }
    
    @Override
    protected void facebookLoginFailed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list) {
        this.showErrorText(this.getString(2131689977));
    }
    
    @Override
    protected void facebookLoginSucceeded() {
        final Intent intent = new Intent((Context)this, (Class)MainActivity.class);
        intent.setFlags(335544320);
        this.startActivity(intent);
    }
    
    @Override
    protected View getFacebookButton() {
        return this._facebookButton;
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427365);
        (this._termsAndConditionsText = this.findViewById(2131296915)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RegistrationActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(RegistrationActivity.this.getResources().getString(2131690393))));
            }
        });
        this._errorTextView = (TextView)this.findViewById(2131296909);
        this._headerTextView = this.findViewById(2131296913);
        this._confirmationContainer = this.findViewById(2131296897);
        this._nickEditText = (CustomEditText)this.findViewById(2131296910);
        this._nickEditText.getEditText().setSingleLine();
        this._nickEditText.getEditText().setInputType(524288);
        this._nickEditText.getEditText().setImeOptions(524293);
        this._emailEditText = (CustomEditText)this.findViewById(2131296908);
        this._emailEditText.getEditText().setSingleLine();
        this._emailEditText.getEditText().setInputType(32);
        this._emailEditText.getEditText().setImeOptions(5);
        this._passwordEditText = (CustomEditPassword)this.findViewById(2131296911);
        this._passwordConfirmationEditText = (CustomEditPassword)this.findViewById(2131296912);
        this._emailConfirmationText = (TextView)this.findViewById(2131296899);
        this.findViewById(2131296906).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RegistrationActivity.this.confirmForm();
            }
        });
        this.findViewById(2131296898).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RegistrationActivity.this.finish();
            }
        });
        ((ImageView)this.findViewById(2131296896)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                RegistrationActivity.this.onBackPressed();
            }
        });
        this._facebookButton = this.findViewById(2131296900);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        ServiceProvider.getInstance().getTrackingService().trackScreenOpen("Register");
    }
}
