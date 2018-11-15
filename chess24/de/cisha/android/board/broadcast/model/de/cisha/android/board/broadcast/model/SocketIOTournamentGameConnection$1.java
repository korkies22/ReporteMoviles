/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.AbstractTimeoutIOAcknowledge;
import de.cisha.android.board.broadcast.model.ITournamentGameConnection;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONGameParser;
import de.cisha.chess.model.Game;
import de.cisha.chess.model.GameType;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

class SocketIOTournamentGameConnection
extends AbstractTimeoutIOAcknowledge {
    SocketIOTournamentGameConnection() {
    }

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
                Logger.getInstance().debug(de.cisha.android.board.broadcast.model.SocketIOTournamentGameConnection.class.getName(), "ParserException:", invalidJsonForObjectException);
                return;
            }
            catch (JSONException jSONException) {
                SocketIOTournamentGameConnection.this._listener.tournamentGameLoadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON);
                Logger.getInstance().debug(de.cisha.android.board.broadcast.model.SocketIOTournamentGameConnection.class.getName(), "JSONEXception:", (Throwable)jSONException);
            }
        }
    }

    @Override
    void onTimeout() {
        if (SocketIOTournamentGameConnection.this.isConnected()) {
            SocketIOTournamentGameConnection.this.loadGame();
        }
    }
}
