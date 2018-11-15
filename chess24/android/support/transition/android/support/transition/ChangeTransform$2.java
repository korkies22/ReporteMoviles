/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.PointF
 *  android.util.Property
 */
package android.support.transition;

import android.graphics.PointF;
import android.support.transition.ChangeTransform;
import android.util.Property;

static final class ChangeTransform
extends Property<ChangeTransform.PathAnimatorMatrix, PointF> {
    ChangeTransform(Class class_, String string) {
        super(class_, string);
    }

    public PointF get(ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix) {
        return null;
    }

    public void set(ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix, PointF pointF) {
        pathAnimatorMatrix.setTranslation(pointF);
    }
}
