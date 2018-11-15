/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders;
import java.util.Map;

@Deprecated
public static class HitBuilders.ItemBuilder
extends HitBuilders.HitBuilder<HitBuilders.ItemBuilder> {
    public HitBuilders.ItemBuilder() {
        this.set("&t", "item");
    }

    public HitBuilders.ItemBuilder setCategory(String string) {
        this.set("&iv", string);
        return this;
    }

    public HitBuilders.ItemBuilder setCurrencyCode(String string) {
        this.set("&cu", string);
        return this;
    }

    public HitBuilders.ItemBuilder setName(String string) {
        this.set("&in", string);
        return this;
    }

    public HitBuilders.ItemBuilder setPrice(double d) {
        this.set("&ip", Double.toString(d));
        return this;
    }

    public HitBuilders.ItemBuilder setQuantity(long l) {
        this.set("&iq", Long.toString(l));
        return this;
    }

    public HitBuilders.ItemBuilder setSku(String string) {
        this.set("&ic", string);
        return this;
    }

    public HitBuilders.ItemBuilder setTransactionId(String string) {
        this.set("&ti", string);
        return this;
    }
}
