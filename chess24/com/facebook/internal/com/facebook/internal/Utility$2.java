/*
 * Decompiled with CFR 0_134.
 */
package com.facebook.internal;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

static final class Utility
implements FilenameFilter {
    Utility() {
    }

    @Override
    public boolean accept(File file, String string) {
        return Pattern.matches("cpu[0-9]+", string);
    }
}
