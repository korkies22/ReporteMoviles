/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ProductParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.oned.UPCEReader;

public final class ProductResultParser
extends ResultParser {
    @Override
    public ProductParsedResult parse(Result object) {
        BarcodeFormat barcodeFormat = object.getBarcodeFormat();
        if (barcodeFormat != BarcodeFormat.UPC_A && barcodeFormat != BarcodeFormat.UPC_E && barcodeFormat != BarcodeFormat.EAN_8 && barcodeFormat != BarcodeFormat.EAN_13) {
            return null;
        }
        String string = ProductResultParser.getMassagedText((Result)object);
        if (!ProductResultParser.isStringOfDigits(string, string.length())) {
            return null;
        }
        object = barcodeFormat == BarcodeFormat.UPC_E && string.length() == 8 ? UPCEReader.convertUPCEtoUPCA(string) : string;
        return new ProductParsedResult(string, (String)object);
    }
}
