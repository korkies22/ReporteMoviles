/*
 * Decompiled with CFR 0_134.
 */
package android.support.design.widget;

import android.support.design.widget.FloatingActionButtonImpl;

private class FloatingActionButtonImpl.ElevateToTranslationZAnimation
extends FloatingActionButtonImpl.ShadowAnimatorImpl {
    FloatingActionButtonImpl.ElevateToTranslationZAnimation() {
        super(FloatingActionButtonImpl.this, null);
    }

    @Override
    protected float getTargetShadowSize() {
        return FloatingActionButtonImpl.this.mElevation + FloatingActionButtonImpl.this.mPressedTranslationZ;
    }
}
