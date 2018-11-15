/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package de.cisha.android.board.tactics;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import de.cisha.android.board.tactics.TacticsStopFragment;
import de.cisha.android.board.view.FieldView;

private class TacticsStopFragment.FieldViewHeightAnimation
extends Animation {
    private float _end;
    private float _start;

    public TacticsStopFragment.FieldViewHeightAnimation(int n, int n2) {
        this._start = n;
        this._end = n2;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        super.applyTransformation(f, transformation);
        TacticsStopFragment.access$600((TacticsStopFragment)TacticsStopFragment.this).getLayoutParams().height = (int)(this._start + (this._end - this._start) * f);
        TacticsStopFragment.this._fieldView.requestLayout();
    }
}
