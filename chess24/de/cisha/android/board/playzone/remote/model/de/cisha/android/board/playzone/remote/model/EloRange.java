/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

public class EloRange {
    private final int _max;
    private final int _min;

    public EloRange(int n, int n2) {
        this._min = n;
        this._max = n2;
    }

    public int getMax() {
        return this._max;
    }

    public int getMin() {
        return this._min;
    }
}
