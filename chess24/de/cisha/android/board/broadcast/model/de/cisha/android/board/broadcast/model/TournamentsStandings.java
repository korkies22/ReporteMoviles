/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

public abstract class TournamentsStandings {
    private int _draws;
    private int _losses;
    private int _wins;

    public TournamentsStandings(int n, int n2, int n3) {
        this._wins = n;
        this._draws = n2;
        this._losses = n3;
    }

    public int getDraws() {
        return this._draws;
    }

    public int getLosses() {
        return this._losses;
    }

    public int getWins() {
        return this._wins;
    }
}
