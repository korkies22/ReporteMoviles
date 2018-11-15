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
import de.cisha.android.board.playzone.remote.model.JSONGameStatusParser;
import de.cisha.android.board.playzone.remote.model.RemoteGameDelegator;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONEloChangeParser;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.android.board.service.jsonparser.JSONGameSessionOverEloChangeParser;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.Move;
import de.cisha.chess.model.Piece;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import org.json.JSONException;
import org.json.JSONObject;

class SocketIORemoteGameConnection
implements IOCallback {
    final /* synthetic */ String val$gameSessionToken;

    SocketIORemoteGameConnection(String string) {
        this.val$gameSessionToken = string;
    }

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
            float f2;
            float f;
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
                                                Log.d((String)de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), (String)JSONException.class.getName(), (Throwable)jSONException);
                                                return;
                                            }
                                            if (object3[0] == null) return;
                                            object2 = new JSONObject(object3[0].toString());
                                            object.put(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.GAME_SESSION_TOKEN, (Object)this.val$gameSessionToken);
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
                                                    ((de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection)object2)._currentHalfMoveNumber = n;
                                                    return;
                                                }
                                                catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                                                    Logger.getInstance().error(this.getClass().getName(), "error parsing returned game", invalidJsonForObjectException);
                                                    return;
                                                }
                                            }
                                            catch (JSONException jSONException) {
                                                Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                                                SocketIORemoteGameConnection.this._delegate.onJoinGameFailed();
                                                SocketIORemoteGameConnection.this.disconnect();
                                                return;
                                            }
                                        }
                                        if (object.equals("receivedMove")) {
                                            SocketIORemoteGameConnection.this._timeOnReceivedMove = System.currentTimeMillis();
                                            de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.access$908(SocketIORemoteGameConnection.this);
                                            SocketIORemoteGameConnection.this._playerActive = true;
                                            try {
                                                object = new JSONObject(object3[0].toString());
                                                object2 = object.optString("move");
                                                n = object.optInt(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.PARAM_KEY_TIME_USAGE);
                                                SocketIORemoteGameConnection.this._delegate.onMove((String)object2, n);
                                                return;
                                            }
                                            catch (JSONException jSONException) {
                                                Logger.getInstance().debug(JSONException.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                                                return;
                                            }
                                        }
                                        if (object.equals("receivedMoveACK")) {
                                            de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.access$908(SocketIORemoteGameConnection.this);
                                            try {
                                                object = new JSONObject(object3[0].toString());
                                                n = object.optInt(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.PARAM_KEY_TIME_USAGE);
                                                int n4 = object.optInt("id");
                                                SocketIORemoteGameConnection.this._playerActive = false;
                                                SocketIORemoteGameConnection.this._delegate.onMoveACK(n, n4);
                                                if (SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck == null) return;
                                                if (SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck.getMoveId() != n4) return;
                                                SocketIORemoteGameConnection.this._currentMoveSendWaitingForAck = null;
                                                return;
                                            }
                                            catch (JSONException jSONException) {
                                                Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
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
                                                Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                                                return;
                                            }
                                            catch (JSONException jSONException) {
                                                Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
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
                                            object2 = object4.getJSONObject(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.PARAM_KEY);
                                            f4 = f;
                                            f3 = f2;
                                            if (object2 == null) break block49;
                                            f3 = f;
                                            f4 = f;
                                            f3 = f = (float)object2.optDouble(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.PARAM_KEY_WHITE, (double)f);
                                            f4 = f;
                                            d = object2.optDouble(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.PARAM_KEY_BLACK, (double)f2);
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
                        Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), (Throwable)var3_26);
                        break block56;
                        catch (JSONException jSONException) {
                            f2 = 0.0f;
                            object = null;
                            f = f2;
                        }
                    }
                    Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)var3_28);
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
                Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
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
            Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
            return;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
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
}
