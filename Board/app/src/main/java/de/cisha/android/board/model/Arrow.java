// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.model;

import de.cisha.chess.model.SEP;

public class Arrow
{
    private ArrowCategory _category;
    private int _color;
    private SEP _sep;
    
    public Arrow(final SEP sep, final ArrowCategory category, final int color) {
        this._sep = sep;
        this._category = category;
        this._color = color;
    }
    
    @Override
    public boolean equals(final Object o) {
        final boolean b = o instanceof Arrow;
        final boolean b2 = false;
        if (b) {
            final Arrow arrow = (Arrow)o;
            boolean b3 = b2;
            if (arrow._color == this._color) {
                b3 = b2;
                if (arrow._category == this._category) {
                    b3 = b2;
                    if (arrow._sep.equals(this._sep)) {
                        b3 = true;
                    }
                }
            }
            return b3;
        }
        return false;
    }
    
    public ArrowCategory getCategory() {
        return this._category;
    }
    
    public int getColor() {
        return this._color;
    }
    
    public SEP getSep() {
        return this._sep;
    }
    
    @Override
    public int hashCode() {
        return 17 + 31 * this._color + 13 * this._sep.getSEPNumber() + 23 * this._category.hashCode();
    }
}
