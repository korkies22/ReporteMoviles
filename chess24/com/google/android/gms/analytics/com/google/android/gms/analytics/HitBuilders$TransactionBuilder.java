/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders;
import java.util.Map;

@Deprecated
public static class HitBuilders.TransactionBuilder
extends HitBuilders.HitBuilder<HitBuilders.TransactionBuilder> {
    public HitBuilders.TransactionBuilder() {
        this.set("&t", "transaction");
    }

    public HitBuilders.TransactionBuilder setAffiliation(String string) {
        this.set("&ta", string);
        return this;
    }

    public HitBuilders.TransactionBuilder setCurrencyCode(String string) {
        this.set("&cu", string);
        return this;
    }

    public HitBuilders.TransactionBuilder setRevenue(double d) {
        this.set("&tr", Double.toString(d));
        return this;
    }

    public HitBuilders.TransactionBuilder setShipping(double d) {
        this.set("&ts", Double.toString(d));
        return this;
    }

    public HitBuilders.TransactionBuilder setTax(double d) {
        this.set("&tt", Double.toString(d));
        return this;
    }

    public HitBuilders.TransactionBuilder setTransactionId(String string) {
        this.set("&ti", string);
        return this;
    }
}
