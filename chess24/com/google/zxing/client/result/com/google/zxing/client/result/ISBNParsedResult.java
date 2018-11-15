/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class ISBNParsedResult
extends ParsedResult {
    private final String isbn;

    ISBNParsedResult(String string) {
        super(ParsedResultType.ISBN);
        this.isbn = string;
    }

    @Override
    public String getDisplayResult() {
        return this.isbn;
    }

    public String getISBN() {
        return this.isbn;
    }
}
