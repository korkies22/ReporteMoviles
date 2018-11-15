/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.TelParsedResult;

public final class TelResultParser
extends ResultParser {
    @Override
    public TelParsedResult parse(Result object) {
        String string = TelResultParser.getMassagedText((Result)object);
        if (!string.startsWith("tel:") && !string.startsWith("TEL:")) {
            return null;
        }
        if (string.startsWith("TEL:")) {
            object = new StringBuilder("tel:");
            object.append(string.substring(4));
            object = object.toString();
        } else {
            object = string;
        }
        int n = string.indexOf(63, 4);
        string = n < 0 ? string.substring(4) : string.substring(4, n);
        return new TelParsedResult(string, (String)object, null);
    }
}
