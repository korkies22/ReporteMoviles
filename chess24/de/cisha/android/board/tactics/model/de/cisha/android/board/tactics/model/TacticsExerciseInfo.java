/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.tactics.model;

import de.cisha.chess.model.Rating;

public class TacticsExerciseInfo {
    int _averageTimeInMillis;
    int _eloChange;
    Rating _eloPuzzle;
    int _puzzleId;
    int _puzzleLikes;
    int _puzzleUnlikes;
    int _solvingTimeInMillis;
    boolean _success;
    Rating _userStartElo;

    public TacticsExerciseInfo(int n, boolean bl, int n2, Rating rating, Rating rating2, int n3, int n4, int n5, int n6) {
        this._puzzleId = n;
        this._success = bl;
        this._eloChange = n2;
        this._eloPuzzle = rating;
        this._userStartElo = rating2;
        this._solvingTimeInMillis = n3;
        this._averageTimeInMillis = n4;
        this._puzzleLikes = n5;
        this._puzzleUnlikes = n6;
    }

    public int getAverageTimeInMillis() {
        return this._averageTimeInMillis;
    }

    public int getEloChange() {
        return this._eloChange;
    }

    public Rating getEloPuzzle() {
        return this._eloPuzzle;
    }

    public int getPuzzleId() {
        return this._puzzleId;
    }

    public int getPuzzleLikes() {
        return this._puzzleLikes;
    }

    public int getPuzzleUnlikes() {
        return this._puzzleUnlikes;
    }

    public int getSolvingTimeInMillis() {
        return this._solvingTimeInMillis;
    }

    public Rating getUserStartElo() {
        return this._userStartElo;
    }

    public boolean isSuccess() {
        return this._success;
    }
}
