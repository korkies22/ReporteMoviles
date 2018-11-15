/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

public class SettingsRequest {
    public final String advertisingId;
    public final String androidId;
    public final String apiKey;
    public final String buildVersion;
    public final String deviceModel;
    public final String displayVersion;
    public final String iconHash;
    public final String installationId;
    public final String instanceId;
    public final String osBuildVersion;
    public final String osDisplayVersion;
    public final int source;

    public SettingsRequest(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, int n, String string11) {
        this.apiKey = string;
        this.deviceModel = string2;
        this.osBuildVersion = string3;
        this.osDisplayVersion = string4;
        this.advertisingId = string5;
        this.installationId = string6;
        this.androidId = string7;
        this.instanceId = string8;
        this.displayVersion = string9;
        this.buildVersion = string10;
        this.source = n;
        this.iconHash = string11;
    }
}
