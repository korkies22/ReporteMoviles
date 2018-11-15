/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.util.Property
 */
package android.support.transition;

import android.graphics.PointF;
import android.support.transition.ChangeBounds;
import android.util.Property;

static final class ChangeBounds
extends Property<ChangeBounds.ViewBounds, PointF> {
    ChangeBounds(Class class_, String string) {
        super(class_, string);
    }

    public PointF get(ChangeBounds.ViewBounds viewBounds) {
        return null;
    }

    public void set(ChangeBounds.ViewBounds viewBounds, PointF pointF) {
        viewBounds.setTopLeft(pointF);
    }
}
