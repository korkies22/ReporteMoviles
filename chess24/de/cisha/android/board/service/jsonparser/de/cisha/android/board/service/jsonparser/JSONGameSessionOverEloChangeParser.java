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

public class JSONGameSessionOverEloChangeParser
extends JSONAPIResultParser<EloChange> {
    private static final String JSON_KEY_CHESS_GAME = "chessGame";
    private static final String JSON_KEY_ELO = "Elo";
    private static final String JSON_KEY_ELO_CHANGE = "EloChange";
    private static final String JSON_KEY_GAME_META = "meta";
    private static final String JSON_KEY_PLAYER_BLACK = "Black";
    private static final String JSON_KEY_PLAYER_WHITE = "White";

    private int getChangeFromPlayer(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optInt(JSON_KEY_ELO_CHANGE);
        }
        return 0;
    }

    private int getEloFromPlayer(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optInt(JSON_KEY_ELO);
        }
        return 0;
    }

    @Override
    public EloChange parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException, JSONException {
        int n;
        JSONObject jSONObject2;
        int n2;
        int n3;
        jSONObject = jSONObject.optJSONObject(JSON_KEY_CHESS_GAME);
        int n4 = 0;
        if (jSONObject != null && (jSONObject2 = jSONObject.optJSONObject(JSON_KEY_GAME_META)) != null) {
            jSONObject = jSONObject2.optJSONObject(JSON_KEY_PLAYER_WHITE);
            jSONObject2 = jSONObject2.optJSONObject(JSON_KEY_PLAYER_BLACK);
            n4 = this.getEloFromPlayer(jSONObject);
            n3 = this.getEloFromPlayer(jSONObject2);
            int n5 = this.getChangeFromPlayer(jSONObject);
            n = this.getChangeFromPlayer(jSONObject2);
            n2 = n5;
        } else {
            int n6;
            n3 = n6 = (n2 = 0);
            n = n6;
        }
        return new EloChange(new Rating(n4), new Rating(n3), n2, n);
    }
}
