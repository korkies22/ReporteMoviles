/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.profile.model;

import de.cisha.android.board.profile.model.PlayzoneStatisticData;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.FEN;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONPlayzoneWidgetDataParser
extends JSONAPIResultParser<PlayzoneStatisticData> {
    @Override
    public PlayzoneStatisticData parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        int n;
        int n2;
        int n3;
        int n4;
        JSONObject jSONObject = object.getJSONObject("game_stats");
        int n5 = 0;
        if (jSONObject != null) {
            n = jSONObject.getInt("won");
            n2 = jSONObject.getInt("lost");
            n4 = jSONObject.getInt("draw");
        } else {
            n2 = n4 = (n3 = 0);
            n = n4;
            n4 = n3;
        }
        object = this.optFEN((JSONObject)object, "last_game_fen", FEN.STARTING_POSITION);
        n3 = object == null ? 1 : 0;
        if (n + n2 + n4 > 0) {
            n5 = 1;
        }
        if ((n5 & n3) != 0) {
            throw new InvalidJsonForObjectException("fen should not be null when already played games exist");
        }
        return new PlayzoneStatisticData(n, n2, n4, (FEN)object);
    }
}
