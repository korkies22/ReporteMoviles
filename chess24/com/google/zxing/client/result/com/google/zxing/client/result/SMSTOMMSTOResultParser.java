/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;

public final class SMSTOMMSTOResultParser
extends ResultParser {
    @Override
    public SMSParsedResult parse(Result object) {
        if (!((object = SMSTOMMSTOResultParser.getMassagedText((Result)object)).startsWith("smsto:") || object.startsWith("SMSTO:") || object.startsWith("mmsto:") || object.startsWith("MMSTO:"))) {
            return null;
        }
        String string = object.substring(6);
        int n = string.indexOf(58);
        if (n >= 0) {
            object = string.substring(n + 1);
            string = string.substring(0, n);
        } else {
            object = null;
        }
        return new SMSParsedResult(string, null, null, (String)object);
    }
}
