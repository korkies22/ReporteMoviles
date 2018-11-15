/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.internal.zztg;
import java.util.Map;

public static class HitBuilders.ExceptionBuilder
extends HitBuilders.HitBuilder<HitBuilders.ExceptionBuilder> {
    public HitBuilders.ExceptionBuilder() {
        this.set("&t", "exception");
    }

    public HitBuilders.ExceptionBuilder setDescription(String string) {
        this.set("&exd", string);
        return this;
    }

    public HitBuilders.ExceptionBuilder setFatal(boolean bl) {
        this.set("&exf", zztg.zzW(bl));
        return this;
    }
}
