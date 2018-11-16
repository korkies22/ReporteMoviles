// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import org.json.JSONArray;
import de.cisha.android.board.playzone.remote.model.JSONGameStatusParser;
import io.socket.SocketIO;
import io.socket.IOAcknowledge;
import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.chess.model.GameType;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import org.json.JSONObject;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.chess.model.Game;

public class SocketIOTournamentGameConnection extends AbstractBroadcastConnection implements ITournamentGameConnection
{
    private Game _game;
    private TournamentGameConnectionListener _listener;
    private TournamentGameID _tournamentGameId;
    
    public SocketIOTournamentGameConnection(final TournamentGameID tournamentGameId, final TournamentGameConnectionListener listener) {
        super(listener);
        this._tournamentGameId = tournamentGameId;
        this._listener = listener;
    }
    
    @Override
    protected void connectingSuccessful() {
    }
    
    @Override
    public void loadGame() {
        final SocketIO socket = this.getSocket();
        if (socket != null) {
            socket.emit("get", new AbstractTimeoutIOAcknowledge() {
                @Override
                void onAck(final Object... array) {
                    Logger.getInstance().debug("TournamentGameBroadcastSocketIO:", "get answered:");
                    for (int i = 0; i < array.length; ++i) {
                        final Logger instance = Logger.getInstance();
                        final StringBuilder sb = new StringBuilder();
                        sb.append("args[");
                        sb.append(i);
                        sb.append("]: ");
                        sb.append(array[i]);
                        instance.debug("TournamnetGameBroadcastSocketIO:", sb.toString());
                    }
                    if (array.length > 0 && array[0] instanceof JSONObject) {
                        final JSONObject jsonObject = (JSONObject)array[0];
                        try {
                            SocketIOTournamentGameConnection.this._game = new JSONGameParser(SocketIOTournamentGameConnection.this.getServerTimeOffset()).parseResult(jsonObject.getJSONObject("chessGame"));
                            SocketIOTournamentGameConnection.this._game.setType(GameType.BROADCAST);
                            SocketIOTournamentGameConnection.this._listener.tournamentGameLoaded(SocketIOTournamentGameConnection.this._game);
                        }
                        catch (InvalidJsonForObjectException ex) {
                            SocketIOTournamentGameConnection.this._listener.tournamentGameLoadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON);
                            Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), "ParserException:", ex);
                        }
                        catch (JSONException ex2) {
                            SocketIOTournamentGameConnection.this._listener.tournamentGameLoadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON);
                            Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), "JSONEXception:", (Throwable)ex2);
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
    
    @Override
    public void on(final String s, final IOAcknowledge ioAcknowledge, final Object... array) {
        Logger.getInstance().debug("SocketIOTournamentGameConnection Event", s);
        for (int length = array.length, i = 0; i < length; ++i) {
            final Object o = array[i];
            final Logger instance = Logger.getInstance();
            final StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(o);
            instance.debug("SocketIOTournamentGameConnection", sb.toString());
        }
        if (s.equals(this._tournamentGameId.getUuid())) {
            try {
                final JSONObject jsonObject = new JSONObject(array[0].toString());
                final String optString = jsonObject.optString("action", "undef");
                jsonObject.optString("id");
                if (optString.equals("set")) {
                    final JSONObject optJSONObject = jsonObject.optJSONObject("diffs");
                    if (optJSONObject != null) {
                        final JSONArray optJSONArray = optJSONObject.optJSONArray("chessGame");
                        if (optJSONArray != null) {
                            for (int j = 0; j < optJSONArray.length(); ++j) {
                                final JSONObject jsonObject2 = optJSONArray.getJSONObject(j);
                                if (jsonObject2 != null) {
                                    final String optString2 = jsonObject2.optString("command", "undef");
                                    final JSONObject optJSONObject2 = jsonObject2.optJSONObject("data");
                                    if (optString2.equals("addMove") && optJSONObject2 != null) {
                                        final int optInt = optJSONObject2.optInt("parentId", -1);
                                        final int optInt2 = optJSONObject2.optInt("knotId", -1);
                                        final int optInt3 = optJSONObject2.optInt("timeUsage", 0);
                                        optJSONObject2.optBoolean("mainline", false);
                                        this._listener.newMove(optInt, optJSONObject2.optString("move"), optInt2, optInt3);
                                    }
                                    if (optString2.equals("updateMove")) {
                                        if (optJSONObject2 != null && optJSONObject2.has("knotId") && optJSONObject2.has("timeUsage")) {
                                            this._listener.moveTimeChanged(optJSONObject2.optInt("knotId", -1), optJSONObject2.optInt("timeUsage", 0));
                                        }
                                    }
                                    else if (optString2.equals("removeMove")) {
                                        if (optJSONObject2 != null) {
                                            final int optInt4 = optJSONObject2.optInt("knotId", -1);
                                            if (optInt4 >= 0) {
                                                this._listener.moveDeleted(optInt4);
                                            }
                                        }
                                    }
                                    else if (optString2.equals("resultChanged")) {
                                        final JSONGameStatusParser jsonGameStatusParser = new JSONGameStatusParser();
                                        try {
                                            this._listener.gameStatusChanged(jsonGameStatusParser.parseResult(optJSONObject2));
                                        }
                                        catch (InvalidJsonForObjectException ex) {
                                            Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), InvalidJsonForObjectException.class.getName(), ex);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (JSONException ex2) {
                Logger.getInstance().debug(SocketIOTournamentGameConnection.class.getName(), JSONException.class.getName(), (Throwable)ex2);
            }
        }
        super.on(s, ioAcknowledge, array);
    }
    
    @Override
    public void subscribeToGame() {
        this.subscribeToRKey(this._tournamentGameId.getUuid());
    }
}
