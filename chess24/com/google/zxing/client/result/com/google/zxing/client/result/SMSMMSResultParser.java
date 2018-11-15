/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import java.util.ArrayList;
import java.util.Collection;

public final class SMSMMSResultParser
extends ResultParser {
    private static void addNumberVia(Collection<String> object, Collection<String> collection, String string) {
        int n = string.indexOf(59);
        Object var4_4 = null;
        if (n < 0) {
            object.add((String)string);
            collection.add(null);
            return;
        }
        object.add((String)string.substring(0, n));
        string = string.substring(n + 1);
        object = var4_4;
        if (string.startsWith("via=")) {
            object = string.substring(4);
        }
        collection.add((String)object);
    }

    @Override
    public SMSParsedResult parse(Result object) {
        int n;
        String string = SMSMMSResultParser.getMassagedText((Result)object);
        boolean bl = string.startsWith("sms:");
        String string2 = null;
        if (!(bl || string.startsWith("SMS:") || string.startsWith("mms:") || string.startsWith("MMS:"))) {
            return null;
        }
        object = SMSMMSResultParser.parseNameValuePairs(string);
        int n2 = 0;
        if (object != null && !object.isEmpty()) {
            string2 = (String)object.get("subject");
            object = (String)object.get("body");
            n2 = 1;
        } else {
            object = null;
        }
        int n3 = string.indexOf(63, 4);
        string = n3 >= 0 && n2 != 0 ? string.substring(4, n3) : string.substring(4);
        n2 = -1;
        ArrayList<String> arrayList = new ArrayList<String>(1);
        ArrayList<String> arrayList2 = new ArrayList<String>(1);
        while ((n3 = string.indexOf(44, n = n2 + 1)) > n2) {
            SMSMMSResultParser.addNumberVia(arrayList, arrayList2, string.substring(n, n3));
            n2 = n3;
        }
        SMSMMSResultParser.addNumberVia(arrayList, arrayList2, string.substring(n));
        return new SMSParsedResult(arrayList.toArray(new String[arrayList.size()]), arrayList2.toArray(new String[arrayList2.size()]), string2, (String)object);
    }
}
