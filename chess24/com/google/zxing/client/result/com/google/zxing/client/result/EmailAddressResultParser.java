/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.EmailDoCoMoResultParser;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import java.util.regex.Pattern;

public final class EmailAddressResultParser
extends ResultParser {
    private static final Pattern COMMA = Pattern.compile(",");

    @Override
    public EmailAddressParsedResult parse(Result object) {
        Object object2;
        Object object3;
        Object object4 = EmailAddressResultParser.getMassagedText((Result)object);
        boolean bl = object4.startsWith("mailto:");
        String[] arrstring = null;
        if (!bl && !object4.startsWith("MAILTO:")) {
            if (!EmailDoCoMoResultParser.isBasicallyValidEmailAddress((String)object4)) {
                return null;
            }
            return new EmailAddressParsedResult((String)object4);
        }
        Object object5 = object4.substring(7);
        int n = object5.indexOf(63);
        object = object5;
        if (n >= 0) {
            object = object5.substring(0, n);
        }
        try {
            object = EmailAddressResultParser.urlDecode((String)object);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        object = !object.isEmpty() ? COMMA.split((CharSequence)object) : null;
        object4 = EmailAddressResultParser.parseNameValuePairs((String)object4);
        if (object4 != null) {
            object5 = object;
            if (object == null) {
                object3 = (String[])object4.get("to");
                object5 = object;
                if (object3 != null) {
                    object5 = COMMA.split((CharSequence)object3);
                }
            }
            object = (object = (String)object4.get("cc")) != null ? COMMA.split((CharSequence)object) : null;
            object3 = (String)object4.get("bcc");
            if (object3 != null) {
                arrstring = COMMA.split((CharSequence)object3);
            }
            object3 = (String)object4.get("subject");
            object2 = (String)object4.get("body");
            object4 = object;
            object = object5;
            object5 = object4;
            object4 = arrstring;
        } else {
            arrstring = object;
            Object var9_10 = null;
            object = var9_10;
            object2 = object5 = object;
            object3 = object5;
            object4 = object;
            object5 = var9_10;
            object = arrstring;
        }
        return new EmailAddressParsedResult((String[])object, (String[])object5, (String[])object4, (String)object3, (String)object2);
    }
}
