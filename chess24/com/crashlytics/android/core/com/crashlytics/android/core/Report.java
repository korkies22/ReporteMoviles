/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.core;

import java.io.File;
import java.util.Map;

interface Report {
    public Map<String, String> getCustomHeaders();

    public File getFile();

    public String getFileName();

    public File[] getFiles();

    public String getIdentifier();

    public Type getType();

    public void remove();

    public static enum Type {
        JAVA,
        NATIVE;
        

        private Type() {
        }
    }

}
