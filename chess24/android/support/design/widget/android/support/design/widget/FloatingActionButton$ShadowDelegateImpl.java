/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 */
package android.support.design.widget;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.ShadowViewDelegate;

private class FloatingActionButton.ShadowDelegateImpl
implements ShadowViewDelegate {
    FloatingActionButton.ShadowDelegateImpl() {
    }

    @Override
    public float getRadius() {
        return (float)FloatingActionButton.this.getSizeDimension() / 2.0f;
    }

    @Override
    public boolean isCompatPaddingEnabled() {
        return FloatingActionButton.this.mCompatPadding;
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {
        FloatingActionButton.super.setBackgroundDrawable(drawable);
    }

    @Override
    public void setShadowPadding(int n, int n2, int n3, int n4) {
        FloatingActionButton.this.mShadowPadding.set(n, n2, n3, n4);
        FloatingActionButton.this.setPadding(n + FloatingActionButton.this.mImagePadding, n2 + FloatingActionButton.this.mImagePadding, n3 + FloatingActionButton.this.mImagePadding, n4 + FloatingActionButton.this.mImagePadding);
    }
}
