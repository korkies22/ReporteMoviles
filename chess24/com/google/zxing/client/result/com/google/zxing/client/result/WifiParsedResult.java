/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class WifiParsedResult
extends ParsedResult {
    private final boolean hidden;
    private final String networkEncryption;
    private final String password;
    private final String ssid;

    public WifiParsedResult(String string, String string2, String string3) {
        this(string, string2, string3, false);
    }

    public WifiParsedResult(String string, String string2, String string3, boolean bl) {
        super(ParsedResultType.WIFI);
        this.ssid = string2;
        this.networkEncryption = string;
        this.password = string3;
        this.hidden = bl;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(80);
        WifiParsedResult.maybeAppend(this.ssid, stringBuilder);
        WifiParsedResult.maybeAppend(this.networkEncryption, stringBuilder);
        WifiParsedResult.maybeAppend(this.password, stringBuilder);
        WifiParsedResult.maybeAppend(Boolean.toString(this.hidden), stringBuilder);
        return stringBuilder.toString();
    }

    public String getNetworkEncryption() {
        return this.networkEncryption;
    }

    public String getPassword() {
        return this.password;
    }

    public String getSsid() {
        return this.ssid;
    }

    public boolean isHidden() {
        return this.hidden;
    }
}
