/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CreateReportRequest;
import com.crashlytics.android.core.CreateReportSpiCall;
import com.crashlytics.android.core.Report;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class DefaultCreateReportSpiCall
extends AbstractSpiCall
implements CreateReportSpiCall {
    static final String FILE_CONTENT_TYPE = "application/octet-stream";
    static final String FILE_PARAM = "report[file]";
    static final String IDENTIFIER_PARAM = "report[identifier]";
    static final String MULTI_FILE_PARAM = "report[file";

    public DefaultCreateReportSpiCall(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory) {
        super(kit, string, string2, httpRequestFactory, HttpMethod.POST);
    }

    DefaultCreateReportSpiCall(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, string, string2, httpRequestFactory, httpMethod);
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, CreateReportRequest object) {
        httpRequest = httpRequest.header("X-CRASHLYTICS-API-KEY", object.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
        object = object.report.getCustomHeaders().entrySet().iterator();
        while (object.hasNext()) {
            httpRequest = httpRequest.header((Map.Entry)object.next());
        }
        return httpRequest;
    }

    private HttpRequest applyMultipartDataTo(HttpRequest httpRequest, Report report) {
        httpRequest.part(IDENTIFIER_PARAM, report.getIdentifier());
        if (report.getFiles().length == 1) {
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Adding single file ");
            stringBuilder.append(report.getFileName());
            stringBuilder.append(" to report ");
            stringBuilder.append(report.getIdentifier());
            logger.d("CrashlyticsCore", stringBuilder.toString());
            return httpRequest.part(FILE_PARAM, report.getFileName(), FILE_CONTENT_TYPE, report.getFile());
        }
        File[] arrfile = report.getFiles();
        int n = arrfile.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            File file = arrfile[i];
            Object object = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Adding file ");
            stringBuilder.append(file.getName());
            stringBuilder.append(" to report ");
            stringBuilder.append(report.getIdentifier());
            object.d("CrashlyticsCore", stringBuilder.toString());
            object = new StringBuilder();
            object.append(MULTI_FILE_PARAM);
            object.append(n2);
            object.append("]");
            httpRequest.part(object.toString(), file.getName(), FILE_CONTENT_TYPE, file);
            ++n2;
        }
        return httpRequest;
    }

    @Override
    public boolean invoke(CreateReportRequest object) {
        object = this.applyMultipartDataTo(this.applyHeadersTo(this.getHttpRequest(), (CreateReportRequest)object), object.report);
        Object object2 = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sending report to: ");
        stringBuilder.append(this.getUrl());
        object2.d("CrashlyticsCore", stringBuilder.toString());
        int n = object.code();
        object2 = Fabric.getLogger();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Create report request ID: ");
        stringBuilder.append(object.header("X-REQUEST-ID"));
        object2.d("CrashlyticsCore", stringBuilder.toString());
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
