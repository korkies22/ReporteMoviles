/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders;
import java.util.Map;

public static class HitBuilders.TimingBuilder
extends HitBuilders.HitBuilder<HitBuilders.TimingBuilder> {
    public HitBuilders.TimingBuilder() {
        this.set("&t", "timing");
    }

    public HitBuilders.TimingBuilder(String string, String string2, long l) {
        this();
        this.setVariable(string2);
        this.setValue(l);
        this.setCategory(string);
    }

    public HitBuilders.TimingBuilder setCategory(String string) {
        this.set("&utc", string);
        return this;
    }

    public HitBuilders.TimingBuilder setLabel(String string) {
        this.set("&utl", string);
        return this;
    }

    public HitBuilders.TimingBuilder setValue(long l) {
        this.set("&utt", Long.toString(l));
        return this;
    }

    public HitBuilders.TimingBuilder setVariable(String string) {
        this.set("&utv", string);
        return this;
    }
}
