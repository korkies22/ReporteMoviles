/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.services.settings.AppIconSettingsData;

public class AppSettingsData {
    public static final String STATUS_ACTIVATED = "activated";
    public static final String STATUS_CONFIGURED = "configured";
    public static final String STATUS_NEW = "new";
    public final AppIconSettingsData icon;
    public final String identifier;
    public final String ndkReportsUrl;
    public final String reportsUrl;
    public final String status;
    public final boolean updateRequired;
    public final String url;

    public AppSettingsData(String string, String string2, String string3, String string4, String string5, boolean bl, AppIconSettingsData appIconSettingsData) {
        this.identifier = string;
        this.status = string2;
        this.url = string3;
        this.reportsUrl = string4;
        this.ndkReportsUrl = string5;
        this.updateRequired = bl;
        this.icon = appIconSettingsData;
    }
}
