/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders;
import java.util.Map;

public static class HitBuilders.EventBuilder
extends HitBuilders.HitBuilder<HitBuilders.EventBuilder> {
    public HitBuilders.EventBuilder() {
        this.set("&t", "event");
    }

    public HitBuilders.EventBuilder(String string, String string2) {
        this();
        this.setCategory(string);
        this.setAction(string2);
    }

    public HitBuilders.EventBuilder setAction(String string) {
        this.set("&ea", string);
        return this;
    }

    public HitBuilders.EventBuilder setCategory(String string) {
        this.set("&ec", string);
        return this;
    }

    public HitBuilders.EventBuilder setLabel(String string) {
        this.set("&el", string);
        return this;
    }

    public HitBuilders.EventBuilder setValue(long l) {
        this.set("&ev", Long.toString(l));
        return this;
    }
}
