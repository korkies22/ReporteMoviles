// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.analyze.model;

import de.cisha.chess.model.Move;

public class OpeningMoveInformation implements Comparable<OpeningMoveInformation>
{
    private float _avgElo;
    private float _avgPerformance;
    private float _draw;
    private Move _move;
    private int _numberOfGames;
    private float _winBlack;
    private float _winWhite;
    
    public OpeningMoveInformation(final Move move, final int numberOfGames, final float winWhite, final float winBlack, final float draw, final float avgElo, final float avgPerformance) {
        this._move = move;
        this._numberOfGames = numberOfGames;
        this._winWhite = winWhite;
        this._winBlack = winBlack;
        this._draw = draw;
        this._avgElo = avgElo;
        this._avgPerformance = avgPerformance;
    }
    
    @Override
    public int compareTo(final OpeningMoveInformation openingMoveInformation) {
        if (this._numberOfGames > openingMoveInformation._numberOfGames) {
            return -1;
        }
        if (this._numberOfGames == openingMoveInformation._numberOfGames) {
            return 0;
        }
        return 1;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof OpeningMoveInformation)) {
            return false;
        }
        final OpeningMoveInformation openingMoveInformation = (OpeningMoveInformation)o;
        return Float.compare(openingMoveInformation._avgElo, this._avgElo) == 0 && Float.compare(openingMoveInformation._avgPerformance, this._avgPerformance) == 0 && Float.compare(openingMoveInformation._draw, this._draw) == 0 && Float.compare(openingMoveInformation._winBlack, this._winBlack) == 0 && Float.compare(openingMoveInformation._winWhite, this._winWhite) == 0 && openingMoveInformation._numberOfGames == this._numberOfGames && openingMoveInformation._move.equals(this._move);
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
    
    @Override
    public int hashCode() {
        return ((((((527 + this._numberOfGames) * 31 + Float.floatToIntBits(this._avgElo)) * 31 + Float.floatToIntBits(this._avgPerformance)) * 31 + Float.floatToIntBits(this._draw)) * 31 + Float.floatToIntBits(this._winBlack)) * 31 + Float.floatToIntBits(this._winWhite)) * 31 + this._move.hashCode();
    }
}
