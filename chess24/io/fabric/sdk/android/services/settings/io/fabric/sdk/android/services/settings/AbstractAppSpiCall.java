/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 */
package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.content.res.Resources;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.AppSpiCall;
import io.fabric.sdk.android.services.settings.IconRequest;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

abstract class AbstractAppSpiCall
extends AbstractSpiCall
implements AppSpiCall {
    public static final String APP_BUILD_VERSION_PARAM = "app[build_version]";
    public static final String APP_BUILT_SDK_VERSION_PARAM = "app[built_sdk_version]";
    public static final String APP_DISPLAY_VERSION_PARAM = "app[display_version]";
    public static final String APP_ICON_DATA_PARAM = "app[icon][data]";
    public static final String APP_ICON_HASH_PARAM = "app[icon][hash]";
    public static final String APP_ICON_HEIGHT_PARAM = "app[icon][height]";
    public static final String APP_ICON_PRERENDERED_PARAM = "app[icon][prerendered]";
    public static final String APP_ICON_WIDTH_PARAM = "app[icon][width]";
    public static final String APP_IDENTIFIER_PARAM = "app[identifier]";
    public static final String APP_INSTANCE_IDENTIFIER_PARAM = "app[instance_identifier]";
    public static final String APP_MIN_SDK_VERSION_PARAM = "app[minimum_sdk_version]";
    public static final String APP_NAME_PARAM = "app[name]";
    public static final String APP_SDK_MODULES_PARAM_BUILD_TYPE = "app[build][libraries][%s][type]";
    public static final String APP_SDK_MODULES_PARAM_PREFIX = "app[build][libraries][%s]";
    public static final String APP_SDK_MODULES_PARAM_VERSION = "app[build][libraries][%s][version]";
    public static final String APP_SOURCE_PARAM = "app[source]";
    static final String ICON_CONTENT_TYPE = "application/octet-stream";
    static final String ICON_FILE_NAME = "icon.png";

    public AbstractAppSpiCall(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, string, string2, httpRequestFactory, httpMethod);
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, AppRequestData appRequestData) {
        return httpRequest.header("X-CRASHLYTICS-API-KEY", appRequestData.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private HttpRequest applyMultipartDataTo(HttpRequest object, AppRequestData object22) {
        HttpRequest httpRequest;
        block13 : {
            httpRequest = object.part(APP_IDENTIFIER_PARAM, object22.appId).part(APP_NAME_PARAM, object22.name).part(APP_DISPLAY_VERSION_PARAM, object22.displayVersion).part(APP_BUILD_VERSION_PARAM, object22.buildVersion).part(APP_SOURCE_PARAM, object22.source).part(APP_MIN_SDK_VERSION_PARAM, object22.minSdkVersion).part(APP_BUILT_SDK_VERSION_PARAM, object22.builtSdkVersion);
            if (!CommonUtils.isNullOrEmpty(object22.instanceIdentifier)) {
                httpRequest.part(APP_INSTANCE_IDENTIFIER_PARAM, object22.instanceIdentifier);
            }
            if (object22.icon != null) {
                void var1_4;
                Object object2;
                block12 : {
                    block10 : {
                        object2 = object = this.kit.getContext().getResources().openRawResource(object22.icon.iconResourceId);
                        try {
                            void var4_12;
                            block11 : {
                                try {
                                    httpRequest.part(APP_ICON_HASH_PARAM, object22.icon.hash).part(APP_ICON_DATA_PARAM, ICON_FILE_NAME, ICON_CONTENT_TYPE, (InputStream)object).part(APP_ICON_WIDTH_PARAM, object22.icon.width).part(APP_ICON_HEIGHT_PARAM, object22.icon.height);
                                    break block10;
                                }
                                catch (Resources.NotFoundException notFoundException) {
                                    break block11;
                                }
                                catch (Throwable throwable) {
                                    object2 = null;
                                    break block12;
                                }
                                catch (Resources.NotFoundException notFoundException) {
                                    object = null;
                                }
                            }
                            object2 = object;
                            Logger logger = Fabric.getLogger();
                            object2 = object;
                            StringBuilder stringBuilder = new StringBuilder();
                            object2 = object;
                            stringBuilder.append("Failed to find app icon with resource ID: ");
                            object2 = object;
                            stringBuilder.append(object22.icon.iconResourceId);
                            object2 = object;
                            logger.e("Fabric", stringBuilder.toString(), (Throwable)var4_12);
                        }
                        catch (Throwable throwable) {
                            // empty catch block
                        }
                    }
                    CommonUtils.closeOrLog((Closeable)object, "Failed to close app icon InputStream.");
                    break block13;
                }
                CommonUtils.closeOrLog(object2, "Failed to close app icon InputStream.");
                throw var1_4;
            }
        }
        if (object22.sdkKits == null) return httpRequest;
        object = object22.sdkKits.iterator();
        while (object.hasNext()) {
            KitInfo kitInfo = (KitInfo)object.next();
            httpRequest.part(this.getKitVersionKey(kitInfo), kitInfo.getVersion());
            httpRequest.part(this.getKitBuildTypeKey(kitInfo), kitInfo.getBuildType());
        }
        return httpRequest;
    }

    String getKitBuildTypeKey(KitInfo kitInfo) {
        return String.format(Locale.US, APP_SDK_MODULES_PARAM_BUILD_TYPE, kitInfo.getIdentifier());
    }

    String getKitVersionKey(KitInfo kitInfo) {
        return String.format(Locale.US, APP_SDK_MODULES_PARAM_VERSION, kitInfo.getIdentifier());
    }

    @Override
    public boolean invoke(AppRequestData object) {
        Object object2 = this.applyMultipartDataTo(this.applyHeadersTo(this.getHttpRequest(), (AppRequestData)object), (AppRequestData)object);
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sending app info to ");
        stringBuilder.append(this.getUrl());
        logger.d("Fabric", stringBuilder.toString());
        if (object.icon != null) {
            logger = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("App icon hash is ");
            stringBuilder.append(object.icon.hash);
            logger.d("Fabric", stringBuilder.toString());
            logger = Fabric.getLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("App icon size is ");
            stringBuilder.append(object.icon.width);
            stringBuilder.append("x");
            stringBuilder.append(object.icon.height);
            logger.d("Fabric", stringBuilder.toString());
        }
        int n = object2.code();
        object = "POST".equals(object2.method()) ? "Create" : "Update";
        logger = Fabric.getLogger();
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append(" app request ID: ");
        stringBuilder.append(object2.header("X-REQUEST-ID"));
        logger.d("Fabric", stringBuilder.toString());
        object = Fabric.getLogger();
        object2 = new StringBuilder();
        object2.append("Result was ");
        object2.append(n);
        object.d("Fabric", object2.toString());
        if (ResponseParser.parse(n) == 0) {
            return true;
        }
        return false;
    }
}
