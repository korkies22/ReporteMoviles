/*
 * Decompiled with CFR 0_134.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import java.util.Map;

public final class ExpandedProductParsedResult
extends ParsedResult {
    public static final String KILOGRAM = "KG";
    public static final String POUND = "LB";
    private final String bestBeforeDate;
    private final String expirationDate;
    private final String lotNumber;
    private final String packagingDate;
    private final String price;
    private final String priceCurrency;
    private final String priceIncrement;
    private final String productID;
    private final String productionDate;
    private final String rawText;
    private final String sscc;
    private final Map<String, String> uncommonAIs;
    private final String weight;
    private final String weightIncrement;
    private final String weightType;

    public ExpandedProductParsedResult(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, String string10, String string11, String string12, String string13, String string14, Map<String, String> map) {
        super(ParsedResultType.PRODUCT);
        this.rawText = string;
        this.productID = string2;
        this.sscc = string3;
        this.lotNumber = string4;
        this.productionDate = string5;
        this.packagingDate = string6;
        this.bestBeforeDate = string7;
        this.expirationDate = string8;
        this.weight = string9;
        this.weightType = string10;
        this.weightIncrement = string11;
        this.price = string12;
        this.priceIncrement = string13;
        this.priceCurrency = string14;
        this.uncommonAIs = map;
    }

    private static boolean equalsOrNull(Object object, Object object2) {
        if (object == null) {
            if (object2 == null) {
                return true;
            }
            return false;
        }
        return object.equals(object2);
    }

    private static int hashNotNull(Object object) {
        if (object == null) {
            return 0;
        }
        return object.hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof ExpandedProductParsedResult)) {
            return false;
        }
        object = (ExpandedProductParsedResult)object;
        if (ExpandedProductParsedResult.equalsOrNull(this.productID, object.productID) && ExpandedProductParsedResult.equalsOrNull(this.sscc, object.sscc) && ExpandedProductParsedResult.equalsOrNull(this.lotNumber, object.lotNumber) && ExpandedProductParsedResult.equalsOrNull(this.productionDate, object.productionDate) && ExpandedProductParsedResult.equalsOrNull(this.bestBeforeDate, object.bestBeforeDate) && ExpandedProductParsedResult.equalsOrNull(this.expirationDate, object.expirationDate) && ExpandedProductParsedResult.equalsOrNull(this.weight, object.weight) && ExpandedProductParsedResult.equalsOrNull(this.weightType, object.weightType) && ExpandedProductParsedResult.equalsOrNull(this.weightIncrement, object.weightIncrement) && ExpandedProductParsedResult.equalsOrNull(this.price, object.price) && ExpandedProductParsedResult.equalsOrNull(this.priceIncrement, object.priceIncrement) && ExpandedProductParsedResult.equalsOrNull(this.priceCurrency, object.priceCurrency) && ExpandedProductParsedResult.equalsOrNull(this.uncommonAIs, object.uncommonAIs)) {
            return true;
        }
        return false;
    }

    public String getBestBeforeDate() {
        return this.bestBeforeDate;
    }

    @Override
    public String getDisplayResult() {
        return String.valueOf(this.rawText);
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public String getLotNumber() {
        return this.lotNumber;
    }

    public String getPackagingDate() {
        return this.packagingDate;
    }

    public String getPrice() {
        return this.price;
    }

    public String getPriceCurrency() {
        return this.priceCurrency;
    }

    public String getPriceIncrement() {
        return this.priceIncrement;
    }

    public String getProductID() {
        return this.productID;
    }

    public String getProductionDate() {
        return this.productionDate;
    }

    public String getRawText() {
        return this.rawText;
    }

    public String getSscc() {
        return this.sscc;
    }

    public Map<String, String> getUncommonAIs() {
        return this.uncommonAIs;
    }

    public String getWeight() {
        return this.weight;
    }

    public String getWeightIncrement() {
        return this.weightIncrement;
    }

    public String getWeightType() {
        return this.weightType;
    }

    public int hashCode() {
        return ExpandedProductParsedResult.hashNotNull(this.productID) ^ 0 ^ ExpandedProductParsedResult.hashNotNull(this.sscc) ^ ExpandedProductParsedResult.hashNotNull(this.lotNumber) ^ ExpandedProductParsedResult.hashNotNull(this.productionDate) ^ ExpandedProductParsedResult.hashNotNull(this.bestBeforeDate) ^ ExpandedProductParsedResult.hashNotNull(this.expirationDate) ^ ExpandedProductParsedResult.hashNotNull(this.weight) ^ ExpandedProductParsedResult.hashNotNull(this.weightType) ^ ExpandedProductParsedResult.hashNotNull(this.weightIncrement) ^ ExpandedProductParsedResult.hashNotNull(this.price) ^ ExpandedProductParsedResult.hashNotNull(this.priceIncrement) ^ ExpandedProductParsedResult.hashNotNull(this.priceCurrency) ^ ExpandedProductParsedResult.hashNotNull(this.uncommonAIs);
    }
}
