/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LevelListDrawable
 *  android.util.AttributeSet
 *  android.widget.ImageView
 */
package de.cisha.android.board.tactics.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SolvedIndicatorView
extends ImageView {
    private LevelListDrawable _bgDrawable;
    private Paint _paint;
    private SolveType _type;

    public SolvedIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public SolvedIndicatorView(Context context, SolveType solveType) {
        super(context);
        this._type = solveType;
        this.init();
    }

    private void init() {
        this.setImageResource(2131231812);
        this._bgDrawable = (LevelListDrawable)this.getResources().getDrawable(2131231804);
        this.setBackgroundDrawable((Drawable)this._bgDrawable);
        SolveType solveType = this._type == null ? SolveType.RUNNING : this._type;
        this.setSolveType(solveType);
        this._paint = new Paint();
        this._paint.setColor(-16777216);
        this._paint.setStyle(Paint.Style.STROKE);
        this._paint.setStrokeWidth(3.0f);
    }

    public SolveType getSolveType() {
        return this._type;
    }

    public void setImageLevel(int n) {
        super.setImageLevel(n);
        this._bgDrawable.setLevel(n);
        n = n == 3 ? 4 : 0;
        this.setVisibility(n);
    }

    public void setSelected(boolean bl) {
        super.setSelected(bl);
        LevelListDrawable levelListDrawable = this._bgDrawable;
        int n = bl ? 3 : this._type._level;
        levelListDrawable.setLevel(n);
    }

    public void setSolveType(SolveType solveType) {
        this._type = solveType;
        this.setImageLevel(solveType._level);
    }

    public static enum SolveType {
        CORRECT(1),
        INCORRECT(2),
        RUNNING(0),
        INVISIBLE(3);
        
        private int _level;

        private SolveType(int n2) {
            this._level = n2;
        }
    }

}
