/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.util.Property
 *  android.view.View
 */
package android.support.transition;

import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.Property;
import android.view.View;

static final class ViewUtils
extends Property<View, Rect> {
    ViewUtils(Class class_, String string) {
        super(class_, string);
    }

    public Rect get(View view) {
        return ViewCompat.getClipBounds(view);
    }

    public void set(View view, Rect rect) {
        ViewCompat.setClipBounds(view, rect);
    }
}
