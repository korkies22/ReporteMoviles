// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.model;

import de.cisha.chess.model.position.Position;
import de.cisha.chess.model.SEP;
import java.util.Iterator;
import java.util.List;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameHolder;

public class PlayzoneGameHolder extends GameHolder
{
    private PlayZoneChessClock _clock;
    private int _countMoveTimeFromMoveNumber;
    private boolean _gameActive;
    private boolean _myDrawOfferPending;
    private boolean _opponentsDrawOfferPending;
    private boolean _playerColorWhite;
    
    public PlayzoneGameHolder(final Game game, final boolean playerColorWhite) {
        super(game);
        this._opponentsDrawOfferPending = false;
        this._myDrawOfferPending = false;
        this._gameActive = false;
        this._countMoveTimeFromMoveNumber = 1;
        this.gotoEndingPosition();
        this._playerColorWhite = playerColorWhite;
        this.setClock(new PlayZoneChessClock(game.getClockSetting()));
    }
    
    private void setClock(final PlayZoneChessClock clock) {
        boolean running;
        if (this._clock != null) {
            running = this._clock.isRunning();
            this._clock.stop();
        }
        else {
            running = false;
        }
        (this._clock = clock).setActiveColor(this.getPosition().getActiveColor());
        final List<Move> allMainLineMoves = this.getRootMoveContainer().getAllMainLineMoves();
        if (allMainLineMoves.size() > 0) {
            for (final Move move : allMainLineMoves) {
                final boolean color = move.getPiece().getColor();
                if (move.getMoveNumber() > this._countMoveTimeFromMoveNumber) {
                    this._clock.addTimeUsage(move.getMoveTimeInMills(), color);
                }
            }
        }
        if (running) {
            this._clock.start();
        }
        this._clock.fireUpdate();
    }
    
    public void addMoveTimeUsageForColor(final Move move, final int moveTimeInMills, final boolean b) {
        if (move.getPiece().getColor() == b) {
            move.setMoveTimeInMills(moveTimeInMills);
        }
        if (move.getMoveNumber() > this._countMoveTimeFromMoveNumber) {
            this._clock.addTimeUsage(moveTimeInMills, b);
        }
    }
    
    public Move doMoveInCurrentPosition(final SEP sep, final boolean b) {
        final Position position = this.getPosition();
        final boolean activeColor = position.getActiveColor();
        final boolean playerColorWhite = this._playerColorWhite;
        int setActiveColor = 0;
        if (activeColor == playerColorWhite) {
            this.setOpponentsDrawOffer(false);
        }
        else {
            this.setPlayersDrawOffer(false);
        }
        if (this._gameActive) {
            setActiveColor = this._clock.setActiveColor(activeColor ^ true);
        }
        if (position.getHalfMoveNumber() == 1 && this._gameActive) {
            this._clock.start();
        }
        final Move doMoveInCurrentPosition = super.doMoveInCurrentPosition(sep, b);
        if (this._gameActive && doMoveInCurrentPosition != null) {
            doMoveInCurrentPosition.setMoveTimeInMills(setActiveColor);
        }
        return doMoveInCurrentPosition;
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
    
    public void setGameActive(final boolean gameActive) {
        this._gameActive = gameActive;
        this._myDrawOfferPending = false;
        this._opponentsDrawOfferPending = false;
        if (this._gameActive) {
            final Move lastMoveinMainLine = this.getRootMoveContainer().getLastMoveinMainLine();
            if (lastMoveinMainLine != null && lastMoveinMainLine.getResultingPosition().getHalfMoveNumber() > 1) {
                this._clock.start();
                this._clock.fireUpdate();
            }
        }
        else {
            this.getClock().stop();
        }
    }
    
    @Override
    public void setNewGame(final Game newGame) {
        super.setNewGame(newGame);
        this.gotoEndingPosition();
        this.setClock(new PlayZoneChessClock(newGame.getClockSetting()));
    }
    
    public void setOpponentsDrawOffer(final boolean opponentsDrawOfferPending) {
        this._opponentsDrawOfferPending = opponentsDrawOfferPending;
    }
    
    public void setPlayerColor(final boolean playerColorWhite) {
        this._playerColorWhite = playerColorWhite;
    }
    
    public void setPlayersDrawOffer(final boolean myDrawOfferPending) {
        this._myDrawOfferPending = myDrawOfferPending;
    }
}
