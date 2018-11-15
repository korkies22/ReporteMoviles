/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

class SessionAnalyticsFilesSender
extends AbstractSpiCall
implements FilesSender {
    static final String FILE_CONTENT_TYPE = "application/vnd.crashlytics.android.events";
    static final String FILE_PARAM_NAME = "session_analytics_file_";
    private final String apiKey;

    public SessionAnalyticsFilesSender(Kit kit, String string, String string2, HttpRequestFactory httpRequestFactory, String string3) {
        super(kit, string, string2, httpRequestFactory, HttpMethod.POST);
        this.apiKey = string3;
    }

    @Override
    public boolean send(List<File> object) {
        Serializable serializable;
        Object object2 = this.getHttpRequest().header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("X-CRASHLYTICS-API-KEY", this.apiKey);
        Object object3 = object.iterator();
        boolean bl = false;
        int n = 0;
        while (object3.hasNext()) {
            serializable = object3.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(FILE_PARAM_NAME);
            stringBuilder.append(n);
            object2.part(stringBuilder.toString(), serializable.getName(), FILE_CONTENT_TYPE, (File)serializable);
            ++n;
        }
        object3 = Fabric.getLogger();
        serializable = new StringBuilder();
        serializable.append("Sending ");
        serializable.append(object.size());
        serializable.append(" analytics files to ");
        serializable.append(this.getUrl());
        object3.d("Answers", serializable.toString());
        n = object2.code();
        object = Fabric.getLogger();
        object2 = new StringBuilder();
        object2.append("Response code for analytics file send is ");
        object2.append(n);
        object.d("Answers", object2.toString());
        if (ResponseParser.parse(n) == 0) {
            bl = true;
        }
        return bl;
    }
}
