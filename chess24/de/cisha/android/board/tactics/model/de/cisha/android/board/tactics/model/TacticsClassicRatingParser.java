/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.tactics.model;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.Rating;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class TacticsClassicRatingParser
extends JSONAPIResultParser<Rating> {
    @Override
    public Rating parseResult(JSONObject object) throws InvalidJsonForObjectException {
        try {
            object = new Rating(object.getInt("classic"));
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(TacticsClassicRatingParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("Error getting Rating: ", (Throwable)jSONException);
        }
    }
}
