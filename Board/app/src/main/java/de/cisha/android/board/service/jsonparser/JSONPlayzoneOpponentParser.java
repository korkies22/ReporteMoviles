// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.chess.model.Opponent;

public class JSONPlayzoneOpponentParser extends JSONAPIResultParser<Opponent>
{
    private static final String COUNTRY = "Country";
    private static final String NAME = "Name";
    private static final String PREMIUM = "Premium";
    private static final String RATING = "Elo";
    private static final String TITLE = "Title";
    private static final String TYPE = "Type";
    
    @Override
    public Opponent parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        final Opponent opponent = new Opponent();
        opponent.setRating(new Rating(jsonObject.optInt("Elo", Rating.NO_RATING.getRating())));
        opponent.setName(this.optStringNotNull(jsonObject, "Name", "anonymous"));
        opponent.setTitle(this.optStringNotNull(jsonObject, "Title", ""));
        final String optString = jsonObject.optString("Country");
        if (optString != null && !"".trim().equals(optString)) {
            opponent.setCountry(ServiceProvider.getInstance().getCountryService().getCountryForString(optString));
        }
        opponent.setType(jsonObject.optString("Type", ""));
        opponent.setPremium(this.optBoolean(jsonObject, "Premium", false));
        return opponent;
    }
}
