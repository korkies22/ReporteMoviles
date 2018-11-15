/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.facebook.AccessTokenSource;
import com.facebook.CustomTabMainActivity;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.FacebookServiceException;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.LoginClient;
import com.facebook.login.WebLoginMethodHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomTabLoginMethodHandler
extends WebLoginMethodHandler {
    private static final int API_EC_DIALOG_CANCEL = 4201;
    private static final int CHALLENGE_LENGTH = 20;
    private static final String[] CHROME_PACKAGES = new String[]{"com.android.chrome", "com.chrome.beta", "com.chrome.dev"};
    public static final Parcelable.Creator<CustomTabLoginMethodHandler> CREATOR = new Parcelable.Creator(){

        public CustomTabLoginMethodHandler createFromParcel(Parcel parcel) {
            return new CustomTabLoginMethodHandler(parcel);
        }

        public CustomTabLoginMethodHandler[] newArray(int n) {
            return new CustomTabLoginMethodHandler[n];
        }
    };
    private static final String CUSTOM_TABS_SERVICE_ACTION = "android.support.customtabs.action.CustomTabsService";
    private static final int CUSTOM_TAB_REQUEST_CODE = 1;
    private String currentPackage;
    private String expectedChallenge;

    CustomTabLoginMethodHandler(Parcel parcel) {
        super(parcel);
        this.expectedChallenge = parcel.readString();
    }

    CustomTabLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
        this.expectedChallenge = Utility.generateRandomString(20);
    }

    private String getChromePackage() {
        if (this.currentPackage != null) {
            return this.currentPackage;
        }
        Object object = this.loginClient.getActivity();
        Object object2 = new Intent(CUSTOM_TABS_SERVICE_ACTION);
        object2 = object.getPackageManager().queryIntentServices((Intent)object2, 0);
        if (object2 != null) {
            object = new HashSet<String>(Arrays.asList(CHROME_PACKAGES));
            object2 = object2.iterator();
            while (object2.hasNext()) {
                ServiceInfo serviceInfo = ((ResolveInfo)object2.next()).serviceInfo;
                if (serviceInfo == null || !object.contains(serviceInfo.packageName)) continue;
                this.currentPackage = serviceInfo.packageName;
                return this.currentPackage;
            }
        }
        return null;
    }

    private boolean isCustomTabsAllowed() {
        if (this.isCustomTabsEnabled() && this.getChromePackage() != null && this.isCustomTabsCompatibleWithAutofill() && Validate.hasCustomTabRedirectActivity(FacebookSdk.getApplicationContext())) {
            return true;
        }
        return false;
    }

    private boolean isCustomTabsCompatibleWithAutofill() {
        if (!Utility.isAutofillAvailable((Context)this.loginClient.getActivity())) {
            return true;
        }
        return false;
    }

    private boolean isCustomTabsEnabled() {
        FetchedAppSettings fetchedAppSettings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(Utility.getMetadataApplicationId((Context)this.loginClient.getActivity()));
        if (fetchedAppSettings != null && fetchedAppSettings.getCustomTabsEnabled()) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void onCustomTabComplete(String string, LoginClient.Request request) {
        if (string != null && string.startsWith(CustomTabMainActivity.getRedirectUrl())) {
            int n;
            Bundle bundle;
            String string2;
            String string3;
            block11 : {
                string = Uri.parse((String)string);
                bundle = Utility.parseUrlQueryString(string.getQuery());
                bundle.putAll(Utility.parseUrlQueryString(string.getFragment()));
                if (!this.validateChallengeParam(bundle)) {
                    super.onComplete(request, null, new FacebookException("Invalid state parameter"));
                    return;
                }
                string3 = string = bundle.getString("error");
                if (string == null) {
                    string3 = bundle.getString("error_type");
                }
                string = string2 = bundle.getString("error_msg");
                if (string2 == null) {
                    string = bundle.getString("error_message");
                }
                string2 = string;
                if (string == null) {
                    string2 = bundle.getString("error_description");
                }
                if (!Utility.isNullOrEmpty(string = bundle.getString("error_code"))) {
                    try {
                        n = Integer.parseInt(string);
                        break block11;
                    }
                    catch (NumberFormatException numberFormatException) {}
                }
                n = -1;
            }
            if (Utility.isNullOrEmpty(string3) && Utility.isNullOrEmpty(string2) && n == -1) {
                super.onComplete(request, bundle, null);
                return;
            }
            if (string3 != null && (string3.equals("access_denied") || string3.equals("OAuthAccessDeniedException"))) {
                super.onComplete(request, null, new FacebookOperationCanceledException());
                return;
            }
            if (n == 4201) {
                super.onComplete(request, null, new FacebookOperationCanceledException());
                return;
            }
            super.onComplete(request, null, new FacebookServiceException(new FacebookRequestError(n, string3, string2), string2));
        }
    }

    private boolean validateChallengeParam(Bundle object) {
        block3 : {
            try {
                object = object.getString("state");
                if (object != null) break block3;
                return false;
            }
            catch (JSONException jSONException) {
                return false;
            }
        }
        boolean bl = new JSONObject((String)object).getString("7_challenge").equals(this.expectedChallenge);
        return bl;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    String getNameForLogging() {
        return "custom_tab";
    }

    @Override
    protected String getSSODevice() {
        return "chrome_custom_tab";
    }

    @Override
    AccessTokenSource getTokenSource() {
        return AccessTokenSource.CHROME_CUSTOM_TAB;
    }

    @Override
    boolean onActivityResult(int n, int n2, Intent intent) {
        if (n != 1) {
            return super.onActivityResult(n, n2, intent);
        }
        LoginClient.Request request = this.loginClient.getPendingRequest();
        if (n2 == -1) {
            this.onCustomTabComplete(intent.getStringExtra(CustomTabMainActivity.EXTRA_URL), request);
            return true;
        }
        super.onComplete(request, null, new FacebookOperationCanceledException());
        return false;
    }

    @Override
    protected void putChallengeParam(JSONObject jSONObject) throws JSONException {
        jSONObject.put("7_challenge", (Object)this.expectedChallenge);
    }

    @Override
    boolean tryAuthorize(LoginClient.Request request) {
        if (!this.isCustomTabsAllowed()) {
            return false;
        }
        request = this.addExtraParameters(this.getParameters(request), request);
        Intent intent = new Intent((Context)this.loginClient.getActivity(), CustomTabMainActivity.class);
        intent.putExtra(CustomTabMainActivity.EXTRA_PARAMS, (Bundle)request);
        intent.putExtra(CustomTabMainActivity.EXTRA_CHROME_PACKAGE, this.getChromePackage());
        this.loginClient.getFragment().startActivityForResult(intent, 1);
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.expectedChallenge);
    }

}
