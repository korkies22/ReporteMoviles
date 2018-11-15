/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Property
 *  android.view.View
 */
package android.support.transition;

import android.util.Property;
import android.view.View;

static final class ViewUtils
extends Property<View, Float> {
    ViewUtils(Class class_, String string) {
        super(class_, string);
    }

    public Float get(View view) {
        return Float.valueOf(android.support.transition.ViewUtils.getTransitionAlpha(view));
    }

    public void set(View view, Float f) {
        android.support.transition.ViewUtils.setTransitionAlpha(view, f.floatValue());
    }
}
