// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.model;

import java.io.Serializable;
import de.cisha.chess.model.Game;
import de.cisha.android.board.service.jsonparser.JSONGameSessionOverEloChangeParser;
import de.cisha.chess.model.GameEndReason;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.service.jsonparser.JSONEloChangeParser;
import de.cisha.chess.model.GameStatus;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import java.util.TimerTask;
import java.net.MalformedURLException;
import io.socket.ConnectionListener;
import io.socket.SocketIOException;
import android.util.Log;
import org.json.JSONException;
import de.cisha.chess.model.ClockSetting;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONNodeServerAddressParser;
import org.json.JSONObject;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import de.cisha.chess.util.Logger;
import io.socket.SocketIO;
import de.cisha.android.board.service.NodeServerAddress;
import java.util.Timer;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.CishaUUID;

public class SocketIORemoteGameConnection implements IRemoteGameConnection
{
    private static final String AUTH_TOKEN = "authToken";
    private static final String COMMAND_ABORT_OR_RESIGN = "abortOrResign";
    private static final String COMMAND_ACCEPT_DRAW = "acceptDraw";
    private static final String COMMAND_CANCEL = "cancel";
    private static final String COMMAND_DRAW_OFFER = "drawOffer";
    private static final String COMMAND_JOIN_GAME_SESSION = "joinGameSession";
    private static final String COMMAND_MOVE = "move";
    private static final String COMMAND_REMATCH = "reMatch";
    private static final String GAME_SESSION_TOKEN = "gameSessionToken";
    private static final long MOVE_SEND_TIMEOUT = 2000L;
    private static final String PARAM_KEY = "score";
    private static final String PARAM_KEY_ABORT = "abort";
    private static final String PARAM_KEY_ABORT_TIMEOUT = "abort";
    private static final String PARAM_KEY_BLACK = "black";
    private static final String PARAM_KEY_MOVE = "move";
    private static final String PARAM_KEY_TIME_USAGE = "timeUsage";
    private static final String PARAM_KEY_TIME_USAGE_CLIENT = "timeUsageClient";
    private static final String PARAM_KEY_WHITE = "white";
    private static final String PARAM_KEY_WIN_ON_DISCONNECT = "win";
    private static final String PARAM_KEY_WIN_TIMEOUT = "win";
    private CishaUUID _authToken;
    private int _baseTimeInSeconds;
    private int _commandId;
    private int _currentHalfMoveNumber;
    private Move _currentMoveSendWaitingForAck;
    private RemoteGameDelegator _delegate;
    private String _gameSessionToken;
    private int _increment;
    private Timer _moveAckTimer;
    private NodeServerAddress _pairingServerAddress;
    private boolean _playSocketDisconnected;
    private boolean _playerActive;
    private boolean _playerIsWhite;
    private NodeServerAddress _playingServerAddress;
    private SocketIO _socketPairing;
    private SocketIO _socketPlay;
    private long _timeOnReceivedMove;
    
    public SocketIORemoteGameConnection(final NodeServerAddress playingServerAddress, final NodeServerAddress pairingServerAddress, final CishaUUID authToken) {
        this._commandId = 0;
        this._currentHalfMoveNumber = 0;
        this._moveAckTimer = new Timer();
        this._pairingServerAddress = pairingServerAddress;
        this._playingServerAddress = playingServerAddress;
        this._authToken = authToken;
    }
    
    private void checkMoveNumber(final int n) {
        final boolean b = n != this._currentHalfMoveNumber;
        final Logger instance = Logger.getInstance();
        final StringBuilder sb = new StringBuilder();
        sb.append("local moveNo: ");
        sb.append(this._currentHalfMoveNumber);
        sb.append(" server moveNo: ");
        sb.append(n);
        String s;
        if (b) {
            s = " <<<<<<<<<<< reconnect";
        }
        else {
            s = "";
        }
        sb.append(s);
        instance.debug("Playzone Ping", sb.toString());
        if (b) {
            this.disconnect();
        }
    }
    
