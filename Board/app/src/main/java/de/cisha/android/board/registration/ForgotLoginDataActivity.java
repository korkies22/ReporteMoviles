// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.registration;

import android.widget.ImageView;
import android.view.KeyEvent;
import android.widget.TextView.OnEditorActionListener;
import android.os.Bundle;
import android.text.Editable;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import android.view.View.OnClickListener;
import android.support.v4.app.FragmentActivity;
import de.cisha.android.board.IErrorPresenter;
import de.cisha.android.board.StatusCodeErrorHelper;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.ui.patterns.input.CustomEditText;
import android.widget.TextView;
import android.view.View;
import android.text.TextWatcher;

public class ForgotLoginDataActivity extends SingleScreenFragmentActivity implements TextWatcher
{
    private boolean _activityDestroyed;
    private View _confirmLostPasswordButton;
    private TextView _confirmationText;
    private CustomEditText _editEmail;
    private TextView _errorTextView;
    private View _okButton;
    
    private void confirmForm() {
        final String string = this._editEmail.getEditText().getText().toString();
        this.showDialogWaiting(false);
        ServiceProvider.getInstance().getLoginService().lostPassword(string, new LoadCommandCallbackWithTimeout<Boolean>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                ForgotLoginDataActivity.this.hideDialogWaiting();
                if (list != null) {
                    ForgotLoginDataActivity.this.showErrorText(StatusCodeErrorHelper.createErrorMessageFrom(list));
                    return;
                }
                switch (ForgotLoginDataActivity.6..SwitchMap.de.cisha.android.board.service.jsonparser.APIStatusCode[apiStatusCode.ordinal()]) {
                    default: {
                        ForgotLoginDataActivity.this.showErrorText(ForgotLoginDataActivity.this.getResources().getString(2131690030));
                    }
                    case 2: {
                        if (!ForgotLoginDataActivity.this._activityDestroyed) {
                            StatusCodeErrorHelper.handleErrorCode(ForgotLoginDataActivity.this, apiStatusCode, new IErrorPresenter.IReloadAction() {
                                @Override
                                public void performReload() {
                                    ForgotLoginDataActivity.this.confirmForm();
                                }
                            }, null);
                            return;
                        }
                        break;
                    }
                    case 1: {
                        ForgotLoginDataActivity.this.showErrorText(ForgotLoginDataActivity.this.getResources().getString(2131690030));
                        ServiceProvider.getInstance().getDUUIDService().renewDuuid();
                        break;
                    }
                }
            }
            
            @Override
            protected void succeded(final Boolean b) {
                ForgotLoginDataActivity.this.hideDialogWaiting();
                ForgotLoginDataActivity.this._confirmLostPasswordButton.setVisibility(8);
                ForgotLoginDataActivity.this._editEmail.setVisibility(8);
                ForgotLoginDataActivity.this._okButton.setVisibility(0);
                ForgotLoginDataActivity.this._confirmationText.setVisibility(0);
            }
        });
    }
    
    private void showErrorText(final String text) {
        this._errorTextView.setText((CharSequence)text);
        final TextView errorTextView = this._errorTextView;
        int visibility;
        if (text != null && text.length() > 0) {
            visibility = 0;
        }
        else {
            visibility = 8;
        }
        errorTextView.setVisibility(visibility);
    }
    
    public void afterTextChanged(final Editable editable) {
    }
    
    public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
        this.showErrorText("");
    }
    
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427361);
        this._editEmail = (CustomEditText)this.findViewById(2131296902);
        this._editEmail.getEditText().setImeOptions(4);
        this._editEmail.getEditText().setSingleLine();
        this._editEmail.getEditText().setInputType(32);
        (this._okButton = this.findViewById(2131296905)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ForgotLoginDataActivity.this.finish();
            }
        });
        this._confirmationText = (TextView)this.findViewById(2131296901);
        this._errorTextView = (TextView)this.findViewById(2131296903);
        (this._confirmLostPasswordButton = this.findViewById(2131296904)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ForgotLoginDataActivity.this.confirmForm();
            }
        });
        this._editEmail.getEditText().addTextChangedListener((TextWatcher)this);
        this._editEmail.getEditText().setOnEditorActionListener((TextView.OnEditorActionListener)new TextView.OnEditorActionListener() {
            public boolean onEditorAction(final TextView textView, final int n, final KeyEvent keyEvent) {
                if (n == 4) {
                    ForgotLoginDataActivity.this.confirmForm();
                    return true;
                }
                return false;
            }
        });
        ((ImageView)this.findViewById(2131296536)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ForgotLoginDataActivity.this.onBackPressed();
            }
        });
    }
    
    protected void onDestroy() {
        super.onDestroy();
        this._activityDestroyed = true;
    }
    
    protected void onStart() {
        super.onStart();
        ServiceProvider.getInstance().getTrackingService().trackScreenOpen("ForgotPassword");
    }
    
    public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
    }
}
