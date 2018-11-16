// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import org.json.JSONException;
import java.util.Iterator;
import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.chess.model.Country;
import com.neovisionaries.i18n.CountryCode;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import java.util.Map;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONTournamentTeamParser extends JSONAPIResultParser<TournamentTeam>
{
    private String _currentRound;
    private Map<String, TournamentPlayer> _players;
    private String _tournamentKey;
    
    public JSONTournamentTeamParser(final String tournamentKey, final Map<String, TournamentPlayer> players, final String currentRound) {
        this._tournamentKey = tournamentKey;
        this._players = players;
        this._currentRound = currentRound;
    }
    
    @Override
    public TournamentTeam parseResult(JSONObject optJSONObject) throws InvalidJsonForObjectException, JSONException {
        if (optJSONObject == null) {
            final StringBuilder sb = new StringBuilder();
            sb.append("Team for key ");
            sb.append(this._tournamentKey);
            sb.append(" was null");
            throw new InvalidJsonForObjectException(sb.toString());
        }
        final String optString = optJSONObject.optString("name", "");
        final JSONObject optJSONObject2 = optJSONObject.optJSONObject("rooster");
        final HashMap<String, TournamentPlayer> hashMap = new HashMap<String, TournamentPlayer>();
        if (optJSONObject2 != null) {
            final Iterator keys = optJSONObject2.keys();
            while (keys.hasNext()) {
                final String s = keys.next();
                final String optString2 = optJSONObject2.optString(s, "");
                final TournamentPlayer tournamentPlayer = this._players.get(optString2);
                if (tournamentPlayer != null) {
                    hashMap.put(s, tournamentPlayer);
                }
                else {
                    final Logger instance = Logger.getInstance();
                    final String name = this.getClass().getName();
                    final StringBuilder sb2 = new StringBuilder();
                    sb2.append("Invalid player id in TournamentTeam: '");
                    sb2.append(optString);
                    sb2.append("' id:");
                    sb2.append(optString2);
                    instance.debug(name, sb2.toString());
                }
            }
        }
        else {
            final Logger instance2 = Logger.getInstance();
            final String name2 = this.getClass().getName();
            final StringBuilder sb3 = new StringBuilder();
            sb3.append("TournamentTeam ");
            sb3.append(optString);
            sb3.append(" didn't contain any players");
            instance2.debug(name2, sb3.toString());
        }
        final TournamentTeam tournamentTeam = new TournamentTeam(this._tournamentKey, optString, hashMap);
        final String optString3 = optJSONObject.optString("country", (String)null);
        if (optString3 != null) {
            tournamentTeam.setCountry(CountryCode.getByCode(optString3, false));
        }
        optJSONObject = optJSONObject.optJSONObject("results");
        if (optJSONObject == null) {
            return tournamentTeam;
        }
        while (true) {
            while (true) {
                try {
                    tournamentTeam.setCurrentStandings(new JSONTeamResultParser(this._currentRound).parseResult(optJSONObject));
                    return tournamentTeam;
                    final Logger instance3 = Logger.getInstance();
                    final StringBuilder sb4 = new StringBuilder();
                    sb4.append("No Standings for Team ");
                    sb4.append(optString);
                    instance3.error(sb4.toString());
                    return tournamentTeam;
                }
                catch (InvalidJsonForObjectException ex) {}
                continue;
            }
        }
    }
}
