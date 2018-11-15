/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTournamentStandingsParser
extends JSONAPIResultParser<List<String>> {
    private String _standingsType;

    public JSONTournamentStandingsParser(String string) {
        this._standingsType = string;
    }

    @Override
    public List<String> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        Object object = jSONObject.optJSONArray(this._standingsType);
        ArrayList<String> arrayList = new ArrayList<String>();
        if (object != null) {
            for (int i = 0; i < object.length(); ++i) {
                arrayList.add(object.getString(i));
            }
            return arrayList;
        }
        object = new StringBuilder();
        object.append("no standings list for '");
        object.append(this._standingsType);
        object.append("' found in object");
        object.append((Object)jSONObject);
        throw new InvalidJsonForObjectException(object.toString());
    }
}
