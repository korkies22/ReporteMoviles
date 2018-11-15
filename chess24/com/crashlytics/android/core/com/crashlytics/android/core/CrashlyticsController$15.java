/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

class CrashlyticsController
implements FilenameFilter {
    final /* synthetic */ Set val$invalidSessionIds;

    CrashlyticsController(Set set) {
        this.val$invalidSessionIds = set;
    }

    @Override
    public boolean accept(File file, String string) {
        if (string.length() < 35) {
            return false;
        }
        return this.val$invalidSessionIds.contains(string.substring(0, 35));
    }
}
