// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import de.cisha.chess.model.position.FastMoveStringGeneratingPosition;
import de.cisha.chess.model.position.Position;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;

public class TournamentGameInfo
{
    private FEN _currentFen;
    private FEN _earlierFEN;
    private EngineEvaluation _evaluation;
    private String _fullRKey;
    private int _gameNumber;
    private long _lastMoveTimeStamp;
    private String[] _lastMovesEANs;
    private String _lastMovesString;
    private int _matchNumber;
    private TournamentPlayer _playerBlack;
    private TournamentPlayer _playerWhite;
    private long _remainingTimeMillisBlack;
    private long _remainingTimeMillisWhite;
    private int _roundNumber;
    private BroadcastGameStatus _status;
    private TimeControl _timeControl;
    private boolean _whitePlayerActive;
    private boolean _whitePlayerLeftSide;
    
    public TournamentGameInfo(final String fullRKey, final TournamentPlayer playerWhite, final TournamentPlayer playerBlack) {
        this._fullRKey = fullRKey;
        this._playerWhite = playerWhite;
        this._playerBlack = playerBlack;
        this._whitePlayerLeftSide = true;
        this._lastMovesString = "";
        this._lastMoveTimeStamp = System.currentTimeMillis();
    }
    
    public boolean getColorForPlayer(final TournamentPlayer tournamentPlayer) {
        return this.getPlayerWhite() != null && this.getPlayerWhite().equals(tournamentPlayer);
    }
    
    public FEN getCurrentFen() {
        return this._currentFen;
    }
    
    public String getEngineEvaluationString() {
        if (this._evaluation == null) {
            return "";
        }
        return this._evaluation.getEvaluationAsString();
    }
    
    public float getEval() {
        if (this._evaluation == null) {
            return 0.0f;
        }
        return this._evaluation.getEvaluation();
    }
    
    public TournamentGameID getGameID() {
        return new TournamentGameID(this._fullRKey);
    }
    
    public int getGameNumber() {
        return this._gameNumber;
    }
    
    public CharSequence getLastMoveText() {
        if (this._lastMovesEANs != null) {
            FEN fen;
            if (this._earlierFEN == null) {
                fen = FEN.STARTING_POSITION;
            }
            else {
                fen = this._earlierFEN;
            }
            if (fen.isValid()) {
                this._lastMovesString = new FastMoveStringGeneratingPosition(new Position(fen)).convertCANToFAN(this._lastMovesEANs);
                this._lastMovesEANs = null;
            }
        }
        return this._lastMovesString;
    }
    
    public long getLastMoveTimeStamp() {
        return this._lastMoveTimeStamp;
    }
    
    public int getMatchNumber() {
        return this._matchNumber;
    }
    
    public TournamentPlayer getOpponentOfPlayer(final TournamentPlayer tournamentPlayer) {
        if (this.getPlayerWhite() == tournamentPlayer) {
            return this.getPlayerBlack();
        }
        if (this.getPlayerBlack() == tournamentPlayer) {
            return this.getPlayerWhite();
        }
        return null;
    }
    
    public TournamentPlayer getPlayerBlack() {
        return this._playerBlack;
    }
    
    public TournamentPlayer getPlayerLeft() {
        if (this._whitePlayerLeftSide) {
            return this._playerWhite;
        }
        return this._playerBlack;
    }
    
    public TournamentPlayer getPlayerRight() {
        if (this._whitePlayerLeftSide) {
            return this._playerBlack;
        }
        return this._playerWhite;
    }
    
    public TournamentPlayer getPlayerWhite() {
        return this._playerWhite;
    }
    
    public long getRemainingTimeMillisBlack() {
        return this._remainingTimeMillisBlack;
    }
    
    public long getRemainingTimeMillisPlayerLeft() {
        if (this._whitePlayerLeftSide) {
            return this._remainingTimeMillisWhite;
        }
        return this._remainingTimeMillisBlack;
    }
    
    public long getRemainingTimeMillisPlayerRight() {
        if (this._whitePlayerLeftSide) {
            return this._remainingTimeMillisBlack;
        }
        return this._remainingTimeMillisWhite;
    }
    
    public long getRemainingTimeMillisWhite() {
        return this._remainingTimeMillisWhite;
    }
    
    public int getRoundNumber() {
        return this._roundNumber;
    }
    
    public float getScoreForPlayer(final TournamentPlayer tournamentPlayer) {
        if (this._status != null && this.hasPlayer(tournamentPlayer)) {
            if (tournamentPlayer.equals(this.getPlayerWhite())) {
                return this._status.getResult().getScoreWhite();
            }
            if (tournamentPlayer.equals(this.getPlayerBlack())) {
                return this._status.getResult().getScoreBlack();
            }
        }
        return 0.0f;
    }
    
    public BroadcastGameStatus getStatus() {
        return this._status;
    }
    
    public TimeControl getTimeControl() {
        return this._timeControl;
    }
    
    public boolean hasPlayer(final TournamentPlayer tournamentPlayer) {
        return (this.getPlayerWhite() != null && this.getPlayerWhite().equals(tournamentPlayer)) || (this.getPlayerBlack() != null && this.getPlayerBlack().equals(tournamentPlayer));
    }
    
    public boolean isOngoing() {
        return this._status != null && this._status.isOngoing();
    }
    
    public boolean isWhitePlayerActive() {
        return this._whitePlayerActive;
    }
    
    public boolean isWhitePlayerLeftSide() {
        return this._whitePlayerLeftSide;
    }
    
    public void setBlackPlayer(final TournamentPlayer playerBlack) {
        this._playerBlack = playerBlack;
    }
    
    public void setCurrentFen(final FEN currentFen) {
        this._currentFen = currentFen;
    }
    
    public void setEngineEvaluation(final EngineEvaluation evaluation) {
        this._evaluation = evaluation;
    }
    
    public void setGameNumber(final int gameNumber) {
        this._gameNumber = gameNumber;
    }
    
    public void setLastMoveTimeStamp(final long lastMoveTimeStamp) {
        this._lastMoveTimeStamp = lastMoveTimeStamp;
    }
    
    public void setLastMovesEANsAndEarlierFEN(final String[] lastMovesEANs, final FEN earlierFEN) {
        this._lastMovesEANs = lastMovesEANs;
        this._earlierFEN = earlierFEN;
    }
    
    public void setMatchNumber(final int matchNumber) {
        this._matchNumber = matchNumber;
    }
    
    public void setRemainingTimeBlack(final long remainingTimeMillisBlack) {
        this._remainingTimeMillisBlack = remainingTimeMillisBlack;
    }
    
    public void setRemainingTimeWhite(final long remainingTimeMillisWhite) {
        this._remainingTimeMillisWhite = remainingTimeMillisWhite;
    }
    
    public void setRoundNumber(final int roundNumber) {
        this._roundNumber = roundNumber;
    }
    
    public void setStatus(final BroadcastGameStatus status) {
        this._status = status;
    }
    
    public void setTimeControl(final TimeControl timeControl) {
        this._timeControl = timeControl;
    }
    
    public void setWhitePlayer(final TournamentPlayer playerWhite) {
        this._playerWhite = playerWhite;
    }
    
    public void setWhitePlayerActive(final boolean whitePlayerActive) {
        this._whitePlayerActive = whitePlayerActive;
    }
    
    public void setWhitePlayerLeftSide(final boolean whitePlayerLeftSide) {
        this._whitePlayerLeftSide = whitePlayerLeftSide;
    }
}
