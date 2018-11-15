/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.remote.model.EloChange;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Rating;

public class GameEndInformation {
    private EloChange _eloChange;
    private float _scoreBlack;
    private float _scoreWhite;
    private GameStatus _status;
    private boolean _whiteToMove;

    public GameEndInformation(GameStatus gameStatus, float f, float f2, boolean bl) {
        this(gameStatus, null, f, f2, bl);
    }

    public GameEndInformation(GameStatus gameStatus, EloChange eloChange, float f, float f2, boolean bl) {
        this._status = gameStatus;
        if (eloChange == null) {
            eloChange = new EloChange(Rating.NO_RATING, Rating.NO_RATING, 0, 0);
        }
        this._eloChange = eloChange;
        this._scoreWhite = f;
        this._scoreBlack = f2;
        this._whiteToMove = bl;
    }

    public GameEndInformation(GameStatus gameStatus, boolean bl) {
        this(gameStatus, null, 0.0f, 0.0f, bl);
    }

    public EloChange getEloChange() {
        return this._eloChange;
    }

    public Rating getRating(boolean bl) {
        if (bl) {
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

    public float getScore(boolean bl) {
        if (bl) {
            return this._scoreWhite;
        }
        return this._scoreBlack;
    }

    public GameStatus getStatus() {
        return this._status;
    }

    public boolean hasRating() {
        if (this._eloChange.getRatingWhite().hasRating() && this._eloChange.getRatingBlack().hasRating()) {
            return true;
        }
        return false;
    }

    public boolean hasScore() {
        if (this._scoreBlack + this._scoreWhite > 0.0f) {
            return true;
        }
        return false;
    }

    public boolean isWhiteToMove() {
        return this._whiteToMove;
    }

    public void setWhiteToMove(boolean bl) {
        this._whiteToMove = bl;
    }
}
