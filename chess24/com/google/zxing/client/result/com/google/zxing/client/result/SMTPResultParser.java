/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

public final class SMTPResultParser
extends ResultParser {
    @Override
    public EmailAddressParsedResult parse(Result object) {
        Object object2;
        if (!(object = SMTPResultParser.getMassagedText((Result)object)).startsWith("smtp:") && !object.startsWith("SMTP:")) {
            return null;
        }
        String string = object.substring(5);
        int n = string.indexOf(58);
        if (n >= 0) {
            object = string.substring(n + 1);
            string = string.substring(0, n);
            n = object.indexOf(58);
            if (n >= 0) {
                object2 = object.substring(n + 1);
                object = object.substring(0, n);
            } else {
                object2 = null;
            }
        } else {
            object2 = object = null;
        }
        return new EmailAddressParsedResult(new String[]{string}, null, null, (String)object, (String)object2);
    }
}
