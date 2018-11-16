// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.chess.model.Rating;
import org.json.JSONObject;
import de.cisha.android.board.playzone.remote.model.EloChange;

public class JSONGameSessionOverEloChangeParser extends JSONAPIResultParser<EloChange>
{
    private static final String JSON_KEY_CHESS_GAME = "chessGame";
    private static final String JSON_KEY_ELO = "Elo";
    private static final String JSON_KEY_ELO_CHANGE = "EloChange";
    private static final String JSON_KEY_GAME_META = "meta";
    private static final String JSON_KEY_PLAYER_BLACK = "Black";
    private static final String JSON_KEY_PLAYER_WHITE = "White";
    
    private int getChangeFromPlayer(final JSONObject jsonObject) {
        if (jsonObject != null) {
            return jsonObject.optInt("EloChange");
        }
        return 0;
    }
    
    private int getEloFromPlayer(final JSONObject jsonObject) {
        if (jsonObject != null) {
            return jsonObject.optInt("Elo");
        }
        return 0;
    }
    
    @Override
    public EloChange parseResult(JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        jsonObject = jsonObject.optJSONObject("chessGame");
        int eloFromPlayer = 0;
        if (jsonObject != null) {
            final JSONObject optJSONObject = jsonObject.optJSONObject("meta");
            if (optJSONObject != null) {
                jsonObject = optJSONObject.optJSONObject("White");
                final JSONObject optJSONObject2 = optJSONObject.optJSONObject("Black");
                eloFromPlayer = this.getEloFromPlayer(jsonObject);
                final int eloFromPlayer2 = this.getEloFromPlayer(optJSONObject2);
                final int changeFromPlayer = this.getChangeFromPlayer(jsonObject);
                final int changeFromPlayer2 = this.getChangeFromPlayer(optJSONObject2);
                final int n = changeFromPlayer;
                return new EloChange(new Rating(eloFromPlayer), new Rating(eloFromPlayer2), n, changeFromPlayer2);
            }
        }
        final int n = 0;
        int changeFromPlayer2;
        final int eloFromPlayer2 = changeFromPlayer2 = n;
        return new EloChange(new Rating(eloFromPlayer), new Rating(eloFromPlayer2), n, changeFromPlayer2);
    }
}
