// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Opponent;

public class AfterGameInformation extends GameEndInformation
{
    private Opponent _opponent;
    private Opponent _player;
    private boolean _playerIsWhite;
    
    public AfterGameInformation(final GameEndInformation gameEndInformation, final boolean playerIsWhite, final Opponent player, final Opponent opponent) {
        super(gameEndInformation.getStatus(), gameEndInformation.getEloChange(), gameEndInformation.getScore(true), gameEndInformation.getScore(false), gameEndInformation.isWhiteToMove());
        this._playerIsWhite = playerIsWhite;
        this._player = player;
        this._opponent = opponent;
    }
    
    public Opponent getOpponent() {
        return this._opponent;
    }
    
    public Opponent getPlayer() {
        return this._player;
    }
    
    public int getRatingChangePlayer() {
        if (this._playerIsWhite) {
            return this.getRatingChangeWhite();
        }
        return this.getRatingChangeBlack();
    }
    
    public Rating getRatingOpponent() {
        return this.getRating(this._playerIsWhite ^ true);
    }
    
    public Rating getRatingPlayer() {
        return this.getRating(this._playerIsWhite);
    }
    
    public float getScoreOpponent() {
        return this.getScore(this._playerIsWhite ^ true);
    }
    
    public float getScorePlayer() {
        return this.getScore(this._playerIsWhite);
    }
    
    public boolean playerIsWhite() {
        return this._playerIsWhite;
    }
    
    public boolean playerToMove() {
        return this._playerIsWhite == this.isWhiteToMove();
    }
}
