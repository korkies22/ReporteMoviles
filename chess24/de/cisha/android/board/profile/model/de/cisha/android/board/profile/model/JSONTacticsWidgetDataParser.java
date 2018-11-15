/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile.model;

import de.cisha.android.board.profile.model.TacticStatisticData;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Rating;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTacticsWidgetDataParser
extends JSONAPIResultParser<TacticStatisticData> {
    @Override
    public TacticStatisticData parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        int n = object.optInt("rating_classic", -1);
        Rating rating = n == -1 ? Rating.NO_RATING : new Rating(n);
        n = object.optInt("attempts_classic", 0);
        int n2 = object.optInt("success_classic", 0);
        float f = (float)object.optDouble("tempo_classic", 0.0);
        if ((object = this.optFEN((JSONObject)object, "last_fen_classic", FEN.STARTING_POSITION)) == null && n > 0) {
            throw new InvalidJsonForObjectException("fen should not be null if attemps > 0");
        }
        return new TacticStatisticData(rating, n, n2, f, (FEN)object);
    }
}
