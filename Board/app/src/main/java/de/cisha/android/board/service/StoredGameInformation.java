// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service;

import java.util.Iterator;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Rating;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Game;

public class StoredGameInformation
{
    private final Game _game;
    private final boolean _playerWhite;
    
    public StoredGameInformation(final Game game, final boolean playerWhite) {
        this._game = game;
        this._playerWhite = playerWhite;
    }
    
    public int getBaseTimeMinutes() {
        return this._game.getClockSetting().getBase(this._playerWhite) / 60000;
    }
    
    public Game getGame() {
        return this._game;
    }
    
    public int getIncrementSeconds() {
        return this._game.getClockSetting().getIncrementPerMove(this._playerWhite) / 1000;
    }
    
    public String getOpponentsName() {
        Opponent opponent;
        if (!this._playerWhite) {
            opponent = this._game.getWhitePlayer();
        }
        else {
            opponent = this._game.getBlackPlayer();
        }
        return opponent.getName();
    }
    
    public Rating getOpponentsRating() {
        Opponent opponent;
        if (!this._playerWhite) {
            opponent = this._game.getWhitePlayer();
        }
        else {
            opponent = this._game.getBlackPlayer();
        }
        return opponent.getRating();
    }
    
    public int getRemainingMillisFrom(final boolean b) {
        final int incrementPerMove = this._game.getClockSetting().getIncrementPerMove(b);
        final Iterator<Move> iterator = this._game.getAllMainLineMoves().iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final Move move = iterator.next();
            final int moveNumber = move.getMoveNumber();
            if (move.getPiece().getColor() == b && moveNumber > 1) {
                n = n + move.getMoveTimeInMills() - incrementPerMove;
            }
        }
        final Move lastMoveinMainLine = this._game.getLastMoveinMainLine();
        int n2 = n;
        if (lastMoveinMainLine != null) {
            n2 = n;
            if (lastMoveinMainLine.getPiece().getColor() != b) {
                n2 = n + this._game.getClockSetting().getTimeGoneSinceLastMove();
            }
        }
        final int base = this._game.getClockSetting().getBase(b);
        if (incrementPerMove <= 0 && base <= 0) {
            return n2;
        }
        return base - n2;
    }
    
    public boolean isPlayerWhite() {
        return this._playerWhite;
    }
}
