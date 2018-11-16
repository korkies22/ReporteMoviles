// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import org.json.JSONArray;
import java.util.ArrayList;
import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import java.util.List;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONTournamentStandingsParser extends JSONAPIResultParser<List<String>>
{
    private String _standingsType;
    
    public JSONTournamentStandingsParser(final String standingsType) {
        this._standingsType = standingsType;
    }
    
    @Override
    public List<String> parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final JSONArray optJSONArray = jsonObject.optJSONArray(this._standingsType);
        final ArrayList<String> list = new ArrayList<String>();
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); ++i) {
                list.add(optJSONArray.getString(i));
            }
            return list;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("no standings list for '");
        sb.append(this._standingsType);
        sb.append("' found in object");
        sb.append(jsonObject);
        throw new InvalidJsonForObjectException(sb.toString());
    }
}
