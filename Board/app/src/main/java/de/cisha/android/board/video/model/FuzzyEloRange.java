// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.video.model;

import android.content.res.Resources;

public class FuzzyEloRange implements EloRangeRepresentation
{
    private int _stringResId;
    
    public FuzzyEloRange(final int stringResId) {
        this._stringResId = stringResId;
    }
    
    @Override
    public String getRangeString(final Resources resources) {
        return resources.getString(this._stringResId);
    }
}
