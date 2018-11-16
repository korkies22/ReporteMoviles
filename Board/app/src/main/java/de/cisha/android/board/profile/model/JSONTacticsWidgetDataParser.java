// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.model;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.chess.model.FEN;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONTacticsWidgetDataParser extends JSONAPIResultParser<TacticStatisticData>
{
    @Override
    public TacticStatisticData parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final int optInt = jsonObject.optInt("rating_classic", -1);
        Rating no_RATING;
        if (optInt == -1) {
            no_RATING = Rating.NO_RATING;
        }
        else {
            no_RATING = new Rating(optInt);
        }
        final int optInt2 = jsonObject.optInt("attempts_classic", 0);
        final int optInt3 = jsonObject.optInt("success_classic", 0);
        final float n = (float)jsonObject.optDouble("tempo_classic", 0.0);
        final FEN optFEN = this.optFEN(jsonObject, "last_fen_classic", FEN.STARTING_POSITION);
        if (optFEN == null && optInt2 > 0) {
            throw new InvalidJsonForObjectException("fen should not be null if attemps > 0");
        }
        return new TacticStatisticData(no_RATING, optInt2, optInt3, n, optFEN);
    }
}
