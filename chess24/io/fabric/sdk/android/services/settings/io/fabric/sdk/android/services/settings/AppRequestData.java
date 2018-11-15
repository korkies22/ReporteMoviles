/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.services.settings.IconRequest;
import java.util.Collection;

public class AppRequestData {
    public final String apiKey;
    public final String appId;
    public final String buildVersion;
    public final String builtSdkVersion;
    public final String displayVersion;
    public final IconRequest icon;
    public final String instanceIdentifier;
    public final String minSdkVersion;
    public final String name;
    public final Collection<KitInfo> sdkKits;
    public final int source;

    public AppRequestData(String string, String string2, String string3, String string4, String string5, String string6, int n, String string7, String string8, IconRequest iconRequest, Collection<KitInfo> collection) {
        this.apiKey = string;
        this.appId = string2;
        this.displayVersion = string3;
        this.buildVersion = string4;
        this.instanceIdentifier = string5;
        this.name = string6;
        this.source = n;
        this.minSdkVersion = string7;
        this.builtSdkVersion = string8;
        this.icon = iconRequest;
        this.sdkKits = collection;
    }
}
