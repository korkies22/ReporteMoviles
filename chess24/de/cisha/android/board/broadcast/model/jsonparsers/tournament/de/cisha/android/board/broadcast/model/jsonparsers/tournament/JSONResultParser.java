/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import de.cisha.android.board.broadcast.model.TournamentsStandings;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONResultParser<E extends TournamentsStandings>
extends JSONAPIResultParser<E> {
    public static final String RESULT_DRAWS = "draws";
    public static final String RESULT_LOSSES = "loses";
    public static final String RESULT_WINS = "wins";
    protected String _standingsType;

    public JSONResultParser(String string) {
        this._standingsType = string;
    }

    protected abstract E createStandingsObject(int var1, int var2, int var3, JSONObject var4);

    @Override
    public E parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        Object object = jSONObject.optJSONObject(this._standingsType);
        if (object != null) {
            int n = object.optInt("wins");
            int n2 = object.optInt("loses");
            return this.createStandingsObject(n, object.optInt("draws"), n2, (JSONObject)object);
        }
        object = new StringBuilder();
        object.append("no standings list for ");
        object.append(this._standingsType);
        object.append(" found in object");
        object.append((Object)jSONObject);
        throw new InvalidJsonForObjectException(object.toString());
    }
}
