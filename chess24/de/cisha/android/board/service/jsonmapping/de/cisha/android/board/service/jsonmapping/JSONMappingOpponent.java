/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonmapping;

import de.cisha.android.board.service.jsonmapping.JSONAPIMapping;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONMappingOpponent
implements JSONAPIMapping<Opponent> {
    private static final String COUNTRY = "Country";
    private static final String NAME = "Name";
    private static final String PREMIUM = "Premium";
    private static final String RATING = "Elo";
    private static final String TITLE = "Title";
    private static final String TYPE = "Type";

    @Override
    public JSONObject mapToJSON(Opponent opponent) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(TYPE, (Object)opponent.getType());
        jSONObject.put(COUNTRY, (Object)opponent.getCountry());
        jSONObject.put(TITLE, (Object)opponent.getTitle());
        jSONObject.put(NAME, (Object)opponent.getName());
        Object object = opponent.hasRating() ? Integer.valueOf(opponent.getRating().getRating()) : Rating.NO_RATING;
        jSONObject.put(RATING, object);
        jSONObject.put(PREMIUM, opponent.isPremium());
        return jSONObject;
    }
}
