/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.appevents.internal.AutomaticAnalyticsLogger;
import com.facebook.internal.Utility;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

static final class FetchedAppSettingsManager
implements Runnable {
    final /* synthetic */ String val$applicationId;
    final /* synthetic */ Context val$context;
    final /* synthetic */ String val$settingsKey;

    FetchedAppSettingsManager(Context context, String string, String string2) {
        this.val$context = context;
        this.val$settingsKey = string;
        this.val$applicationId = string2;
    }

    @Override
    public void run() {
        SharedPreferences sharedPreferences = this.val$context.getSharedPreferences(com.facebook.internal.FetchedAppSettingsManager.APP_SETTINGS_PREFS_STORE, 0);
        String string = this.val$settingsKey;
        Object object = null;
        if (!Utility.isNullOrEmpty(string = sharedPreferences.getString(string, null))) {
            try {
                string = new JSONObject(string);
                object = string;
            }
            catch (JSONException jSONException) {
                Utility.logd("FacebookSDK", (Exception)jSONException);
            }
            if (object != null) {
                com.facebook.internal.FetchedAppSettingsManager.parseAppSettingsFromJSON(this.val$applicationId, object);
            }
        }
        if ((object = com.facebook.internal.FetchedAppSettingsManager.getAppSettingsQueryResponse(this.val$applicationId)) != null) {
            com.facebook.internal.FetchedAppSettingsManager.parseAppSettingsFromJSON(this.val$applicationId, object);
            sharedPreferences.edit().putString(this.val$settingsKey, object.toString()).apply();
        }
        AutomaticAnalyticsLogger.logActivateAppEvent();
        com.facebook.internal.FetchedAppSettingsManager.startInAppPurchaseAutoLogging(this.val$context);
        loadingSettings.set(false);
    }
}
