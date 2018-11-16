// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.broadcast.model.jsonparsers.tournament;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.broadcast.model.TournamentsStandings;

public abstract class JSONResultParser<E extends TournamentsStandings> extends JSONAPIResultParser<E>
{
    public static final String RESULT_DRAWS = "draws";
    public static final String RESULT_LOSSES = "loses";
    public static final String RESULT_WINS = "wins";
    protected String _standingsType;
    
    public JSONResultParser(final String standingsType) {
        this._standingsType = standingsType;
    }
    
    protected abstract E createStandingsObject(final int p0, final int p1, final int p2, final JSONObject p3);
    
    @Override
    public E parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final JSONObject optJSONObject = jsonObject.optJSONObject(this._standingsType);
        if (optJSONObject != null) {
            return this.createStandingsObject(optJSONObject.optInt("wins"), optJSONObject.optInt("draws"), optJSONObject.optInt("loses"), optJSONObject);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("no standings list for ");
        sb.append(this._standingsType);
        sb.append(" found in object");
        sb.append(jsonObject);
        throw new InvalidJsonForObjectException(sb.toString());
    }
}
