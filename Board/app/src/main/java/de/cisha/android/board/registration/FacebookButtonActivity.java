// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.registration;

import java.util.Iterator;
import com.facebook.AccessToken;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.modalfragments.RookieDialogFrament;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.FacebookCallback;
import java.util.Collections;
import java.util.LinkedList;
import android.os.Bundle;
import android.content.Intent;
import java.util.Collection;
import android.app.Activity;
import java.util.Arrays;
import com.facebook.login.LoginManager;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.LoadCommandCallbackWithTimeout;
import de.cisha.android.board.service.UserLoginData;
import android.view.View;
import android.view.View.OnClickListener;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import java.util.List;
import com.facebook.CallbackManager;

public abstract class FacebookButtonActivity extends SingleScreenFragmentActivity
{
    private static final String FACEBOOK_PERMISSION_EMAIL = "email";
    private static final String FACEBOOK_PERMISSION_PUBLIC_PROFILE = "public_profile";
    static CallbackManager _callbackManager;
    private List<Runnable> _delayedRunnables;
    private boolean _flagActivityDestroyed;
    private boolean _flagOnSaveInstanceStateCalled;
    
    static {
        FacebookButtonActivity._callbackManager = CallbackManager.Factory.create();
    }
    
    private View.OnClickListener createFacebookButtonListener() {
        return (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                FacebookButtonActivity.this.loginFacebook();
            }
        };
    }
    
    private LoadCommandCallback<UserLoginData> createFacebookLoginCallback() {
        return new LoadCommandCallbackWithTimeout<UserLoginData>() {
            @Override
            protected void failed(final APIStatusCode apiStatusCode, final String s, final List<LoadFieldError> list, final JSONObject jsonObject) {
                FacebookButtonActivity.this.hideDialogWaiting();
                Logger.getInstance().debug(this.getClass().getName(), "Logging in with Facebook failed!");
                FacebookButtonActivity.this.facebookLoginFailed(apiStatusCode, s, list);
            }
            
            @Override
            protected void succeded(final UserLoginData userLoginData) {
                FacebookButtonActivity.this.hideDialogWaiting();
                Logger.getInstance().debug(this.getClass().getName(), "Logging in with Facebook succeeded!");
                FacebookButtonActivity.this.facebookLoginSucceeded();
            }
        };
    }
    
    private void loginFacebook() {
        this.showDialogWaiting(false);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }
    
    protected abstract void facebookLoginFailed(final APIStatusCode p0, final String p1, final List<LoadFieldError> p2);
    
    protected abstract void facebookLoginSucceeded();
    
    protected abstract View getFacebookButton();
    
    public void onActivityResult(final int n, final int n2, final Intent intent) {
        super.onActivityResult(n, n2, intent);
        FacebookButtonActivity._callbackManager.onActivityResult(n, n2, intent);
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this._delayedRunnables = Collections.synchronizedList(new LinkedList<Runnable>());
        LoginManager.getInstance().registerCallback(FacebookButtonActivity._callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onCancel() {
                FacebookButtonActivity.this.facebookLoginFailed(APIStatusCode.API_OK, "Facebook login cancelled.", null);
            }
            
            @Override
            public void onError(final FacebookException ex) {
                FacebookButtonActivity.this.facebookLoginFailed(APIStatusCode.API_OK, ex.getMessage(), null);
            }
            
            @Override
            public void onSuccess(final LoginResult loginResult) {
                final AccessToken accessToken = loginResult.getAccessToken();
                if (!accessToken.getPermissions().contains("email")) {
                    FacebookButtonActivity.this.hideDialogWaiting();
                    final RookieDialogFrament rookieDialogFrament = new RookieDialogFrament();
                    rookieDialogFrament.setCancelable(true);
                    rookieDialogFrament.setOnCancelButtonClickListener((View.OnClickListener)new View.OnClickListener() {
                        public void onClick(final View view) {
                            rookieDialogFrament.dismiss();
                        }
                    });
                    rookieDialogFrament.setOnReloadButtonClickListener((View.OnClickListener)new View.OnClickListener() {
                        public void onClick(final View view) {
                            rookieDialogFrament.dismiss();
                            FacebookButtonActivity.this.loginFacebook();
                        }
                    });
                    rookieDialogFrament.setButtonTypes(RookieDialogFrament.RookieButtonType.CANCEL, RookieDialogFrament.RookieButtonType.RELOAD);
                    rookieDialogFrament.setText(FacebookButtonActivity.this.getString(2131689975));
                    rookieDialogFrament.setTitle(FacebookButtonActivity.this.getString(2131689976));
                    rookieDialogFrament.show(FacebookButtonActivity.this.getSupportFragmentManager(), "fbalertemail");
                    return;
                }
                ServiceProvider.getInstance().getLoginService().authenticateByFacebook(accessToken.getToken(), FacebookButtonActivity.this.createFacebookLoginCallback());
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this._flagActivityDestroyed = true;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        this._flagOnSaveInstanceStateCalled = false;
        synchronized (this._delayedRunnables) {
            if (this._delayedRunnables.size() > 0) {
                final Iterator<Runnable> iterator = this._delayedRunnables.iterator();
                while (iterator.hasNext()) {
                    iterator.next().run();
                }
            }
            // monitorexit(this._delayedRunnables)
            this._delayedRunnables.clear();
        }
    }
    
    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
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
            this.runOnUiThread((Runnable)new Runnable() {
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
