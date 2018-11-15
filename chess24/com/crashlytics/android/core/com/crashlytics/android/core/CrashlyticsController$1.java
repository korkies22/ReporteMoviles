/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.CrashlyticsController;
import java.io.File;

static final class CrashlyticsController
extends CrashlyticsController.FileNameContainsFilter {
    CrashlyticsController(String string) {
        super(string);
    }

    @Override
    public boolean accept(File file, String string) {
        if (super.accept(file, string) && string.endsWith(".cls")) {
            return true;
        }
        return false;
    }
}
