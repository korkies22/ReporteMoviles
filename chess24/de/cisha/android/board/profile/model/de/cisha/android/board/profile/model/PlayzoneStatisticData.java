/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.profile.model;

import de.cisha.chess.model.FEN;

public class PlayzoneStatisticData {
    private int _draws;
    private FEN _fen;
    private int _losses;
    private int _wins;

    public PlayzoneStatisticData(int n, int n2, int n3, FEN fEN) {
        this._wins = n;
        this._losses = n2;
        this._draws = n3;
        this._fen = fEN;
    }

    public int getDraws() {
        return this._draws;
    }

    public FEN getFen() {
        return this._fen;
    }

    public int getLosses() {
        return this._losses;
    }

    public int getTotalGameCount() {
        return this._wins + this._losses + this._draws;
    }

    public int getWins() {
        return this._wins;
    }
}
