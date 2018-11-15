/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package de.cisha.android.board.tactics.view;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import de.cisha.android.board.tactics.view.TranslateViewGroup;

private class TranslateViewGroup.WidthAnimation
extends Animation {
    private boolean _canceled = false;
    private int _from;
    private float _interpolatedAdjustOffset = 0.0f;
    private int _offset;

    public TranslateViewGroup.WidthAnimation(int n) {
        this._from = TranslateViewGroup.this._xOffset;
        this._offset = this._from - n;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        super.applyTransformation(f, transformation);
        if (!this._canceled) {
            float f2;
            f = f2 = f + this._interpolatedAdjustOffset;
            if (f2 >= 1.0f) {
                TranslateViewGroup.this.clearAnimation();
                f = 1.0f;
            }
            TranslateViewGroup.this._xOffset = (int)((float)this._from - f * (float)this._offset);
            TranslateViewGroup.this.forceOnLayout();
        }
    }

    public void cancel() {
        this._canceled = true;
        super.cancel();
    }

    public void reduceAnimationTime(int n) {
        float f = (float)n / (float)this.getDuration();
        this._interpolatedAdjustOffset += f;
    }
}
