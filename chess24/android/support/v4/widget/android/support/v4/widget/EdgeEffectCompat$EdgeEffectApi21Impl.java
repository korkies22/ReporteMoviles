/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.widget.EdgeEffect
 */
package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.EdgeEffectCompat;
import android.widget.EdgeEffect;

@RequiresApi(value=21)
static class EdgeEffectCompat.EdgeEffectApi21Impl
extends EdgeEffectCompat.EdgeEffectBaseImpl {
    EdgeEffectCompat.EdgeEffectApi21Impl() {
    }

    @Override
    public void onPull(EdgeEffect edgeEffect, float f, float f2) {
        edgeEffect.onPull(f, f2);
    }
}
