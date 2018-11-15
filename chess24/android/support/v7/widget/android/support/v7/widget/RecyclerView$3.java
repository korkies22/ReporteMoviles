/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Interpolator
 */
package android.support.v7.widget;

import android.view.animation.Interpolator;

static final class RecyclerView
implements Interpolator {
    RecyclerView() {
    }

    public float getInterpolation(float f) {
        return f * f * f * f * (f -= 1.0f) + 1.0f;
    }
}
