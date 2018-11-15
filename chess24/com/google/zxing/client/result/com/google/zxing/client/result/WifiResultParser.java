/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.WifiParsedResult;

public final class WifiResultParser
extends ResultParser {
    @Override
    public WifiParsedResult parse(Result object) {
        String string = WifiResultParser.getMassagedText((Result)object);
        if (!string.startsWith("WIFI:")) {
            return null;
        }
        String string2 = WifiResultParser.matchSinglePrefixedField("S:", string, ';', false);
        if (string2 != null) {
            if (string2.isEmpty()) {
                return null;
            }
            String string3 = WifiResultParser.matchSinglePrefixedField("P:", string, ';', false);
            String string4 = WifiResultParser.matchSinglePrefixedField("T:", string, ';', false);
            object = string4;
            if (string4 == null) {
                object = "nopass";
            }
            return new WifiParsedResult((String)object, string2, string3, Boolean.parseBoolean(WifiResultParser.matchSinglePrefixedField("H:", string, ';', false)));
        }
        return null;
    }
}
