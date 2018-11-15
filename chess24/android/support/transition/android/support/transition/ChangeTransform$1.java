/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Property
 */
package android.support.transition;

import android.support.transition.ChangeTransform;
import android.util.Property;

static final class ChangeTransform
extends Property<ChangeTransform.PathAnimatorMatrix, float[]> {
    ChangeTransform(Class class_, String string) {
        super(class_, string);
    }

    public float[] get(ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix) {
        return null;
    }

    public void set(ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix, float[] arrf) {
        pathAnimatorMatrix.setValues(arrf);
    }
}
