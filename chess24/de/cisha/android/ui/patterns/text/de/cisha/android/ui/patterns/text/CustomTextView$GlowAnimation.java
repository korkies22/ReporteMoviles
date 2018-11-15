/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package de.cisha.android.ui.patterns.text;

import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import de.cisha.android.ui.patterns.text.CustomTextView;
import de.cisha.android.ui.patterns.text.CustomTextViewTextStyle;

public class CustomTextView.GlowAnimation
extends Animation {
    private int _colorEnd;
    private int _colorStart;

    public CustomTextView.GlowAnimation() {
        int n = CustomTextView.this._style.getColor();
        int n2 = CustomTextView.this._selectedStyle != null ? CustomTextView.this._selectedStyle.getColor() : -16777216;
        this._colorStart = Math.min(n, n2);
        this._colorEnd = Math.max(n, n2);
        this.setRepeatMode(2);
        this.setRepeatCount(-1);
        this.setDuration(500L);
    }

    private int getColorValue(CustomTextView.RGBFunction rGBFunction, float f) {
        return rGBFunction.returnIntFor(this._colorStart) + (int)((float)(rGBFunction.returnIntFor(this._colorEnd) - rGBFunction.returnIntFor(this._colorStart)) * f);
    }

    protected void applyTransformation(float f, Transformation transformation) {
        int n = this.getColorValue(CustomTextView.RGBFunction.RED, f);
        int n2 = this.getColorValue(CustomTextView.RGBFunction.BLUE, f);
        int n3 = this.getColorValue(CustomTextView.RGBFunction.GREEN, f);
        CustomTextView.this.setTextColor(Color.rgb((int)n, (int)n3, (int)n2));
    }
}
