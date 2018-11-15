/*
 * Decompiled with CFR 0_134.
 */
package com.j256.ormlite.android.apptools;

import java.io.File;
import java.io.FileFilter;

static final class OrmLiteConfigUtil
implements FileFilter {
    OrmLiteConfigUtil() {
    }

    @Override
    public boolean accept(File file) {
        if (file.getName().equals(com.j256.ormlite.android.apptools.OrmLiteConfigUtil.RAW_DIR_NAME) && file.isDirectory()) {
            return true;
        }
        return false;
    }
}
