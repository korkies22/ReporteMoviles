/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONTournamentTeamParser;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTournamentTeamsParser
extends JSONAPIResultParser<Map<String, TournamentTeam>> {
    private String _currentRoundForStandings;
    private Map<String, TournamentPlayer> _players;

    public JSONTournamentTeamsParser(Map<String, TournamentPlayer> map, String string) {
        this._players = map;
        this._currentRoundForStandings = string;
    }

    @Override
    public Map<String, TournamentTeam> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        if (jSONObject == null) {
            throw new InvalidJsonForObjectException("teamsObject was null");
        }
        Iterator iterator = jSONObject.keys();
        HashMap<String, TournamentTeam> hashMap = new HashMap<String, TournamentTeam>();
        while (iterator.hasNext()) {
            String string = (String)iterator.next();
            JSONObject jSONObject2 = jSONObject.optJSONObject(string);
            try {
                hashMap.put(string, new JSONTournamentTeamParser(string, this._players, this._currentRoundForStandings).parseResult(jSONObject2));
            }
            catch (JSONException jSONException) {
                Logger.getInstance().debug(this.getClass().getName(), "Incorrect Team JSON", (Throwable)jSONException);
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {
                Logger.getInstance().debug(this.getClass().getName(), "Incorrect Team JSON", invalidJsonForObjectException);
            }
        }
        return hashMap;
    }
}
