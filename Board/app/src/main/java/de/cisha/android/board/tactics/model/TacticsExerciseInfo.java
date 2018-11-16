// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import de.cisha.chess.model.Rating;

public class TacticsExerciseInfo
{
    int _averageTimeInMillis;
    int _eloChange;
    Rating _eloPuzzle;
    int _puzzleId;
    int _puzzleLikes;
    int _puzzleUnlikes;
    int _solvingTimeInMillis;
    boolean _success;
    Rating _userStartElo;
    
    public TacticsExerciseInfo(final int puzzleId, final boolean success, final int eloChange, final Rating eloPuzzle, final Rating userStartElo, final int solvingTimeInMillis, final int averageTimeInMillis, final int puzzleLikes, final int puzzleUnlikes) {
        this._puzzleId = puzzleId;
        this._success = success;
        this._eloChange = eloChange;
        this._eloPuzzle = eloPuzzle;
        this._userStartElo = userStartElo;
        this._solvingTimeInMillis = solvingTimeInMillis;
        this._averageTimeInMillis = averageTimeInMillis;
        this._puzzleLikes = puzzleLikes;
        this._puzzleUnlikes = puzzleUnlikes;
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
