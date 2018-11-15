/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.URIParsedResult;

public final class URLTOResultParser
extends ResultParser {
    @Override
    public URIParsedResult parse(Result object) {
        String string = URLTOResultParser.getMassagedText((Result)object);
        boolean bl = string.startsWith("urlto:");
        object = null;
        if (!bl && !string.startsWith("URLTO:")) {
            return null;
        }
        int n = string.indexOf(58, 6);
        if (n < 0) {
            return null;
        }
        if (n > 6) {
            object = string.substring(6, n);
        }
        return new URIParsedResult(string.substring(n + 1), (String)object);
    }
}
