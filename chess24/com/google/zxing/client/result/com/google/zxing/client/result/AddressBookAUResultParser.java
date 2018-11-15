/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.ArrayList;

public final class AddressBookAUResultParser
extends ResultParser {
    private static String[] matchMultipleValuePrefix(String string, int n, String string2, boolean bl) {
        ArrayList<String> arrayList = null;
        for (int i = 1; i <= n; ++i) {
            ArrayList<String> arrayList2 = new StringBuilder();
            arrayList2.append(string);
            arrayList2.append(i);
            arrayList2.append(':');
            String string3 = AddressBookAUResultParser.matchSinglePrefixedField(((StringBuilder)((Object)arrayList2)).toString(), string2, '\r', bl);
            if (string3 == null) break;
            arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<String>(n);
            }
            arrayList2.add(string3);
            arrayList = arrayList2;
        }
        if (arrayList == null) {
            return null;
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    @Override
    public AddressBookParsedResult parse(Result arrstring) {
        String string = AddressBookAUResultParser.getMassagedText((Result)arrstring);
        boolean bl = string.contains("MEMORY");
        arrstring = null;
        if (bl) {
            if (!string.contains("\r\n")) {
                return null;
            }
            String string2 = AddressBookAUResultParser.matchSinglePrefixedField("NAME1:", string, '\r', true);
            String string3 = AddressBookAUResultParser.matchSinglePrefixedField("NAME2:", string, '\r', true);
            String[] arrstring2 = AddressBookAUResultParser.matchMultipleValuePrefix("TEL", 3, string, true);
            String[] arrstring3 = AddressBookAUResultParser.matchMultipleValuePrefix("MAIL", 3, string, true);
            String string4 = AddressBookAUResultParser.matchSinglePrefixedField("MEMORY:", string, '\r', false);
            if ((string = AddressBookAUResultParser.matchSinglePrefixedField("ADD:", string, '\r', true)) != null) {
                arrstring = new String[]{string};
            }
            return new AddressBookParsedResult(AddressBookAUResultParser.maybeWrap(string2), null, string3, arrstring2, null, arrstring3, null, null, string4, arrstring, null, null, null, null, null, null);
        }
        return null;
    }
}
