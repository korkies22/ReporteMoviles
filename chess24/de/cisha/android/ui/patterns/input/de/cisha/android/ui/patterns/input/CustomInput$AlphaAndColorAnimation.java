/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Transformation
 *  android.widget.TextView
 */
package de.cisha.android.ui.patterns.input;

import android.graphics.Color;
import android.view.animation.AlphaAnimation;
import android.view.animation.Transformation;
import android.widget.TextView;
import de.cisha.android.ui.patterns.input.CustomInput;

private static class CustomInput.AlphaAndColorAnimation
extends AlphaAnimation {
    private int _endColor;
    private int _startColor;
    private TextView _text;

    public CustomInput.AlphaAndColorAnimation(float f, float f2, TextView textView, int n, int n2) {
        super(f, f2);
        this._text = textView;
        this._startColor = n;
        this._endColor = n2;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        super.applyTransformation(f, transformation);
        float f2 = 1.0f - f;
        int n = Color.argb((int)((int)((float)Color.alpha((int)this._startColor) * f2 + (float)Color.alpha((int)this._endColor) * f)), (int)((int)((float)Color.red((int)this._startColor) * f2 + (float)Color.red((int)this._endColor) * f)), (int)((int)((float)Color.green((int)this._startColor) * f2 + (float)Color.green((int)this._endColor) * f)), (int)((int)(f2 * (float)Color.blue((int)this._startColor) + f * (float)Color.blue((int)this._endColor))));
        this._text.setTextColor(n);
    }
}
