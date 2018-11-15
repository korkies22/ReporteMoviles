/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.Animation
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.OvershootInterpolator
 *  android.view.animation.Transformation
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import de.cisha.android.ui.patterns.dialogs.DropDownListView;

public class DropDownListView.ResizeHeightAnimation
extends Animation {
    private int _offset;
    private int _oldViewHeight;
    private boolean _open;
    private View _view;

    public DropDownListView.ResizeHeightAnimation(View object, int n, int n2, boolean bl, boolean bl2) {
        this._view = object;
        this._oldViewHeight = this._view.getHeight();
        this._offset = Math.min(n, n2) - this._oldViewHeight;
        this._open = bl ^ true;
        if (bl2) {
            DropDownListView.this = n < n2 ? new OvershootInterpolator() : new BounceInterpolator();
            object = n < n2 ? new AnticipateInterpolator() : new DecelerateInterpolator();
            if (!this._open) {
                DropDownListView.this = object;
            }
            this.setInterpolator((Interpolator)DropDownListView.this);
        }
    }

    protected void applyTransformation(float f, Transformation transformation) {
        transformation = this._view.getLayoutParams();
        int n = this._open ? this._oldViewHeight + (int)(f * (float)this._offset) : (int)((float)this._oldViewHeight * (1.0f - f));
        transformation.height = n;
        this._view.requestLayout();
    }
}
