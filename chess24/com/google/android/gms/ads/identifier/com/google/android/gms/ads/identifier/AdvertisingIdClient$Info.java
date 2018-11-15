/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.ads.identifier;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

public static final class AdvertisingIdClient.Info {
    private final String zzsp;
    private final boolean zzsq;

    public AdvertisingIdClient.Info(String string, boolean bl) {
        this.zzsp = string;
        this.zzsq = bl;
    }

    public String getId() {
        return this.zzsp;
    }

    public boolean isLimitAdTrackingEnabled() {
        return this.zzsq;
    }

    public String toString() {
        String string = this.zzsp;
        boolean bl = this.zzsq;
        StringBuilder stringBuilder = new StringBuilder(7 + String.valueOf(string).length());
        stringBuilder.append("{");
        stringBuilder.append(string);
        stringBuilder.append("}");
        stringBuilder.append(bl);
        return stringBuilder.toString();
    }
}
