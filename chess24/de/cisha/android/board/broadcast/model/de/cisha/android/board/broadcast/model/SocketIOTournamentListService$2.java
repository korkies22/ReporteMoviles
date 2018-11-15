/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model;

import de.cisha.android.board.broadcast.model.TournamentInfo;
import de.cisha.android.board.broadcast.model.jsonparsers.JSONTournamentInfoParser;
import de.cisha.android.board.service.jsonparser.APIStatusCode;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.LoadCommandCallback;
import de.cisha.android.board.service.jsonparser.LoadFieldError;
import de.cisha.chess.util.Logger;
import io.socket.IOAcknowledge;
import io.socket.SocketIO;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class SocketIOTournamentListService
implements IOAcknowledge {
    final /* synthetic */ SocketIO val$broadcastNodeSocket;
    final /* synthetic */ LoadCommandCallback val$callback;
    final /* synthetic */ long val$timeOffset;

    SocketIOTournamentListService(long l, LoadCommandCallback loadCommandCallback, SocketIO socketIO) {
        this.val$timeOffset = l;
        this.val$callback = loadCommandCallback;
        this.val$broadcastNodeSocket = socketIO;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public /* varargs */ void ack(Object ... jSONObject) {
        block10 : {
            int n;
            Object object;
            Serializable serializable;
            JSONTournamentInfoParser jSONTournamentInfoParser;
            block9 : {
                Logger.getInstance().debug("BroadcastSocketIO:", "getTournaments answered:");
                int n2 = 0;
                for (n = 0; n < ((JSONObject)jSONObject).length; ++n) {
                    object = Logger.getInstance();
                    serializable = new StringBuilder();
                    serializable.append("args[");
                    serializable.append(n);
                    serializable.append("]: ");
                    serializable.append((Object)jSONObject[n]);
                    object.debug("BroadcastSocketIO:", serializable.toString());
                }
                if (((JSONObject)jSONObject).length > 0 && jSONObject[0] instanceof JSONObject) {
                    jSONObject = jSONObject[0];
                    try {
                        jSONObject = jSONObject.getJSONArray("tournaments");
                        object = new HashSet(jSONObject.length());
                        serializable = new LinkedList();
                        jSONTournamentInfoParser = new JSONTournamentInfoParser(this.val$timeOffset, SocketIOTournamentListService.this._language);
                        n = n2;
                        break block9;
                    }
                    catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                        Logger.getInstance().debug(de.cisha.android.board.broadcast.model.SocketIOTournamentListService.class.getName(), JSONException.class.getName(), invalidJsonForObjectException);
                        this.val$callback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                    }
                    catch (JSONException jSONException) {
                        this.val$callback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                        Logger.getInstance().debug(de.cisha.android.board.broadcast.model.SocketIOTournamentListService.class.getName(), InvalidJsonForObjectException.class.getName(), (Throwable)jSONException);
                    }
                }
                this.val$callback.loadingFailed(APIStatusCode.INTERNAL_INCORRECT_SERVER_JSON, "received incorrec server json for getTournaments", null, null);
                break block10;
            }
            do {
                if (n < jSONObject.length()) {
                    TournamentInfo tournamentInfo = jSONTournamentInfoParser.parseResult(jSONObject.getJSONObject(n));
                    if (object.add(tournamentInfo)) {
                        serializable.add(tournamentInfo);
                    }
                } else {
                    this.val$callback.loadingSucceded(serializable);
                    break;
                }
                ++n;
            } while (true);
        }
        this.val$broadcastNodeSocket.disconnect();
    }
}
