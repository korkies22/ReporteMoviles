// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.tactics.model;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import org.json.JSONException;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.chess.model.Rating;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class TacticsClassicRatingParser extends JSONAPIResultParser<Rating>
{
    @Override
    public Rating parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new Rating(jsonObject.getInt("classic"));
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(TacticsClassicRatingParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("Error getting Rating: ", (Throwable)ex);
        }
    }
}
