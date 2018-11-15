/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.design.widget.FloatingActionButtonImpl;

private class FloatingActionButtonImpl.DisabledElevationAnimation
extends FloatingActionButtonImpl.ShadowAnimatorImpl {
    FloatingActionButtonImpl.DisabledElevationAnimation() {
        super(FloatingActionButtonImpl.this, null);
    }

    @Override
    protected float getTargetShadowSize() {
        return 0.0f;
    }
}
