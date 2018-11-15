/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  org.json.JSONException
 */
package com.facebook.internal;

import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.util.Collection;
import org.json.JSONException;

public final class ServerProtocol {
    private static final String DIALOG_AUTHORITY_FORMAT = "m.%s";
    public static final String DIALOG_CANCEL_URI = "fbconnect://cancel";
    public static final String DIALOG_PARAM_ACCESS_TOKEN = "access_token";
    public static final String DIALOG_PARAM_APP_ID = "app_id";
    public static final String DIALOG_PARAM_AUTH_TYPE = "auth_type";
    public static final String DIALOG_PARAM_CLIENT_ID = "client_id";
    public static final String DIALOG_PARAM_DEFAULT_AUDIENCE = "default_audience";
    public static final String DIALOG_PARAM_DISPLAY = "display";
    public static final String DIALOG_PARAM_DISPLAY_TOUCH = "touch";
    public static final String DIALOG_PARAM_E2E = "e2e";
    public static final String DIALOG_PARAM_LEGACY_OVERRIDE = "legacy_override";
    public static final String DIALOG_PARAM_REDIRECT_URI = "redirect_uri";
    public static final String DIALOG_PARAM_RESPONSE_TYPE = "response_type";
    public static final String DIALOG_PARAM_RETURN_SCOPES = "return_scopes";
    public static final String DIALOG_PARAM_SCOPE = "scope";
    public static final String DIALOG_PARAM_SDK_VERSION = "sdk";
    public static final String DIALOG_PARAM_SSO_DEVICE = "sso";
    public static final String DIALOG_PARAM_STATE = "state";
    public static final String DIALOG_PATH = "dialog/";
    public static final String DIALOG_REDIRECT_URI = "fbconnect://success";
    public static final String DIALOG_REREQUEST_AUTH_TYPE = "rerequest";
    public static final String DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST = "token,signed_request";
    public static final String DIALOG_RETURN_SCOPES_TRUE = "true";
    public static final String FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH = "touch";
    public static final String FALLBACK_DIALOG_PARAM_APP_ID = "app_id";
    public static final String FALLBACK_DIALOG_PARAM_BRIDGE_ARGS = "bridge_args";
    public static final String FALLBACK_DIALOG_PARAM_KEY_HASH = "android_key_hash";
    public static final String FALLBACK_DIALOG_PARAM_METHOD_ARGS = "method_args";
    public static final String FALLBACK_DIALOG_PARAM_METHOD_RESULTS = "method_results";
    public static final String FALLBACK_DIALOG_PARAM_VERSION = "version";
    private static final String GRAPH_API_VERSION = "v2.11";
    private static final String GRAPH_URL_FORMAT = "https://graph.%s";
    private static final String GRAPH_VIDEO_URL_FORMAT = "https://graph-video.%s";
    private static final String TAG = "com.facebook.internal.ServerProtocol";
    public static final String errorConnectionFailure = "CONNECTION_FAILURE";
    public static final Collection<String> errorsProxyAuthDisabled = Utility.unmodifiableCollection("service_disabled", "AndroidAuthKillSwitchException");
    public static final Collection<String> errorsUserCanceled = Utility.unmodifiableCollection("access_denied", "OAuthAccessDeniedException");

    public static final String getDefaultAPIVersion() {
        return GRAPH_API_VERSION;
    }

    public static final String getDialogAuthority() {
        return String.format(DIALOG_AUTHORITY_FORMAT, FacebookSdk.getFacebookDomain());
    }

    public static final String getGraphUrlBase() {
        return String.format(GRAPH_URL_FORMAT, FacebookSdk.getFacebookDomain());
    }

    public static final String getGraphVideoUrlBase() {
        return String.format(GRAPH_VIDEO_URL_FORMAT, FacebookSdk.getFacebookDomain());
    }

    public static Bundle getQueryParamsForPlatformActivityIntentWebFallback(String string, int n, Bundle object) {
        block5 : {
            Object object2;
            block6 : {
                CharSequence charSequence = FacebookSdk.getApplicationSignature(FacebookSdk.getApplicationContext());
                if (Utility.isNullOrEmpty((String)charSequence)) {
                    return null;
                }
                object2 = new Bundle();
                object2.putString(FALLBACK_DIALOG_PARAM_KEY_HASH, (String)charSequence);
                object2.putString("app_id", FacebookSdk.getApplicationId());
                object2.putInt(FALLBACK_DIALOG_PARAM_VERSION, n);
                object2.putString(DIALOG_PARAM_DISPLAY, "touch");
                charSequence = new Bundle();
                charSequence.putString("action_id", string);
                string = object;
                if (object == null) {
                    string = new Bundle();
                }
                try {
                    object = BundleJSONConverter.convertToJSON((Bundle)charSequence);
                    string = BundleJSONConverter.convertToJSON((Bundle)string);
                    if (object == null) break block5;
                    if (string != null) break block6;
                    return null;
                }
                catch (JSONException jSONException) {
                    object = LoggingBehavior.DEVELOPER_ERRORS;
                    object2 = TAG;
                    charSequence = new StringBuilder();
                    charSequence.append("Error creating Url -- ");
                    charSequence.append((Object)jSONException);
                    Logger.log((LoggingBehavior)((Object)object), 6, (String)object2, charSequence.toString());
                    return null;
                }
            }
            object2.putString(FALLBACK_DIALOG_PARAM_BRIDGE_ARGS, object.toString());
            object2.putString(FALLBACK_DIALOG_PARAM_METHOD_ARGS, string.toString());
            return object2;
        }
        return null;
    }
}
