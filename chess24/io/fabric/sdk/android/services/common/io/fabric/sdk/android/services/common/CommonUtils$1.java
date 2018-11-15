/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.common;

import java.io.File;
import java.util.Comparator;

static final class CommonUtils
implements Comparator<File> {
    CommonUtils() {
    }

    @Override
    public int compare(File file, File file2) {
        return (int)(file.lastModified() - file2.lastModified());
    }
}
