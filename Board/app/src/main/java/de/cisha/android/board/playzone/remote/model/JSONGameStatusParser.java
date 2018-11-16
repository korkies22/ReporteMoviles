// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.playzone.remote.model;

import org.json.JSONException;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.chess.util.Logger;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import org.json.JSONObject;
import de.cisha.chess.model.GameStatus;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;

public class JSONGameStatusParser extends JSONAPIResultParser<GameStatus>
{
    @Override
    public GameStatus parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException, JSONException {
        final GameResult no_RESULT = GameResult.NO_RESULT;
        final GameEndReason no_REASON = GameEndReason.NO_REASON;
        final JSONObject optJSONObject = jsonObject.optJSONObject("result");
        final String string = jsonObject.getString("endedBy");
        GameEndReason gameEndReason;
        if ((gameEndReason = GameEndReason.getReasonForKey(string)) == null) {
            gameEndReason = GameEndReason.NO_REASON;
            final Logger instance = Logger.getInstance();
            final String name = SocketIORemoteGameConnection.class.getName();
            final StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(" -gameEnd is unknown");
            instance.error(name, sb.toString());
        }
        return new GameStatus(GameResult.getResultForScore(optJSONObject.optDouble("white", -1.0), optJSONObject.optDouble("black", -1.0), GameEndReason.FORFEITURE == gameEndReason), gameEndReason);
    }
}
