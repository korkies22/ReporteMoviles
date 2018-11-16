// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;

public class GameModel
{
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
    
    public GameModel(final String fullRKey, final String playerWhiteId, final String playerBlackId) {
        this._fullRKey = fullRKey;
        this._playerWhiteId = playerWhiteId;
        this._playerBlackId = playerBlackId;
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
    
    public void setCurrentFen(final FEN currentFen) {
        this._currentFen = currentFen;
    }
    
    public void setEngineEvaluation(final EngineEvaluation engineEvaluation) {
        this._engineEvaluation = engineEvaluation;
    }
    
    public void setGameNumber(final int gameNumber) {
        this._gameNumber = gameNumber;
    }
    
    public void setLastMovesEANAndEarlierFEN(final String[] lastMoveEANs, final FEN earlierFEN) {
        this._earlierFEN = earlierFEN;
        this._lastMoveEANs = lastMoveEANs;
    }
    
    public void setRemainingTimeBlack(final long remainingTimeBlack) {
        this._remainingTimeBlack = remainingTimeBlack;
    }
    
    public void setRemainingTimeWhite(final long remainingTimeWhite) {
        this._remainingTimeWhite = remainingTimeWhite;
    }
    
    public void setStatus(final BroadcastGameStatus gameStatus) {
        this._gameStatus = gameStatus;
    }
}
