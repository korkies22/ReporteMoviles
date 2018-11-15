/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.facebook.login;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginClient;
import com.facebook.login.WebLoginMethodHandler;

class WebViewLoginMethodHandler
extends WebLoginMethodHandler {
    public static final Parcelable.Creator<WebViewLoginMethodHandler> CREATOR = new Parcelable.Creator(){

        public WebViewLoginMethodHandler createFromParcel(Parcel parcel) {
            return new WebViewLoginMethodHandler(parcel);
        }

        public WebViewLoginMethodHandler[] newArray(int n) {
            return new WebViewLoginMethodHandler[n];
        }
    };
    private String e2e;
    private WebDialog loginDialog;

    WebViewLoginMethodHandler(Parcel parcel) {
        super(parcel);
        this.e2e = parcel.readString();
    }

    WebViewLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    @Override
    void cancel() {
        if (this.loginDialog != null) {
            this.loginDialog.cancel();
            this.loginDialog = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    @Override
    String getNameForLogging() {
        return "web_view";
    }

    @Override
    AccessTokenSource getTokenSource() {
        return AccessTokenSource.WEB_VIEW;
    }

    @Override
    boolean needsInternetPermission() {
        return true;
    }

    void onWebDialogComplete(LoginClient.Request request, Bundle bundle, FacebookException facebookException) {
        super.onComplete(request, bundle, facebookException);
    }

    @Override
    boolean tryAuthorize(LoginClient.Request object) {
        Bundle bundle = this.getParameters((LoginClient.Request)object);
        WebDialog.OnCompleteListener onCompleteListener = new WebDialog.OnCompleteListener((LoginClient.Request)object){
            final /* synthetic */ LoginClient.Request val$request;
            {
                this.val$request = request;
            }

            @Override
            public void onComplete(Bundle bundle, FacebookException facebookException) {
                WebViewLoginMethodHandler.this.onWebDialogComplete(this.val$request, bundle, facebookException);
            }
        };
        this.e2e = LoginClient.getE2E();
        this.addLoggingExtra("e2e", this.e2e);
        FragmentActivity fragmentActivity = this.loginClient.getActivity();
        this.loginDialog = new AuthDialogBuilder((Context)fragmentActivity, object.getApplicationId(), bundle).setE2E(this.e2e).setIsRerequest(object.isRerequest()).setOnCompleteListener(onCompleteListener).build();
        object = new FacebookDialogFragment();
        object.setRetainInstance(true);
        object.setDialog(this.loginDialog);
        object.show(fragmentActivity.getSupportFragmentManager(), "FacebookDialogFragment");
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.e2e);
    }

    static class AuthDialogBuilder
    extends WebDialog.Builder {
        private static final String OAUTH_DIALOG = "oauth";
        static final String REDIRECT_URI = "fbconnect://success";
        private String e2e;
        private boolean isRerequest;

        public AuthDialogBuilder(Context context, String string, Bundle bundle) {
            super(context, string, OAUTH_DIALOG, bundle);
        }

        @Override
        public WebDialog build() {
            Bundle bundle = this.getParameters();
            bundle.putString("redirect_uri", REDIRECT_URI);
            bundle.putString("client_id", this.getApplicationId());
            bundle.putString("e2e", this.e2e);
            bundle.putString("response_type", "token,signed_request");
            bundle.putString("return_scopes", "true");
            bundle.putString("auth_type", "rerequest");
            return WebDialog.newInstance(this.getContext(), OAUTH_DIALOG, bundle, this.getTheme(), this.getListener());
        }

        public AuthDialogBuilder setE2E(String string) {
            this.e2e = string;
            return this;
        }

        public AuthDialogBuilder setIsRerequest(boolean bl) {
            this.isRerequest = bl;
            return this;
        }
    }

}
