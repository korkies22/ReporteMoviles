/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  org.json.JSONObject
 */
package de.cisha.android.board.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import de.cisha.android.board.modalfragments.RookieDialogFrament;
import de.cisha.android.board.registration.SingleScreenFragmentActivity;
import de.cisha.android.board.service.ILoginService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.UserLoginData;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public abstract class FacebookButtonActivity
extends SingleScreenFragmentActivity {
    private static final String FACEBOOK_PERMISSION_EMAIL = "email";
    private static final String FACEBOOK_PERMISSION_PUBLIC_PROFILE = "public_profile";
    static CallbackManager _callbackManager = CallbackManager.Factory.create();
    private List<Runnable> _delayedRunnables;
    private boolean _flagActivityDestroyed;
    private boolean _flagOnSaveInstanceStateCalled;

    private View.OnClickListener createFacebookButtonListener() {
        return new View.OnClickListener(){

            public void onClick(View view) {
                FacebookButtonActivity.this.loginFacebook();
            }
        };
    }

    private LoadCommandCallback<UserLoginData> createFacebookLoginCallback() {
        return new LoadCommandCallbackWithTimeout<UserLoginData>(){

            @Override
            protected void failed(APIStatusCode aPIStatusCode, String string, List<LoadFieldError> list, JSONObject jSONObject) {
                FacebookButtonActivity.this.hideDialogWaiting();
                Logger.getInstance().debug(this.getClass().getName(), "Logging in with Facebook failed!");
                FacebookButtonActivity.this.facebookLoginFailed(aPIStatusCode, string, list);
            }

            @Override
            protected void succeded(UserLoginData userLoginData) {
                FacebookButtonActivity.this.hideDialogWaiting();
                Logger.getInstance().debug(this.getClass().getName(), "Logging in with Facebook succeeded!");
                FacebookButtonActivity.this.facebookLoginSucceeded();
            }
        };
    }

    private void loginFacebook() {
        this.showDialogWaiting(false);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(FACEBOOK_PERMISSION_EMAIL, FACEBOOK_PERMISSION_PUBLIC_PROFILE));
    }

    protected abstract void facebookLoginFailed(APIStatusCode var1, String var2, List<LoadFieldError> var3);

    protected abstract void facebookLoginSucceeded();

    protected abstract View getFacebookButton();

    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        _callbackManager.onActivityResult(n, n2, intent);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this._delayedRunnables = Collections.synchronizedList(new LinkedList<E>());
        LoginManager.getInstance().registerCallback(_callbackManager, new FacebookCallback<LoginResult>(){

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
                if (!(object = object.getAccessToken()).getPermissions().contains(FacebookButtonActivity.FACEBOOK_PERMISSION_EMAIL)) {
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

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this._flagActivityDestroyed = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void onResume() {
        super.onResume();
        this._flagOnSaveInstanceStateCalled = false;
        List<Runnable> list = this._delayedRunnables;
        synchronized (list) {
            if (this._delayedRunnables.size() > 0) {
                Iterator<Runnable> iterator = this._delayedRunnables.iterator();
                while (iterator.hasNext()) {
                    iterator.next().run();
                }
            }
        }
        this._delayedRunnables.clear();
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this._flagOnSaveInstanceStateCalled = true;
    }

    @Override
    protected void onStart() {
        this.getFacebookButton().setOnClickListener(this.createFacebookButtonListener());
        super.onStart();
    }

    protected void runOnUiThreadWaitForResumed(final Runnable runnable) {
        if (!this._flagActivityDestroyed) {
            this.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    if (FacebookButtonActivity.this._flagOnSaveInstanceStateCalled) {
                        FacebookButtonActivity.this._delayedRunnables.add(runnable);
                        return;
                    }
                    runnable.run();
                }
            });
        }
    }

}