    private void initSocketPairingInstance(final RemoteGameDelegator delegate, final CishaUUID cishaUUID) {
        this._delegate = delegate;
        if (this._socketPairing != null && this._socketPairing.isConnected()) {
            this._socketPairing.disconnect();
        }
        try {
            (this._socketPairing = new SocketIO(this._pairingServerAddress.getURLString())).connect(new IOCallback() {
                @Override
                public void on(String string, IOAcknowledge jsonObject, final Object... array) {
                    for (int i = 0; i < array.length; ++i) {
                        final Logger instance = Logger.getInstance();
                        final StringBuilder sb = new StringBuilder();
                        sb.append("on ");
                        sb.append(string);
                        sb.append(i);
                        sb.append(" ");
                        sb.append(array[i].toString());
                        instance.debug("de.cisha.test.socketIO", sb.toString());
                    }
                    if (string.equals("initGameSession")) {
                        try {
                            final JSONObject jsonObject2 = new JSONObject(array[0].toString());
                            string = jsonObject2.getString("gameSessionToken");
                            final boolean equals = jsonObject2.getString("role").equals("w");
                            jsonObject2.optString("mode", "human");
                            jsonObject = (IOAcknowledge)jsonObject2.getJSONObject("playingServer");
                            final JSONNodeServerAddressParser jsonNodeServerAddressParser = new JSONNodeServerAddressParser();
                            try {
                                SocketIORemoteGameConnection.this._playingServerAddress = jsonNodeServerAddressParser.parseResult((JSONObject)jsonObject);
                            }
                            catch (InvalidJsonForObjectException ex) {
                                final Logger instance2 = Logger.getInstance();
                                final StringBuilder sb2 = new StringBuilder();
                                sb2.append("Cant parse playingServer adress:");
                                sb2.append(jsonObject);
                                instance2.debug("SocketIORemoteGameConnection", sb2.toString(), ex);
                            }
                            SocketIORemoteGameConnection.this._delegate.onPairingSucceeded(equals, new ClockSetting(SocketIORemoteGameConnection.this._baseTimeInSeconds, SocketIORemoteGameConnection.this._increment), string, SocketIORemoteGameConnection.this._playingServerAddress);
                            SocketIORemoteGameConnection.this.initGameSession(string, equals, SocketIORemoteGameConnection.this._delegate);
                        }
                        catch (JSONException ex2) {
                            Log.d(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex2);
                        }
                    }
                    else if (string.equals("noMatch")) {
                        SocketIORemoteGameConnection.this._delegate.onPairingFailedNoOpponentFound();
                    }
                    else if (string.equals("informNotAllowed")) {
                        SocketIORemoteGameConnection.this._delegate.onPairingFailedNotAllowed();
                    }
                    else {
                        final Logger instance3 = Logger.getInstance();
                        final StringBuilder sb3 = new StringBuilder();
                        sb3.append("unkown command from  pairing server:");
                        sb3.append(string);
                        instance3.error("SocketIORemoteGameConnection:", sb3.toString());
                        SocketIORemoteGameConnection.this._delegate.onPairingFailed();
                    }
                    SocketIORemoteGameConnection.this._socketPairing.disconnect();
                }
                
                @Override
                public void onConnect() {
                    Logger.getInstance().debug("de.cisha.test.socketIO", "onConnect ");
                }
                
                @Override
                public void onDisconnect() {
                    Logger.getInstance().debug("de.cisha.test.socketIO", "onDisconnect ");
                }
                
                @Override
                public void onError(final SocketIOException ex) {
                    Logger.getInstance().debug("de.cisha.test.socketIO", "Pairing: onError ", ex);
                    SocketIORemoteGameConnection.this._delegate.onPairingFailed();
                }
                
                @Override
                public void onMessage(final String s, final IOAcknowledge ioAcknowledge) {
                    final Logger instance = Logger.getInstance();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("onMessage(String) ");
                    sb.append(s.toString());
                    instance.debug("de.cisha.test.socketIO", sb.toString());
                }
                
                @Override
                public void onMessage(final JSONObject jsonObject, final IOAcknowledge ioAcknowledge) {
                    final Logger instance = Logger.getInstance();
                    final StringBuilder sb = new StringBuilder();
                    sb.append("onMessage(json) ");
                    sb.append(jsonObject.toString());
                    instance.debug("de.cisha.test.socketIO", sb.toString());
                }
            }, null);
        }
        catch (MalformedURLException ex) {
            Log.e(SocketIORemoteGameConnection.class.getName(), MalformedURLException.class.getName(), (Throwable)ex);
        }
    }
    
