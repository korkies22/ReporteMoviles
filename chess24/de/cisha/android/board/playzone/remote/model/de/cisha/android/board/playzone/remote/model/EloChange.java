/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.chess.model.Rating;

public class EloChange {
    private Rating _black;
    private int _blackChange;
    private Rating _white;
    private int _whiteChange;

    public EloChange(Rating rating, Rating rating2, int n, int n2) {
        this._white = rating;
        this._black = rating2;
        this._whiteChange = n;
        this._blackChange = n2;
    }

    public Rating getRatingBlack() {
        return this._black;
    }

    public int getRatingBlackChange() {
        return this._blackChange;
    }

    public Rating getRatingWhite() {
        return this._white;
    }

    public int getRatingWhiteChange() {
        return this._whiteChange;
    }
}
