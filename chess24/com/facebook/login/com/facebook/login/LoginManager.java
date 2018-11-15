/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.facebook.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.facebook.AccessToken;
import com.facebook.AccessTokenSource;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginClient;
import com.facebook.login.LoginLogger;
import com.facebook.login.LoginMethodHandler;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginStatusClient;
import com.facebook.login.StartActivityDelegate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoginManager {
    private static final String EXPRESS_LOGIN_ALLOWED = "express_login_allowed";
    private static final String MANAGE_PERMISSION_PREFIX = "manage";
    private static final Set<String> OTHER_PUBLISH_PERMISSIONS = LoginManager.getOtherPublishPermissions();
    private static final String PREFERENCE_LOGIN_MANAGER = "com.facebook.loginManager";
    private static final String PUBLISH_PERMISSION_PREFIX = "publish";
    private static volatile LoginManager instance;
    private DefaultAudience defaultAudience = DefaultAudience.FRIENDS;
    private LoginBehavior loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
    private final SharedPreferences sharedPreferences;

    LoginManager() {
        Validate.sdkInitialized();
        this.sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(PREFERENCE_LOGIN_MANAGER, 0);
    }

    static LoginResult computeLoginResult(LoginClient.Request object, AccessToken accessToken) {
        Set<String> set = object.getPermissions();
        HashSet<String> hashSet = new HashSet<String>(accessToken.getPermissions());
        if (object.isRerequest()) {
            hashSet.retainAll(set);
        }
        object = new HashSet<String>(set);
        object.removeAll(hashSet);
        return new LoginResult(accessToken, hashSet, (Set<String>)object);
    }

    private LoginClient.Request createLoginRequestFromResponse(GraphResponse set) {
        Validate.notNull(set, "response");
        set = set.getRequest().getAccessToken();
        set = set != null ? set.getPermissions() : null;
        return this.createLoginRequest(set);
    }

    private void finishLogin(AccessToken accessToken, LoginClient.Request object, FacebookException facebookException, boolean bl, FacebookCallback<LoginResult> facebookCallback) {
        if (accessToken != null) {
            AccessToken.setCurrentAccessToken(accessToken);
            Profile.fetchProfileForCurrentAccessToken();
        }
        if (facebookCallback != null) {
            object = accessToken != null ? LoginManager.computeLoginResult((LoginClient.Request)object, accessToken) : null;
            if (!(bl || object != null && object.getRecentlyGrantedPermissions().size() == 0)) {
                if (facebookException != null) {
                    facebookCallback.onError(facebookException);
                    return;
                }
                if (accessToken != null) {
                    this.setExpressLoginStatus(true);
                    facebookCallback.onSuccess((LoginResult)object);
                    return;
                }
            } else {
                facebookCallback.onCancel();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LoginManager getInstance() {
        if (instance != null) return instance;
        synchronized (LoginManager.class) {
            if (instance != null) return instance;
            instance = new LoginManager();
            return instance;
        }
    }

    private static Set<String> getOtherPublishPermissions() {
        return Collections.unmodifiableSet(new HashSet<String>(){
            {
                this.add("ads_management");
                this.add("create_event");
                this.add("rsvp_event");
            }
        });
    }

    @Nullable
    private static Profile getProfileFromBundle(Bundle object) {
        String string = object.getString("com.facebook.platform.extra.PROFILE_NAME");
        String string2 = object.getString("com.facebook.platform.extra.PROFILE_FIRST_NAME");
        String string3 = object.getString("com.facebook.platform.extra.PROFILE_MIDDLE_NAME");
        String string4 = object.getString("com.facebook.platform.extra.PROFILE_LAST_NAME");
        String string5 = object.getString("com.facebook.platform.extra.PROFILE_LINK");
        object = object.getString("com.facebook.platform.extra.PROFILE_USER_ID");
        if (string != null && string2 != null && string3 != null && string4 != null && string5 != null && object != null) {
            return new Profile((String)object, string2, string3, string4, string, Uri.parse((String)string5));
        }
        return null;
    }

    private static void handleLoginStatusError(String object, String string, String string2, LoginLogger loginLogger, LoginStatusCallback loginStatusCallback) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append(": ");
        stringBuilder.append(string);
        object = new FacebookException(stringBuilder.toString());
        loginLogger.logLoginStatusError(string2, (Exception)object);
        loginStatusCallback.onError((Exception)object);
    }

    private boolean isExpressLoginAllowed() {
        return this.sharedPreferences.getBoolean(EXPRESS_LOGIN_ALLOWED, true);
    }

    static boolean isPublishPermission(String string) {
        if (string != null && (string.startsWith(PUBLISH_PERMISSION_PREFIX) || string.startsWith(MANAGE_PERMISSION_PREFIX) || OTHER_PUBLISH_PERMISSIONS.contains(string))) {
            return true;
        }
        return false;
    }

    private void logCompleteLogin(Context object, LoginClient.Result.Code code, Map<String, String> map, Exception exception, boolean bl, LoginClient.Request request) {
        LoginLogger loginLogger = LoginLoggerHolder.getLogger(object);
        if (loginLogger == null) {
            return;
        }
        if (request == null) {
            loginLogger.logUnexpectedError("fb_mobile_login_complete", "Unexpected call to logCompleteLogin with null pendingAuthorizationRequest.");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        object = bl ? "1" : "0";
        hashMap.put("try_login_activity", (String)object);
        loginLogger.logCompleteLogin(request.getAuthId(), hashMap, code, map, exception);
    }

    private void logInWithPublishPermissions(FragmentWrapper fragmentWrapper, Collection<String> object) {
        this.validatePublishPermissions((Collection<String>)object);
        object = this.createLoginRequest((Collection<String>)object);
        this.startLogin(new FragmentStartActivityDelegate(fragmentWrapper), (LoginClient.Request)object);
    }

    private void logInWithReadPermissions(FragmentWrapper fragmentWrapper, Collection<String> object) {
        this.validateReadPermissions((Collection<String>)object);
        object = this.createLoginRequest((Collection<String>)object);
        this.startLogin(new FragmentStartActivityDelegate(fragmentWrapper), (LoginClient.Request)object);
    }

    private void logStartLogin(Context object, LoginClient.Request request) {
        if ((object = LoginLoggerHolder.getLogger((Context)object)) != null && request != null) {
            object.logStartLogin(request);
        }
    }

    private void resolveError(FragmentWrapper fragmentWrapper, GraphResponse graphResponse) {
        this.startLogin(new FragmentStartActivityDelegate(fragmentWrapper), this.createLoginRequestFromResponse(graphResponse));
    }

    private boolean resolveIntent(Intent intent) {
        PackageManager packageManager = FacebookSdk.getApplicationContext().getPackageManager();
        boolean bl = false;
        if (packageManager.resolveActivity(intent, 0) != null) {
            bl = true;
        }
        return bl;
    }

    private void retrieveLoginStatusImpl(Context object, final LoginStatusCallback loginStatusCallback, long l) {
        final String string = FacebookSdk.getApplicationId();
        final String string2 = UUID.randomUUID().toString();
        final LoginLogger loginLogger = new LoginLogger((Context)object, string);
        if (!this.isExpressLoginAllowed()) {
            loginLogger.logLoginStatusFailure(string2);
            loginStatusCallback.onFailure();
            return;
        }
        object = new LoginStatusClient((Context)object, string, string2, FacebookSdk.getGraphApiVersion(), l);
        object.setCompletedListener(new PlatformServiceClient.CompletedListener(){

            @Override
            public void completed(Bundle object) {
                if (object != null) {
                    Object object2 = object.getString("com.facebook.platform.status.ERROR_TYPE");
                    String string3 = object.getString("com.facebook.platform.status.ERROR_DESCRIPTION");
                    if (object2 != null) {
                        LoginManager.handleLoginStatusError((String)object2, string3, string2, loginLogger, loginStatusCallback);
                        return;
                    }
                    string3 = object.getString("com.facebook.platform.extra.ACCESS_TOKEN");
                    long l = object.getLong("com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH");
                    ArrayList arrayList = object.getStringArrayList("com.facebook.platform.extra.PERMISSIONS");
                    String string22 = object.getString("signed request");
                    object2 = null;
                    if (!Utility.isNullOrEmpty(string22)) {
                        object2 = LoginMethodHandler.getUserIDFromSignedRequest(string22);
                    }
                    if (!(Utility.isNullOrEmpty(string3) || arrayList == null || arrayList.isEmpty() || Utility.isNullOrEmpty((String)object2))) {
                        object2 = new AccessToken(string3, string, (String)object2, arrayList, null, null, new Date(l), null);
                        AccessToken.setCurrentAccessToken((AccessToken)object2);
                        object = LoginManager.getProfileFromBundle(object);
                        if (object != null) {
                            Profile.setCurrentProfile((Profile)object);
                        } else {
                            Profile.fetchProfileForCurrentAccessToken();
                        }
                        loginLogger.logLoginStatusSuccess(string2);
                        loginStatusCallback.onCompleted((AccessToken)object2);
                        return;
                    }
                    loginLogger.logLoginStatusFailure(string2);
                    loginStatusCallback.onFailure();
                    return;
                }
                loginLogger.logLoginStatusFailure(string2);
                loginStatusCallback.onFailure();
            }
        });
        loginLogger.logLoginStatusStart(string2);
        if (!object.start()) {
            loginLogger.logLoginStatusFailure(string2);
            loginStatusCallback.onFailure();
        }
    }

    private void setExpressLoginStatus(boolean bl) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(EXPRESS_LOGIN_ALLOWED, bl);
        editor.apply();
    }

    private void startLogin(StartActivityDelegate startActivityDelegate, LoginClient.Request request) throws FacebookException {
        this.logStartLogin((Context)startActivityDelegate.getActivityContext(), request);
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback(){

            @Override
            public boolean onActivityResult(int n, Intent intent) {
                return LoginManager.this.onActivityResult(n, intent);
            }
        });
        if (!this.tryFacebookActivity(startActivityDelegate, request)) {
            FacebookException facebookException = new FacebookException("Log in attempt failed: FacebookActivity could not be started. Please make sure you added FacebookActivity to the AndroidManifest.");
            this.logCompleteLogin((Context)startActivityDelegate.getActivityContext(), LoginClient.Result.Code.ERROR, null, facebookException, false, request);
            throw facebookException;
        }
    }

    private boolean tryFacebookActivity(StartActivityDelegate startActivityDelegate, LoginClient.Request request) {
        if (!this.resolveIntent((Intent)(request = this.getFacebookActivityIntent(request)))) {
            return false;
        }
        try {
            startActivityDelegate.startActivityForResult((Intent)request, LoginClient.getLoginRequestCode());
            return true;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            return false;
        }
    }

    private void validatePublishPermissions(Collection<String> object) {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            if (LoginManager.isPublishPermission(string)) continue;
            throw new FacebookException(String.format("Cannot pass a read permission (%s) to a request for publish authorization", string));
        }
    }

    private void validateReadPermissions(Collection<String> object) {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            String string = (String)object.next();
            if (!LoginManager.isPublishPermission(string)) continue;
            throw new FacebookException(String.format("Cannot pass a publish or manage permission (%s) to a request for read authorization", string));
        }
    }

    protected LoginClient.Request createLoginRequest(Collection<String> hashSet) {
        LoginBehavior loginBehavior = this.loginBehavior;
        hashSet = hashSet != null ? new HashSet<String>(hashSet) : new HashSet<String>();
        hashSet = new LoginClient.Request(loginBehavior, Collections.unmodifiableSet(hashSet), this.defaultAudience, FacebookSdk.getApplicationId(), UUID.randomUUID().toString());
        boolean bl = AccessToken.getCurrentAccessToken() != null;
        hashSet.setRerequest(bl);
        return hashSet;
    }

    public DefaultAudience getDefaultAudience() {
        return this.defaultAudience;
    }

    protected Intent getFacebookActivityIntent(LoginClient.Request request) {
        Intent intent = new Intent();
        intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
        intent.setAction(request.getLoginBehavior().toString());
        Bundle bundle = new Bundle();
        bundle.putParcelable("request", (Parcelable)request);
        intent.putExtra("com.facebook.LoginFragment:Request", bundle);
        return intent;
    }

    public LoginBehavior getLoginBehavior() {
        return this.loginBehavior;
    }

    public void logInWithPublishPermissions(Activity activity, Collection<String> object) {
        this.validatePublishPermissions((Collection<String>)object);
        object = this.createLoginRequest((Collection<String>)object);
        this.startLogin(new ActivityStartActivityDelegate(activity), (LoginClient.Request)object);
    }

    public void logInWithPublishPermissions(Fragment fragment, Collection<String> collection) {
        this.logInWithPublishPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logInWithPublishPermissions(android.support.v4.app.Fragment fragment, Collection<String> collection) {
        this.logInWithPublishPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logInWithReadPermissions(Activity activity, Collection<String> object) {
        this.validateReadPermissions((Collection<String>)object);
        object = this.createLoginRequest((Collection<String>)object);
        this.startLogin(new ActivityStartActivityDelegate(activity), (LoginClient.Request)object);
    }

    public void logInWithReadPermissions(Fragment fragment, Collection<String> collection) {
        this.logInWithReadPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logInWithReadPermissions(android.support.v4.app.Fragment fragment, Collection<String> collection) {
        this.logInWithReadPermissions(new FragmentWrapper(fragment), collection);
    }

    public void logOut() {
        AccessToken.setCurrentAccessToken(null);
        Profile.setCurrentProfile(null);
        this.setExpressLoginStatus(false);
    }

    boolean onActivityResult(int n, Intent intent) {
        return this.onActivityResult(n, intent, null);
    }

    boolean onActivityResult(int n, Intent object, FacebookCallback<LoginResult> facebookCallback) {
        Object object2;
        LoginClient.Result.Code code = LoginClient.Result.Code.ERROR;
        Object object3 = null;
        Object object4 = null;
        Intent intent = null;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (object != null) {
            object2 = (LoginClient.Result)object.getParcelableExtra("com.facebook.LoginFragment:Result");
            if (object2 != null) {
                object3 = object2.request;
                code = object2.code;
                if (n == -1) {
                    if (object2.code == LoginClient.Result.Code.SUCCESS) {
                        object = object2.token;
                        object4 = intent;
                        bl3 = bl;
                    } else {
                        object4 = new FacebookAuthorizationException(object2.errorMessage);
                        object = null;
                        bl3 = bl;
                    }
                } else {
                    if (n == 0) {
                        bl3 = true;
                    }
                    object = null;
                    object4 = intent;
                }
                object2 = object2.loggingExtras;
                intent = object;
                object = object3;
            } else {
                intent = null;
                object2 = intent;
                object = object2;
                bl3 = bl2;
                object4 = object3;
            }
        } else if (n == 0) {
            code = LoginClient.Result.Code.CANCEL;
            bl3 = true;
            intent = object = null;
            object2 = intent;
        } else {
            intent = object = null;
            object2 = intent;
            bl3 = false;
        }
        object3 = object4;
        if (object4 == null) {
            object3 = object4;
            if (intent == null) {
                object3 = object4;
                if (!bl3) {
                    object3 = new FacebookException("Unexpected call to LoginManager.onActivityResult");
                }
            }
        }
        this.logCompleteLogin(null, code, (Map<String, String>)object2, (Exception)object3, true, (LoginClient.Request)object);
        this.finishLogin((AccessToken)intent, (LoginClient.Request)object, (FacebookException)object3, bl3, facebookCallback);
        return true;
    }

    public void registerCallback(CallbackManager callbackManager, final FacebookCallback<LoginResult> facebookCallback) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl)callbackManager).registerCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode(), new CallbackManagerImpl.Callback(){

            @Override
            public boolean onActivityResult(int n, Intent intent) {
                return LoginManager.this.onActivityResult(n, intent, facebookCallback);
            }
        });
    }

    public void resolveError(Activity activity, GraphResponse graphResponse) {
        this.startLogin(new ActivityStartActivityDelegate(activity), this.createLoginRequestFromResponse(graphResponse));
    }

    public void resolveError(Fragment fragment, GraphResponse graphResponse) {
        this.resolveError(new FragmentWrapper(fragment), graphResponse);
    }

    public void resolveError(android.support.v4.app.Fragment fragment, GraphResponse graphResponse) {
        this.resolveError(new FragmentWrapper(fragment), graphResponse);
    }

    public void retrieveLoginStatus(Context context, long l, LoginStatusCallback loginStatusCallback) {
        this.retrieveLoginStatusImpl(context, loginStatusCallback, l);
    }

    public void retrieveLoginStatus(Context context, LoginStatusCallback loginStatusCallback) {
        this.retrieveLoginStatus(context, 5000L, loginStatusCallback);
    }

    public LoginManager setDefaultAudience(DefaultAudience defaultAudience) {
        this.defaultAudience = defaultAudience;
        return this;
    }

    public LoginManager setLoginBehavior(LoginBehavior loginBehavior) {
        this.loginBehavior = loginBehavior;
        return this;
    }

    public void unregisterCallback(CallbackManager callbackManager) {
        if (!(callbackManager instanceof CallbackManagerImpl)) {
            throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
        }
        ((CallbackManagerImpl)callbackManager).unregisterCallback(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode());
    }

    private static class ActivityStartActivityDelegate
    implements StartActivityDelegate {
        private final Activity activity;

        ActivityStartActivityDelegate(Activity activity) {
            Validate.notNull((Object)activity, "activity");
            this.activity = activity;
        }

        @Override
        public Activity getActivityContext() {
            return this.activity;
        }

        @Override
        public void startActivityForResult(Intent intent, int n) {
            this.activity.startActivityForResult(intent, n);
        }
    }

    private static class FragmentStartActivityDelegate
    implements StartActivityDelegate {
        private final FragmentWrapper fragment;

        FragmentStartActivityDelegate(FragmentWrapper fragmentWrapper) {
            Validate.notNull(fragmentWrapper, "fragment");
            this.fragment = fragmentWrapper;
        }

        @Override
        public Activity getActivityContext() {
            return this.fragment.getActivity();
        }

        @Override
        public void startActivityForResult(Intent intent, int n) {
            this.fragment.startActivityForResult(intent, n);
        }
    }

    private static class LoginLoggerHolder {
        private static LoginLogger logger;

        private LoginLoggerHolder() {
        }

        private static LoginLogger getLogger(Context object) {
            synchronized (LoginLoggerHolder.class) {
                if (object == null) {
                    object = FacebookSdk.getApplicationContext();
                }
                if (object == null) {
                    return null;
                }
                try {
                    if (logger == null) {
                        logger = new LoginLogger((Context)object, FacebookSdk.getApplicationId());
                    }
                    object = logger;
                    return object;
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                }
            }
        }
    }

}
