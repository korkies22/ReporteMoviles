/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.beta;

class CheckForUpdatesResponse {
    public final String buildVersion;
    public final String displayVersion;
    public final String instanceId;
    public final String packageName;
    public final String url;
    public final String versionString;

    public CheckForUpdatesResponse(String string, String string2, String string3, String string4, String string5, String string6) {
        this.url = string;
        this.versionString = string2;
        this.displayVersion = string3;
        this.buildVersion = string4;
        this.packageName = string5;
        this.instanceId = string6;
    }
}
