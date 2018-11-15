/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.util.Property
 *  android.view.View
 */
package android.support.transition;

import android.graphics.PointF;
import android.support.transition.ViewUtils;
import android.util.Property;
import android.view.View;

static final class ChangeBounds
extends Property<View, PointF> {
    ChangeBounds(Class class_, String string) {
        super(class_, string);
    }

    public PointF get(View view) {
        return null;
    }

    public void set(View view, PointF pointF) {
        int n = Math.round(pointF.x);
        int n2 = Math.round(pointF.y);
        ViewUtils.setLeftTopRightBottom(view, n, n2, view.getWidth() + n, view.getHeight() + n2);
    }
}
