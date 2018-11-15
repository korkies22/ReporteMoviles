/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.remote.model.EloChange;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;

public class AfterGameInformation
extends GameEndInformation {
    private Opponent _opponent;
    private Opponent _player;
    private boolean _playerIsWhite;

    public AfterGameInformation(GameEndInformation gameEndInformation, boolean bl, Opponent opponent, Opponent opponent2) {
        super(gameEndInformation.getStatus(), gameEndInformation.getEloChange(), gameEndInformation.getScore(true), gameEndInformation.getScore(false), gameEndInformation.isWhiteToMove());
        this._playerIsWhite = bl;
        this._player = opponent;
        this._opponent = opponent2;
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
        if (this._playerIsWhite == this.isWhiteToMove()) {
            return true;
        }
        return false;
    }
}
