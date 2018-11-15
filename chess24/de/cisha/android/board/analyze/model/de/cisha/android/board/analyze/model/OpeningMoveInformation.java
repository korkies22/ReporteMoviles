/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.analyze.model;

import de.cisha.chess.model.Move;

public class OpeningMoveInformation
implements Comparable<OpeningMoveInformation> {
    private float _avgElo;
    private float _avgPerformance;
    private float _draw;
    private Move _move;
    private int _numberOfGames;
    private float _winBlack;
    private float _winWhite;

    public OpeningMoveInformation(Move move, int n, float f, float f2, float f3, float f4, float f5) {
        this._move = move;
        this._numberOfGames = n;
        this._winWhite = f;
        this._winBlack = f2;
        this._draw = f3;
        this._avgElo = f4;
        this._avgPerformance = f5;
    }

    @Override
    public int compareTo(OpeningMoveInformation openingMoveInformation) {
        if (this._numberOfGames > openingMoveInformation._numberOfGames) {
            return -1;
        }
        if (this._numberOfGames == openingMoveInformation._numberOfGames) {
            return 0;
        }
        return 1;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof OpeningMoveInformation)) {
            return false;
        }
        object = (OpeningMoveInformation)object;
        if (Float.compare(object._avgElo, this._avgElo) == 0 && Float.compare(object._avgPerformance, this._avgPerformance) == 0 && Float.compare(object._draw, this._draw) == 0 && Float.compare(object._winBlack, this._winBlack) == 0 && Float.compare(object._winWhite, this._winWhite) == 0 && object._numberOfGames == this._numberOfGames && object._move.equals(this._move)) {
            return true;
        }
        return false;
    }

    public float getAvgElo() {
        return this._avgElo;
    }

    public float getAvgPerformance() {
        return this._avgPerformance;
    }

    public float getDraw() {
        return this._draw;
    }

    public Move getMove() {
        return this._move;
    }

    public int getNumberOfGames() {
        return this._numberOfGames;
    }

    public float getWinBlack() {
        return this._winBlack;
    }

    public float getWinWhite() {
        return this._winWhite;
    }

    public int hashCode() {
        return ((((((527 + this._numberOfGames) * 31 + Float.floatToIntBits(this._avgElo)) * 31 + Float.floatToIntBits(this._avgPerformance)) * 31 + Float.floatToIntBits(this._draw)) * 31 + Float.floatToIntBits(this._winBlack)) * 31 + Float.floatToIntBits(this._winWhite)) * 31 + this._move.hashCode();
    }
}
