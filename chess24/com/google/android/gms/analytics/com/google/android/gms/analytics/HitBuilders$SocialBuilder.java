/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders;
import java.util.Map;

public static class HitBuilders.SocialBuilder
extends HitBuilders.HitBuilder<HitBuilders.SocialBuilder> {
    public HitBuilders.SocialBuilder() {
        this.set("&t", "social");
    }

    public HitBuilders.SocialBuilder setAction(String string) {
        this.set("&sa", string);
        return this;
    }

    public HitBuilders.SocialBuilder setNetwork(String string) {
        this.set("&sn", string);
        return this;
    }

    public HitBuilders.SocialBuilder setTarget(String string) {
        this.set("&st", string);
        return this;
    }
}
