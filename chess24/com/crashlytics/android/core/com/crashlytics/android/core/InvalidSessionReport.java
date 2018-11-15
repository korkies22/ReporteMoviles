/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.Report;
import com.crashlytics.android.core.ReportUploader;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class InvalidSessionReport
implements Report {
    private final Map<String, String> customHeaders;
    private final File[] files;
    private final String identifier;

    public InvalidSessionReport(String string, File[] arrfile) {
        this.files = arrfile;
        this.customHeaders = new HashMap<String, String>(ReportUploader.HEADER_INVALID_CLS_FILE);
        this.identifier = string;
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        return Collections.unmodifiableMap(this.customHeaders);
    }

    @Override
    public File getFile() {
        return this.files[0];
    }

    @Override
    public String getFileName() {
        return this.files[0].getName();
    }

    @Override
    public File[] getFiles() {
        return this.files;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Report.Type getType() {
        return Report.Type.JAVA;
    }

    @Override
    public void remove() {
        for (File file : this.files) {
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Removing invalid report file at ");
            stringBuilder.append(file.getPath());
            logger.d("CrashlyticsCore", stringBuilder.toString());
            file.delete();
        }
    }
}
