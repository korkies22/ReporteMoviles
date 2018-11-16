// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.view;

import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.LevelListDrawable;
import android.widget.ImageView;

public class SolvedIndicatorView extends ImageView
{
    private LevelListDrawable _bgDrawable;
    private Paint _paint;
    private SolveType _type;
    
    public SolvedIndicatorView(final Context context, final AttributeSet set) {
        super(context, set);
        this.init();
    }
    
    public SolvedIndicatorView(final Context context, final SolveType type) {
        super(context);
        this._type = type;
        this.init();
    }
    
    private void init() {
        this.setImageResource(2131231812);
        this.setBackgroundDrawable((Drawable)(this._bgDrawable = (LevelListDrawable)this.getResources().getDrawable(2131231804)));
        SolveType solveType;
        if (this._type == null) {
            solveType = SolveType.RUNNING;
        }
        else {
            solveType = this._type;
        }
        this.setSolveType(solveType);
        (this._paint = new Paint()).setColor(-16777216);
        this._paint.setStyle(Paint.Style.STROKE);
        this._paint.setStrokeWidth(3.0f);
    }
    
    public SolveType getSolveType() {
        return this._type;
    }
    
    public void setImageLevel(int visibility) {
        super.setImageLevel(visibility);
        this._bgDrawable.setLevel(visibility);
        if (visibility == 3) {
            visibility = 4;
        }
        else {
            visibility = 0;
        }
        this.setVisibility(visibility);
    }
    
    public void setSelected(final boolean selected) {
        super.setSelected(selected);
        final LevelListDrawable bgDrawable = this._bgDrawable;
        int access.000;
        if (selected) {
            access.000 = 3;
        }
        else {
            access.000 = this._type._level;
        }
        bgDrawable.setLevel(access.000);
    }
    
    public void setSolveType(final SolveType type) {
        this._type = type;
        this.setImageLevel(type._level);
    }
    
    public enum SolveType
    {
        CORRECT(1), 
        INCORRECT(2), 
        INVISIBLE(3), 
        RUNNING(0);
        
        private int _level;
        
        private SolveType(final int level) {
            this._level = level;
        }
    }
}
