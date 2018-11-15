/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Transformation
 */
package de.cisha.android.board.mainmenu.view;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import de.cisha.android.board.mainmenu.view.MenuSlider;

public class SlideAnimation
extends Animation {
    private MenuSlider _menuSlider;
    private int _start;
    private int _stop;
    private XCoordinate _xCoordinate;

    public SlideAnimation(MenuSlider menuSlider, int n) {
        this._menuSlider = menuSlider;
        this._start = menuSlider.getScrollX();
        this._stop = n;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        int n = this._stop;
        int n2 = this._start;
        float f2 = this._start;
        float f3 = n - n2;
        this._menuSlider.scrollTo((int)(f2 + f3 * f), 0);
    }

    public boolean willChangeBounds() {
        return false;
    }

    public static class XCoordinate {
        private int _xc = 0;

        public int getX() {
            return this._xc;
        }

        public void setX(int n) {
            this._xc = n;
        }
    }

}
