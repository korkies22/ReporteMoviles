// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.profile.model;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.chess.model.FEN;
import org.json.JSONObject;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONPlayzoneWidgetDataParser extends JSONAPIResultParser<PlayzoneStatisticData>
{
    @Override
    public PlayzoneStatisticData parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final JSONObject jsonObject2 = jsonObject.getJSONObject("game_stats");
        int n = false ? 1 : 0;
        int int1;
        int int2;
        int int3;
        if (jsonObject2 != null) {
            int1 = jsonObject2.getInt("won");
            int2 = jsonObject2.getInt("lost");
            int3 = jsonObject2.getInt("draw");
        }
        else {
            final int n2 = 0;
            int2 = (int1 = n2);
            int3 = n2;
        }
        final FEN optFEN = this.optFEN(jsonObject, "last_game_fen", FEN.STARTING_POSITION);
        final boolean b = optFEN == null;
        if (int1 + int2 + int3 > 0) {
            n = (true ? 1 : 0);
        }
        if ((n & (b ? 1 : 0)) != 0x0) {
            throw new InvalidJsonForObjectException("fen should not be null when already played games exist");
        }
        return new PlayzoneStatisticData(int1, int2, int3, optFEN);
    }
}
