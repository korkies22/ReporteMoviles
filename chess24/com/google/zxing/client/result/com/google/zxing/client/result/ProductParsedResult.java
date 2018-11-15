/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class ProductParsedResult
extends ParsedResult {
    private final String normalizedProductID;
    private final String productID;

    ProductParsedResult(String string) {
        this(string, string);
    }

    ProductParsedResult(String string, String string2) {
        super(ParsedResultType.PRODUCT);
        this.productID = string;
        this.normalizedProductID = string2;
    }

    @Override
    public String getDisplayResult() {
        return this.productID;
    }

    public String getNormalizedProductID() {
        return this.normalizedProductID;
    }

    public String getProductID() {
        return this.productID;
    }
}
