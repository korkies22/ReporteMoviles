/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.beta;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class BuildProperties {
    private static final String BUILD_ID = "build_id";
    private static final String PACKAGE_NAME = "package_name";
    private static final String VERSION_CODE = "version_code";
    private static final String VERSION_NAME = "version_name";
    public final String buildId;
    public final String packageName;
    public final String versionCode;
    public final String versionName;

    BuildProperties(String string, String string2, String string3, String string4) {
        this.versionCode = string;
        this.versionName = string2;
        this.buildId = string3;
        this.packageName = string4;
    }

    public static BuildProperties fromProperties(Properties properties) {
        return new BuildProperties(properties.getProperty(VERSION_CODE), properties.getProperty(VERSION_NAME), properties.getProperty(BUILD_ID), properties.getProperty(PACKAGE_NAME));
    }

    public static BuildProperties fromPropertiesStream(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return BuildProperties.fromProperties(properties);
    }
}
