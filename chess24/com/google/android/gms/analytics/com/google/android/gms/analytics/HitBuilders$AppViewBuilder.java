/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.analytics;

import com.google.android.gms.analytics.HitBuilders;
import java.util.Map;

@Deprecated
public static class HitBuilders.AppViewBuilder
extends HitBuilders.HitBuilder<HitBuilders.AppViewBuilder> {
    public HitBuilders.AppViewBuilder() {
        this.set("&t", "screenview");
    }
}