    private void initSocketPlayInstance() {
        if (this._socketPlay != null && this._socketPlay.isConnected()) {
            this._socketPlay.disconnect();
        }
        try {
            if (this._playingServerAddress != null) {
                this._socketPlay = new SocketIO(this._playingServerAddress.getURLString());
                return;
            }
            Logger.getInstance().debug("SocketIORemoteGameConnection", "no playingServerAdress loaded - not able to init Socket instance");
        }
        catch (MalformedURLException ex) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), MalformedURLException.class.getName(), ex);
        }
    }
    
    private void joinGame(final String gameSessionToken, CishaUUID cishaUUID) {
        this._gameSessionToken = gameSessionToken;
        cishaUUID = (CishaUUID)new JSONObject();
        try {
            ((JSONObject)cishaUUID).put("gameSessionToken", (Object)gameSessionToken);
            ((JSONObject)cishaUUID).put("authToken", (Object)this._authToken.getUuid());
        }
        catch (JSONException ex) {
            this._delegate.onJoinGameFailed();
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("exception ");
            sb.append(JSONException.class.getName());
            instance.debug("de.cisha.test.sockectIO", sb.toString(), (Throwable)ex);
        }
        this._socketPlay.emit("joinGameSession", cishaUUID);
        final Logger instance2 = Logger.getInstance();
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("send joinGameSession ");
        sb2.append(((JSONObject)cishaUUID).toString());
        instance2.debug("de.cisha.test.socketIO", sb2.toString());
    }
    
    private void notifyDisconnectListener() {
        synchronized (this) {
            if (!this._playSocketDisconnected) {
                this._playSocketDisconnected = true;
                this._delegate.onMyConnectionStateChanged(false);
            }
        }
    }
    
    private void sendPairingRequest(final ClockSetting clockSetting, final EloRange eloRange, final RemoteGameDelegator remoteGameDelegator, final String s, final boolean b) {
        this._baseTimeInSeconds = clockSetting.getBase(true) / 1000;
        this._increment = clockSetting.getIncrementPerMove(true) / 1000;
        this.initSocketPairingInstance(remoteGameDelegator, this._authToken);
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("authToken", (Object)this._authToken.getUuid());
            jsonObject.put("clock", this._baseTimeInSeconds);
            if (s != null) {
                jsonObject.put("engineUuid", (Object)s);
                jsonObject.put("rated", b);
            }
            jsonObject.put("increment", this._increment);
            if (eloRange != null) {
                final JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("min", eloRange.getMin());
                jsonObject2.put("max", eloRange.getMax());
                jsonObject.put("eloRange", (Object)jsonObject2);
            }
            this._socketPairing.emit("play", jsonObject);
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("send play (pairing) ");
            sb.append(jsonObject.toString());
            instance.debug("de.cisha.test.socketIO", sb.toString());
        }
        catch (JSONException ex) {
            final Logger instance2 = Logger.getInstance();
            final StringBuilder sb2 = new StringBuilder();
            sb2.append("exception ");
            sb2.append(JSONException.class.getName());
            instance2.debug("de.cisha.test.sockectIO", sb2.toString(), (Throwable)ex);
            this._delegate.onPairingFailed();
            this._socketPairing.disconnect();
        }
    }
    
    @Override
    public void acceptDrawOffer() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gameSessionToken", (Object)this._gameSessionToken);
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex);
        }
        this._socketPlay.emit("acceptDraw", jsonObject);
    }
    
    @Override
    public void disconnect() {
        if (this._socketPlay != null) {
            this._socketPlay.disconnect();
        }
        if (this._socketPairing == null) {
            return;
        }
        while (true) {
            try {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("authToken", (Object)this._authToken.getUuid());
                this._socketPairing.emit("cancel", jsonObject);
                this._socketPairing.disconnect();
            }
            catch (JSONException ex) {
                continue;
            }
            break;
        }
    }
    
    @Override
    public void doMove(final Move currentMoveSendWaitingForAck) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("move", (Object)currentMoveSendWaitingForAck.getEAN());
            jsonObject.put("gameSessionToken", (Object)this._gameSessionToken);
            jsonObject.put("id", ++this._commandId);
            if (this._timeOnReceivedMove > 0L) {
                jsonObject.put("timeUsageClient", System.currentTimeMillis() - this._timeOnReceivedMove);
            }
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex);
        }
        this._currentMoveSendWaitingForAck = currentMoveSendWaitingForAck;
        this._socketPlay.emit("move", jsonObject);
        currentMoveSendWaitingForAck.setMoveId(this._commandId);
        this._moveAckTimer.schedule(new WaitForMoveAck(currentMoveSendWaitingForAck), 2000L);
    }
    
    @Override
    public void initGameSession(final String s, final boolean playerIsWhite, final RemoteGameDelegator delegate) {
        this._playerIsWhite = playerIsWhite;
        this._delegate = delegate;
        this.initSocketPlayInstance();
        if (this._socketPlay == null) {
            this._delegate.onJoinGameFailed();
            return;
        }
        this._socketPlay.connect(new IOCallback() {
            @Override
            public void on(String result, IOAcknowledge game_RUNNING, Object... result2) {
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("on ");
                sb.append(result);
                instance.debug("de.cisha.test.socketIO", sb.toString());
                final boolean b = false;
                for (int i = 0; i < result2.length; ++i) {
                    final Logger instance2 = Logger.getInstance();
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append("on ");
                    sb2.append(result);
                    sb2.append(i);
                    sb2.append(" ");
                    sb2.append(result2[i].toString());
                    instance2.debug("de.cisha.test.socketIO", sb2.toString());
                }
                if (result.equals("ping")) {
                    final JSONObject jsonObject = new JSONObject();
                    if (result2 == null) {
                        return;
                    }
                    try {
                        if (result2.length <= 0 || result2[0] == null) {
                            return;
                        }
                        final JSONObject jsonObject2 = new JSONObject(result2[0].toString());
                        jsonObject.put("gameSessionToken", (Object)s);
                        jsonObject.put("pingToken", (Object)jsonObject2.optString("pingToken"));
                        SocketIORemoteGameConnection.this._socketPlay.emit("pingACK", jsonObject);
                        final JSONObject optJSONObject = jsonObject2.optJSONObject("data");
                        if (optJSONObject == null) {
                            return;
                        }
                        final int optInt = optJSONObject.optInt("moveNo", -1);
                        if (optInt != -1) {
                            SocketIORemoteGameConnection.this.checkMoveNumber(optInt);
                        }
                        return;
                    }
                    catch (JSONException ex) {
                        Log.d(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex);
                        return;
                    }
                }
                if (result.equals("startGameSession")) {
                    try {
                        final JSONObject jsonObject3 = new JSONObject(result2[0].toString());
                        final JSONObject jsonObject4 = jsonObject3.getJSONObject("game");
                        try {
                            final Game result3 = new JSONGameParser().parseResult(jsonObject4);
                            int optInt2 = 10000;
                            int optInt3 = 30000;
                            if (jsonObject3.has("timeouts")) {
                                final JSONObject jsonObject5 = jsonObject3.getJSONObject("timeouts");
                                optInt2 = jsonObject5.optInt("abort");
                                optInt3 = jsonObject5.optInt("win");
                            }
                            SocketIORemoteGameConnection.this._delegate.onGameStart(result3, optInt2, optInt3);
                            final Move lastMoveinMainLine = result3.getLastMoveinMainLine();
                            final SocketIORemoteGameConnection this.0 = SocketIORemoteGameConnection.this;
                            int halfMoveNumber = b ? 1 : 0;
                            if (lastMoveinMainLine != null) {
                                halfMoveNumber = lastMoveinMainLine.getHalfMoveNumber();
                            }
                            this.0._currentHalfMoveNumber = halfMoveNumber;
                            return;
                        }
                        catch (InvalidJsonForObjectException ex2) {
                            Logger.getInstance().error(this.getClass().getName(), "error parsing returned game", ex2);
                            return;
                        }
                    }
                    catch (JSONException ex3) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex3);
                        SocketIORemoteGameConnection.this._delegate.onJoinGameFailed();
                        SocketIORemoteGameConnection.this.disconnect();
                        return;
                    }
                }
                if (result.equals("receivedMove")) {
                    SocketIORemoteGameConnection.this._timeOnReceivedMove = System.currentTimeMillis();
                    SocketIORemoteGameConnection.this._currentHalfMoveNumber++;
                    SocketIORemoteGameConnection.this._playerActive = true;
                    try {
                        final JSONObject jsonObject6 = new JSONObject(result2[0].toString());
                        SocketIORemoteGameConnection.this._delegate.onMove(jsonObject6.optString("move"), jsonObject6.optInt("timeUsage"));
                        return;
                    }
                    catch (JSONException ex4) {
                        Logger.getInstance().debug(JSONException.class.getName(), JSONException.class.getName(), (Throwable)ex4);
                        return;
                    }
                }
                if (result.equals("receivedMoveACK")) {
                    SocketIORemoteGameConnection.this._currentHalfMoveNumber++;
                    try {
                        final JSONObject jsonObject7 = new JSONObject(result2[0].toString());
                        final int optInt4 = jsonObject7.optInt("timeUsage");
                        final int optInt5 = jsonObject7.optInt("id");
                        SocketIORemoteGameConnection.this._playerActive = false;
                        SocketIORemoteGameConnection.this._delegate.onMoveACK(optInt4, optInt5);
                        if (SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck != null && SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck.getMoveId() == optInt5) {
                            SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck = null;
                        }
                        return;
                    }
                    catch (JSONException ex5) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex5);
                        return;
                    }
                }
                if (result.equals("gameSessionEnded")) {
                    try {
                        SocketIORemoteGameConnection.this._delegate.onGameSessionEnded(new JSONGameStatusParser().parseResult(new JSONObject(result2[0].toString()).getJSONObject("gameStatus")));
                        return;
                    }
                    catch (InvalidJsonForObjectException ex6) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), ex6);
                        return;
                    }
                    catch (JSONException ex7) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex7);
                        return;
                    }
                }
                if (result.equals("gameSessionResults")) {
                    SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck = null;
                    game_RUNNING = (InvalidJsonForObjectException)GameStatus.GAME_RUNNING;
                    float scoreWhite;
                    float scoreBlack;
                    InvalidJsonForObjectException ex8 = null;
                    try {
                        final JSONObject jsonObject8 = new JSONObject(result2[0].toString());
                        result = (String)new JSONGameStatusParser().parseResult(jsonObject8.getJSONObject("gameStatus"));
                        try {
                            result2 = (Object[])(Object)new JSONEloChangeParser().parseResult(jsonObject8.getJSONObject("elo"));
                            try {
                                scoreWhite = ((GameStatus)result).getResult().getScoreWhite();
                                try {
                                    scoreBlack = ((GameStatus)result).getResult().getScoreBlack();
                                    float n = scoreWhite;
                                    float n2 = scoreWhite;
                                    try {
                                        final JSONObject jsonObject9 = jsonObject8.getJSONObject("score");
                                        n2 = scoreWhite;
                                        n = scoreBlack;
                                        if (jsonObject9 != null) {
                                            n = scoreWhite;
                                            n2 = scoreWhite;
                                            scoreWhite = (n2 = (n = (float)jsonObject9.optDouble("white", (double)scoreWhite)));
                                            n = (float)jsonObject9.optDouble("black", (double)scoreBlack);
                                            n2 = scoreWhite;
                                        }
                                        scoreWhite = n2;
                                        scoreBlack = n;
                                    }
                                    catch (InvalidJsonForObjectException game_RUNNING) {
                                        scoreWhite = n;
                                    }
                                    catch (JSONException game_RUNNING) {
                                        scoreWhite = n2;
                                    }
                                }
                                catch (InvalidJsonForObjectException game_RUNNING) {
                                    scoreBlack = 0.0f;
                                }
                                catch (JSONException game_RUNNING) {
                                    scoreBlack = 0.0f;
                                }
                            }
                            catch (InvalidJsonForObjectException game_RUNNING) {
                                scoreWhite = (scoreBlack = 0.0f);
                            }
                            catch (JSONException game_RUNNING) {
                                scoreWhite = (scoreBlack = 0.0f);
                            }
                            final Serializable s = result;
                            result = (String)result2;
                            ex8 = game_RUNNING;
                            game_RUNNING = (InvalidJsonForObjectException)s;
                        }
                        catch (InvalidJsonForObjectException ex12) {
                            scoreBlack = 0.0f;
                            game_RUNNING = (InvalidJsonForObjectException)result;
                            result = null;
                            scoreWhite = scoreBlack;
                        }
                        catch (JSONException ex8) {
                            scoreBlack = 0.0f;
                            game_RUNNING = (InvalidJsonForObjectException)result;
                            result = null;
                            scoreWhite = scoreBlack;
                        }
                    }
                    catch (InvalidJsonForObjectException ex12) {
                        scoreBlack = 0.0f;
                        result = null;
                        scoreWhite = scoreBlack;
                    }
                    catch (JSONException ex8) {
                        scoreBlack = 0.0f;
                        result = null;
                        scoreWhite = scoreBlack;
                    }
                    Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), ex8);
                    result2 = (Object[])(Object)result;
                    result = (String)game_RUNNING;
                    SocketIORemoteGameConnection.this._delegate.onGameEnd(new GameEndInformation((GameStatus)result, (EloChange)result2, scoreWhite, scoreBlack, SocketIORemoteGameConnection.this._playerActive == SocketIORemoteGameConnection.this._playerIsWhite));
                    return;
                }
                if (result.equals("drawOffered")) {
                    SocketIORemoteGameConnection.this._delegate.onReceiveDrawOffer();
                    return;
                }
                if (result.equals("playerDisconnected")) {
                    try {
                        final JSONObject jsonObject10 = new JSONObject(result2[0].toString());
                        SocketIORemoteGameConnection.this._delegate.onOpponentsConnectionStateChanged(false, jsonObject10.getInt("timeout"), jsonObject10.getBoolean("win") ^ true);
                        return;
                    }
                    catch (JSONException ex9) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex9);
                        return;
                    }
                }
                if (result.equals("playerReconnected")) {
                    SocketIORemoteGameConnection.this._delegate.onOpponentsConnectionStateChanged(true, 0, true);
                    return;
                }
                if (result.equals("reMatchOffered")) {
                    SocketIORemoteGameConnection.this._delegate.onRematchOffer();
                    return;
                }
                if (result.equals("noReMatch")) {
                    SocketIORemoteGameConnection.this._delegate.opponentDeclinedRematch();
                    return;
                }
                if (result.equals("gameSessionOver")) {
                    try {
                        final JSONObject jsonObject11 = new JSONObject(result2[0].toString());
                        final Game result4 = new JSONGameParser().parseResult(jsonObject11.getJSONObject("chessGame"));
                        SocketIORemoteGameConnection.this._delegate.onGameSessionOver(result4, new GameEndInformation(new GameStatus(result4.getResult().getResult(), GameEndReason.getReasonForKey(jsonObject11.optString("endedBy"))), new JSONGameSessionOverEloChangeParser().parseResult(jsonObject11), (float)jsonObject11.optDouble("whiteResult"), (float)jsonObject11.optDouble("blackResult"), result4.getLastMoveinMainLine().getPiece().getColor() ^ true));
                    }
                    catch (InvalidJsonForObjectException ex10) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), ex10);
                    }
                    catch (JSONException ex11) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex11);
                    }
                }
            }
            
            @Override
            public void onConnect() {
                SocketIORemoteGameConnection.this._playSocketDisconnected = false;
                Logger.getInstance().debug("de.cisha.test.socketIO", "onConnect ");
                SocketIORemoteGameConnection.this._delegate.onMyConnectionStateChanged(true);
            }
            
            @Override
            public void onDisconnect() {
                Logger.getInstance().debug("de.cisha.test.socketIO", "onDisconnect ");
                SocketIORemoteGameConnection.this.notifyDisconnectListener();
            }
            
            @Override
            public void onError(final SocketIOException ex) {
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("onError ");
                sb.append(ex.toString());
                instance.debug("de.cisha.test.socketIO", sb.toString());
                SocketIORemoteGameConnection.this.notifyDisconnectListener();
            }
            
            @Override
            public void onMessage(final String s, final IOAcknowledge ioAcknowledge) {
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("onMessage(String) ");
                sb.append(s.toString());
                instance.debug("de.cisha.test.socketIO", sb.toString());
            }
            
            @Override
            public void onMessage(final JSONObject jsonObject, final IOAcknowledge ioAcknowledge) {
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("onMessage(json) ");
                sb.append(jsonObject.toString());
                instance.debug("de.cisha.test.socketIO", sb.toString());
            }
        }, null);
        this.joinGame(s, this._authToken);
    }
    
    @Override
    public void offerDraw() {
        if (this._socketPlay != null) {
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("gameSessionToken", (Object)this._gameSessionToken);
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex);
            }
            this._socketPlay.emit("drawOffer", jsonObject);
        }
    }
    
    @Override
    public void requestAbort() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gameSessionToken", (Object)this._gameSessionToken);
            jsonObject.put("abort", true);
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex);
        }
        if (this._socketPlay != null) {
            this._socketPlay.emit("abortOrResign", jsonObject);
        }
    }
    
    @Override
    public void requestForChallengeEngine(final String s, final ClockSetting clockSetting, final boolean b, final RemoteGameDelegator remoteGameDelegator) {
        this.sendPairingRequest(clockSetting, null, remoteGameDelegator, s, b);
    }
    
    @Override
    public void requestForPairing(final ClockSetting clockSetting, final EloRange eloRange, final RemoteGameDelegator remoteGameDelegator) {
        this.sendPairingRequest(clockSetting, eloRange, remoteGameDelegator, null, true);
    }
    
    @Override
    public void requestForRematch(final String s, final RemoteGameDelegator delegate) {
        this._delegate = delegate;
        if (this._socketPlay != null && this._socketPlay.isConnected()) {
            if (this._socketPairing == null || !this._socketPairing.isConnected()) {
                this.initSocketPairingInstance(this._delegate, this._authToken);
            }
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("authToken", (Object)this._authToken.getUuid());
                jsonObject.put("gameSessionToken", (Object)s);
                this._socketPairing.emit("reMatch", jsonObject);
                this._socketPlay.emit("reMatch", jsonObject);
            }
            catch (JSONException ex) {
                final Logger instance = Logger.getInstance();
                final StringBuilder sb = new StringBuilder();
                sb.append("exception ");
                sb.append(JSONException.class.getName());
                instance.debug("de.cisha.test.sockectIO", sb.toString(), (Throwable)ex);
                this._delegate.onPairingFailed();
                this._socketPairing.disconnect();
            }
        }
    }
    
    @Override
    public void resign() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gameSessionToken", (Object)this._gameSessionToken);
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex);
        }
        if (this._socketPlay != null) {
            this._socketPlay.emit("abortOrResign", jsonObject);
        }
    }
    
    private class WaitForMoveAck extends TimerTask
    {
        private Move _move;
        
        public WaitForMoveAck(final Move move) {
            this._move = move;
        }
        
        @Override
        public void run() {
            if (this._move == SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck) {
                SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck = null;
                if (SocketIORemoteGameConnection.this._socketPlay != null) {
                    SocketIORemoteGameConnection.this.doMove(this._move);
                }
            }
        }
    }
}
