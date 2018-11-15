/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package de.cisha.android.board.registration;

import android.support.v4.app.FragmentManager;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import de.cisha.android.board.modalfragments.RookieDialogFrament;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import java.util.List;
import java.util.Set;

class FacebookButtonActivity
implements FacebookCallback<LoginResult> {
    FacebookButtonActivity() {
    }

    @Override
    public void onCancel() {
        FacebookButtonActivity.this.facebookLoginFailed(APIStatusCode.API_OK, "Facebook login cancelled.", null);
    }

    @Override
    public void onError(FacebookException facebookException) {
        FacebookButtonActivity.this.facebookLoginFailed(APIStatusCode.API_OK, facebookException.getMessage(), null);
    }

    @Override
    public void onSuccess(LoginResult object) {
        if (!(object = object.getAccessToken()).getPermissions().contains(de.cisha.android.board.registration.FacebookButtonActivity.FACEBOOK_PERMISSION_EMAIL)) {
            FacebookButtonActivity.this.hideDialogWaiting();
            object = new RookieDialogFrament();
            object.setCancelable(true);
            object.setOnCancelButtonClickListener(new View.OnClickListener((RookieDialogFrament)object){
                final /* synthetic */ RookieDialogFrament val$alertFragement;
                {
                    this.val$alertFragement = rookieDialogFrament;
                }

                public void onClick(View view) {
                    this.val$alertFragement.dismiss();
                }
            });
            object.setOnReloadButtonClickListener(new View.OnClickListener((RookieDialogFrament)object){
                final /* synthetic */ RookieDialogFrament val$alertFragement;
                {
                    this.val$alertFragement = rookieDialogFrament;
                }

                public void onClick(View view) {
                    this.val$alertFragement.dismiss();
                    FacebookButtonActivity.this.loginFacebook();
                }
            });
            object.setButtonTypes(RookieDialogFrament.RookieButtonType.CANCEL, RookieDialogFrament.RookieButtonType.RELOAD);
            object.setText(FacebookButtonActivity.this.getString(2131689975));
            object.setTitle(FacebookButtonActivity.this.getString(2131689976));
            object.show(FacebookButtonActivity.this.getSupportFragmentManager(), "fbalertemail");
            return;
        }
        ServiceProvider.getInstance().getLoginService().authenticateByFacebook(object.getToken(), FacebookButtonActivity.this.createFacebookLoginCallback());
    }

}
