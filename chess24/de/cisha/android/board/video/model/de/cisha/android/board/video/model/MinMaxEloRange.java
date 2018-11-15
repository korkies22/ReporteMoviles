/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 */
package de.cisha.android.board.video.model;

import android.content.res.Resources;
import de.cisha.android.board.video.model.EloRangeRepresentation;

public class MinMaxEloRange
implements EloRangeRepresentation {
    private int _max;
    private int _min;

    public MinMaxEloRange(int n, int n2) {
        this._min = n;
        this._max = n2;
    }

    public int getMax() {
        return this._max;
    }

    public int getMin() {
        return this._min;
    }

    @Override
    public String getRangeString(Resources object) {
        object = new StringBuilder();
        object.append(this._min);
        object.append(" - ");
        object.append(this._max);
        return object.toString();
    }
}
