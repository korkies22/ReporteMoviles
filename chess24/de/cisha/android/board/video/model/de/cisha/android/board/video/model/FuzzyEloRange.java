/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model;

import android.content.res.Resources;
import de.cisha.android.board.video.model.EloRangeRepresentation;

public class FuzzyEloRange
implements EloRangeRepresentation {
    private int _stringResId;

    public FuzzyEloRange(int n) {
        this._stringResId = n;
    }

    @Override
    public String getRangeString(Resources resources) {
        return resources.getString(this._stringResId);
    }
}
