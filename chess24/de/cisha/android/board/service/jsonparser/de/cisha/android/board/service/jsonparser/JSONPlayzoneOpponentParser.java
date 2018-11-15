/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.ICountryService;
import de.cisha.android.board.service.ServiceProvider;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.Country;
import de.cisha.chess.model.Opponent;
import de.cisha.chess.model.Rating;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONPlayzoneOpponentParser
extends JSONAPIResultParser<Opponent> {
    private static final String COUNTRY = "Country";
    private static final String NAME = "Name";
    private static final String PREMIUM = "Premium";
    private static final String RATING = "Elo";
    private static final String TITLE = "Title";
    private static final String TYPE = "Type";

    @Override
    public Opponent parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        Opponent opponent = new Opponent();
        opponent.setRating(new Rating(jSONObject.optInt(RATING, Rating.NO_RATING.getRating())));
        opponent.setName(this.optStringNotNull(jSONObject, NAME, "anonymous"));
        opponent.setTitle(this.optStringNotNull(jSONObject, TITLE, ""));
        String string = jSONObject.optString(COUNTRY);
        if (string != null && !"".trim().equals(string)) {
            opponent.setCountry(ServiceProvider.getInstance().getCountryService().getCountryForString(string));
        }
        opponent.setType(jSONObject.optString(TYPE, ""));
        opponent.setPremium(this.optBoolean(jSONObject, PREMIUM, false));
        return opponent;
    }
}
