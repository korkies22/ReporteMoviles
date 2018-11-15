/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;

public final class AddressBookDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    private static String parseName(String string) {
        int n = string.indexOf(44);
        if (n >= 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string.substring(n + 1));
            stringBuilder.append(' ');
            stringBuilder.append(string.substring(0, n));
            return stringBuilder.toString();
        }
        return string;
    }

    @Override
    public AddressBookParsedResult parse(Result object) {
        String string = AddressBookDoCoMoResultParser.getMassagedText((Result)object);
        if (!string.startsWith("MECARD:")) {
            return null;
        }
        object = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("N:", string, true);
        if (object == null) {
            return null;
        }
        String string2 = AddressBookDoCoMoResultParser.parseName(object[0]);
        String string3 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SOUND:", string, true);
        String[] arrstring = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("TEL:", string, true);
        String[] arrstring2 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("EMAIL:", string, true);
        String string4 = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("NOTE:", string, false);
        String[] arrstring3 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("ADR:", string, true);
        object = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BDAY:", string, true);
        if (!AddressBookDoCoMoResultParser.isStringOfDigits((CharSequence)object, 8)) {
            object = null;
        }
        String[] arrstring4 = AddressBookDoCoMoResultParser.matchDoCoMoPrefixedField("URL:", string, true);
        string = AddressBookDoCoMoResultParser.matchSingleDoCoMoPrefixedField("ORG:", string, true);
        return new AddressBookParsedResult(AddressBookDoCoMoResultParser.maybeWrap(string2), null, string3, arrstring, null, arrstring2, null, null, string4, arrstring3, null, string, (String)object, null, arrstring4, null);
    }
}
