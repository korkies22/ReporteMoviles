/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ISBNParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

public final class ISBNResultParser
extends ResultParser {
    @Override
    public ISBNParsedResult parse(Result object) {
        if (object.getBarcodeFormat() != BarcodeFormat.EAN_13) {
            return null;
        }
        if ((object = ISBNResultParser.getMassagedText((Result)object)).length() != 13) {
            return null;
        }
        if (!object.startsWith("978") && !object.startsWith("979")) {
            return null;
        }
        return new ISBNParsedResult((String)object);
    }
}
