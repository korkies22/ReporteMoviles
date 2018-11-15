/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class SMSParsedResult
extends ParsedResult {
    private final String body;
    private final String[] numbers;
    private final String subject;
    private final String[] vias;

    public SMSParsedResult(String string, String string2, String string3, String string4) {
        super(ParsedResultType.SMS);
        this.numbers = new String[]{string};
        this.vias = new String[]{string2};
        this.subject = string3;
        this.body = string4;
    }

    public SMSParsedResult(String[] arrstring, String[] arrstring2, String string, String string2) {
        super(ParsedResultType.SMS);
        this.numbers = arrstring;
        this.vias = arrstring2;
        this.subject = string;
        this.body = string2;
    }

    public String getBody() {
        return this.body;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(100);
        SMSParsedResult.maybeAppend(this.numbers, stringBuilder);
        SMSParsedResult.maybeAppend(this.subject, stringBuilder);
        SMSParsedResult.maybeAppend(this.body, stringBuilder);
        return stringBuilder.toString();
    }

    public String[] getNumbers() {
        return this.numbers;
    }

    public String getSMSURI() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sms:");
        boolean bl = false;
        boolean bl2 = true;
        for (n = 0; n < this.numbers.length; ++n) {
            if (bl2) {
                bl2 = false;
            } else {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.numbers[n]);
            if (this.vias == null || this.vias[n] == null) continue;
            stringBuilder.append(";via=");
            stringBuilder.append(this.vias[n]);
        }
        n = this.body != null ? 1 : 0;
        bl2 = bl;
        if (this.subject != null) {
            bl2 = true;
        }
        if (n != 0 || bl2) {
            stringBuilder.append('?');
            if (n != 0) {
                stringBuilder.append("body=");
                stringBuilder.append(this.body);
            }
            if (bl2) {
                if (n != 0) {
                    stringBuilder.append('&');
                }
                stringBuilder.append("subject=");
                stringBuilder.append(this.subject);
            }
        }
        return stringBuilder.toString();
    }

    public String getSubject() {
        return this.subject;
    }

    public String[] getVias() {
        return this.vias;
    }
}
