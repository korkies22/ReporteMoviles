// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.mainmenu.view;

import android.view.animation.Transformation;
import android.view.animation.Animation;

public class SlideAnimation extends Animation
{
    private MenuSlider _menuSlider;
    private int _start;
    private int _stop;
    private XCoordinate _xCoordinate;
    
    public SlideAnimation(final MenuSlider menuSlider, final int stop) {
        this._menuSlider = menuSlider;
        this._start = menuSlider.getScrollX();
        this._stop = stop;
    }
    
    protected void applyTransformation(final float n, final Transformation transformation) {
        this._menuSlider.scrollTo((int)(this._start + (this._stop - this._start) * n), 0);
    }
    
    public boolean willChangeBounds() {
        return false;
    }
    
    public static class XCoordinate
    {
        private int _xc;
        
        public XCoordinate() {
            this._xc = 0;
        }
        
        public int getX() {
            return this._xc;
        }
        
        public void setX(final int xc) {
            this._xc = xc;
        }
    }
}
