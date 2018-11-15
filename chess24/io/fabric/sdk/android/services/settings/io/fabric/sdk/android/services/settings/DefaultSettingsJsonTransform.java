/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import io.fabric.sdk.android.services.settings.AppIconSettingsData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import io.fabric.sdk.android.services.settings.SettingsJsonTransform;
import org.json.JSONException;
import org.json.JSONObject;

class DefaultSettingsJsonTransform
implements SettingsJsonTransform {
    DefaultSettingsJsonTransform() {
    }

    private AnalyticsSettingsData buildAnalyticsSessionDataFrom(JSONObject jSONObject) {
        return new AnalyticsSettingsData(jSONObject.optString("url", "https://e.crashlytics.com/spi/v2/events"), jSONObject.optInt("flush_interval_secs", 600), jSONObject.optInt("max_byte_size_per_file", 8000), jSONObject.optInt("max_file_count_per_send", 1), jSONObject.optInt("max_pending_send_file_count", 100), jSONObject.optBoolean("forward_to_google_analytics", false), jSONObject.optBoolean("include_purchase_events_in_forwarded_events", false), jSONObject.optBoolean("track_custom_events", true), jSONObject.optBoolean("track_predefined_events", true), jSONObject.optInt("sampling_rate", 1), jSONObject.optBoolean("flush_on_background", true));
    }

    private AppSettingsData buildAppDataFrom(JSONObject object) throws JSONException {
        String string = object.getString("identifier");
        String string2 = object.getString("status");
        String string3 = object.getString("url");
        String string4 = object.getString("reports_url");
        String string5 = object.getString("ndk_reports_url");
        boolean bl = object.optBoolean("update_required", false);
        object = object.has("icon") && object.getJSONObject("icon").has("hash") ? this.buildIconDataFrom(object.getJSONObject("icon")) : null;
        return new AppSettingsData(string, string2, string3, string4, string5, bl, (AppIconSettingsData)object);
    }

    private BetaSettingsData buildBetaSettingsDataFrom(JSONObject jSONObject) throws JSONException {
        return new BetaSettingsData(jSONObject.optString("update_endpoint", SettingsJsonConstants.BETA_UPDATE_ENDPOINT_DEFAULT), jSONObject.optInt("update_suspend_duration", 3600));
    }

    private FeaturesSettingsData buildFeaturesSessionDataFrom(JSONObject jSONObject) {
        return new FeaturesSettingsData(jSONObject.optBoolean("prompt_enabled", false), jSONObject.optBoolean("collect_logged_exceptions", true), jSONObject.optBoolean("collect_reports", true), jSONObject.optBoolean("collect_analytics", false), jSONObject.optBoolean("firebase_crashlytics_enabled", false));
    }

    private AppIconSettingsData buildIconDataFrom(JSONObject jSONObject) throws JSONException {
        return new AppIconSettingsData(jSONObject.getString("hash"), jSONObject.getInt("width"), jSONObject.getInt("height"));
    }

    private PromptSettingsData buildPromptDataFrom(JSONObject jSONObject) throws JSONException {
        return new PromptSettingsData(jSONObject.optString("title", "Send Crash Report?"), jSONObject.optString("message", "Looks like we crashed! Please help us fix the problem by sending a crash report."), jSONObject.optString("send_button_title", "Send"), jSONObject.optBoolean("show_cancel_button", true), jSONObject.optString("cancel_button_title", "Don't Send"), jSONObject.optBoolean("show_always_send_button", true), jSONObject.optString("always_send_button_title", "Always Send"));
    }

    private SessionSettingsData buildSessionDataFrom(JSONObject jSONObject) throws JSONException {
        return new SessionSettingsData(jSONObject.optInt("log_buffer_size", 64000), jSONObject.optInt("max_chained_exception_depth", 8), jSONObject.optInt("max_custom_exception_events", 64), jSONObject.optInt("max_custom_key_value_pairs", 64), jSONObject.optInt("identifier_mask", 255), jSONObject.optBoolean("send_session_without_crash", false), jSONObject.optInt("max_complete_sessions_count", 4));
    }

    private long getExpiresAtFrom(CurrentTimeProvider currentTimeProvider, long l, JSONObject jSONObject) throws JSONException {
        if (jSONObject.has("expires_at")) {
            return jSONObject.getLong("expires_at");
        }
        return currentTimeProvider.getCurrentTimeMillis() + l * 1000L;
    }

    private JSONObject toAnalyticsJson(AnalyticsSettingsData analyticsSettingsData) throws JSONException {
        return new JSONObject().put("url", (Object)analyticsSettingsData.analyticsURL).put("flush_interval_secs", analyticsSettingsData.flushIntervalSeconds).put("max_byte_size_per_file", analyticsSettingsData.maxByteSizePerFile).put("max_file_count_per_send", analyticsSettingsData.maxFileCountPerSend).put("max_pending_send_file_count", analyticsSettingsData.maxPendingSendFileCount);
    }

    private JSONObject toAppJson(AppSettingsData appSettingsData) throws JSONException {
        JSONObject jSONObject = new JSONObject().put("identifier", (Object)appSettingsData.identifier).put("status", (Object)appSettingsData.status).put("url", (Object)appSettingsData.url).put("reports_url", (Object)appSettingsData.reportsUrl).put("ndk_reports_url", (Object)appSettingsData.ndkReportsUrl).put("update_required", appSettingsData.updateRequired);
        if (appSettingsData.icon != null) {
            jSONObject.put("icon", (Object)this.toIconJson(appSettingsData.icon));
        }
        return jSONObject;
    }

    private JSONObject toBetaJson(BetaSettingsData betaSettingsData) throws JSONException {
        return new JSONObject().put("update_endpoint", (Object)betaSettingsData.updateUrl).put("update_suspend_duration", betaSettingsData.updateSuspendDurationSeconds);
    }

    private JSONObject toFeaturesJson(FeaturesSettingsData featuresSettingsData) throws JSONException {
        return new JSONObject().put("collect_logged_exceptions", featuresSettingsData.collectLoggedException).put("collect_reports", featuresSettingsData.collectReports).put("collect_analytics", featuresSettingsData.collectAnalytics);
    }

    private JSONObject toIconJson(AppIconSettingsData appIconSettingsData) throws JSONException {
        return new JSONObject().put("hash", (Object)appIconSettingsData.hash).put("width", appIconSettingsData.width).put("height", appIconSettingsData.height);
    }

    private JSONObject toPromptJson(PromptSettingsData promptSettingsData) throws JSONException {
        return new JSONObject().put("title", (Object)promptSettingsData.title).put("message", (Object)promptSettingsData.message).put("send_button_title", (Object)promptSettingsData.sendButtonTitle).put("show_cancel_button", promptSettingsData.showCancelButton).put("cancel_button_title", (Object)promptSettingsData.cancelButtonTitle).put("show_always_send_button", promptSettingsData.showAlwaysSendButton).put("always_send_button_title", (Object)promptSettingsData.alwaysSendButtonTitle);
    }

    private JSONObject toSessionJson(SessionSettingsData sessionSettingsData) throws JSONException {
        return new JSONObject().put("log_buffer_size", sessionSettingsData.logBufferSize).put("max_chained_exception_depth", sessionSettingsData.maxChainedExceptionDepth).put("max_custom_exception_events", sessionSettingsData.maxCustomExceptionEvents).put("max_custom_key_value_pairs", sessionSettingsData.maxCustomKeyValuePairs).put("identifier_mask", sessionSettingsData.identifierMask).put("send_session_without_crash", sessionSettingsData.sendSessionWithoutCrash);
    }

    @Override
    public SettingsData buildFromJson(CurrentTimeProvider currentTimeProvider, JSONObject jSONObject) throws JSONException {
        int n = jSONObject.optInt("settings_version", 0);
        int n2 = jSONObject.optInt("cache_duration", 3600);
        AppSettingsData appSettingsData = this.buildAppDataFrom(jSONObject.getJSONObject("app"));
        SessionSettingsData sessionSettingsData = this.buildSessionDataFrom(jSONObject.getJSONObject("session"));
        PromptSettingsData promptSettingsData = this.buildPromptDataFrom(jSONObject.getJSONObject("prompt"));
        FeaturesSettingsData featuresSettingsData = this.buildFeaturesSessionDataFrom(jSONObject.getJSONObject("features"));
        AnalyticsSettingsData analyticsSettingsData = this.buildAnalyticsSessionDataFrom(jSONObject.getJSONObject("analytics"));
        BetaSettingsData betaSettingsData = this.buildBetaSettingsDataFrom(jSONObject.getJSONObject("beta"));
        return new SettingsData(this.getExpiresAtFrom(currentTimeProvider, n2, jSONObject), appSettingsData, sessionSettingsData, promptSettingsData, featuresSettingsData, analyticsSettingsData, betaSettingsData, n, n2);
    }

    @Override
    public JSONObject toJson(SettingsData settingsData) throws JSONException {
        return new JSONObject().put("expires_at", settingsData.expiresAtMillis).put("cache_duration", settingsData.cacheDuration).put("settings_version", settingsData.settingsVersion).put("features", (Object)this.toFeaturesJson(settingsData.featuresData)).put("analytics", (Object)this.toAnalyticsJson(settingsData.analyticsSettingsData)).put("beta", (Object)this.toBetaJson(settingsData.betaSettingsData)).put("app", (Object)this.toAppJson(settingsData.appData)).put("session", (Object)this.toSessionJson(settingsData.sessionData)).put("prompt", (Object)this.toPromptJson(settingsData.promptData));
    }
}
