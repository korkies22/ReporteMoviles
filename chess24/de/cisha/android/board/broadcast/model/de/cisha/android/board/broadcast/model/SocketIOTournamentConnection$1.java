/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.AbstractTimeoutIOAcknowledge;
import de.cisha.android.board.broadcast.model.ITournamentConnection;
import de.cisha.android.board.broadcast.model.Tournament;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONTournamentParser;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.chess.util.Logger;
import io.socket.SocketIO;
import org.json.JSONObject;

class SocketIOTournamentConnection
extends AbstractTimeoutIOAcknowledge {
    SocketIOTournamentConnection(int n) {
        super(n);
    }

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
                Logger.getInstance().error(de.cisha.android.board.broadcast.model.SocketIOTournamentConnection.class.getName(), InvalidJsonForObjectException.class.getName(), invalidJsonForObjectException);
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
}
