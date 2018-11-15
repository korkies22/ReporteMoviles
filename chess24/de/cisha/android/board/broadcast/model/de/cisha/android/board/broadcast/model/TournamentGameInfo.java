/*
 * Decompiled with CFR 0_134.
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.position.FastMoveStringGeneratingPosition;
import de.cisha.chess.model.position.Position;

public class TournamentGameInfo {
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

    public TournamentGameInfo(String string, TournamentPlayer tournamentPlayer, TournamentPlayer tournamentPlayer2) {
        this._fullRKey = string;
        this._playerWhite = tournamentPlayer;
        this._playerBlack = tournamentPlayer2;
        this._whitePlayerLeftSide = true;
        this._lastMovesString = "";
        this._lastMoveTimeStamp = System.currentTimeMillis();
    }

    public boolean getColorForPlayer(TournamentPlayer tournamentPlayer) {
        if (this.getPlayerWhite() != null && this.getPlayerWhite().equals(tournamentPlayer)) {
            return true;
        }
        return false;
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
            FEN fEN = this._earlierFEN == null ? FEN.STARTING_POSITION : this._earlierFEN;
            if (fEN.isValid()) {
                this._lastMovesString = new FastMoveStringGeneratingPosition(new Position(fEN)).convertCANToFAN(this._lastMovesEANs);
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

    public TournamentPlayer getOpponentOfPlayer(TournamentPlayer tournamentPlayer) {
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

    public float getScoreForPlayer(TournamentPlayer tournamentPlayer) {
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

    public boolean hasPlayer(TournamentPlayer tournamentPlayer) {
        if (this.getPlayerWhite() != null && this.getPlayerWhite().equals(tournamentPlayer) || this.getPlayerBlack() != null && this.getPlayerBlack().equals(tournamentPlayer)) {
            return true;
        }
        return false;
    }

    public boolean isOngoing() {
        if (this._status != null && this._status.isOngoing()) {
            return true;
        }
        return false;
    }

    public boolean isWhitePlayerActive() {
        return this._whitePlayerActive;
    }

    public boolean isWhitePlayerLeftSide() {
        return this._whitePlayerLeftSide;
    }

    public void setBlackPlayer(TournamentPlayer tournamentPlayer) {
        this._playerBlack = tournamentPlayer;
    }

    public void setCurrentFen(FEN fEN) {
        this._currentFen = fEN;
    }

    public void setEngineEvaluation(EngineEvaluation engineEvaluation) {
        this._evaluation = engineEvaluation;
    }

    public void setGameNumber(int n) {
        this._gameNumber = n;
    }

    public void setLastMoveTimeStamp(long l) {
        this._lastMoveTimeStamp = l;
    }

    public void setLastMovesEANsAndEarlierFEN(String[] arrstring, FEN fEN) {
        this._lastMovesEANs = arrstring;
        this._earlierFEN = fEN;
    }

    public void setMatchNumber(int n) {
        this._matchNumber = n;
    }

    public void setRemainingTimeBlack(long l) {
        this._remainingTimeMillisBlack = l;
    }

    public void setRemainingTimeWhite(long l) {
        this._remainingTimeMillisWhite = l;
    }

    public void setRoundNumber(int n) {
        this._roundNumber = n;
    }

    public void setStatus(BroadcastGameStatus broadcastGameStatus) {
        this._status = broadcastGameStatus;
    }

    public void setTimeControl(TimeControl timeControl) {
        this._timeControl = timeControl;
    }

    public void setWhitePlayer(TournamentPlayer tournamentPlayer) {
        this._playerWhite = tournamentPlayer;
    }

    public void setWhitePlayerActive(boolean bl) {
        this._whitePlayerActive = bl;
    }

    public void setWhitePlayerLeftSide(boolean bl) {
        this._whitePlayerLeftSide = bl;
    }
}
