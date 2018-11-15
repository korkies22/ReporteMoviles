/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.Report;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.File;
import java.util.Map;

class NativeSessionReport
implements Report {
    private final File reportDirectory;

    public NativeSessionReport(File file) {
        this.reportDirectory = file;
    }

    @Override
    public Map<String, String> getCustomHeaders() {
        return null;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public File[] getFiles() {
        return this.reportDirectory.listFiles();
    }

    @Override
    public String getIdentifier() {
        return this.reportDirectory.getName();
    }

    @Override
    public Report.Type getType() {
        return Report.Type.NATIVE;
    }

    @Override
    public void remove() {
        for (File serializable2 : this.getFiles()) {
            Logger logger = Fabric.getLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Removing native report file at ");
            stringBuilder.append(serializable2.getPath());
            logger.d("CrashlyticsCore", stringBuilder.toString());
            serializable2.delete();
        }
        Logger logger = Fabric.getLogger();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Removing native report directory at ");
        stringBuilder.append(this.reportDirectory);
        logger.d("CrashlyticsCore", stringBuilder.toString());
        this.reportDirectory.delete();
    }
}
