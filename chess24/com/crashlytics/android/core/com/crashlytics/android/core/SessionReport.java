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

class SessionReport
implements Report {
    private final Map<String, String> customHeaders;
    private final File file;
    private final File[] files;

    public SessionReport(File file) {
        this(file, Collections.emptyMap());
    }

    public SessionReport(File file, Map<String, String> map) {
        this.file = file;
        this.files = new File[]{file};
        this.customHeaders = new HashMap<String, String>(map);
        if (this.file.length() == 0L) {
            this.customHeaders.putAll(ReportUploader.HEADER_INVALID_CLS_FILE);
        }
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        return Collections.unmodifiableMap(this.customHeaders);
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public String getFileName() {
        return this.getFile().getName();
    }

    @Override
    public File[] getFiles() {
        return this.files;
    }

    @Override
    public String getIdentifier() {
        String string = this.getFileName();
        return string.substring(0, string.lastIndexOf(46));
    }

    @Override
    public Report.Type getType() {
        return Report.Type.JAVA;
    }

    @Override
    public void remove() {
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Removing report at ");
        stringBuilder.append(this.file.getPath());
        logger.d("CrashlyticsCore", stringBuilder.toString());
        this.file.delete();
    }
}
