/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;

public class GameModel {
    private FEN _currentFen;
    private FEN _earlierFEN;
    private EngineEvaluation _engineEvaluation;
    private String _fullRKey;
    private int _gameNumber;
    private BroadcastGameStatus _gameStatus;
    private String[] _lastMoveEANs;
    private String _playerBlackId;
    private String _playerWhiteId;
    private long _remainingTimeBlack;
    private long _remainingTimeWhite;

    public GameModel(String string, String string2, String string3) {
        this._fullRKey = string;
        this._playerWhiteId = string2;
        this._playerBlackId = string3;
    }

    public FEN getCurrentFen() {
        return this._currentFen;
    }

    public FEN getEarlierFEN() {
        return this._earlierFEN;
    }

    public EngineEvaluation getEngineEvaluation() {
        return this._engineEvaluation;
    }

    public String getFullRKey() {
        return this._fullRKey;
    }

    public int getGameNumber() {
        return this._gameNumber;
    }

    public BroadcastGameStatus getGameStatus() {
        return this._gameStatus;
    }

    public String[] getLastMoveEANs() {
        return this._lastMoveEANs;
    }

    public String getPlayerBlackId() {
        return this._playerBlackId;
    }

    public String getPlayerWhiteId() {
        return this._playerWhiteId;
    }

    public long getRemainingTimeBlack() {
        return this._remainingTimeBlack;
    }

    public long getRemainingTimeWhite() {
        return this._remainingTimeWhite;
    }

    public void setCurrentFen(FEN fEN) {
        this._currentFen = fEN;
    }

    public void setEngineEvaluation(EngineEvaluation engineEvaluation) {
        this._engineEvaluation = engineEvaluation;
    }

    public void setGameNumber(int n) {
        this._gameNumber = n;
    }

    public void setLastMovesEANAndEarlierFEN(String[] arrstring, FEN fEN) {
        this._earlierFEN = fEN;
        this._lastMoveEANs = arrstring;
    }

    public void setRemainingTimeBlack(long l) {
        this._remainingTimeBlack = l;
    }

    public void setRemainingTimeWhite(long l) {
        this._remainingTimeWhite = l;
    }

    public void setStatus(BroadcastGameStatus broadcastGameStatus) {
        this._gameStatus = broadcastGameStatus;
    }
}
