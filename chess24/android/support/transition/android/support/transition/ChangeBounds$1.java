/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.Property
 */
package android.support.transition;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Property;

static final class ChangeBounds
extends Property<Drawable, PointF> {
    private Rect mBounds = new Rect();

    ChangeBounds(Class class_, String string) {
        super(class_, string);
    }

    public PointF get(Drawable drawable) {
        drawable.copyBounds(this.mBounds);
        return new PointF((float)this.mBounds.left, (float)this.mBounds.top);
    }

    public void set(Drawable drawable, PointF pointF) {
        drawable.copyBounds(this.mBounds);
        this.mBounds.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
        drawable.setBounds(this.mBounds);
    }
}
