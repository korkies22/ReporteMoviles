/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.design.widget.FloatingActionButtonImpl;

private class FloatingActionButtonImpl.ResetElevationAnimation
extends FloatingActionButtonImpl.ShadowAnimatorImpl {
    FloatingActionButtonImpl.ResetElevationAnimation() {
        super(FloatingActionButtonImpl.this, null);
    }

    @Override
    protected float getTargetShadowSize() {
        return FloatingActionButtonImpl.this.mElevation;
    }
}
