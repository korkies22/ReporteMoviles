/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.appevents.internal.Constants;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.SmartLoginOption;
import com.facebook.internal.Utility;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class FetchedAppSettingsManager {
    private static final String APPLICATION_FIELDS = "fields";
    private static final String APP_SETTINGS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_SETTINGS.%s";
    private static final String APP_SETTINGS_PREFS_STORE = "com.facebook.internal.preferences.APP_SETTINGS";
    private static final String APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES = "android_sdk_error_categories";
    private static final String APP_SETTING_APP_EVENTS_FEATURE_BITMASK = "app_events_feature_bitmask";
    private static final String APP_SETTING_APP_EVENTS_SESSION_TIMEOUT = "app_events_session_timeout";
    private static final String APP_SETTING_CUSTOM_TABS_ENABLED = "gdpv4_chrome_custom_tabs_enabled";
    private static final String APP_SETTING_DIALOG_CONFIGS = "android_dialog_configs";
    private static final String[] APP_SETTING_FIELDS;
    private static final String APP_SETTING_NUX_CONTENT = "gdpv4_nux_content";
    private static final String APP_SETTING_NUX_ENABLED = "gdpv4_nux_enabled";
    private static final String APP_SETTING_SMART_LOGIN_OPTIONS = "seamless_login";
    private static final String APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING = "supports_implicit_sdk_logging";
    private static final int AUTOMATIC_LOGGING_ENABLED_BITMASK_FIELD = 8;
    private static final int IAP_AUTOMATIC_LOGGING_ENABLED_BITMASK_FIELD = 16;
    private static final String SMART_LOGIN_BOOKMARK_ICON_URL = "smart_login_bookmark_icon_url";
    private static final String SMART_LOGIN_MENU_ICON_URL = "smart_login_menu_icon_url";
    private static final String TAG;
    private static Map<String, FetchedAppSettings> fetchedAppSettings;
    private static AtomicBoolean loadingSettings;

    static {
        TAG = FetchedAppSettingsManager.class.getCanonicalName();
        APP_SETTING_FIELDS = new String[]{APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, APP_SETTING_NUX_CONTENT, APP_SETTING_NUX_ENABLED, APP_SETTING_CUSTOM_TABS_ENABLED, APP_SETTING_DIALOG_CONFIGS, APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES, APP_SETTING_APP_EVENTS_SESSION_TIMEOUT, APP_SETTING_APP_EVENTS_FEATURE_BITMASK, APP_SETTING_SMART_LOGIN_OPTIONS, SMART_LOGIN_BOOKMARK_ICON_URL, SMART_LOGIN_MENU_ICON_URL};
        fetchedAppSettings = new ConcurrentHashMap<String, FetchedAppSettings>();
        loadingSettings = new AtomicBoolean(false);
    }

    private static JSONObject getAppSettingsQueryResponse(String object) {
        Bundle bundle = new Bundle();
        bundle.putString(APPLICATION_FIELDS, TextUtils.join((CharSequence)",", (Object[])APP_SETTING_FIELDS));
        object = GraphRequest.newGraphPathRequest(null, (String)object, null);
        object.setSkipClientToken(true);
        object.setParameters(bundle);
        return object.executeAndWait().getJSONObject();
    }

    public static FetchedAppSettings getAppSettingsWithoutQuery(String string) {
        if (string != null) {
            return fetchedAppSettings.get(string);
        }
        return null;
    }

    public static void loadAppSettingsAsync() {
        final Context context = FacebookSdk.getApplicationContext();
        final String string = FacebookSdk.getApplicationId();
        boolean bl = loadingSettings.compareAndSet(false, true);
        if (!Utility.isNullOrEmpty(string) && !fetchedAppSettings.containsKey(string)) {
            if (!bl) {
                return;
            }
            final String string2 = String.format(APP_SETTINGS_PREFS_KEY_FORMAT, string);
            FacebookSdk.getExecutor().execute(new Runnable(){

                @Override
                public void run() {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(FetchedAppSettingsManager.APP_SETTINGS_PREFS_STORE, 0);
                    String string3 = string2;
                    Object object = null;
                    if (!Utility.isNullOrEmpty(string3 = sharedPreferences.getString(string3, null))) {
                        try {
                            string3 = new JSONObject(string3);
                            object = string3;
                        }
                        catch (JSONException jSONException) {
                            Utility.logd("FacebookSDK", (Exception)jSONException);
                        }
                        if (object != null) {
                            FetchedAppSettingsManager.parseAppSettingsFromJSON(string, object);
                        }
                    }
                    if ((object = FetchedAppSettingsManager.getAppSettingsQueryResponse(string)) != null) {
                        FetchedAppSettingsManager.parseAppSettingsFromJSON(string, object);
                        sharedPreferences.edit().putString(string2, object.toString()).apply();
                    }
                    AutomaticAnalyticsLogger.logActivateAppEvent();
                    FetchedAppSettingsManager.startInAppPurchaseAutoLogging(context);
                    loadingSettings.set(false);
                }
            });
            return;
        }
    }

    private static FetchedAppSettings parseAppSettingsFromJSON(String string, JSONObject object) {
        Object object2 = object.optJSONArray(APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES);
        object2 = object2 == null ? FacebookRequestErrorClassification.getDefaultErrorClassification() : FacebookRequestErrorClassification.createFromJSON(object2);
        int n = object.optInt(APP_SETTING_APP_EVENTS_FEATURE_BITMASK, 0);
        boolean bl = (n & 8) != 0;
        boolean bl2 = (n & 16) != 0;
        object = new FetchedAppSettings(object.optBoolean(APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, false), object.optString(APP_SETTING_NUX_CONTENT, ""), object.optBoolean(APP_SETTING_NUX_ENABLED, false), object.optBoolean(APP_SETTING_CUSTOM_TABS_ENABLED, false), object.optInt(APP_SETTING_APP_EVENTS_SESSION_TIMEOUT, Constants.getDefaultAppEventsSessionTimeoutInSeconds()), SmartLoginOption.parseOptions(object.optLong(APP_SETTING_SMART_LOGIN_OPTIONS)), FetchedAppSettingsManager.parseDialogConfigurations(object.optJSONObject(APP_SETTING_DIALOG_CONFIGS)), bl, (FacebookRequestErrorClassification)object2, object.optString(SMART_LOGIN_BOOKMARK_ICON_URL), object.optString(SMART_LOGIN_MENU_ICON_URL), bl2);
        fetchedAppSettings.put(string, (FetchedAppSettings)object);
        return object;
    }

    private static Map<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> parseDialogConfigurations(JSONObject object) {
        JSONArray jSONArray;
        HashMap<String, Map<String, FetchedAppSettings.DialogFeatureConfig>> hashMap = new HashMap<String, Map<String, FetchedAppSettings.DialogFeatureConfig>>();
        if (object != null && (jSONArray = object.optJSONArray("data")) != null) {
            for (int i = 0; i < jSONArray.length(); ++i) {
                FetchedAppSettings.DialogFeatureConfig dialogFeatureConfig = FetchedAppSettings.DialogFeatureConfig.parseDialogConfig(jSONArray.optJSONObject(i));
                if (dialogFeatureConfig == null) continue;
                String string = dialogFeatureConfig.getDialogName();
                Map<String, FetchedAppSettings.DialogFeatureConfig> map = hashMap.get(string);
                object = map;
                if (map == null) {
                    object = new HashMap();
                    hashMap.put(string, (Map<String, FetchedAppSettings.DialogFeatureConfig>)object);
                }
                object.put(dialogFeatureConfig.getFeatureName(), dialogFeatureConfig);
            }
        }
        return hashMap;
    }

    public static FetchedAppSettings queryAppSettings(String string, boolean bl) {
        if (!bl && fetchedAppSettings.containsKey(string)) {
            return fetchedAppSettings.get(string);
        }
        JSONObject jSONObject = FetchedAppSettingsManager.getAppSettingsQueryResponse(string);
        if (jSONObject == null) {
            return null;
        }
        return FetchedAppSettingsManager.parseAppSettingsFromJSON(string, jSONObject);
    }

    private static void startInAppPurchaseAutoLogging(final Context context) {
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.InAppPurchase.toRequestCode(), new CallbackManagerImpl.Callback(){

            @Override
            public boolean onActivityResult(final int n, final Intent intent) {
                FacebookSdk.getExecutor().execute(new Runnable(){

                    @Override
                    public void run() {
                        AutomaticAnalyticsLogger.logInAppPurchaseEvent(context, n, intent);
                    }
                });
                return true;
            }

        });
    }

}
