/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.login;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.common.R;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.CustomTabLoginMethodHandler;
import com.facebook.login.DefaultAudience;
import com.facebook.login.DeviceAuthMethodHandler;
import com.facebook.login.FacebookLiteLoginMethodHandler;
import com.facebook.login.GetTokenLoginMethodHandler;
import com.facebook.login.KatanaProxyLoginMethodHandler;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginMethodHandler;
import com.facebook.login.WebViewLoginMethodHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class LoginClient
implements Parcelable {
    public static final Parcelable.Creator<LoginClient> CREATOR = new Parcelable.Creator(){

        public LoginClient createFromParcel(Parcel parcel) {
            return new LoginClient(parcel);
        }

        public LoginClient[] newArray(int n) {
            return new LoginClient[n];
        }
    };
    BackgroundProcessingListener backgroundProcessingListener;
    boolean checkedInternetPermission;
    int currentHandler = -1;
    Fragment fragment;
    LoginMethodHandler[] handlersToTry;
    Map<String, String> loggingExtras;
    private LoginLogger loginLogger;
    OnCompletedListener onCompletedListener;
    Request pendingRequest;

    public LoginClient(Parcel parcel) {
        Parcelable[] arrparcelable = parcel.readParcelableArray(LoginMethodHandler.class.getClassLoader());
        this.handlersToTry = new LoginMethodHandler[arrparcelable.length];
        for (int i = 0; i < arrparcelable.length; ++i) {
            this.handlersToTry[i] = (LoginMethodHandler)arrparcelable[i];
            this.handlersToTry[i].setLoginClient(this);
        }
        this.currentHandler = parcel.readInt();
        this.pendingRequest = (Request)parcel.readParcelable(Request.class.getClassLoader());
        this.loggingExtras = Utility.readStringMapFromParcel(parcel);
    }

    public LoginClient(Fragment fragment) {
        this.fragment = fragment;
    }

    private void addLoggingExtra(String string, String string2, boolean bl) {
        if (this.loggingExtras == null) {
            this.loggingExtras = new HashMap<String, String>();
        }
        CharSequence charSequence = string2;
        if (this.loggingExtras.containsKey(string)) {
            charSequence = string2;
            if (bl) {
                charSequence = new StringBuilder();
                charSequence.append(this.loggingExtras.get(string));
                charSequence.append(",");
                charSequence.append(string2);
                charSequence = charSequence.toString();
            }
        }
        this.loggingExtras.put(string, (String)charSequence);
    }

    private void completeWithFailure() {
        this.complete(Result.createErrorResult(this.pendingRequest, "Login attempt failed.", null));
    }

    private static AccessToken createFromTokenWithRefreshedPermissions(AccessToken accessToken, Collection<String> collection, Collection<String> collection2) {
        return new AccessToken(accessToken.getToken(), accessToken.getApplicationId(), accessToken.getUserId(), collection, collection2, accessToken.getSource(), accessToken.getExpires(), accessToken.getLastRefresh());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String getE2E() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("init", System.currentTimeMillis());
        }
        catch (JSONException jSONException) {
            return jSONObject.toString();
        }
        do {
            return jSONObject.toString();
            break;
        } while (true);
    }

    private LoginLogger getLogger() {
        if (this.loginLogger == null || !this.loginLogger.getApplicationId().equals(this.pendingRequest.getApplicationId())) {
            this.loginLogger = new LoginLogger((Context)this.getActivity(), this.pendingRequest.getApplicationId());
        }
        return this.loginLogger;
    }

    public static int getLoginRequestCode() {
        return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode();
    }

    private void logAuthorizationMethodComplete(String string, Result result, Map<String, String> map) {
        this.logAuthorizationMethodComplete(string, result.code.getLoggingValue(), result.errorMessage, result.errorCode, map);
    }

    private void logAuthorizationMethodComplete(String string, String string2, String string3, String string4, Map<String, String> map) {
        if (this.pendingRequest == null) {
            this.getLogger().logUnexpectedError("fb_mobile_login_method_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.", string);
            return;
        }
        this.getLogger().logAuthorizationMethodComplete(this.pendingRequest.getAuthId(), string, string2, string3, string4, map);
    }

    private void notifyOnCompleteListener(Result result) {
        if (this.onCompletedListener != null) {
            this.onCompletedListener.onCompleted(result);
        }
    }

    void authorize(Request request) {
        if (request == null) {
            return;
        }
        if (this.pendingRequest != null) {
            throw new FacebookException("Attempted to authorize while a request is pending.");
        }
        if (AccessToken.getCurrentAccessToken() != null && !this.checkInternetPermission()) {
            return;
        }
        this.pendingRequest = request;
        this.handlersToTry = this.getHandlersToTry(request);
        this.tryNextHandler();
    }

    void cancelCurrentHandler() {
        if (this.currentHandler >= 0) {
            this.getCurrentHandler().cancel();
        }
    }

    boolean checkInternetPermission() {
        if (this.checkedInternetPermission) {
            return true;
        }
        if (this.checkPermission("android.permission.INTERNET") != 0) {
            Object object = this.getActivity();
            String string2 = object.getString(R.string.com_facebook_internet_permission_error_title);
            object = object.getString(R.string.com_facebook_internet_permission_error_message);
            this.complete(Result.createErrorResult(this.pendingRequest, string2, (String)object));
            return false;
        }
        this.checkedInternetPermission = true;
        return true;
    }

    int checkPermission(String string2) {
        return this.getActivity().checkCallingOrSelfPermission(string2);
    }

    void complete(Result result) {
        LoginMethodHandler loginMethodHandler = this.getCurrentHandler();
        if (loginMethodHandler != null) {
            this.logAuthorizationMethodComplete(loginMethodHandler.getNameForLogging(), result, loginMethodHandler.methodLoggingExtras);
        }
        if (this.loggingExtras != null) {
            result.loggingExtras = this.loggingExtras;
        }
        this.handlersToTry = null;
        this.currentHandler = -1;
        this.pendingRequest = null;
        this.loggingExtras = null;
        this.notifyOnCompleteListener(result);
    }

    void completeAndValidate(Result result) {
        if (result.token != null && AccessToken.getCurrentAccessToken() != null) {
            this.validateSameFbidAndFinish(result);
            return;
        }
        this.complete(result);
    }

    public int describeContents() {
        return 0;
    }

    FragmentActivity getActivity() {
        return this.fragment.getActivity();
    }

    BackgroundProcessingListener getBackgroundProcessingListener() {
        return this.backgroundProcessingListener;
    }

    LoginMethodHandler getCurrentHandler() {
        if (this.currentHandler >= 0) {
            return this.handlersToTry[this.currentHandler];
        }
        return null;
    }

    public Fragment getFragment() {
        return this.fragment;
    }

    protected LoginMethodHandler[] getHandlersToTry(Request object) {
        ArrayList<LoginMethodHandler> arrayList = new ArrayList<LoginMethodHandler>();
        if ((object = object.getLoginBehavior()).allowsGetTokenAuth()) {
            arrayList.add(new GetTokenLoginMethodHandler(this));
        }
        if (object.allowsKatanaAuth()) {
            arrayList.add(new KatanaProxyLoginMethodHandler(this));
        }
        if (object.allowsFacebookLiteAuth()) {
            arrayList.add(new FacebookLiteLoginMethodHandler(this));
        }
        if (object.allowsCustomTabAuth()) {
            arrayList.add(new CustomTabLoginMethodHandler(this));
        }
        if (object.allowsWebViewAuth()) {
            arrayList.add(new WebViewLoginMethodHandler(this));
        }
        if (object.allowsDeviceAuth()) {
            arrayList.add(new DeviceAuthMethodHandler(this));
        }
        object = new LoginMethodHandler[arrayList.size()];
        arrayList.toArray((T[])object);
        return object;
    }

    boolean getInProgress() {
        if (this.pendingRequest != null && this.currentHandler >= 0) {
            return true;
        }
        return false;
    }

    OnCompletedListener getOnCompletedListener() {
        return this.onCompletedListener;
    }

    public Request getPendingRequest() {
        return this.pendingRequest;
    }

    void notifyBackgroundProcessingStart() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStarted();
        }
    }

    void notifyBackgroundProcessingStop() {
        if (this.backgroundProcessingListener != null) {
            this.backgroundProcessingListener.onBackgroundProcessingStopped();
        }
    }

    public boolean onActivityResult(int n, int n2, Intent intent) {
        if (this.pendingRequest != null) {
            return this.getCurrentHandler().onActivityResult(n, n2, intent);
        }
        return false;
    }

    void setBackgroundProcessingListener(BackgroundProcessingListener backgroundProcessingListener) {
        this.backgroundProcessingListener = backgroundProcessingListener;
    }

    void setFragment(Fragment fragment) {
        if (this.fragment != null) {
            throw new FacebookException("Can't set fragment once it is already set.");
        }
        this.fragment = fragment;
    }

    void setOnCompletedListener(OnCompletedListener onCompletedListener) {
        this.onCompletedListener = onCompletedListener;
    }

    void startOrContinueAuth(Request request) {
        if (!this.getInProgress()) {
            this.authorize(request);
        }
    }

    boolean tryCurrentHandler() {
        LoginMethodHandler loginMethodHandler = this.getCurrentHandler();
        if (loginMethodHandler.needsInternetPermission() && !this.checkInternetPermission()) {
            this.addLoggingExtra("no_internet_permission", "1", false);
            return false;
        }
        boolean bl = loginMethodHandler.tryAuthorize(this.pendingRequest);
        if (bl) {
            this.getLogger().logAuthorizationMethodStart(this.pendingRequest.getAuthId(), loginMethodHandler.getNameForLogging());
            return bl;
        }
        this.getLogger().logAuthorizationMethodNotTried(this.pendingRequest.getAuthId(), loginMethodHandler.getNameForLogging());
        this.addLoggingExtra("not_tried", loginMethodHandler.getNameForLogging(), true);
        return bl;
    }

    void tryNextHandler() {
        if (this.currentHandler >= 0) {
            this.logAuthorizationMethodComplete(this.getCurrentHandler().getNameForLogging(), "skipped", null, null, this.getCurrentHandler().methodLoggingExtras);
        }
        while (this.handlersToTry != null && this.currentHandler < this.handlersToTry.length - 1) {
            ++this.currentHandler;
            if (!this.tryCurrentHandler()) continue;
            return;
        }
        if (this.pendingRequest != null) {
            this.completeWithFailure();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void validateSameFbidAndFinish(Result var1_1) {
        if (var1_1.token == null) {
            throw new FacebookException("Can't validate without a token");
        }
        var2_3 = AccessToken.getCurrentAccessToken();
        var3_4 = var1_1.token;
        if (var2_3 == null || var3_4 == null) ** GOTO lbl-1000
        try {
            if (var2_3.getUserId().equals(var3_4.getUserId())) {
                var1_1 = Result.createTokenResult(this.pendingRequest, var1_1.token);
            } else lbl-1000: // 2 sources:
            {
                var1_1 = Result.createErrorResult(this.pendingRequest, "User logged in as different Facebook user.", null);
            }
            this.complete(var1_1);
            return;
        }
        catch (Exception var1_2) {
            this.complete(Result.createErrorResult(this.pendingRequest, "Caught exception", var1_2.getMessage()));
            return;
        }
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelableArray((Parcelable[])this.handlersToTry, n);
        parcel.writeInt(this.currentHandler);
        parcel.writeParcelable((Parcelable)this.pendingRequest, n);
        Utility.writeStringMapToParcel(parcel, this.loggingExtras);
    }

    static interface BackgroundProcessingListener {
        public void onBackgroundProcessingStarted();

        public void onBackgroundProcessingStopped();
    }

    public static interface OnCompletedListener {
        public void onCompleted(Result var1);
    }

    public static class Request
    implements Parcelable {
        public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator(){

            public Request createFromParcel(Parcel parcel) {
                return new Request(parcel);
            }

            public Request[] newArray(int n) {
                return new Request[n];
            }
        };
        private final String applicationId;
        private final String authId;
        private final DefaultAudience defaultAudience;
        private String deviceRedirectUriString;
        private boolean isRerequest;
        private final LoginBehavior loginBehavior;
        private Set<String> permissions;

        private Request(Parcel parcel) {
            boolean bl = false;
            this.isRerequest = false;
            Object object = parcel.readString();
            Object var4_4 = null;
            object = object != null ? LoginBehavior.valueOf(object) : null;
            this.loginBehavior = object;
            object = new ArrayList();
            parcel.readStringList((List)object);
            this.permissions = new HashSet<String>((Collection<String>)object);
            String string = parcel.readString();
            object = var4_4;
            if (string != null) {
                object = DefaultAudience.valueOf(string);
            }
            this.defaultAudience = object;
            this.applicationId = parcel.readString();
            this.authId = parcel.readString();
            if (parcel.readByte() != 0) {
                bl = true;
            }
            this.isRerequest = bl;
            this.deviceRedirectUriString = parcel.readString();
        }

        Request(LoginBehavior loginBehavior, Set<String> set, DefaultAudience defaultAudience, String string, String string2) {
            this.isRerequest = false;
            this.loginBehavior = loginBehavior;
            if (set == null) {
                set = new HashSet<String>();
            }
            this.permissions = set;
            this.defaultAudience = defaultAudience;
            this.applicationId = string;
            this.authId = string2;
        }

        public int describeContents() {
            return 0;
        }

        String getApplicationId() {
            return this.applicationId;
        }

        String getAuthId() {
            return this.authId;
        }

        DefaultAudience getDefaultAudience() {
            return this.defaultAudience;
        }

        String getDeviceRedirectUriString() {
            return this.deviceRedirectUriString;
        }

        LoginBehavior getLoginBehavior() {
            return this.loginBehavior;
        }

        Set<String> getPermissions() {
            return this.permissions;
        }

        boolean hasPublishPermission() {
            Iterator<String> iterator = this.permissions.iterator();
            while (iterator.hasNext()) {
                if (!LoginManager.isPublishPermission(iterator.next())) continue;
                return true;
            }
            return false;
        }

        boolean isRerequest() {
            return this.isRerequest;
        }

        void setDeviceRedirectUriString(String string) {
            this.deviceRedirectUriString = string;
        }

        void setPermissions(Set<String> set) {
            Validate.notNull(set, "permissions");
            this.permissions = set;
        }

        void setRerequest(boolean bl) {
            this.isRerequest = bl;
        }

        public void writeToParcel(Parcel parcel, int n) {
            Object object = this.loginBehavior;
            Object var4_4 = null;
            object = object != null ? this.loginBehavior.name() : null;
            parcel.writeString((String)object);
            parcel.writeStringList(new ArrayList<String>(this.permissions));
            object = var4_4;
            if (this.defaultAudience != null) {
                object = this.defaultAudience.name();
            }
            parcel.writeString((String)object);
            parcel.writeString(this.applicationId);
            parcel.writeString(this.authId);
            parcel.writeByte((byte)(this.isRerequest ? 1 : 0));
            parcel.writeString(this.deviceRedirectUriString);
        }

    }

    public static class Result
    implements Parcelable {
        public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator(){

            public Result createFromParcel(Parcel parcel) {
                return new Result(parcel);
            }

            public Result[] newArray(int n) {
                return new Result[n];
            }
        };
        final Result$Code code;
        final String errorCode;
        final String errorMessage;
        public Map<String, String> loggingExtras;
        final Request request;
        final AccessToken token;

        private Result(Parcel parcel) {
            this.code = Result$Code.valueOf(parcel.readString());
            this.token = (AccessToken)parcel.readParcelable(AccessToken.class.getClassLoader());
            this.errorMessage = parcel.readString();
            this.errorCode = parcel.readString();
            this.request = (Request)parcel.readParcelable(Request.class.getClassLoader());
            this.loggingExtras = Utility.readStringMapFromParcel(parcel);
        }

        Result(Request request, Result$Code result$Code, AccessToken accessToken, String string, String string2) {
            Validate.notNull((Object)result$Code, "code");
            this.request = request;
            this.token = accessToken;
            this.errorMessage = string;
            this.code = result$Code;
            this.errorCode = string2;
        }

        static Result createCancelResult(Request request, String string) {
            return new Result(request, Result$Code.CANCEL, null, string, null);
        }

        static Result createErrorResult(Request request, String string, String string2) {
            return Result.createErrorResult(request, string, string2, null);
        }

        static Result createErrorResult(Request request, String string, String string2, String string3) {
            string = TextUtils.join((CharSequence)": ", Utility.asListNoNulls(string, string2));
            return new Result(request, Result$Code.ERROR, null, string, string3);
        }

        static Result createTokenResult(Request request, AccessToken accessToken) {
            return new Result(request, Result$Code.SUCCESS, accessToken, null, null);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.code.name());
            parcel.writeParcelable((Parcelable)this.token, n);
            parcel.writeString(this.errorMessage);
            parcel.writeString(this.errorCode);
            parcel.writeParcelable((Parcelable)this.request, n);
            Utility.writeStringMapToParcel(parcel, this.loggingExtras);
        }

    }

    static enum Result$Code {
        SUCCESS("success"),
        CANCEL("cancel"),
        ERROR("error");
        
        private final String loggingValue;

        private Result$Code(String string2) {
            this.loggingValue = string2;
        }

        String getLoggingValue() {
            return this.loggingValue;
        }
    }

}
