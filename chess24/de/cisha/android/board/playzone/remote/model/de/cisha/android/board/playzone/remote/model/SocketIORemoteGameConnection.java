/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote.model;

import android.util.Log;
import de.cisha.android.board.playzone.model.GameEndInformation;
import de.cisha.android.board.playzone.remote.model.EloChange;
import de.cisha.android.board.playzone.remote.model.EloRange;
import de.cisha.android.board.playzone.remote.model.IRemoteGameConnection;
import de.cisha.android.board.playzone.remote.model.JSONGameStatusParser;
import de.cisha.android.board.playzone.remote.model.RemoteGameDelegator;
import de.cisha.android.board.service.NodeServerAddress;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONEloChangeParser;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.android.board.service.jsonparser.JSONGameSessionOverEloChangeParser;
import de.cisha.android.board.service.jsonparser.JSONNodeServerAddressParser;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.model.ClockSetting;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.util.Logger;
import io.socket.ConnectionListener;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketIORemoteGameConnection
implements IRemoteGameConnection {
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
    private int _commandId = 0;
    private int _currentHalfMoveNumber = 0;
    private Move _currentMoveSendWaitingForAck;
    private RemoteGameDelegator _delegate;
    private String _gameSessionToken;
    private int _increment;
    private Timer _moveAckTimer = new Timer();
    private NodeServerAddress _pairingServerAddress;
    private boolean _playSocketDisconnected;
    private boolean _playerActive;
    private boolean _playerIsWhite;
    private NodeServerAddress _playingServerAddress;
    private SocketIO _socketPairing;
    private SocketIO _socketPlay;
    private long _timeOnReceivedMove;

    public SocketIORemoteGameConnection(NodeServerAddress nodeServerAddress, NodeServerAddress nodeServerAddress2, CishaUUID cishaUUID) {
        this._pairingServerAddress = nodeServerAddress2;
        this._playingServerAddress = nodeServerAddress;
        this._authToken = cishaUUID;
    }

    static /* synthetic */ int access$908(SocketIORemoteGameConnection socketIORemoteGameConnection) {
        int n = socketIORemoteGameConnection._currentHalfMoveNumber;
        socketIORemoteGameConnection._currentHalfMoveNumber = n + 1;
        return n;
    }

    private void checkMoveNumber(int n) {
        boolean bl = n != this._currentHalfMoveNumber;
        Logger logger = Logger.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("local moveNo: ");
        stringBuilder.append(this._currentHalfMoveNumber);
        stringBuilder.append(" server moveNo: ");
        stringBuilder.append(n);
        String string = bl ? " <<<<<<<<<<< reconnect" : "";
        stringBuilder.append(string);
        logger.debug("Playzone Ping", stringBuilder.toString());
        if (bl) {
            this.disconnect();
        }
    }

    private void initSocketPairingInstance(RemoteGameDelegator remoteGameDelegator, CishaUUID cishaUUID) {
        this._delegate = remoteGameDelegator;
        if (this._socketPairing != null && this._socketPairing.isConnected()) {
            this._socketPairing.disconnect();
        }
        try {
            this._socketPairing = new SocketIO(this._pairingServerAddress.getURLString());
            this._socketPairing.connect(new IOCallback(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public /* varargs */ void on(String string, IOAcknowledge object, Object ... object2) {
                    Object object3;
                    for (int i = 0; i < ((Object)object2).length; ++i) {
                        object = Logger.getInstance();
                        object3 = new StringBuilder();
                        object3.append("on ");
                        object3.append(string);
                        object3.append(i);
                        object3.append(" ");
                        object3.append(object2[i].toString());
                        object.debug("de.cisha.test.socketIO", object3.toString());
                    }
                    if (string.equals("initGameSession")) {
                        try {
                            object = new JSONObject(object2[0].toString());
                            string = object.getString(SocketIORemoteGameConnection.GAME_SESSION_TOKEN);
                            boolean bl = object.getString("role").equals("w");
                            object.optString("mode", "human");
                            object = object.getJSONObject("playingServer");
                            object2 = new JSONNodeServerAddressParser();
                            try {
                                SocketIORemoteGameConnection.this._playingServerAddress = object2.parseResult((JSONObject)object);
                            }
                            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                object3 = Logger.getInstance();
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Cant parse playingServer adress:");
                                stringBuilder.append(object);
                                object3.debug("SocketIORemoteGameConnection", stringBuilder.toString(), invalidJsonForObjectException);
                            }
                            object = new ClockSetting(SocketIORemoteGameConnection.this._baseTimeInSeconds, SocketIORemoteGameConnection.this._increment);
                            SocketIORemoteGameConnection.this._delegate.onPairingSucceeded(bl, (ClockSetting)object, string, SocketIORemoteGameConnection.this._playingServerAddress);
                            SocketIORemoteGameConnection.this.initGameSession(string, bl, SocketIORemoteGameConnection.this._delegate);
                        }
                        catch (JSONException jSONException) {
                            Log.d((String)SocketIORemoteGameConnection.class.getName(), (String)JSONException.class.getName(), (Throwable)jSONException);
                        }
                    } else if (string.equals("noMatch")) {
                        SocketIORemoteGameConnection.this._delegate.onPairingFailedNoOpponentFound();
                    } else if (string.equals("informNotAllowed")) {
                        SocketIORemoteGameConnection.this._delegate.onPairingFailedNotAllowed();
                    } else {
                        object = Logger.getInstance();
                        object2 = new StringBuilder();
                        object2.append("unkown command from  pairing server:");
                        object2.append(string);
                        object.error("SocketIORemoteGameConnection:", object2.toString());
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
                public void onError(SocketIOException socketIOException) {
                    Logger.getInstance().debug("de.cisha.test.socketIO", "Pairing: onError ", socketIOException);
                    SocketIORemoteGameConnection.this._delegate.onPairingFailed();
                }

                @Override
                public void onMessage(String string, IOAcknowledge object) {
                    object = Logger.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onMessage(String) ");
                    stringBuilder.append(string.toString());
                    object.debug("de.cisha.test.socketIO", stringBuilder.toString());
                }

                @Override
                public void onMessage(JSONObject jSONObject, IOAcknowledge object) {
                    object = Logger.getInstance();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onMessage(json) ");
                    stringBuilder.append(jSONObject.toString());
                    object.debug("de.cisha.test.socketIO", stringBuilder.toString());
                }
            }, null);
            return;
        }
        catch (MalformedURLException malformedURLException) {
            Log.e((String)SocketIORemoteGameConnection.class.getName(), (String)MalformedURLException.class.getName(), (Throwable)malformedURLException);
            return;
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
            return;
        }
        catch (MalformedURLException malformedURLException) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), MalformedURLException.class.getName(), malformedURLException);
            return;
        }
    }

    private void joinGame(String object, CishaUUID cishaUUID) {
        Object object2;
        this._gameSessionToken = object;
        cishaUUID = new JSONObject();
        try {
            cishaUUID.put(GAME_SESSION_TOKEN, object);
            cishaUUID.put(AUTH_TOKEN, (Object)this._authToken.getUuid());
        }
        catch (JSONException jSONException) {
            this._delegate.onJoinGameFailed();
            object2 = Logger.getInstance();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("exception ");
            stringBuilder.append(JSONException.class.getName());
            object2.debug("de.cisha.test.sockectIO", stringBuilder.toString(), (Throwable)jSONException);
        }
        this._socketPlay.emit(COMMAND_JOIN_GAME_SESSION, cishaUUID);
        object = Logger.getInstance();
        object2 = new StringBuilder();
        object2.append("send joinGameSession ");
        object2.append(cishaUUID.toString());
        object.debug("de.cisha.test.socketIO", object2.toString());
    }

    private void notifyDisconnectListener() {
        synchronized (this) {
            if (!this._playSocketDisconnected) {
                this._playSocketDisconnected = true;
                this._delegate.onMyConnectionStateChanged(false);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendPairingRequest(ClockSetting clockSetting, EloRange object, RemoteGameDelegator object2, String string, boolean bl) {
        block3 : {
            this._baseTimeInSeconds = clockSetting.getBase(true) / 1000;
            this._increment = clockSetting.getIncrementPerMove(true) / 1000;
            this.initSocketPairingInstance((RemoteGameDelegator)object2, this._authToken);
            clockSetting = new JSONObject();
            try {
                clockSetting.put(AUTH_TOKEN, (Object)this._authToken.getUuid());
                clockSetting.put("clock", this._baseTimeInSeconds);
                if (string != null) {
                    clockSetting.put("engineUuid", (Object)string);
                    clockSetting.put("rated", bl);
                }
                clockSetting.put("increment", this._increment);
                if (object == null) break block3;
                object2 = new JSONObject();
                object2.put("min", object.getMin());
                object2.put("max", object.getMax());
                clockSetting.put("eloRange", object2);
            }
            catch (JSONException jSONException) {
                object = Logger.getInstance();
                object2 = new StringBuilder();
                object2.append("exception ");
                object2.append(JSONException.class.getName());
                object.debug("de.cisha.test.sockectIO", object2.toString(), (Throwable)jSONException);
                this._delegate.onPairingFailed();
                this._socketPairing.disconnect();
                return;
            }
        }
        this._socketPairing.emit("play", clockSetting);
        object = Logger.getInstance();
        object2 = new StringBuilder();
        object2.append("send play (pairing) ");
        object2.append(clockSetting.toString());
        object.debug("de.cisha.test.socketIO", object2.toString());
    }

    @Override
    public void acceptDrawOffer() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(GAME_SESSION_TOKEN, (Object)this._gameSessionToken);
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
        }
        this._socketPlay.emit(COMMAND_ACCEPT_DRAW, new Object[]{jSONObject});
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void disconnect() {
        if (this._socketPlay != null) {
            this._socketPlay.disconnect();
        }
        if (this._socketPairing != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(AUTH_TOKEN, (Object)this._authToken.getUuid());
                this._socketPairing.emit(COMMAND_CANCEL, new Object[]{jSONObject});
            }
            catch (JSONException jSONException) {}
            this._socketPairing.disconnect();
        }
    }

    @Override
    public void doMove(Move move) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("move", (Object)move.getEAN());
            jSONObject.put(GAME_SESSION_TOKEN, (Object)this._gameSessionToken);
            ++this._commandId;
            jSONObject.put("id", this._commandId);
            if (this._timeOnReceivedMove > 0L) {
                jSONObject.put(PARAM_KEY_TIME_USAGE_CLIENT, System.currentTimeMillis() - this._timeOnReceivedMove);
            }
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
        }
        this._currentMoveSendWaitingForAck = move;
        this._socketPlay.emit("move", new Object[]{jSONObject});
        move.setMoveId(this._commandId);
        this._moveAckTimer.schedule((TimerTask)new WaitForMoveAck(move), 2000L);
    }

    @Override
    public void initGameSession(final String string, boolean bl, RemoteGameDelegator remoteGameDelegator) {
        this._playerIsWhite = bl;
        this._delegate = remoteGameDelegator;
        this.initSocketPlayInstance();
        if (this._socketPlay == null) {
            this._delegate.onJoinGameFailed();
            return;
        }
        this._socketPlay.connect(new IOCallback(){

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public /* varargs */ void on(String object, IOAcknowledge object2, Object ... object3) {
                int n;
                block54 : {
                    void var3_31;
                    float f;
                    float f2;
                    block55 : {
                        block56 : {
                            void var3_28;
                            block53 : {
                                void var3_26;
                                block52 : {
                                    EloChange eloChange;
                                    Object object4;
                                    block51 : {
                                        block50 : {
                                            float f4;
                                            float f3;
                                            block49 : {
                                                double d;
                                                object2 = Logger.getInstance();
                                                object4 = new StringBuilder();
                                                object4.append("on ");
                                                object4.append((String)object);
                                                object2.debug("de.cisha.test.socketIO", object4.toString());
                                                int n2 = 0;
                                                for (n = 0; n < ((Object)object3).length; ++n) {
                                                    object2 = Logger.getInstance();
                                                    object4 = new StringBuilder();
                                                    object4.append("on ");
                                                    object4.append((String)object);
                                                    object4.append(n);
                                                    object4.append(" ");
                                                    object4.append(object3[n].toString());
                                                    object2.debug("de.cisha.test.socketIO", object4.toString());
                                                }
                                                if (object.equals("ping")) {
                                                    object = new JSONObject();
                                                    if (object3 == null) return;
                                                    try {
                                                        if (((Object)object3).length <= 0) return;
                                                    }
                                                    catch (JSONException jSONException) {
                                                        Log.d((String)SocketIORemoteGameConnection.class.getName(), (String)JSONException.class.getName(), (Throwable)jSONException);
                                                        return;
                                                    }
                                                    if (object3[0] == null) return;
                                                    object2 = new JSONObject(object3[0].toString());
                                                    object.put(SocketIORemoteGameConnection.GAME_SESSION_TOKEN, (Object)string);
                                                    object.put("pingToken", (Object)object2.optString("pingToken"));
                                                    SocketIORemoteGameConnection.this._socketPlay.emit("pingACK", object);
                                                    object = object2.optJSONObject("data");
                                                    if (object == null) return;
                                                    n = object.optInt("moveNo", -1);
                                                    if (n == -1) return;
                                                    SocketIORemoteGameConnection.this.checkMoveNumber(n);
                                                    return;
                                                }
                                                if (object.equals("startGameSession")) {
                                                    try {
                                                        object = new JSONObject(object3[0].toString());
                                                        object2 = object.getJSONObject("game");
                                                        try {
                                                            object2 = new JSONGameParser().parseResult((JSONObject)object2);
                                                            n = 10000;
                                                            int n3 = 30000;
                                                            if (object.has("timeouts")) {
                                                                object = object.getJSONObject("timeouts");
                                                                n = object.optInt("abort");
                                                                n3 = object.optInt("win");
                                                            }
                                                            SocketIORemoteGameConnection.this._delegate.onGameStart((Game)object2, n, n3);
                                                            object = object2.getLastMoveinMainLine();
                                                            object2 = SocketIORemoteGameConnection.this;
                                                            n = n2;
                                                            if (object != null) {
                                                                n = object.getHalfMoveNumber();
                                                            }
                                                            ((SocketIORemoteGameConnection)object2)._currentHalfMoveNumber = n;
                                                            return;
                                                        }
                                                        catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                                            Logger.getInstance().error(this.getClass().getName(), "error parsing returned game", invalidJsonForObjectException);
                                                            return;
                                                        }
                                                    }
                                                    catch (JSONException jSONException) {
                                                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                                                        SocketIORemoteGameConnection.this._delegate.onJoinGameFailed();
                                                        SocketIORemoteGameConnection.this.disconnect();
                                                        return;
                                                    }
                                                }
                                                if (object.equals("receivedMove")) {
                                                    SocketIORemoteGameConnection.this._timeOnReceivedMove = System.currentTimeMillis();
                                                    SocketIORemoteGameConnection.access$908(SocketIORemoteGameConnection.this);
                                                    SocketIORemoteGameConnection.this._playerActive = true;
                                                    try {
                                                        object = new JSONObject(object3[0].toString());
                                                        object2 = object.optString("move");
                                                        n = object.optInt(SocketIORemoteGameConnection.PARAM_KEY_TIME_USAGE);
                                                        SocketIORemoteGameConnection.this._delegate.onMove((String)object2, n);
                                                        return;
                                                    }
                                                    catch (JSONException jSONException) {
                                                        Logger.getInstance().debug(JSONException.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                                                        return;
                                                    }
                                                }
                                                if (object.equals("receivedMoveACK")) {
                                                    SocketIORemoteGameConnection.access$908(SocketIORemoteGameConnection.this);
                                                    try {
                                                        object = new JSONObject(object3[0].toString());
                                                        n = object.optInt(SocketIORemoteGameConnection.PARAM_KEY_TIME_USAGE);
                                                        int n4 = object.optInt("id");
                                                        SocketIORemoteGameConnection.this._playerActive = false;
                                                        SocketIORemoteGameConnection.this._delegate.onMoveACK(n, n4);
                                                        if (SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck == null) return;
                                                        if (SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck.getMoveId() != n4) return;
                                                        SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck = null;
                                                        return;
                                                    }
                                                    catch (JSONException jSONException) {
                                                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                                                        return;
                                                    }
                                                }
                                                if (object.equals("gameSessionEnded")) {
                                                    try {
                                                        object = new JSONObject(object3[0].toString());
                                                        object = new JSONGameStatusParser().parseResult(object.getJSONObject("gameStatus"));
                                                        SocketIORemoteGameConnection.this._delegate.onGameSessionEnded((GameStatus)object);
                                                        return;
                                                    }
                                                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                                                        return;
                                                    }
                                                    catch (JSONException jSONException) {
                                                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                                                        return;
                                                    }
                                                }
                                                if (!object.equals("gameSessionResults")) break block54;
                                                SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck = null;
                                                object2 = GameStatus.GAME_RUNNING;
                                                object4 = new JSONObject(object3[0].toString());
                                                object = object4.getJSONObject("gameStatus");
                                                object = new JSONGameStatusParser().parseResult((JSONObject)object);
                                                eloChange = new JSONEloChangeParser().parseResult(object4.getJSONObject("elo"));
                                                f = object.getResult().getScoreWhite();
                                                f2 = object.getResult().getScoreBlack();
                                                f3 = f;
                                                f4 = f;
                                                try {
                                                    object2 = object4.getJSONObject(SocketIORemoteGameConnection.PARAM_KEY);
                                                    f4 = f;
                                                    f3 = f2;
                                                    if (object2 == null) break block49;
                                                    f3 = f;
                                                    f4 = f;
                                                    f3 = f = (float)object2.optDouble(SocketIORemoteGameConnection.PARAM_KEY_WHITE, (double)f);
                                                    f4 = f;
                                                    d = object2.optDouble(SocketIORemoteGameConnection.PARAM_KEY_BLACK, (double)f2);
                                                }
                                                catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                                    f = f3;
                                                    break block50;
                                                }
                                                catch (JSONException jSONException) {
                                                    f = f4;
                                                    break block51;
                                                }
                                                f3 = (float)d;
                                                f4 = f;
                                            }
                                            f = f4;
                                            f2 = f3;
                                            break block55;
                                            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                                f2 = 0.0f;
                                                break block50;
                                            }
                                            catch (JSONException jSONException) {
                                                f2 = 0.0f;
                                                break block51;
                                            }
                                            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                                f2 = f = 0.0f;
                                            }
                                        }
                                        object4 = object;
                                        object = eloChange;
                                        Object object5 = object2;
                                        object2 = object4;
                                        break block52;
                                        catch (JSONException jSONException) {
                                            f2 = f = 0.0f;
                                        }
                                    }
                                    object4 = object;
                                    object = eloChange;
                                    Object object6 = object2;
                                    object2 = object4;
                                    break block53;
                                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                        f2 = 0.0f;
                                        object2 = object;
                                        object = null;
                                        f = f2;
                                        break block52;
                                    }
                                    catch (JSONException jSONException) {
                                        f2 = 0.0f;
                                        object2 = object;
                                        object = null;
                                        f = f2;
                                        break block53;
                                    }
                                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                        f2 = 0.0f;
                                        object = null;
                                        f = f2;
                                    }
                                }
                                Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), (Throwable)var3_26);
                                break block56;
                                catch (JSONException jSONException) {
                                    f2 = 0.0f;
                                    object = null;
                                    f = f2;
                                }
                            }
                            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)var3_28);
                        }
                        Object object7 = object;
                        object = object2;
                    }
                    object2 = SocketIORemoteGameConnection.this._delegate;
                    boolean bl = SocketIORemoteGameConnection.this._playerActive == SocketIORemoteGameConnection.this._playerIsWhite;
                    object2.onGameEnd(new GameEndInformation((GameStatus)object, (EloChange)var3_31, f, f2, bl));
                    return;
                }
                if (object.equals("drawOffered")) {
                    SocketIORemoteGameConnection.this._delegate.onReceiveDrawOffer();
                    return;
                }
                if (object.equals("playerDisconnected")) {
                    try {
                        object = new JSONObject(object3[0].toString());
                        n = object.getInt("timeout");
                        boolean bl = object.getBoolean("win");
                        SocketIORemoteGameConnection.this._delegate.onOpponentsConnectionStateChanged(false, n, bl ^ true);
                        return;
                    }
                    catch (JSONException jSONException) {
                        Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                        return;
                    }
                }
                if (object.equals("playerReconnected")) {
                    SocketIORemoteGameConnection.this._delegate.onOpponentsConnectionStateChanged(true, 0, true);
                    return;
                }
                if (object.equals("reMatchOffered")) {
                    SocketIORemoteGameConnection.this._delegate.onRematchOffer();
                    return;
                }
                if (object.equals("noReMatch")) {
                    SocketIORemoteGameConnection.this._delegate.opponentDeclinedRematch();
                    return;
                }
                if (!object.equals("gameSessionOver")) return;
                try {
                    JSONObject jSONObject = new JSONObject(object3[0].toString());
                    object = new JSONGameParser().parseResult(jSONObject.getJSONObject("chessGame"));
                    object2 = new JSONGameSessionOverEloChangeParser().parseResult(jSONObject);
                    float f = (float)jSONObject.optDouble("whiteResult");
                    float f5 = (float)jSONObject.optDouble("blackResult");
                    GameEndReason gameEndReason = GameEndReason.getReasonForKey(jSONObject.optString("endedBy"));
                    object2 = new GameEndInformation(new GameStatus(object.getResult().getResult(), gameEndReason), (EloChange)object2, f, f5, object.getLastMoveinMainLine().getPiece().getColor() ^ true);
                    SocketIORemoteGameConnection.this._delegate.onGameSessionOver((Game)object, (GameEndInformation)object2);
                    return;
                }
                catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                    Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                    return;
                }
                catch (JSONException jSONException) {
                    Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
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
            public void onError(SocketIOException socketIOException) {
                Logger logger = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onError ");
                stringBuilder.append(socketIOException.toString());
                logger.debug("de.cisha.test.socketIO", stringBuilder.toString());
                SocketIORemoteGameConnection.this.notifyDisconnectListener();
            }

            @Override
            public void onMessage(String string2, IOAcknowledge object) {
                object = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onMessage(String) ");
                stringBuilder.append(string2.toString());
                object.debug("de.cisha.test.socketIO", stringBuilder.toString());
            }

            @Override
            public void onMessage(JSONObject jSONObject, IOAcknowledge object) {
                object = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onMessage(json) ");
                stringBuilder.append(jSONObject.toString());
                object.debug("de.cisha.test.socketIO", stringBuilder.toString());
            }
        }, null);
        this.joinGame(string, this._authToken);
    }

    @Override
    public void offerDraw() {
        if (this._socketPlay != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(GAME_SESSION_TOKEN, (Object)this._gameSessionToken);
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
            this._socketPlay.emit(COMMAND_DRAW_OFFER, new Object[]{jSONObject});
        }
    }

    @Override
    public void requestAbort() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(GAME_SESSION_TOKEN, (Object)this._gameSessionToken);
            jSONObject.put("abort", true);
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
        }
        if (this._socketPlay != null) {
            this._socketPlay.emit(COMMAND_ABORT_OR_RESIGN, new Object[]{jSONObject});
        }
    }

    @Override
    public void requestForChallengeEngine(String string, ClockSetting clockSetting, boolean bl, RemoteGameDelegator remoteGameDelegator) {
        this.sendPairingRequest(clockSetting, null, remoteGameDelegator, string, bl);
    }

    @Override
    public void requestForPairing(ClockSetting clockSetting, EloRange eloRange, RemoteGameDelegator remoteGameDelegator) {
        this.sendPairingRequest(clockSetting, eloRange, remoteGameDelegator, null, true);
    }

    @Override
    public void requestForRematch(String string, RemoteGameDelegator object) {
        this._delegate = object;
        if (this._socketPlay != null && this._socketPlay.isConnected()) {
            if (this._socketPairing == null || !this._socketPairing.isConnected()) {
                this.initSocketPairingInstance(this._delegate, this._authToken);
            }
            object = new JSONObject();
            try {
                object.put(AUTH_TOKEN, (Object)this._authToken.getUuid());
                object.put(GAME_SESSION_TOKEN, (Object)string);
            }
            catch (JSONException jSONException) {
                object = Logger.getInstance();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("exception ");
                stringBuilder.append(JSONException.class.getName());
                object.debug("de.cisha.test.sockectIO", stringBuilder.toString(), (Throwable)jSONException);
                this._delegate.onPairingFailed();
                this._socketPairing.disconnect();
                return;
            }
            this._socketPairing.emit(COMMAND_REMATCH, object);
            this._socketPlay.emit(COMMAND_REMATCH, object);
            return;
        }
    }

    @Override
    public void resign() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(GAME_SESSION_TOKEN, (Object)this._gameSessionToken);
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
        }
        if (this._socketPlay != null) {
            this._socketPlay.emit(COMMAND_ABORT_OR_RESIGN, new Object[]{jSONObject});
        }
    }

    private class WaitForMoveAck
    extends TimerTask {
        private Move _move;

        public WaitForMoveAck(Move move) {
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
