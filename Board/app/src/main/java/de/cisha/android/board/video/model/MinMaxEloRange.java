// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import android.content.res.Resources;

public class MinMaxEloRange implements EloRangeRepresentation
{
    private int _max;
    private int _min;
    
    public MinMaxEloRange(final int min, final int max) {
        this._min = min;
        this._max = max;
    }
    
    public int getMax() {
        return this._max;
    }
    
    public int getMin() {
        return this._min;
    }
    
    @Override
    public String getRangeString(final Resources resources) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this._min);
        sb.append(" - ");
        sb.append(this._max);
        return sb.toString();
    }
}
