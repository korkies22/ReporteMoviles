/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Property
 */
package android.support.v7.widget;

import android.util.Property;

static final class SwitchCompat
extends Property<android.support.v7.widget.SwitchCompat, Float> {
    SwitchCompat(Class class_, String string) {
        super(class_, string);
    }

    public Float get(android.support.v7.widget.SwitchCompat switchCompat) {
        return Float.valueOf(switchCompat.mThumbPosition);
    }

    public void set(android.support.v7.widget.SwitchCompat switchCompat, Float f) {
        switchCompat.setThumbPosition(f.floatValue());
    }
}
