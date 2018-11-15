/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package de.cisha.android.ui.patterns.text;

import android.graphics.Color;
import de.cisha.android.ui.patterns.text.CustomTextView;

static final class CustomTextView.RGBFunction
extends CustomTextView.RGBFunction {
    CustomTextView.RGBFunction(String string2, int n2) {
    }

    @Override
    public int returnIntFor(int n) {
        return Color.blue((int)n);
    }
}
