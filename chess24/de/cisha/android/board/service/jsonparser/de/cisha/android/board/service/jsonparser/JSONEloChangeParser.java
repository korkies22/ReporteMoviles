/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.playzone.remote.model.EloChange;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.Rating;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONEloChangeParser
extends JSONAPIResultParser<EloChange> {
    private static final String JSON_KEY_BLACK = "black";
    private static final String JSON_KEY_BLACK_CHANGE = "blackChange";
    private static final String JSON_KEY_WHITE = "white";
    private static final String JSON_KEY_WHITE_CHANGE = "whiteChange";

    @Override
    public EloChange parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        int n = jSONObject.optInt(JSON_KEY_WHITE_CHANGE);
        int n2 = jSONObject.optInt(JSON_KEY_BLACK_CHANGE);
        int n3 = jSONObject.optInt(JSON_KEY_WHITE);
        int n4 = jSONObject.optInt(JSON_KEY_BLACK);
        return new EloChange(new Rating(n3), new Rating(n4), n, n2);
    }
}
