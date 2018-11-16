// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model;

import java.util.Iterator;
import org.json.JSONException;
import de.cisha.chess.model.position.Position;
import io.socket.IOAcknowledge;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONTournamentParser;
import org.json.JSONObject;
import de.cisha.chess.util.Logger;
import de.cisha.android.board.broadcast.connection.IConnection;
import io.socket.IOCallback;

public class SocketIOTournamentConnection extends AbstractBroadcastConnection implements ITournamentConnection, IOCallback
{
    private TournamentChangeListener _changeListener;
    private String _language;
    private TournamentConnectionListener _listener;
    private Tournament _tournament;
    private TournamentID _tournamentId;
    
    public SocketIOTournamentConnection(final TournamentID tournamentId, final TournamentConnectionListener listener, final TournamentChangeListener changeListener, final String language) {
        super(listener);
        this._tournamentId = tournamentId;
        this._listener = listener;
        this._changeListener = changeListener;
        this._language = language;
    }
    
    private void getTournament() {
        this.getSocket().emit("get", new AbstractTimeoutIOAcknowledge(90000) {
            @Override
            void onAck(final Object... array) {
                Logger.getInstance().debug("TournamentBroadcastSocketIO:", "get Tournament answered: - not logging json here since it heavily costs performance on big tournaments");
                if (array.length > 0 && array[0] instanceof JSONObject) {
                    final JSONObject jsonObject = (JSONObject)array[0];
                    try {
                        SocketIOTournamentConnection.this._tournament = new JSONTournamentParser(SocketIOTournamentConnection.this.getServerTimeOffset(), SocketIOTournamentConnection.this._language).parseResult(jsonObject);
                        SocketIOTournamentConnection.this._listener.tournamentLoaded(SocketIOTournamentConnection.this._tournament);
                    }
                    catch (InvalidJsonForObjectException ex) {
                        Logger.getInstance().error(SocketIOTournamentConnection.class.getName(), InvalidJsonForObjectException.class.getName(), ex);
                        SocketIOTournamentConnection.this.getSocket().disconnect();
                    }
                }
            }
            
            @Override
            void onTimeout() {
                if (SocketIOTournamentConnection.this.isConnected()) {
                    SocketIOTournamentConnection.this.getTournament();
                }
            }
        }, this._tournamentId.getUuid());
    }
    
    private void parseGameDiffs(final String s, final String s2, final String s3, final JSONObject jsonObject) {
        final TournamentGameInfo game = this._tournament.getGameFor(s, s2, s3);
        if (game != null) {
            if (jsonObject.has("engineScore") || jsonObject.has("engine")) {
                game.setEngineEvaluation(JSONTournamentParser.parseEngineScore(jsonObject));
            }
            if (jsonObject.has("gameStatus")) {
                game.setStatus(JSONTournamentParser.parseGameStatus(jsonObject));
            }
            if (jsonObject.has("clock")) {
                final long clock = JSONTournamentParser.parseClock(true, jsonObject);
                if (clock >= 0L) {
                    game.setRemainingTimeWhite(clock);
                    game.setLastMoveTimeStamp(System.currentTimeMillis());
                }
                final long clock2 = JSONTournamentParser.parseClock(false, jsonObject);
                if (clock2 >= 0L) {
                    game.setRemainingTimeBlack(clock2);
                    game.setLastMoveTimeStamp(System.currentTimeMillis());
                }
            }
            if (jsonObject.has("lastMoves")) {
                game.setLastMovesEANsAndEarlierFEN(JSONTournamentParser.parseLastMovesString(jsonObject), JSONTournamentParser.parseEarlierFEN(jsonObject));
            }
            if (jsonObject.has("currentFen")) {
                game.setWhitePlayerActive(new Position(JSONTournamentParser.parseCurrentFEN(jsonObject)).getActiveColor());
            }
            if (this._changeListener != null) {
                this._changeListener.tournamentGameChanged(game);
            }
        }
    }
    
    @Override
    protected void connectingSuccessful() {
    }
    
    @Override
    public void loadTournament() {
        this.getTournament();
    }
    
    @Override
    public void on(final String s, final IOAcknowledge ioAcknowledge, final Object... array) {
        if (s.equals(this._tournamentId.getUuid())) {
            try {
                final JSONObject jsonObject = new JSONObject(array[0].toString());
                final String optString = jsonObject.optString("action", "undef");
                jsonObject.optString("id");
                if (optString.equals("set")) {
                    final JSONObject optJSONObject = jsonObject.optJSONObject("diffs");
                    if (optJSONObject != null) {
                        final JSONObject optJSONObject2 = optJSONObject.optJSONObject("rounds");
                        if (optJSONObject2 != null) {
                            final Iterator keys = optJSONObject2.keys();
                            while (keys.hasNext()) {
                                final String s2 = keys.next();
                                final JSONObject optJSONObject3 = optJSONObject2.optJSONObject(s2);
                                if (optJSONObject3 != null) {
                                    final JSONObject optJSONObject4 = optJSONObject3.optJSONObject("matches");
                                    if (optJSONObject4 == null) {
                                        continue;
                                    }
                                    final Iterator keys2 = optJSONObject4.keys();
                                    while (keys2.hasNext()) {
                                        final String s3 = keys2.next();
                                        final JSONObject optJSONObject5 = optJSONObject4.optJSONObject(s3);
                                        if (optJSONObject5 != null) {
                                            final JSONObject optJSONObject6 = optJSONObject5.optJSONObject("games");
                                            final Iterator keys3 = optJSONObject6.keys();
                                            while (keys3.hasNext()) {
                                                final String s4 = keys3.next();
                                                this.parseGameDiffs(s2, s3, s4, optJSONObject6.optJSONObject(s4));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(SocketIOTournamentConnection.class.getName(), JSONException.class.getName(), (Throwable)ex);
            }
        }
        super.on(s, ioAcknowledge, array);
    }
    
    @Override
    public void subscribeToTournament() {
        this.subscribeToRKey(this._tournamentId.getUuid());
    }
}
