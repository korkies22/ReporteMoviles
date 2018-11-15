/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package de.cisha.android.board.playzone.remote.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import de.cisha.android.board.playzone.model.AbstractGameModel;
import de.cisha.android.board.playzone.model.ChessClock;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.model.IChessGameConnection;
import de.cisha.android.board.playzone.model.IGameModelDelegate;
import de.cisha.android.board.playzone.model.PlayZoneChessClock;
import de.cisha.android.board.playzone.model.PlayzoneGameHolder;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.playzone.remote.PlayzoneConnectionListener;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;
import de.cisha.android.board.playzone.remote.model.RemoteGameDelegator;
import de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection;
import de.cisha.android.board.service.IGameService;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.MoveContainer;
import de.cisha.chess.model.MovesObserver;
import de.cisha.chess.model.Piece;
import de.cisha.chess.model.SEP;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import java.util.Timer;
import java.util.TimerTask;

public class RemoteGameAdapter
extends AbstractGameModel
implements RemoteGameDelegator {
    public static final int START_GAME_FAILED_NO_GAME_ALLOWED = 12;
    public static final int START_GAME_FAILED_NO_OPPONENT_FOUND = 11;
    private int _abortTimeout;
    private final CishaUUID _authToken;
    private IRemoteGameConnection _connection;
    private PlayzoneConnectionListener _connectionListener;
    private TimerTask _disconnectTask;
    private EloRange _eloRange;
    private String _engineUuid;
    private String _gameSessionToken;
    private Handler _handler;
    private Move _lastSentMove;
    private MovesObserver _movesObserver;
    private boolean _myConnectionState = true;
    private NodeServerAddress _pairing;
    private NodeServerAddress _playing;
    private boolean _ratedGame;
    private final boolean _reconnect;
    private boolean _reconnectStateWaitingForResponse;
    private boolean _rematch;
    private boolean _started;
    private TimeoutTask _timeoutTask;
    private Timer _timer;
    private long _timoutForReconnect;
    private int _winTimeout;

    private RemoteGameAdapter(Builder builder) {
        super(builder._delegate, builder._playerIsWhite);
        this._pairing = builder._pairing;
        this._playing = builder._playing;
        this._authToken = builder._authToken;
        this._connection = builder._connection;
        this._reconnect = builder._reconnect;
        this._rematch = builder._rematch;
        this._gameSessionToken = builder._gameSessionToken;
        this._engineUuid = builder._engineUuid;
        this._ratedGame = builder._ratedGame;
        this._eloRange = builder._eloRange;
        if (!this._reconnect) {
            this._clock = new PlayZoneChessClock(builder._clockSetting);
        }
        this._timer = new Timer("remotegameadapter");
    }

    private Handler getHandler() {
        if (this._handler == null) {
            this._handler = new Handler(Looper.getMainLooper());
        }
        return this._handler;
    }

    @Override
    public void destroy() {
        this._connectionListener = null;
        if (this._handler != null) {
            this._handler.removeMessages(0);
            this._handler = null;
        }
        super.destroy();
    }

    @Override
    protected ChessClock getClock() {
        return this._gameHolder.getClock();
    }

    @Override
    protected IChessGameConnection getConnection() {
        return this._connection;
    }

    public int getConnectionTimeout(boolean bl) {
        int n = this._gameHolder.getPosition().getHalfMoveNumber() >= 2 ? this._winTimeout : this._abortTimeout;
        boolean bl2 = this._gameHolder.getPosition().getActiveColor() == this._playerIsWhite;
        int n2 = n;
        if (bl ^ true ^ bl2) {
            n2 = Math.min(n, (int)(this.getClock().getTimeMillis(this.getClock().getActiveColor()) + this.getClock().getTimeSinceLastMove()));
        }
        return n2;
    }

    public PlayzoneGameSessionInfo getGameSessionInfo() {
        return new PlayzoneGameSessionInfo(this._gameSessionToken, this._playerIsWhite, this._playing.getServerAddress(), this._playing.getPort());
    }

    @Override
    public boolean isRemoteGame() {
        return true;
    }

    @Override
    public void offerOrAcceptDraw() {
        if (this._gameHolder.opponentsDrawOfferPending()) {
            this._connection.acceptDrawOffer();
            return;
        }
        this._connection.offerDraw();
        this._gameHolder.setPlayersDrawOffer(true);
    }

    @Override
    public void onGameEnd(final GameEndInformation gameEndInformation) {
        this._gameHolder.setGameActive(false);
        this._game.setResult(gameEndInformation.getStatus());
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._delegate.onGameEnd(gameEndInformation);
                RemoteGameAdapter.this._disconnectTask = new TimerTask(){

                    @Override
                    public void run() {
                        if (RemoteGameAdapter.this._connection != null) {
                            RemoteGameAdapter.this._connection.disconnect();
                            RemoteGameAdapter.this._delegate.onOpponentDeclinedRematch();
                        }
                    }
                };
                RemoteGameAdapter.this._timer.schedule(RemoteGameAdapter.this._disconnectTask, 30000L);
                RemoteGameAdapter.this._lastSentMove = null;
            }

        });
        if (this._game != null) {
            ServiceProvider.getInstance().getGameService().savePlayzoneGame(this._game);
        }
    }

    @Override
    public void onGameEndDetected(GameEndInformation gameEndInformation) {
    }

    @Override
    public void onGameSessionEnded(final GameStatus gameStatus) {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._gameHolder.setGameActive(false);
                RemoteGameAdapter.this._delegate.onGameSessionEnded(gameStatus);
            }
        });
    }

    @Override
    public void onGameSessionOver(final Game game, final GameEndInformation gameEndInformation) {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._gameHolder.setNewGame(game);
                RemoteGameAdapter.this._game = game;
                RemoteGameAdapter.this._gameHolder.setGameActive(false);
                RemoteGameAdapter.this._delegate.onGameSessionOver(game, gameEndInformation);
                RemoteGameAdapter.this._delegate.onOpponentDeclinedRematch();
                if (RemoteGameAdapter.this._connection != null) {
                    RemoteGameAdapter.this._connection.disconnect();
                }
            }
        });
    }

    @Override
    public void onGameStart(final Game game, final int n, final int n2) {
        Logger.getInstance().debug("de.cisha.test.socketIO", "called onGameStart() in RemoteGameAdapter");
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.super.onGameStart(game, n, n2);
                game.setType(GameType.ONLINEPLAYZONE);
                game.setSite("chess24.com");
                game.setTitle("Online Playzone Game");
                RemoteGameAdapter.this._winTimeout = n2;
                RemoteGameAdapter.this._abortTimeout = n;
                if (RemoteGameAdapter.this._lastSentMove != null) {
                    RemoteGameAdapter.this._gameHolder.doMoveInCurrentPosition(RemoteGameAdapter.this._lastSentMove.getSEP());
                }
            }
        });
    }

    @Override
    public void onJoinGameFailed() {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._delegate.onStartGameFailed(0);
            }
        });
    }

    @Override
    public void onMove(Move move) {
    }

    @Override
    public void onMove(String object, int n) {
        object = new SEP(this._gameHolder.getPosition().translateCastling((String)object));
        this.getHandler().post(new Runnable((SEP)object, n){
            final /* synthetic */ SEP val$sep;
            final /* synthetic */ int val$timeMillis;
            {
                this.val$sep = sEP;
                this.val$timeMillis = n;
            }

            @Override
            public void run() {
                Move move = RemoteGameAdapter.this._gameHolder.doMoveInCurrentPosition(this.val$sep);
                if (move != null) {
                    move.setMoveTimeInMills(this.val$timeMillis);
                    RemoteGameAdapter.this._gameHolder.addMoveTimeUsageForColor(move, this.val$timeMillis, RemoteGameAdapter.this._playerIsWhite ^ true);
                    RemoteGameAdapter.this._gameHolder.setPlayersDrawOffer(false);
                    RemoteGameAdapter.this._lastSentMove = null;
                }
            }
        });
    }

    @Override
    public void onMoveACK(final int n, int n2) {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._lastSentMove = null;
                Move move = RemoteGameAdapter.this._gameHolder.getRootMoveContainer().getLastMoveinMainLine();
                if (move != null) {
                    RemoteGameAdapter.this._gameHolder.addMoveTimeUsageForColor(move, n, RemoteGameAdapter.this._playerIsWhite);
                }
            }
        });
    }

    @Override
    public void onMyConnectionStateChanged(final boolean bl) {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._reconnectStateWaitingForResponse = false;
                if (RemoteGameAdapter.this._myConnectionState != bl) {
                    RemoteGameAdapter.this._myConnectionState = bl;
                    RemoteGameAdapter.this._timoutForReconnect = System.currentTimeMillis() + (long)RemoteGameAdapter.this.getConnectionTimeout(true);
                    if (RemoteGameAdapter.this._connectionListener != null && (RemoteGameAdapter.this._gameHolder.isGameActive() || bl)) {
                        RemoteGameAdapter.this._connectionListener.onMyConnectionStateChanged(bl);
                    }
                }
                if (!bl && RemoteGameAdapter.this._gameHolder.isGameActive()) {
                    if (System.currentTimeMillis() + 500L < RemoteGameAdapter.this._timoutForReconnect) {
                        RemoteGameAdapter.this._timer.schedule((TimerTask)new ReconnectTask(), 200L);
                        RemoteGameAdapter.this._reconnectStateWaitingForResponse = true;
                    }
                    if (RemoteGameAdapter.this._timeoutTask == null) {
                        RemoteGameAdapter.this._timeoutTask = new TimeoutTask();
                        RemoteGameAdapter.this._timer.schedule((TimerTask)RemoteGameAdapter.this._timeoutTask, RemoteGameAdapter.this.getConnectionTimeout(true));
                    }
                }
                if (bl && RemoteGameAdapter.this._timeoutTask != null) {
                    RemoteGameAdapter.this._timeoutTask.cancel();
                    RemoteGameAdapter.this._timeoutTask = null;
                }
            }
        });
    }

    @Override
    public void onOpponentsConnectionStateChanged(boolean bl, int n, boolean bl2) {
        if (this._connectionListener != null && this._gameHolder.isGameActive()) {
            this._connectionListener.onOpponentsConnectionStateChanged(bl, this.getConnectionTimeout(false), bl2);
        }
    }

    @Override
    public void onPairingFailed() {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._delegate.onStartGameFailed(0);
                RemoteGameAdapter.this._connection.disconnect();
            }
        });
    }

    @Override
    public void onPairingFailedNoOpponentFound() {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._delegate.onStartGameFailed(11);
                RemoteGameAdapter.this._connection.disconnect();
            }
        });
    }

    @Override
    public void onPairingFailedNotAllowed() {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._delegate.onStartGameFailed(12);
                RemoteGameAdapter.this._connection.disconnect();
            }
        });
    }

    @Override
    public void onPairingSucceeded(final boolean bl, ClockSetting clockSetting, final String string, final NodeServerAddress nodeServerAddress) {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._gameSessionToken = string;
                RemoteGameAdapter.this._playerIsWhite = bl;
                RemoteGameAdapter.this._playing = nodeServerAddress;
                RemoteGameAdapter.this._gameHolder.setPlayerColor(RemoteGameAdapter.this._playerIsWhite);
                RemoteGameAdapter.this._movesObserver = new MovesObserver(){

                    @Override
                    public void allMovesChanged(MoveContainer moveContainer) {
                    }

                    @Override
                    public boolean canSkipMoves() {
                        return false;
                    }

                    @Override
                    public void newMove(Move move) {
                        if (move.getPiece().isWhite() == RemoteGameAdapter.this._playerIsWhite) {
                            RemoteGameAdapter.this._lastSentMove = move;
                        }
                    }

                    @Override
                    public void selectedMoveChanged(Move move) {
                    }
                };
                RemoteGameAdapter.this._gameHolder.addMovesObserver(RemoteGameAdapter.this._movesObserver);
                Log.d((String)"de.cisha.test.socketIO", (String)"called onPairingSucceeded()");
            }

        });
    }

    @Override
    public void onReceiveDrawOffer() {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._gameHolder.setOpponentsDrawOffer(true);
                RemoteGameAdapter.this._delegate.onReceiveDrawOffer();
            }
        });
    }

    @Override
    public void onRematchOffer() {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._delegate.onReceivedRematchOffer();
            }
        });
    }

    @Override
    public void opponentDeclinedRematch() {
        this.getHandler().post(new Runnable(){

            @Override
            public void run() {
                RemoteGameAdapter.this._delegate.onOpponentDeclinedRematch();
            }
        });
    }

    @Override
    public boolean playWithChessClocks() {
        return true;
    }

    @Override
    public void requestRematch() {
        if (this._disconnectTask != null) {
            if (this._disconnectTask.cancel()) {
                this._connection.requestForRematch(this._gameSessionToken, this);
            } else {
                this._delegate.onOpponentDeclinedRematch();
            }
            this._disconnectTask = null;
        }
    }

    public void setPlayzoneConnectionListener(PlayzoneConnectionListener playzoneConnectionListener) {
        this._connectionListener = playzoneConnectionListener;
    }

    @Override
    public void startUp() {
        super.startUp();
        if (!this._started) {
            if (this._reconnect) {
                this._connection.initGameSession(this._gameSessionToken, this._playerIsWhite, this);
            } else if (this._rematch) {
                this._connection.requestForRematch(this._gameSessionToken, this);
            } else if (this._engineUuid != null) {
                this._connection.requestForChallengeEngine(this._engineUuid, this._clock.getClockSettings(), this._ratedGame, this);
            } else {
                this._connection.requestForPairing(this._clock.getClockSettings(), this._eloRange, this);
            }
        }
        this._started = true;
    }

    public static class Builder {
        private CishaUUID _authToken;
        private ClockSetting _clockSetting;
        private IRemoteGameConnection _connection;
        private IGameModelDelegate _delegate;
        private EloRange _eloRange;
        private String _engineUuid;
        private String _gameSessionToken;
        private NodeServerAddress _pairing;
        private boolean _playerIsWhite;
        private NodeServerAddress _playing;
        private boolean _ratedGame;
        private boolean _reconnect = false;
        private boolean _rematch = false;

        private Builder(CishaUUID cishaUUID, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
            this._authToken = cishaUUID;
            this._delegate = iGameModelDelegate;
            this._pairing = nodeServerAddress;
        }

        public Builder(CishaUUID cishaUUID, String string, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
            this(cishaUUID, iGameModelDelegate, nodeServerAddress);
            this._gameSessionToken = string;
            this._rematch = true;
            this._clockSetting = new ClockSetting(0, 0);
        }

        public Builder(CishaUUID cishaUUID, String string, boolean bl, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress, NodeServerAddress nodeServerAddress2) {
            this(cishaUUID, iGameModelDelegate, nodeServerAddress2);
            this._gameSessionToken = string;
            this._playerIsWhite = bl;
            this._playing = nodeServerAddress;
            this._reconnect = true;
        }

        public Builder(ClockSetting clockSetting, CishaUUID cishaUUID, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
            this(cishaUUID, iGameModelDelegate, nodeServerAddress);
            this._delegate = iGameModelDelegate;
            this._clockSetting = clockSetting;
        }

        public Builder(String string, ClockSetting clockSetting, boolean bl, CishaUUID cishaUUID, IGameModelDelegate iGameModelDelegate, NodeServerAddress nodeServerAddress) {
            this(clockSetting, cishaUUID, iGameModelDelegate, nodeServerAddress);
            this._engineUuid = string;
            this._ratedGame = bl;
        }

        public RemoteGameAdapter build() {
            if (this._connection == null) {
                this._connection = new SocketIORemoteGameConnection(this._playing, this._pairing, this._authToken);
            }
            return new RemoteGameAdapter(this);
        }

        public Builder connection(IRemoteGameConnection iRemoteGameConnection) {
            this._connection = iRemoteGameConnection;
            return this;
        }

        public Builder setEloRange(EloRange eloRange) {
            this._eloRange = eloRange;
            return this;
        }
    }

    private class ReconnectTask
    extends TimerTask {
        private ReconnectTask() {
        }

        @Override
        public void run() {
            if (RemoteGameAdapter.this._connection != null) {
                RemoteGameAdapter.this._connection.disconnect();
            }
            RemoteGameAdapter.this._connection = new SocketIORemoteGameConnection(RemoteGameAdapter.this._playing, RemoteGameAdapter.this._pairing, RemoteGameAdapter.this._authToken);
            RemoteGameAdapter.this._connection.initGameSession(RemoteGameAdapter.this._gameSessionToken, RemoteGameAdapter.this._playerIsWhite, RemoteGameAdapter.this);
        }
    }

    private class TimeoutTask
    extends TimerTask {
        private TimeoutTask() {
        }

        @Override
        public void run() {
            if (!RemoteGameAdapter.this._reconnectStateWaitingForResponse) {
                RemoteGameAdapter.this._gameHolder.setGameActive(false);
                Object object = RemoteGameAdapter.this._playerIsWhite ? GameResult.BLACK_WINS : GameResult.WHITE_WINS;
                object = new GameStatus((GameResult)((Object)object), GameEndReason.DISCONNECT_TIMEOUT);
                RemoteGameAdapter.this._delegate.onGameEnd(new GameEndInformation((GameStatus)object, RemoteGameAdapter.this.getPosition().getActiveColor()));
                return;
            }
            RemoteGameAdapter.this._timer.schedule((TimerTask)new TimeoutTask(), 500L);
        }
    }

}
