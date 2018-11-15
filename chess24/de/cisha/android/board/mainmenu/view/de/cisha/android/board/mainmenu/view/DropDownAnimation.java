/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package de.cisha.android.board.mainmenu.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class DropDownAnimation
extends Animation {
    boolean _down;
    int _targetHeight;
    View _view;

    public DropDownAnimation(View view, int n, boolean bl) {
        this._view = view;
        this._targetHeight = n;
        this._down = bl;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        int n = this._down ? (int)((float)this._targetHeight * f) : (int)((float)this._targetHeight * (1.0f - f));
        this._view.getLayoutParams().height = n;
        this._view.requestLayout();
        if ((double)f == 1.0) {
            this._view.clearAnimation();
        }
    }

    public boolean willChangeBounds() {
        return true;
    }
}
