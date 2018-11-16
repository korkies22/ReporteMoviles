// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonmapping;

import org.json.JSONException;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.chess.model.Opponent;

public class JSONMappingOpponent implements JSONAPIMapping<Opponent>
{
    private static final String COUNTRY = "Country";
    private static final String NAME = "Name";
    private static final String PREMIUM = "Premium";
    private static final String RATING = "Elo";
    private static final String TITLE = "Title";
    private static final String TYPE = "Type";
    
    @Override
    public JSONObject mapToJSON(final Opponent opponent) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("Type", (Object)opponent.getType());
        jsonObject.put("Country", (Object)opponent.getCountry());
        jsonObject.put("Title", (Object)opponent.getTitle());
        jsonObject.put("Name", (Object)opponent.getName());
        Object o;
        if (opponent.hasRating()) {
            o = opponent.getRating().getRating();
        }
        else {
            o = Rating.NO_RATING;
        }
        jsonObject.put("Elo", o);
        jsonObject.put("Premium", opponent.isPremium());
        return jsonObject;
    }
}
