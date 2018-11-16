// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.android.board.video.model.MinMaxEloRange;
import de.cisha.android.board.video.model.FuzzyEloRange;
import org.json.JSONObject;
import de.cisha.android.board.video.model.EloRangeRepresentation;

public class JSONEloRangeParser extends JSONAPIResultParser<EloRangeRepresentation>
{
    private static final String KEY_ELO_MAX = "elo_max";
    private static final String KEY_ELO_MIN = "elo_min";
    
    @Override
    public EloRangeRepresentation parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final int optInt = jsonObject.optInt("elo_min");
        final int optInt2 = jsonObject.optInt("elo_max");
        if (optInt < 0) {
            return new FuzzyEloRange(2131690405);
        }
        if (optInt == 0) {
            return new FuzzyEloRange(2131690406);
        }
        return new MinMaxEloRange(optInt, optInt2);
    }
}
