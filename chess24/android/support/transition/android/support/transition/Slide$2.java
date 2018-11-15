/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.support.transition.Slide;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

static final class Slide
extends Slide.CalculateSlideHorizontal {
    Slide() {
        super(null);
    }

    @Override
    public float getGoneX(ViewGroup viewGroup, View view) {
        int n = ViewCompat.getLayoutDirection((View)viewGroup);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        if (bl) {
            return view.getTranslationX() + (float)viewGroup.getWidth();
        }
        return view.getTranslationX() - (float)viewGroup.getWidth();
    }
}
