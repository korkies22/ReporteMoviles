/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android;

public class KitInfo {
    private final String buildType;
    private final String identifier;
    private final String version;

    public KitInfo(String string, String string2, String string3) {
        this.identifier = string;
        this.version = string2;
        this.buildType = string3;
    }

    public String getBuildType() {
        return this.buildType;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getVersion() {
        return this.version;
    }
}
