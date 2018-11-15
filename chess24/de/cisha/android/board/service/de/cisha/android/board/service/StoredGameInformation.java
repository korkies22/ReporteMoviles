/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.service;

import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.Rating;
import java.util.Iterator;
import java.util.List;

public class StoredGameInformation {
    private final Game _game;
    private final boolean _playerWhite;

    public StoredGameInformation(Game game, boolean bl) {
        this._game = game;
        this._playerWhite = bl;
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getOpponentsName() {
        Opponent opponent;
        if (!this._playerWhite) {
            opponent = this._game.getWhitePlayer();
            do {
                return opponent.getName();
                break;
            } while (true);
        }
        opponent = this._game.getBlackPlayer();
        return opponent.getName();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Rating getOpponentsRating() {
        Opponent opponent;
        if (!this._playerWhite) {
            opponent = this._game.getWhitePlayer();
            do {
                return opponent.getRating();
                break;
            } while (true);
        }
        opponent = this._game.getBlackPlayer();
        return opponent.getRating();
    }

    public int getRemainingMillisFrom(boolean bl) {
        int n;
        int n2 = this._game.getClockSetting().getIncrementPerMove(bl);
        Object object = this._game.getAllMainLineMoves().iterator();
        int n3 = 0;
        while (object.hasNext()) {
            Move move = object.next();
            n = move.getMoveNumber();
            if (move.getPiece().getColor() != bl || n <= 1) continue;
            n3 = n3 + move.getMoveTimeInMills() - n2;
        }
        object = this._game.getLastMoveinMainLine();
        n = n3;
        if (object != null) {
            n = n3;
            if (object.getPiece().getColor() != bl) {
                n = n3 + this._game.getClockSetting().getTimeGoneSinceLastMove();
            }
        }
        n3 = this._game.getClockSetting().getBase(bl);
        if (n2 <= 0 && n3 <= 0) {
            return n;
        }
        return n3 - n;
    }

    public boolean isPlayerWhite() {
        return this._playerWhite;
    }
}
