/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.crashlytics.android.beta;

import com.crashlytics.android.beta.BuildProperties;
import com.crashlytics.android.beta.CheckForUpdatesResponse;
import com.crashlytics.android.beta.CheckForUpdatesResponseTransform;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CheckForUpdatesRequest
extends AbstractSpiCall {
    static final String BETA_SOURCE = "3";
    static final String BUILD_VERSION = "build_version";
    static final String DISPLAY_VERSION = "display_version";
    static final String HEADER_BETA_TOKEN = "X-CRASHLYTICS-BETA-TOKEN";
    static final String INSTANCE = "instance";
    static final String SDK_ANDROID_DIR_TOKEN_TYPE = "3";
    static final String SOURCE = "source";
    private final CheckForUpdatesResponseTransform responseTransform;

    public CheckForUpdatesRequest(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory, CheckForUpdatesResponseTransform checkForUpdatesResponseTransform) {
        super(kit, string, string2, httpRequestFactory, HttpMethod.GET);
        this.responseTransform = checkForUpdatesResponseTransform;
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, String string, String string2) {
        httpRequest = httpRequest.header("Accept", "application/json");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Crashlytics Android SDK/");
        stringBuilder.append(this.kit.getVersion());
        return httpRequest.header("User-Agent", stringBuilder.toString()).header("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa").header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("X-CRASHLYTICS-API-KEY", string).header(HEADER_BETA_TOKEN, CheckForUpdatesRequest.createBetaTokenHeaderValueFor(string2));
    }

    static String createBetaTokenHeaderValueFor(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("3:");
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    private Map<String, String> getQueryParamsFor(BuildProperties buildProperties) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(BUILD_VERSION, buildProperties.versionCode);
        hashMap.put(DISPLAY_VERSION, buildProperties.versionName);
        hashMap.put(INSTANCE, buildProperties.buildId);
        hashMap.put(SOURCE, "3");
        return hashMap;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public CheckForUpdatesResponse invoke(String object, String object2, BuildProperties object3) {
        void var1_5;
        Object object4;
        block13 : {
            block14 : {
                StringBuilder stringBuilder;
                block12 : {
                    block11 : {
                        object4 = this.getQueryParamsFor((BuildProperties)object3);
                        object3 = this.getHttpRequest((Map<String, String>)object4);
                        object2 = object = this.applyHeadersTo((HttpRequest)object3, (String)object, (String)object2);
                        object3 = Fabric.getLogger();
                        object2 = object;
                        stringBuilder = new StringBuilder();
                        object2 = object;
                        stringBuilder.append("Checking for updates from ");
                        object2 = object;
                        stringBuilder.append(this.getUrl());
                        object2 = object;
                        object3.d("Beta", stringBuilder.toString());
                        object2 = object;
                        object3 = Fabric.getLogger();
                        object2 = object;
                        stringBuilder = new StringBuilder();
                        object2 = object;
                        stringBuilder.append("Checking for updates query params are: ");
                        object2 = object;
                        stringBuilder.append(object4);
                        object2 = object;
                        object3.d("Beta", stringBuilder.toString());
                        object2 = object;
                        if (!object.ok()) break block11;
                        object2 = object;
                        Fabric.getLogger().d("Beta", "Checking for updates was successful");
                        object2 = object;
                        object3 = new JSONObject(object.body());
                        object2 = object;
                        object3 = this.responseTransform.fromJson((JSONObject)object3);
                        if (object == null) return object3;
                        object = object.header("X-REQUEST-ID");
                        object2 = Fabric.getLogger();
                        object4 = new StringBuilder();
                        object4.append("Checking for updates request ID: ");
                        object4.append((String)object);
                        object2.d("Fabric", object4.toString());
                        return object3;
                    }
                    object2 = object;
                    try {
                        object3 = Fabric.getLogger();
                        object2 = object;
                        object4 = new StringBuilder();
                        object2 = object;
                        object4.append("Checking for updates failed. Response code: ");
                        object2 = object;
                        object4.append(object.code());
                        object2 = object;
                        object3.e("Beta", object4.toString());
                        if (object == null) return null;
                    }
                    catch (Exception exception) {
                        break block12;
                    }
                    object2 = object.header("X-REQUEST-ID");
                    object3 = Fabric.getLogger();
                    object = new StringBuilder();
                    break block14;
                    catch (Throwable throwable) {
                        object2 = object3;
                        break block13;
                    }
                    catch (Exception exception) {
                        object = object3;
                        object3 = exception;
                        break block12;
                    }
                    catch (Throwable throwable) {
                        object2 = null;
                        break block13;
                    }
                    catch (Exception exception) {
                        object = null;
                    }
                }
                object2 = object;
                try {
                    object4 = Fabric.getLogger();
                    object2 = object;
                    stringBuilder = new StringBuilder();
                    object2 = object;
                    stringBuilder.append("Error while checking for updates from ");
                    object2 = object;
                    stringBuilder.append(this.getUrl());
                    object2 = object;
                    object4.e("Beta", stringBuilder.toString(), (Throwable)object3);
                    if (object == null) return null;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                object2 = object.header("X-REQUEST-ID");
                object3 = Fabric.getLogger();
                object = new StringBuilder();
            }
            object.append("Checking for updates request ID: ");
            object.append((String)object2);
            object3.d("Fabric", object.toString());
            return null;
        }
        if (object2 == null) throw var1_5;
        object2 = object2.header("X-REQUEST-ID");
        object3 = Fabric.getLogger();
        object4 = new StringBuilder();
        object4.append("Checking for updates request ID: ");
        object4.append((String)object2);
        object3.d("Fabric", object4.toString());
        throw var1_5;
    }
}
