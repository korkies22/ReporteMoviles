// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.model;

public class EloRange
{
    private final int _max;
    private final int _min;
    
    public EloRange(final int min, final int max) {
        this._min = min;
        this._max = max;
    }
    
    public int getMax() {
        return this._max;
    }
    
    public int getMin() {
        return this._min;
    }
}
