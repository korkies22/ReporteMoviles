/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.AbstractDoCoMoResultParser;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailDoCoMoResultParser
extends AbstractDoCoMoResultParser {
    private static final Pattern ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

    static boolean isBasicallyValidEmailAddress(String string) {
        if (string != null && ATEXT_ALPHANUMERIC.matcher(string).matches() && string.indexOf(64) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public EmailAddressParsedResult parse(Result object) {
        if (!(object = EmailDoCoMoResultParser.getMassagedText((Result)object)).startsWith("MATMSG:")) {
            return null;
        }
        String[] arrstring = EmailDoCoMoResultParser.matchDoCoMoPrefixedField("TO:", (String)object, true);
        if (arrstring == null) {
            return null;
        }
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (EmailDoCoMoResultParser.isBasicallyValidEmailAddress(arrstring[i])) continue;
            return null;
        }
        return new EmailAddressParsedResult(arrstring, null, null, EmailDoCoMoResultParser.matchSingleDoCoMoPrefixedField("SUB:", (String)object, false), EmailDoCoMoResultParser.matchSingleDoCoMoPrefixedField("BODY:", (String)object, false));
    }
}
