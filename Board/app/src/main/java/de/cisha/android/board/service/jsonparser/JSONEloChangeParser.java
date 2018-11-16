// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.android.board.playzone.remote.model.EloChange;

public class JSONEloChangeParser extends JSONAPIResultParser<EloChange>
{
    private static final String JSON_KEY_BLACK = "black";
    private static final String JSON_KEY_BLACK_CHANGE = "blackChange";
    private static final String JSON_KEY_WHITE = "white";
    private static final String JSON_KEY_WHITE_CHANGE = "whiteChange";
    
    @Override
    public EloChange parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        return new EloChange(new Rating(jsonObject.optInt("white")), new Rating(jsonObject.optInt("black")), jsonObject.optInt("whiteChange"), jsonObject.optInt("blackChange"));
    }
}
