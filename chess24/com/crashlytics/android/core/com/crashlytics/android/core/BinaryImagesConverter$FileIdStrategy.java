/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.BinaryImagesConverter;
import java.io.File;
import java.io.IOException;

static interface BinaryImagesConverter.FileIdStrategy {
    public String createId(File var1) throws IOException;
}
