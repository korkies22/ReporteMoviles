/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.AbstractBroadcastConnection;
import de.cisha.android.board.broadcast.model.AbstractTimeoutIOAcknowledge;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.broadcast.model.TournamentGameID;
import de.cisha.android.board.playzone.remote.model.JSONGameStatusParser;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.model.GameType;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.SocketIO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketIOTournamentGameConnection
extends AbstractBroadcastConnection
implements ITournamentGameConnection {
    private Game _game;
    private ITournamentGameConnection.TournamentGameConnectionListener _listener;
    private TournamentGameID _tournamentGameId;

    public SocketIOTournamentGameConnection(TournamentGameID tournamentGameID, ITournamentGameConnection.TournamentGameConnectionListener tournamentGameConnectionListener) {
        super(tournamentGameConnectionListener);
        this._tournamentGameId = tournamentGameID;
        this._listener = tournamentGameConnectionListener;
    }

    @Override
    protected void connectingSuccessful() {
    }

    @Override
    public void loadGame() {
        SocketIO socketIO = this.getSocket();
        if (socketIO != null) {
            socketIO.emit("get", new AbstractTimeoutIOAcknowledge(){

                @Override
                /* varargs */ void onAck(Object ... jSONObject) {
                    Object object;
                    Logger.getInstance().debug("TournamentGameBroadcastSocketIO:", "get answered:");
                    for (int i = 0; i < ((Object[])jSONObject).length; ++i) {
                        object = Logger.getInstance();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("args[");
                        stringBuilder.append(i);
                        stringBuilder.append("]: ");
                        stringBuilder.append((Object)jSONObject[i]);
                        object.debug("TournamnetGameBroadcastSocketIO:", stringBuilder.toString());
                    }
                    if (((Object[])jSONObject).length > 0 && jSONObject[0] instanceof JSONObject) {
                        jSONObject = (JSONObject)jSONObject[0];
                        try {
                            jSONObject = jSONObject.getJSONObject("chessGame");
                            object = new JSONGameParser(SocketIOTournamentGameConnection.this.getServerTimeOffset());
                            SocketIOTournamentGameConnection.this._game = object.parseResult(jSONObject);
                            SocketIOTournamentGameConnection.this._game.setType(GameType.BROADCAST);
                            SocketIOTournamentGameConnection.this._listener.tournamentGameLoaded(SocketIOTournamentGameConnection.this._game);
                            return;
                        }
                        catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                            SocketIOTournamentGameConnection.this._listener.tournamentGameLoadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON);
                            Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), "ParserException:", invalidJsonForObjectException);
                            return;
                        }
                        catch (JSONException jSONException) {
                            SocketIOTournamentGameConnection.this._listener.tournamentGameLoadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON);
                            Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), "JSONEXception:", (Throwable)jSONException);
                        }
                    }
                }

                @Override
                void onTimeout() {
                    if (SocketIOTournamentGameConnection.this.isConnected()) {
                        SocketIOTournamentGameConnection.this.loadGame();
                    }
                }
            }, this._tournamentGameId.getUuid());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public /* varargs */ void on(String string, IOAcknowledge iOAcknowledge, Object ... arrobject) {
        Object object;
        StringBuilder stringBuilder;
        int n;
        Object object2;
        Logger.getInstance().debug("SocketIOTournamentGameConnection Event", string);
        int n2 = arrobject.length;
        for (n = 0; n < n2; ++n) {
            object = arrobject[n];
            object2 = Logger.getInstance();
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(object);
            object2.debug("SocketIOTournamentGameConnection", stringBuilder.toString());
        }
        if (string.equals(this._tournamentGameId.getUuid())) {
            try {
                object = new JSONObject(arrobject[0].toString());
                object2 = object.optString("action", "undef");
                object.optString("id");
                if (object2.equals("set") && (object = object.optJSONObject("diffs")) != null && (object = object.optJSONArray("chessGame")) != null) {
                    for (n = 0; n < object.length(); ++n) {
                        int n3;
                        stringBuilder = object.getJSONObject(n);
                        if (stringBuilder == null) continue;
                        object2 = stringBuilder.optString("command", "undef");
                        stringBuilder = stringBuilder.optJSONObject("data");
                        if (object2.equals("addMove") && stringBuilder != null) {
                            n2 = stringBuilder.optInt("parentId", -1);
                            n3 = stringBuilder.optInt("knotId", -1);
                            int n4 = stringBuilder.optInt("timeUsage", 0);
                            stringBuilder.optBoolean("mainline", false);
                            String string2 = stringBuilder.optString("move");
                            this._listener.newMove(n2, string2, n3, n4);
                        }
                        if (object2.equals("updateMove")) {
                            if (stringBuilder == null || !stringBuilder.has("knotId") || !stringBuilder.has("timeUsage")) continue;
                            n2 = stringBuilder.optInt("knotId", -1);
                            n3 = stringBuilder.optInt("timeUsage", 0);
                            this._listener.moveTimeChanged(n2, n3);
                            continue;
                        }
                        if (object2.equals("removeMove")) {
                            if (stringBuilder == null || (n2 = stringBuilder.optInt("knotId", -1)) < 0) continue;
                            this._listener.moveDeleted(n2);
                            continue;
                        }
                        if (!object2.equals("resultChanged")) continue;
                        object2 = new JSONGameStatusParser();
                        try {
                            object2 = object2.parseResult((JSONObject)stringBuilder);
                            this._listener.gameStatusChanged((GameStatus)object2);
                            continue;
                        }
                        catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                            Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
                        }
                    }
                }
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
        }
        super.on(string, iOAcknowledge, arrobject);
    }

    @Override
    public void subscribeToGame() {
        this.subscribeToRKey(this._tournamentGameId.getUuid());
    }

}
