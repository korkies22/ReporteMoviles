/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.view.View
 */
package android.support.v7.widget;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardViewDelegate;
import android.view.View;

class CardView
implements CardViewDelegate {
    private Drawable mCardBackground;

    CardView() {
    }

    @Override
    public Drawable getCardBackground() {
        return this.mCardBackground;
    }

    @Override
    public View getCardView() {
        return CardView.this;
    }

    @Override
    public boolean getPreventCornerOverlap() {
        return CardView.this.getPreventCornerOverlap();
    }

    @Override
    public boolean getUseCompatPadding() {
        return CardView.this.getUseCompatPadding();
    }

    @Override
    public void setCardBackground(Drawable drawable) {
        this.mCardBackground = drawable;
        CardView.this.setBackgroundDrawable(drawable);
    }

    @Override
    public void setMinWidthHeightInternal(int n, int n2) {
        if (n > CardView.this.mUserSetMinWidth) {
            android.support.v7.widget.CardView.super.setMinimumWidth(n);
        }
        if (n2 > CardView.this.mUserSetMinHeight) {
            android.support.v7.widget.CardView.super.setMinimumHeight(n2);
        }
    }

    @Override
    public void setShadowPadding(int n, int n2, int n3, int n4) {
        CardView.this.mShadowBounds.set(n, n2, n3, n4);
        android.support.v7.widget.CardView.super.setPadding(n + CardView.this.mContentPadding.left, n2 + CardView.this.mContentPadding.top, n3 + CardView.this.mContentPadding.right, n4 + CardView.this.mContentPadding.bottom);
    }
}
