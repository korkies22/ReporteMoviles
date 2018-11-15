/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class TextParsedResult
extends ParsedResult {
    private final String language;
    private final String text;

    public TextParsedResult(String string, String string2) {
        super(ParsedResultType.TEXT);
        this.text = string;
        this.language = string2;
    }

    @Override
    public String getDisplayResult() {
        return this.text;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getText() {
        return this.text;
    }
}
