// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.model;

import de.cisha.chess.model.FEN;

public class PlayzoneStatisticData
{
    private int _draws;
    private FEN _fen;
    private int _losses;
    private int _wins;
    
    public PlayzoneStatisticData(final int wins, final int losses, final int draws, final FEN fen) {
        this._wins = wins;
        this._losses = losses;
        this._draws = draws;
        this._fen = fen;
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
