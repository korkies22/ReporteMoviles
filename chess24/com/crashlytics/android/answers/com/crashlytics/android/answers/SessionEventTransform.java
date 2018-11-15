/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import io.fabric.sdk.android.services.events.EventTransform;
import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventTransform
implements EventTransform<SessionEvent> {
    static final String ADVERTISING_ID_KEY = "advertisingId";
    static final String ANDROID_ID_KEY = "androidId";
    static final String APP_BUNDLE_ID_KEY = "appBundleId";
    static final String APP_VERSION_CODE_KEY = "appVersionCode";
    static final String APP_VERSION_NAME_KEY = "appVersionName";
    static final String BETA_DEVICE_TOKEN_KEY = "betaDeviceToken";
    static final String BUILD_ID_KEY = "buildId";
    static final String CUSTOM_ATTRIBUTES = "customAttributes";
    static final String CUSTOM_TYPE = "customType";
    static final String DETAILS_KEY = "details";
    static final String DEVICE_MODEL_KEY = "deviceModel";
    static final String EXECUTION_ID_KEY = "executionId";
    static final String INSTALLATION_ID_KEY = "installationId";
    static final String LIMIT_AD_TRACKING_ENABLED_KEY = "limitAdTrackingEnabled";
    static final String OS_VERSION_KEY = "osVersion";
    static final String PREDEFINED_ATTRIBUTES = "predefinedAttributes";
    static final String PREDEFINED_TYPE = "predefinedType";
    static final String TIMESTAMP_KEY = "timestamp";
    static final String TYPE_KEY = "type";

    SessionEventTransform() {
    }

    @TargetApi(value=9)
    public JSONObject buildJsonForEvent(SessionEvent sessionEvent) throws IOException {
        try {
            JSONObject jSONObject = new JSONObject();
            SessionEventMetadata sessionEventMetadata = sessionEvent.sessionEventMetadata;
            jSONObject.put(APP_BUNDLE_ID_KEY, (Object)sessionEventMetadata.appBundleId);
            jSONObject.put(EXECUTION_ID_KEY, (Object)sessionEventMetadata.executionId);
            jSONObject.put(INSTALLATION_ID_KEY, (Object)sessionEventMetadata.installationId);
            if (TextUtils.isEmpty((CharSequence)sessionEventMetadata.advertisingId)) {
                jSONObject.put(ANDROID_ID_KEY, (Object)sessionEventMetadata.androidId);
            } else {
                jSONObject.put(ADVERTISING_ID_KEY, (Object)sessionEventMetadata.advertisingId);
            }
            jSONObject.put(LIMIT_AD_TRACKING_ENABLED_KEY, (Object)sessionEventMetadata.limitAdTrackingEnabled);
            jSONObject.put(BETA_DEVICE_TOKEN_KEY, (Object)sessionEventMetadata.betaDeviceToken);
            jSONObject.put(BUILD_ID_KEY, (Object)sessionEventMetadata.buildId);
            jSONObject.put(OS_VERSION_KEY, (Object)sessionEventMetadata.osVersion);
            jSONObject.put(DEVICE_MODEL_KEY, (Object)sessionEventMetadata.deviceModel);
            jSONObject.put(APP_VERSION_CODE_KEY, (Object)sessionEventMetadata.appVersionCode);
            jSONObject.put(APP_VERSION_NAME_KEY, (Object)sessionEventMetadata.appVersionName);
            jSONObject.put(TIMESTAMP_KEY, sessionEvent.timestamp);
            jSONObject.put(TYPE_KEY, (Object)sessionEvent.type.toString());
            if (sessionEvent.details != null) {
                jSONObject.put(DETAILS_KEY, (Object)new JSONObject(sessionEvent.details));
            }
            jSONObject.put(CUSTOM_TYPE, (Object)sessionEvent.customType);
            if (sessionEvent.customAttributes != null) {
                jSONObject.put(CUSTOM_ATTRIBUTES, (Object)new JSONObject(sessionEvent.customAttributes));
            }
            jSONObject.put(PREDEFINED_TYPE, (Object)sessionEvent.predefinedType);
            if (sessionEvent.predefinedAttributes != null) {
                jSONObject.put(PREDEFINED_ATTRIBUTES, (Object)new JSONObject(sessionEvent.predefinedAttributes));
            }
            return jSONObject;
        }
        catch (JSONException jSONException) {
            if (Build.VERSION.SDK_INT >= 9) {
                throw new IOException(jSONException.getMessage(), (Throwable)jSONException);
            }
            throw new IOException(jSONException.getMessage());
        }
    }

    @Override
    public byte[] toBytes(SessionEvent sessionEvent) throws IOException {
        return this.buildJsonForEvent(sessionEvent).toString().getBytes("UTF-8");
    }
}
