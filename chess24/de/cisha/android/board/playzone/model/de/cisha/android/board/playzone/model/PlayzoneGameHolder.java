/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.playzone.model;

import de.cisha.android.board.playzone.model.PlayZoneChessClock;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class PlayzoneGameHolder
extends GameHolder {
    private PlayZoneChessClock _clock;
    private int _countMoveTimeFromMoveNumber = 1;
    private boolean _gameActive = false;
    private boolean _myDrawOfferPending = false;
    private boolean _opponentsDrawOfferPending = false;
    private boolean _playerColorWhite;

    public PlayzoneGameHolder(Game game, boolean bl) {
        super(game);
        this.gotoEndingPosition();
        this._playerColorWhite = bl;
        this.setClock(new PlayZoneChessClock(game.getClockSetting()));
    }

    private void setClock(PlayZoneChessClock iterator) {
        boolean bl;
        if (this._clock != null) {
            bl = this._clock.isRunning();
            this._clock.stop();
        } else {
            bl = false;
        }
        this._clock = iterator;
        this._clock.setActiveColor(this.getPosition().getActiveColor());
        iterator = this.getRootMoveContainer().getAllMainLineMoves();
        if (iterator.size() > 0) {
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                Move move = (Move)iterator.next();
                boolean bl2 = move.getPiece().getColor();
                if (move.getMoveNumber() <= this._countMoveTimeFromMoveNumber) continue;
                int n = move.getMoveTimeInMills();
                this._clock.addTimeUsage(n, bl2);
            }
        }
        if (bl) {
            this._clock.start();
        }
        this._clock.fireUpdate();
    }

    public void addMoveTimeUsageForColor(Move move, int n, boolean bl) {
        if (move.getPiece().getColor() == bl) {
            move.setMoveTimeInMills(n);
        }
        if (move.getMoveNumber() > this._countMoveTimeFromMoveNumber) {
            this._clock.addTimeUsage(n, bl);
        }
    }

    @Override
    public Move doMoveInCurrentPosition(SEP serializable, boolean bl) {
        Position position = this.getPosition();
        boolean bl2 = position.getActiveColor();
        boolean bl3 = this._playerColorWhite;
        int n = 0;
        if (bl2 == bl3) {
            this.setOpponentsDrawOffer(false);
        } else {
            this.setPlayersDrawOffer(false);
        }
        if (this._gameActive) {
            n = this._clock.setActiveColor(bl2 ^ true);
        }
        if (position.getHalfMoveNumber() == 1 && this._gameActive) {
            this._clock.start();
        }
        serializable = super.doMoveInCurrentPosition((SEP)serializable, bl);
        if (this._gameActive && serializable != null) {
            serializable.setMoveTimeInMills(n);
        }
        return serializable;
    }

    public PlayZoneChessClock getClock() {
        return this._clock;
    }

    public boolean isGameActive() {
        return this._gameActive;
    }

    public boolean opponentsDrawOfferPending() {
        return this._opponentsDrawOfferPending;
    }

    public boolean playersDrawOfferPending() {
        return this._myDrawOfferPending;
    }

    public void setGameActive(boolean bl) {
        this._gameActive = bl;
        this._myDrawOfferPending = false;
        this._opponentsDrawOfferPending = false;
        if (this._gameActive) {
            Move move = this.getRootMoveContainer().getLastMoveinMainLine();
            if (move != null && move.getResultingPosition().getHalfMoveNumber() > 1) {
                this._clock.start();
                this._clock.fireUpdate();
                return;
            }
        } else {
            this.getClock().stop();
        }
    }

    @Override
    public void setNewGame(Game game) {
        super.setNewGame(game);
        this.gotoEndingPosition();
        this.setClock(new PlayZoneChessClock(game.getClockSetting()));
    }

    public void setOpponentsDrawOffer(boolean bl) {
        this._opponentsDrawOfferPending = bl;
    }

    public void setPlayerColor(boolean bl) {
        this._playerColorWhite = bl;
    }

    public void setPlayersDrawOffer(boolean bl) {
        this._myDrawOfferPending = bl;
    }
}
