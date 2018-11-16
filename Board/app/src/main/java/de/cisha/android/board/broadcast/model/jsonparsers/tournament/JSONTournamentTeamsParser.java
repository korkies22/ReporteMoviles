// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import java.util.Iterator;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import java.util.Map;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONTournamentTeamsParser extends JSONAPIResultParser<Map<String, TournamentTeam>>
{
    private String _currentRoundForStandings;
    private Map<String, TournamentPlayer> _players;
    
    public JSONTournamentTeamsParser(final Map<String, TournamentPlayer> players, final String currentRoundForStandings) {
        this._players = players;
        this._currentRoundForStandings = currentRoundForStandings;
    }
    
    @Override
    public Map<String, TournamentTeam> parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        if (jsonObject == null) {
            throw new InvalidJsonForObjectException("teamsObject was null");
        }
        final Iterator keys = jsonObject.keys();
        final HashMap<String, TournamentTeam> hashMap = new HashMap<String, TournamentTeam>();
        while (keys.hasNext()) {
            final String s = keys.next();
            final JSONObject optJSONObject = jsonObject.optJSONObject(s);
            try {
                hashMap.put(s, new JSONTournamentTeamParser(s, this._players, this._currentRoundForStandings).parseResult(optJSONObject));
            }
            catch (JSONException ex) {
                Logger.getInstance().debug(this.getClass().getName(), "Incorrect Team JSON", (Throwable)ex);
            }
            catch (InvalidJsonForObjectException ex2) {
                Logger.getInstance().debug(this.getClass().getName(), "Incorrect Team JSON", ex2);
            }
        }
        return hashMap;
    }
}
