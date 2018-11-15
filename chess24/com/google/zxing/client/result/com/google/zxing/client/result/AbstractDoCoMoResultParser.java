/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ResultParser;

abstract class AbstractDoCoMoResultParser
extends ResultParser {
    AbstractDoCoMoResultParser() {
    }

    static String[] matchDoCoMoPrefixedField(String string, String string2, boolean bl) {
        return AbstractDoCoMoResultParser.matchPrefixedField(string, string2, ';', bl);
    }

    static String matchSingleDoCoMoPrefixedField(String string, String string2, boolean bl) {
        return AbstractDoCoMoResultParser.matchSinglePrefixedField(string, string2, ';', bl);
    }
}
