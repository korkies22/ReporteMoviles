/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.video.model.EloRangeRepresentation;
import de.cisha.android.board.video.model.FuzzyEloRange;
import de.cisha.android.board.video.model.MinMaxEloRange;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONEloRangeParser
extends JSONAPIResultParser<EloRangeRepresentation> {
    private static final String KEY_ELO_MAX = "elo_max";
    private static final String KEY_ELO_MIN = "elo_min";

    @Override
    public EloRangeRepresentation parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        int n = jSONObject.optInt(KEY_ELO_MIN);
        int n2 = jSONObject.optInt(KEY_ELO_MAX);
        if (n < 0) {
            return new FuzzyEloRange(2131690405);
        }
        if (n == 0) {
            return new FuzzyEloRange(2131690406);
        }
        return new MinMaxEloRange(n, n2);
    }
}
