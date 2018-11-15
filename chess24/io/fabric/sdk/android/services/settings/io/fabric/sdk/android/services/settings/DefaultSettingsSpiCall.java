/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.SettingsRequest;
import io.fabric.sdk.android.services.settings.SettingsSpiCall;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall
extends AbstractSpiCall
implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String HEADER_ADVERTISING_TOKEN = "X-CRASHLYTICS-ADVERTISING-TOKEN";
    static final String HEADER_ANDROID_ID = "X-CRASHLYTICS-ANDROID-ID";
    static final String HEADER_DEVICE_MODEL = "X-CRASHLYTICS-DEVICE-MODEL";
    static final String HEADER_INSTALLATION_ID = "X-CRASHLYTICS-INSTALLATION-ID";
    static final String HEADER_OS_BUILD_VERSION = "X-CRASHLYTICS-OS-BUILD-VERSION";
    static final String HEADER_OS_DISPLAY_VERSION = "X-CRASHLYTICS-OS-DISPLAY-VERSION";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    public DefaultSettingsSpiCall(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory) {
        this(kit, string, string2, httpRequestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, string, string2, httpRequestFactory, httpMethod);
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, SettingsRequest settingsRequest) {
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-API-KEY", settingsRequest.apiKey);
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-API-CLIENT-TYPE", "android");
        this.applyNonNullHeader(httpRequest, "X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
        this.applyNonNullHeader(httpRequest, "Accept", "application/json");
        this.applyNonNullHeader(httpRequest, HEADER_DEVICE_MODEL, settingsRequest.deviceModel);
        this.applyNonNullHeader(httpRequest, HEADER_OS_BUILD_VERSION, settingsRequest.osBuildVersion);
        this.applyNonNullHeader(httpRequest, HEADER_OS_DISPLAY_VERSION, settingsRequest.osDisplayVersion);
        this.applyNonNullHeader(httpRequest, HEADER_INSTALLATION_ID, settingsRequest.installationId);
        if (TextUtils.isEmpty((CharSequence)settingsRequest.advertisingId)) {
            this.applyNonNullHeader(httpRequest, HEADER_ANDROID_ID, settingsRequest.androidId);
            return httpRequest;
        }
        this.applyNonNullHeader(httpRequest, HEADER_ADVERTISING_TOKEN, settingsRequest.advertisingId);
        return httpRequest;
    }

    private void applyNonNullHeader(HttpRequest httpRequest, String string, String string2) {
        if (string2 != null) {
            httpRequest.header(string, string2);
        }
    }

    private JSONObject getJsonObjectFrom(String string) {
        try {
            JSONObject jSONObject = new JSONObject(string);
            return jSONObject;
        }
        catch (Exception exception) {
            Object object = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to parse settings JSON from ");
            stringBuilder.append(this.getUrl());
            object.d("Fabric", stringBuilder.toString(), exception);
            Logger logger = Fabric.getLogger();
            object = new StringBuilder();
            object.append("Settings response ");
            object.append(string);
            logger.d("Fabric", object.toString());
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest object) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(BUILD_VERSION_PARAM, object.buildVersion);
        hashMap.put(DISPLAY_VERSION_PARAM, object.displayVersion);
        hashMap.put(SOURCE_PARAM, Integer.toString(object.source));
        if (object.iconHash != null) {
            hashMap.put(ICON_HASH, object.iconHash);
        }
        if (!CommonUtils.isNullOrEmpty((String)(object = object.instanceId))) {
            hashMap.put(INSTANCE_PARAM, (String)object);
        }
        return hashMap;
    }

    JSONObject handleResponse(HttpRequest object) {
        int n = object.code();
        Object object2 = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Settings result was: ");
        stringBuilder.append(n);
        object2.d("Fabric", stringBuilder.toString());
        if (this.requestWasSuccessful(n)) {
            return this.getJsonObjectFrom(object.body());
        }
        object = Fabric.getLogger();
        object2 = new StringBuilder();
        object2.append("Failed to retrieve settings from ");
        object2.append(this.getUrl());
        object.e("Fabric", object2.toString());
        return null;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public JSONObject invoke(SettingsRequest object) {
        Object object4;
        void var1_5;
        Object object3;
        block11 : {
            block10 : {
                Object object2;
                object4 = this.getQueryParamsFor((SettingsRequest)object);
                object3 = this.getHttpRequest((Map<String, String>)object4);
                object3 = object = this.applyHeadersTo((HttpRequest)object3, (SettingsRequest)object);
                try {
                    object2 = Fabric.getLogger();
                    object3 = object;
                    StringBuilder stringBuilder = new StringBuilder();
                    object3 = object;
                    stringBuilder.append("Requesting settings from ");
                    object3 = object;
                    stringBuilder.append(this.getUrl());
                    object3 = object;
                    object2.d("Fabric", stringBuilder.toString());
                    object3 = object;
                    object2 = Fabric.getLogger();
                    object3 = object;
                    stringBuilder = new StringBuilder();
                    object3 = object;
                    stringBuilder.append("Settings query params were: ");
                    object3 = object;
                    stringBuilder.append(object4);
                    object3 = object;
                    object2.d("Fabric", stringBuilder.toString());
                    object3 = object;
                    object4 = this.handleResponse((HttpRequest)object);
                    if (object == null) return object4;
                }
                catch (HttpRequest.HttpRequestException httpRequestException) {
                    break block10;
                }
                object3 = Fabric.getLogger();
                object2 = new StringBuilder();
                object2.append("Settings request ID: ");
                object2.append(object.header("X-REQUEST-ID"));
                object3.d("Fabric", object2.toString());
                return object4;
                catch (Throwable throwable) {
                    break block11;
                }
                catch (HttpRequest.HttpRequestException httpRequestException) {
                    object = object3;
                    break block10;
                }
                catch (Throwable throwable) {
                    object3 = null;
                    break block11;
                }
                catch (HttpRequest.HttpRequestException httpRequestException) {
                    object = null;
                }
            }
            object3 = object;
            try {
                Fabric.getLogger().e("Fabric", "Settings request failed.", (Throwable)object4);
                if (object == null) return null;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            object3 = Fabric.getLogger();
            object4 = new StringBuilder();
            object4.append("Settings request ID: ");
            object4.append(object.header("X-REQUEST-ID"));
            object3.d("Fabric", object4.toString());
            return null;
        }
        if (object3 == null) throw var1_5;
        object4 = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Settings request ID: ");
        stringBuilder.append(object3.header("X-REQUEST-ID"));
        object4.d("Fabric", stringBuilder.toString());
        throw var1_5;
    }

    boolean requestWasSuccessful(int n) {
        if (n != 200 && n != 201 && n != 202 && n != 203) {
            return false;
        }
        return true;
    }
}
