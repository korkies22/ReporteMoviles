// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.view.animation.Transformation;
import android.view.View;
import android.view.animation.Animation;

public class DropDownAnimation extends Animation
{
    boolean _down;
    int _targetHeight;
    View _view;
    
    public DropDownAnimation(final View view, final int targetHeight, final boolean down) {
        this._view = view;
        this._targetHeight = targetHeight;
        this._down = down;
    }
    
    protected void applyTransformation(final float n, final Transformation transformation) {
        int height;
        if (this._down) {
            height = (int)(this._targetHeight * n);
        }
        else {
            height = (int)(this._targetHeight * (1.0f - n));
        }
        this._view.getLayoutParams().height = height;
        this._view.requestLayout();
        if (n == 1.0) {
            this._view.clearAnimation();
        }
    }
    
    public boolean willChangeBounds() {
        return true;
    }
}
