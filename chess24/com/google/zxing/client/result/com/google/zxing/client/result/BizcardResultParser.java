/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import java.util.ArrayList;

public final class BizcardResultParser
extends AbstractDoCoMoResultParser {
    private static String buildName(String string, String string2) {
        if (string == null) {
            return string2;
        }
        if (string2 == null) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(' ');
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    private static String[] buildPhoneNumbers(String string, String string2, String string3) {
        int n;
        ArrayList<String> arrayList = new ArrayList<String>(3);
        if (string != null) {
            arrayList.add(string);
        }
        if (string2 != null) {
            arrayList.add(string2);
        }
        if (string3 != null) {
            arrayList.add(string3);
        }
        if ((n = arrayList.size()) == 0) {
            return null;
        }
        return arrayList.toArray(new String[n]);
    }

    @Override
    public AddressBookParsedResult parse(Result object) {
        String string = BizcardResultParser.getMassagedText((Result)object);
        if (!string.startsWith("BIZCARD:")) {
            return null;
        }
        object = BizcardResultParser.buildName(BizcardResultParser.matchSingleDoCoMoPrefixedField("N:", string, true), BizcardResultParser.matchSingleDoCoMoPrefixedField("X:", string, true));
        String string2 = BizcardResultParser.matchSingleDoCoMoPrefixedField("T:", string, true);
        String string3 = BizcardResultParser.matchSingleDoCoMoPrefixedField("C:", string, true);
        String[] arrstring = BizcardResultParser.matchDoCoMoPrefixedField("A:", string, true);
        String string4 = BizcardResultParser.matchSingleDoCoMoPrefixedField("B:", string, true);
        String string5 = BizcardResultParser.matchSingleDoCoMoPrefixedField("M:", string, true);
        String string6 = BizcardResultParser.matchSingleDoCoMoPrefixedField("F:", string, true);
        string = BizcardResultParser.matchSingleDoCoMoPrefixedField("E:", string, true);
        return new AddressBookParsedResult(BizcardResultParser.maybeWrap((String)object), null, null, BizcardResultParser.buildPhoneNumbers(string4, string5, string6), null, BizcardResultParser.maybeWrap(string), null, null, null, arrstring, null, string3, null, string2, null, null);
    }
}
