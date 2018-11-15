/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.connection.IConnection;
import de.cisha.android.board.broadcast.model.AbstractBroadcastConnection;
import de.cisha.android.board.broadcast.model.AbstractTimeoutIOAcknowledge;
import de.cisha.android.board.broadcast.model.BroadcastGameStatus;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.TournamentGameInfo;
import de.cisha.android.board.broadcast.model.TournamentID;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONTournamentParser;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.chess.engine.EngineEvaluation;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.position.Position;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketIOTournamentConnection
extends AbstractBroadcastConnection
implements ITournamentConnection,
IOCallback {
    private ITournamentConnection.TournamentChangeListener _changeListener;
    private String _language;
    private ITournamentConnection.TournamentConnectionListener _listener;
    private Tournament _tournament;
    private TournamentID _tournamentId;

    public SocketIOTournamentConnection(TournamentID tournamentID, ITournamentConnection.TournamentConnectionListener tournamentConnectionListener, ITournamentConnection.TournamentChangeListener tournamentChangeListener, String string) {
        super(tournamentConnectionListener);
        this._tournamentId = tournamentID;
        this._listener = tournamentConnectionListener;
        this._changeListener = tournamentChangeListener;
        this._language = string;
    }

    private void getTournament() {
        this.getSocket().emit("get", new AbstractTimeoutIOAcknowledge(90000){

            @Override
            /* varargs */ void onAck(Object ... jSONObject) {
                Logger.getInstance().debug("TournamentBroadcastSocketIO:", "get Tournament answered: - not logging json here since it heavily costs performance on big tournaments");
                if (((Object[])jSONObject).length > 0 && jSONObject[0] instanceof JSONObject) {
                    jSONObject = (JSONObject)jSONObject[0];
                    try {
                        long l = SocketIOTournamentConnection.this.getServerTimeOffset();
                        SocketIOTournamentConnection.this._tournament = new JSONTournamentParser(l, SocketIOTournamentConnection.this._language).parseResult(jSONObject);
                        SocketIOTournamentConnection.this._listener.tournamentLoaded(SocketIOTournamentConnection.this._tournament);
                        return;
                    }
                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                        Logger.getInstance().error(SocketIOTournamentConnection.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
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

    private void parseGameDiffs(String object, String object2, String string, JSONObject jSONObject) {
        if ((object = this._tournament.getGameFor((String)object, (String)object2, string)) != null) {
            if (jSONObject.has("engineScore") || jSONObject.has("engine")) {
                object.setEngineEvaluation(JSONTournamentParser.parseEngineScore(jSONObject));
            }
            if (jSONObject.has("gameStatus")) {
                object.setStatus(JSONTournamentParser.parseGameStatus(jSONObject));
            }
            if (jSONObject.has("clock")) {
                long l = JSONTournamentParser.parseClock(true, jSONObject);
                if (l >= 0L) {
                    object.setRemainingTimeWhite(l);
                    object.setLastMoveTimeStamp(System.currentTimeMillis());
                }
                if ((l = JSONTournamentParser.parseClock(false, jSONObject)) >= 0L) {
                    object.setRemainingTimeBlack(l);
                    object.setLastMoveTimeStamp(System.currentTimeMillis());
                }
            }
            if (jSONObject.has("lastMoves")) {
                object2 = JSONTournamentParser.parseEarlierFEN(jSONObject);
                object.setLastMovesEANsAndEarlierFEN(JSONTournamentParser.parseLastMovesString(jSONObject), (FEN)object2);
            }
            if (jSONObject.has("currentFen")) {
                object.setWhitePlayerActive(new Position(JSONTournamentParser.parseCurrentFEN(jSONObject)).getActiveColor());
            }
            if (this._changeListener != null) {
                this._changeListener.tournamentGameChanged((TournamentGameInfo)object);
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public /* varargs */ void on(String string, IOAcknowledge iOAcknowledge, Object ... arrobject) {
        if (string.equals(this._tournamentId.getUuid())) {
            try {
                JSONObject jSONObject = new JSONObject(arrobject[0].toString());
                Object object = jSONObject.optString("action", "undef");
                jSONObject.optString("id");
                if (object.equals("set") && (jSONObject = jSONObject.optJSONObject("diffs")) != null && (jSONObject = jSONObject.optJSONObject("rounds")) != null) {
                    object = jSONObject.keys();
                    while (object.hasNext()) {
                        String string2 = (String)object.next();
                        JSONObject jSONObject2 = jSONObject.optJSONObject(string2);
                        if (jSONObject2 == null || (jSONObject2 = jSONObject2.optJSONObject("matches")) == null) continue;
                        Iterator iterator = jSONObject2.keys();
                        while (iterator.hasNext()) {
                            String string3 = (String)iterator.next();
                            JSONObject jSONObject3 = jSONObject2.optJSONObject(string3);
                            if (jSONObject3 == null) continue;
                            jSONObject3 = jSONObject3.optJSONObject("games");
                            Iterator iterator2 = jSONObject3.keys();
                            while (iterator2.hasNext()) {
                                String string4 = (String)iterator2.next();
                                this.parseGameDiffs(string2, string3, string4, jSONObject3.optJSONObject(string4));
                            }
                        }
                    }
                }
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(SocketIOTournamentConnection.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            }
        }
        super.on(string, iOAcknowledge, arrobject);
    }

    @Override
    public void subscribeToTournament() {
        this.subscribeToRKey(this._tournamentId.getUuid());
    }

}
