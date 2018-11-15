/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.support.transition.Slide;
import android.view.View;
import android.view.ViewGroup;

static final class Slide
extends Slide.CalculateSlideVertical {
    Slide() {
        super(null);
    }

    @Override
    public float getGoneY(ViewGroup viewGroup, View view) {
        return view.getTranslationY() + (float)viewGroup.getHeight();
    }
}
