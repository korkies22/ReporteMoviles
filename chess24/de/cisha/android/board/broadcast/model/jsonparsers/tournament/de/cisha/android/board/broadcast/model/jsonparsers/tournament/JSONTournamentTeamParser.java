/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import com.neovisionaries.i18n.CountryCode;
import de.cisha.android.board.broadcast.model.TeamStanding;
import de.cisha.android.board.broadcast.model.TournamentPlayer;
import de.cisha.android.board.broadcast.model.TournamentTeam;
import de.cisha.android.board.broadcast.model.jsonparsers.tournament.JSONTeamResultParser;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.Country;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTournamentTeamParser
extends JSONAPIResultParser<TournamentTeam> {
    private String _currentRound;
    private Map<String, TournamentPlayer> _players;
    private String _tournamentKey;

    public JSONTournamentTeamParser(String string, Map<String, TournamentPlayer> map, String string2) {
        this._tournamentKey = string;
        this._players = map;
        this._currentRound = string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public TournamentTeam parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        if (object == null) {
            object = new StringBuilder();
            object.append("Team for key ");
            object.append(this._tournamentKey);
            object.append(" was null");
            throw new InvalidJsonForObjectException(object.toString());
        }
        String string = object.optString("name", "");
        Object object2 = object.optJSONObject("rooster");
        Object object3 = new HashMap<String, TournamentPlayer>();
        if (object2 == null) {
            object2 = Logger.getInstance();
            String string2 = this.getClass().getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TournamentTeam ");
            stringBuilder.append(string);
            stringBuilder.append(" didn't contain any players");
            object2.debug(string2, stringBuilder.toString());
        } else {
            Iterator iterator = object2.keys();
            while (iterator.hasNext()) {
                Object object4 = (String)iterator.next();
                String string3 = object2.optString((String)object4, "");
                Object object5 = this._players.get(string3);
                if (object5 != null) {
                    object3.put(object4, object5);
                    continue;
                }
                object4 = Logger.getInstance();
                object5 = this.getClass().getName();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid player id in TournamentTeam: '");
                stringBuilder.append(string);
                stringBuilder.append("' id:");
                stringBuilder.append(string3);
                object4.debug((String)object5, stringBuilder.toString());
            }
        }
        object3 = new TournamentTeam(this._tournamentKey, string, (Map<String, TournamentPlayer>)object3);
        object2 = object.optString("country", null);
        if (object2 != null) {
            object3.setCountry(CountryCode.getByCode((String)object2, false));
        }
        if ((object = object.optJSONObject("results")) != null) {
            try {
                object3.setCurrentStandings((TeamStanding)new JSONTeamResultParser(this._currentRound).parseResult((JSONObject)object));
                return object3;
            }
            catch (InvalidJsonForObjectException invalidJsonForObjectException) {}
            Logger logger = Logger.getInstance();
            object2 = new StringBuilder();
            object2.append("No Standings for Team ");
            object2.append(string);
            logger.error(object2.toString());
        }
        return object3;
    }
}
