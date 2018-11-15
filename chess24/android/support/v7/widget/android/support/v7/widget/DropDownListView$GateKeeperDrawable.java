/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 */
package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.DropDownListView;

private static class DropDownListView.GateKeeperDrawable
extends DrawableWrapper {
    private boolean mEnabled = true;

    DropDownListView.GateKeeperDrawable(Drawable drawable) {
        super(drawable);
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.mEnabled) {
            super.draw(canvas);
        }
    }

    void setEnabled(boolean bl) {
        this.mEnabled = bl;
    }

    @Override
    public void setHotspot(float f, float f2) {
        if (this.mEnabled) {
            super.setHotspot(f, f2);
        }
    }

    @Override
    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        if (this.mEnabled) {
            super.setHotspotBounds(n, n2, n3, n4);
        }
    }

    @Override
    public boolean setState(int[] arrn) {
        if (this.mEnabled) {
            return super.setState(arrn);
        }
        return false;
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        if (this.mEnabled) {
            return super.setVisible(bl, bl2);
        }
        return false;
    }
}
