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

private static abstract class Slide.CalculateSlideVertical
implements Slide.CalculateSlide {
    private Slide.CalculateSlideVertical() {
    }

    @Override
    public float getGoneX(ViewGroup viewGroup, View view) {
        return view.getTranslationX();
    }
}
