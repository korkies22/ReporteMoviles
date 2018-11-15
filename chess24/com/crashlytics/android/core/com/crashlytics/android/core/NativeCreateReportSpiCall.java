/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CreateReportRequest;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.Report;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;

class NativeCreateReportSpiCall
extends AbstractSpiCall
implements CreateReportSpiCall {
    private static final String APP_META_FILE_MULTIPART_PARAM = "app_meta_file";
    private static final String BINARY_IMAGES_FILE_MULTIPART_PARAM = "binary_images_file";
    private static final String DEVICE_META_FILE_MULTIPART_PARAM = "device_meta_file";
    private static final String GZIP_FILE_CONTENT_TYPE = "application/octet-stream";
    private static final String KEYS_FILE_MULTIPART_PARAM = "keys_file";
    private static final String LOGS_FILE_MULTIPART_PARAM = "logs_file";
    private static final String METADATA_FILE_MULTIPART_PARAM = "crash_meta_file";
    private static final String MINIDUMP_FILE_MULTIPART_PARAM = "minidump_file";
    private static final String OS_META_FILE_MULTIPART_PARAM = "os_meta_file";
    private static final String REPORT_IDENTIFIER_PARAM = "report_id";
    private static final String SESSION_META_FILE_MULTIPART_PARAM = "session_meta_file";
    private static final String USER_META_FILE_MULTIPART_PARAM = "user_meta_file";

    public NativeCreateReportSpiCall(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory) {
        super(kit, string, string2, httpRequestFactory, HttpMethod.POST);
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Crashlytics Android SDK/");
        stringBuilder.append(this.kit.getVersion());
        httpRequest.header("User-Agent", stringBuilder.toString()).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("X-CRASHLYTICS-API-KEY", string);
        return httpRequest;
    }

    private HttpRequest applyMultipartDataTo(HttpRequest httpRequest, Report arrfile) {
        httpRequest.part(REPORT_IDENTIFIER_PARAM, arrfile.getIdentifier());
        for (File file : arrfile.getFiles()) {
            if (file.getName().equals("minidump")) {
                httpRequest.part(MINIDUMP_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("metadata")) {
                httpRequest.part(METADATA_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("binaryImages")) {
                httpRequest.part(BINARY_IMAGES_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("session")) {
                httpRequest.part(SESSION_META_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("app")) {
                httpRequest.part(APP_META_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("device")) {
                httpRequest.part(DEVICE_META_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("os")) {
                httpRequest.part(OS_META_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("user")) {
                httpRequest.part(USER_META_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (file.getName().equals("logs")) {
                httpRequest.part(LOGS_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
                continue;
            }
            if (!file.getName().equals("keys")) continue;
            httpRequest.part(KEYS_FILE_MULTIPART_PARAM, file.getName(), GZIP_FILE_CONTENT_TYPE, file);
        }
        return httpRequest;
    }

    @Override
    public boolean invoke(CreateReportRequest object) {
        object = this.applyMultipartDataTo(this.applyHeadersTo(this.getHttpRequest(), object.apiKey), object.report);
        Object object2 = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sending report to: ");
        stringBuilder.append(this.getUrl());
        object2.d("CrashlyticsCore", stringBuilder.toString());
        int n = object.code();
        object = Fabric.getLogger();
        object2 = new StringBuilder();
        object2.append("Result was: ");
        object2.append(n);
        object.d("CrashlyticsCore", object2.toString());
        if (ResponseParser.parse(n) == 0) {
            return true;
        }
        return false;
    }
}
