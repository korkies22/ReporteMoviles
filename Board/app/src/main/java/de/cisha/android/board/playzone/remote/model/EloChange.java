// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.model;

import de.cisha.chess.model.Rating;

public class EloChange
{
    private Rating _black;
    private int _blackChange;
    private Rating _white;
    private int _whiteChange;
    
    public EloChange(final Rating white, final Rating black, final int whiteChange, final int blackChange) {
        this._white = white;
        this._black = black;
        this._whiteChange = whiteChange;
        this._blackChange = blackChange;
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
