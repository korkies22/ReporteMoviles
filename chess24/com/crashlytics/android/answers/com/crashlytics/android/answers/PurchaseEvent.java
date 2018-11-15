/*
 * Decompiled with CFR 0_134.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.AnswersEventValidator;
import com.crashlytics.android.answers.PredefinedEvent;
import java.math.BigDecimal;
import java.util.Currency;

public class PurchaseEvent
extends PredefinedEvent<PurchaseEvent> {
    static final String CURRENCY_ATTRIBUTE = "currency";
    static final String ITEM_ID_ATTRIBUTE = "itemId";
    static final String ITEM_NAME_ATTRIBUTE = "itemName";
    static final String ITEM_PRICE_ATTRIBUTE = "itemPrice";
    static final String ITEM_TYPE_ATTRIBUTE = "itemType";
    static final BigDecimal MICRO_CONSTANT = BigDecimal.valueOf(1000000L);
    static final String SUCCESS_ATTRIBUTE = "success";
    static final String TYPE = "purchase";

    @Override
    String getPredefinedType() {
        return TYPE;
    }

    long priceToMicros(BigDecimal bigDecimal) {
        return MICRO_CONSTANT.multiply(bigDecimal).longValue();
    }

    public PurchaseEvent putCurrency(Currency currency) {
        if (!this.validator.isNull(currency, CURRENCY_ATTRIBUTE)) {
            this.predefinedAttributes.put(CURRENCY_ATTRIBUTE, currency.getCurrencyCode());
        }
        return this;
    }

    public PurchaseEvent putItemId(String string) {
        this.predefinedAttributes.put(ITEM_ID_ATTRIBUTE, string);
        return this;
    }

    public PurchaseEvent putItemName(String string) {
        this.predefinedAttributes.put(ITEM_NAME_ATTRIBUTE, string);
        return this;
    }

    public PurchaseEvent putItemPrice(BigDecimal bigDecimal) {
        if (!this.validator.isNull(bigDecimal, ITEM_PRICE_ATTRIBUTE)) {
            this.predefinedAttributes.put(ITEM_PRICE_ATTRIBUTE, this.priceToMicros(bigDecimal));
        }
        return this;
    }

    public PurchaseEvent putItemType(String string) {
        this.predefinedAttributes.put(ITEM_TYPE_ATTRIBUTE, string);
        return this;
    }

    public PurchaseEvent putSuccess(boolean bl) {
        this.predefinedAttributes.put(SUCCESS_ATTRIBUTE, Boolean.toString(bl));
        return this;
    }
}
