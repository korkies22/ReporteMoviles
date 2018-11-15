/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.common.R;
import com.facebook.login.LoginClient;

public class LoginFragment
extends Fragment {
    static final String EXTRA_REQUEST = "request";
    private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
    static final String REQUEST_KEY = "com.facebook.LoginFragment:Request";
    static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
    private static final String SAVED_LOGIN_CLIENT = "loginClient";
    private static final String TAG = "LoginFragment";
    private String callingPackage;
    private LoginClient loginClient;
    private LoginClient.Request request;

    private void initializeCallingPackage(Activity activity) {
        if ((activity = activity.getCallingActivity()) == null) {
            return;
        }
        this.callingPackage = activity.getPackageName();
    }

    private void onLoginClientCompleted(LoginClient.Result result) {
        this.request = null;
        int n = result.code == LoginClient.Result.Code.CANCEL ? 0 : -1;
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, (Parcelable)result);
        result = new Intent();
        result.putExtras(bundle);
        if (this.isAdded()) {
            this.getActivity().setResult(n, (Intent)result);
            this.getActivity().finish();
        }
    }

    protected LoginClient createLoginClient() {
        return new LoginClient(this);
    }

    LoginClient getLoginClient() {
        return this.loginClient;
    }

    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        this.loginClient.onActivityResult(n, n2, intent);
    }

    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        if (object != null) {
            this.loginClient = (LoginClient)object.getParcelable(SAVED_LOGIN_CLIENT);
            this.loginClient.setFragment(this);
        } else {
            this.loginClient = this.createLoginClient();
        }
        this.loginClient.setOnCompletedListener(new LoginClient.OnCompletedListener(){

            @Override
            public void onCompleted(LoginClient.Result result) {
                LoginFragment.this.onLoginClientCompleted(result);
            }
        });
        object = this.getActivity();
        if (object == null) {
            return;
        }
        this.initializeCallingPackage((Activity)object);
        object = object.getIntent();
        if (object != null && (object = object.getBundleExtra(REQUEST_KEY)) != null) {
            this.request = (LoginClient.Request)object.getParcelable(EXTRA_REQUEST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        layoutInflater = layoutInflater.inflate(R.layout.com_facebook_login_fragment, viewGroup, false);
        viewGroup = layoutInflater.findViewById(R.id.com_facebook_login_fragment_progress_bar);
        this.loginClient.setBackgroundProcessingListener(new LoginClient.BackgroundProcessingListener((View)viewGroup){
            final /* synthetic */ View val$progressBar;
            {
                this.val$progressBar = view;
            }

            @Override
            public void onBackgroundProcessingStarted() {
                this.val$progressBar.setVisibility(0);
            }

            @Override
            public void onBackgroundProcessingStopped() {
                this.val$progressBar.setVisibility(8);
            }
        });
        return layoutInflater;
    }

    @Override
    public void onDestroy() {
        this.loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        View view = this.getView() == null ? null : this.getView().findViewById(R.id.com_facebook_login_fragment_progress_bar);
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.callingPackage == null) {
            Log.e((String)TAG, (String)NULL_CALLING_PKG_ERROR_MSG);
            this.getActivity().finish();
            return;
        }
        this.loginClient.startOrContinueAuth(this.request);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(SAVED_LOGIN_CLIENT, (Parcelable)this.loginClient);
    }

}
