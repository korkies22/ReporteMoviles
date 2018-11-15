/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

final class SessionEventMetadata {
    public final String advertisingId;
    public final String androidId;
    public final String appBundleId;
    public final String appVersionCode;
    public final String appVersionName;
    public final String betaDeviceToken;
    public final String buildId;
    public final String deviceModel;
    public final String executionId;
    public final String installationId;
    public final Boolean limitAdTrackingEnabled;
    public final String osVersion;
    private String stringRepresentation;

    public SessionEventMetadata(String string, String string2, String string3, String string4, String string5, Boolean bl, String string6, String string7, String string8, String string9, String string10, String string11) {
        this.appBundleId = string;
        this.executionId = string2;
        this.installationId = string3;
        this.androidId = string4;
        this.advertisingId = string5;
        this.limitAdTrackingEnabled = bl;
        this.betaDeviceToken = string6;
        this.buildId = string7;
        this.osVersion = string8;
        this.deviceModel = string9;
        this.appVersionCode = string10;
        this.appVersionName = string11;
    }

    public String toString() {
        if (this.stringRepresentation == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("appBundleId=");
            stringBuilder.append(this.appBundleId);
            stringBuilder.append(", executionId=");
            stringBuilder.append(this.executionId);
            stringBuilder.append(", installationId=");
            stringBuilder.append(this.installationId);
            stringBuilder.append(", androidId=");
            stringBuilder.append(this.androidId);
            stringBuilder.append(", advertisingId=");
            stringBuilder.append(this.advertisingId);
            stringBuilder.append(", limitAdTrackingEnabled=");
            stringBuilder.append(this.limitAdTrackingEnabled);
            stringBuilder.append(", betaDeviceToken=");
            stringBuilder.append(this.betaDeviceToken);
            stringBuilder.append(", buildId=");
            stringBuilder.append(this.buildId);
            stringBuilder.append(", osVersion=");
            stringBuilder.append(this.osVersion);
            stringBuilder.append(", deviceModel=");
            stringBuilder.append(this.deviceModel);
            stringBuilder.append(", appVersionCode=");
            stringBuilder.append(this.appVersionCode);
            stringBuilder.append(", appVersionName=");
            stringBuilder.append(this.appVersionName);
            this.stringRepresentation = stringBuilder.toString();
        }
        return this.stringRepresentation;
    }
}
