// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.Rating;
import de.cisha.chess.model.GameStatus;
import de.cisha.android.board.playzone.remote.model.EloChange;

public class GameEndInformation
{
    private EloChange _eloChange;
    private float _scoreBlack;
    private float _scoreWhite;
    private GameStatus _status;
    private boolean _whiteToMove;
    
    public GameEndInformation(final GameStatus gameStatus, final float n, final float n2, final boolean b) {
        this(gameStatus, null, n, n2, b);
    }
    
    public GameEndInformation(final GameStatus status, EloChange eloChange, final float scoreWhite, final float scoreBlack, final boolean whiteToMove) {
        this._status = status;
        if (eloChange == null) {
            eloChange = new EloChange(Rating.NO_RATING, Rating.NO_RATING, 0, 0);
        }
        this._eloChange = eloChange;
        this._scoreWhite = scoreWhite;
        this._scoreBlack = scoreBlack;
        this._whiteToMove = whiteToMove;
    }
    
    public GameEndInformation(final GameStatus gameStatus, final boolean b) {
        this(gameStatus, null, 0.0f, 0.0f, b);
    }
    
    public EloChange getEloChange() {
        return this._eloChange;
    }
    
    public Rating getRating(final boolean b) {
        if (b) {
            return this._eloChange.getRatingWhite();
        }
        return this._eloChange.getRatingBlack();
    }
    
    public int getRatingChangeBlack() {
        return this._eloChange.getRatingBlackChange();
    }
    
    public int getRatingChangeWhite() {
        return this._eloChange.getRatingWhiteChange();
    }
    
    public float getScore(final boolean b) {
        if (b) {
            return this._scoreWhite;
        }
        return this._scoreBlack;
    }
    
    public GameStatus getStatus() {
        return this._status;
    }
    
    public boolean hasRating() {
        return this._eloChange.getRatingWhite().hasRating() && this._eloChange.getRatingBlack().hasRating();
    }
    
    public boolean hasScore() {
        return this._scoreBlack + this._scoreWhite > 0.0f;
    }
    
    public boolean isWhiteToMove() {
        return this._whiteToMove;
    }
    
    public void setWhiteToMove(final boolean whiteToMove) {
        this._whiteToMove = whiteToMove;
    }
}
