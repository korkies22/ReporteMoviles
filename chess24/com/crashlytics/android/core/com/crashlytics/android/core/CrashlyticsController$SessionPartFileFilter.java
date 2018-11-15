/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;

static class CrashlyticsController.SessionPartFileFilter
implements FilenameFilter {
    private final String sessionId;

    public CrashlyticsController.SessionPartFileFilter(String string) {
        this.sessionId = string;
    }

    @Override
    public boolean accept(File serializable, String string) {
        serializable = new StringBuilder();
        serializable.append(this.sessionId);
        serializable.append(".cls");
        boolean bl = string.equals(serializable.toString());
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        bl = bl2;
        if (string.contains(this.sessionId)) {
            bl = bl2;
            if (!string.endsWith(".cls_temp")) {
                bl = true;
            }
        }
        return bl;
    }
}
