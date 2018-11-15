/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Bundle
 *  android.text.TextUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.DefaultAudience;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginClient;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class LoginLogger {
    static final String EVENT_EXTRAS_DEFAULT_AUDIENCE = "default_audience";
    static final String EVENT_EXTRAS_FACEBOOK_VERSION = "facebookVersion";
    static final String EVENT_EXTRAS_FAILURE = "failure";
    static final String EVENT_EXTRAS_IS_REAUTHORIZE = "isReauthorize";
    static final String EVENT_EXTRAS_LOGIN_BEHAVIOR = "login_behavior";
    static final String EVENT_EXTRAS_MISSING_INTERNET_PERMISSION = "no_internet_permission";
    static final String EVENT_EXTRAS_NEW_PERMISSIONS = "new_permissions";
    static final String EVENT_EXTRAS_NOT_TRIED = "not_tried";
    static final String EVENT_EXTRAS_PERMISSIONS = "permissions";
    static final String EVENT_EXTRAS_REQUEST_CODE = "request_code";
    static final String EVENT_EXTRAS_TRY_LOGIN_ACTIVITY = "try_login_activity";
    static final String EVENT_NAME_LOGIN_COMPLETE = "fb_mobile_login_complete";
    static final String EVENT_NAME_LOGIN_METHOD_COMPLETE = "fb_mobile_login_method_complete";
    static final String EVENT_NAME_LOGIN_METHOD_NOT_TRIED = "fb_mobile_login_method_not_tried";
    static final String EVENT_NAME_LOGIN_METHOD_START = "fb_mobile_login_method_start";
    static final String EVENT_NAME_LOGIN_START = "fb_mobile_login_start";
    static final String EVENT_NAME_LOGIN_STATUS_COMPLETE = "fb_mobile_login_status_complete";
    static final String EVENT_NAME_LOGIN_STATUS_START = "fb_mobile_login_status_start";
    static final String EVENT_PARAM_AUTH_LOGGER_ID = "0_auth_logger_id";
    static final String EVENT_PARAM_CHALLENGE = "7_challenge";
    static final String EVENT_PARAM_ERROR_CODE = "4_error_code";
    static final String EVENT_PARAM_ERROR_MESSAGE = "5_error_message";
    static final String EVENT_PARAM_EXTRAS = "6_extras";
    static final String EVENT_PARAM_LOGIN_RESULT = "2_result";
    static final String EVENT_PARAM_METHOD = "3_method";
    static final String EVENT_PARAM_METHOD_RESULT_SKIPPED = "skipped";
    static final String EVENT_PARAM_TIMESTAMP = "1_timestamp_ms";
    static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    private final AppEventsLogger appEventsLogger;
    private String applicationId;
    private String facebookVersion;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    LoginLogger(Context context, String string) {
        this.applicationId = string;
        this.appEventsLogger = AppEventsLogger.newLogger(context, string);
        try {
            context = context.getPackageManager();
            if (context == null) return;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return;
        }
        context = context.getPackageInfo(FACEBOOK_PACKAGE_NAME, 0);
        if (context == null) return;
        this.facebookVersion = context.versionName;
    }

    static Bundle newAuthorizationLoggingBundle(String string) {
        Bundle bundle = new Bundle();
        bundle.putLong(EVENT_PARAM_TIMESTAMP, System.currentTimeMillis());
        bundle.putString(EVENT_PARAM_AUTH_LOGGER_ID, string);
        bundle.putString(EVENT_PARAM_METHOD, "");
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, "");
        bundle.putString(EVENT_PARAM_ERROR_MESSAGE, "");
        bundle.putString(EVENT_PARAM_ERROR_CODE, "");
        bundle.putString(EVENT_PARAM_EXTRAS, "");
        return bundle;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void logAuthorizationMethodComplete(String string, String string2, String string3, String string4, String string5, Map<String, String> map) {
        string = LoginLogger.newAuthorizationLoggingBundle(string);
        if (string3 != null) {
            string.putString(EVENT_PARAM_LOGIN_RESULT, string3);
        }
        if (string4 != null) {
            string.putString(EVENT_PARAM_ERROR_MESSAGE, string4);
        }
        if (string5 != null) {
            string.putString(EVENT_PARAM_ERROR_CODE, string5);
        }
        if (map != null && !map.isEmpty()) {
            string.putString(EVENT_PARAM_EXTRAS, new JSONObject(map).toString());
        }
        string.putString(EVENT_PARAM_METHOD, string2);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_COMPLETE, null, (Bundle)string);
    }

    public void logAuthorizationMethodNotTried(String string, String string2) {
        string = LoginLogger.newAuthorizationLoggingBundle(string);
        string.putString(EVENT_PARAM_METHOD, string2);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_NOT_TRIED, null, (Bundle)string);
    }

    public void logAuthorizationMethodStart(String string, String string2) {
        string = LoginLogger.newAuthorizationLoggingBundle(string);
        string.putString(EVENT_PARAM_METHOD, string2);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_START, null, (Bundle)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void logCompleteLogin(String object, Map<String, String> object2, LoginClient.Result.Code object3, Map<String, String> map, Exception exception) {
        Bundle bundle = LoginLogger.newAuthorizationLoggingBundle((String)object);
        if (object3 != null) {
            bundle.putString(EVENT_PARAM_LOGIN_RESULT, object3.getLoggingValue());
        }
        if (exception != null && exception.getMessage() != null) {
            bundle.putString(EVENT_PARAM_ERROR_MESSAGE, exception.getMessage());
        }
        object = !object2.isEmpty() ? new JSONObject((Map)object2) : null;
        object3 = object;
        if (map != null) {
            object2 = object;
            if (object == null) {
                object2 = new JSONObject();
            }
            try {
                object = map.entrySet().iterator();
                do {
                    object3 = object2;
                    if (object.hasNext()) {
                        object3 = (Map.Entry)object.next();
                        object2.put((String)object3.getKey(), object3.getValue());
                        continue;
                    }
                    break;
                } while (true);
            }
            catch (JSONException jSONException) {
                object3 = object2;
            }
        }
        if (object3 != null) {
            bundle.putString(EVENT_PARAM_EXTRAS, object3.toString());
        }
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_COMPLETE, null, bundle);
    }

    public void logLoginStatusError(String string, Exception exception) {
        string = LoginLogger.newAuthorizationLoggingBundle(string);
        string.putString(EVENT_PARAM_LOGIN_RESULT, LoginClient.Result.Code.ERROR.getLoggingValue());
        string.putString(EVENT_PARAM_ERROR_MESSAGE, exception.toString());
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_COMPLETE, null, (Bundle)string);
    }

    public void logLoginStatusFailure(String string) {
        string = LoginLogger.newAuthorizationLoggingBundle(string);
        string.putString(EVENT_PARAM_LOGIN_RESULT, EVENT_EXTRAS_FAILURE);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_COMPLETE, null, (Bundle)string);
    }

    public void logLoginStatusStart(String string) {
        string = LoginLogger.newAuthorizationLoggingBundle(string);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_START, null, (Bundle)string);
    }

    public void logLoginStatusSuccess(String string) {
        string = LoginLogger.newAuthorizationLoggingBundle(string);
        string.putString(EVENT_PARAM_LOGIN_RESULT, LoginClient.Result.Code.SUCCESS.getLoggingValue());
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_COMPLETE, null, (Bundle)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void logStartLogin(LoginClient.Request request) {
        Bundle bundle;
        bundle = LoginLogger.newAuthorizationLoggingBundle(request.getAuthId());
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(EVENT_EXTRAS_LOGIN_BEHAVIOR, (Object)request.getLoginBehavior().toString());
            jSONObject.put(EVENT_EXTRAS_REQUEST_CODE, LoginClient.getLoginRequestCode());
            jSONObject.put(EVENT_EXTRAS_PERMISSIONS, (Object)TextUtils.join((CharSequence)",", request.getPermissions()));
            jSONObject.put(EVENT_EXTRAS_DEFAULT_AUDIENCE, (Object)request.getDefaultAudience().toString());
            jSONObject.put(EVENT_EXTRAS_IS_REAUTHORIZE, request.isRerequest());
            if (this.facebookVersion != null) {
                jSONObject.put(EVENT_EXTRAS_FACEBOOK_VERSION, (Object)this.facebookVersion);
            }
            bundle.putString(EVENT_PARAM_EXTRAS, jSONObject.toString());
        }
        catch (JSONException jSONException) {}
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_START, null, bundle);
    }

    public void logUnexpectedError(String string, String string2) {
        this.logUnexpectedError(string, string2, "");
    }

    public void logUnexpectedError(String string, String string2, String string3) {
        Bundle bundle = LoginLogger.newAuthorizationLoggingBundle("");
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, LoginClient.Result.Code.ERROR.getLoggingValue());
        bundle.putString(EVENT_PARAM_ERROR_MESSAGE, string2);
        bundle.putString(EVENT_PARAM_METHOD, string3);
        this.appEventsLogger.logSdkEvent(string, null, bundle);
    }
}
