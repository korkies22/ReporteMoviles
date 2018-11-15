/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.playzone.remote.model;

import de.cisha.android.board.playzone.remote.model.SocketIORemoteGameConnection;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.GameEndReason;
import de.cisha.chess.model.GameResult;
import de.cisha.chess.model.GameStatus;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONGameStatusParser
extends JSONAPIResultParser<GameStatus> {
    @Override
    public GameStatus parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        Object object2 = GameResult.NO_RESULT;
        object2 = GameEndReason.NO_REASON;
        JSONObject jSONObject = object.optJSONObject("result");
        String string = object.getString("endedBy");
        object2 = GameEndReason.getReasonForKey(string);
        object = object2;
        if (object2 == null) {
            object = GameEndReason.NO_REASON;
            object2 = Logger.getInstance();
            String string2 = SocketIORemoteGameConnection.class.getName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" -gameEnd is unknown");
            object2.error(string2, stringBuilder.toString());
        }
        double d = jSONObject.optDouble("white", -1.0);
        double d2 = jSONObject.optDouble("black", -1.0);
        boolean bl = GameEndReason.FORFEITURE == object;
        return new GameStatus(GameResult.getResultForScore(d, d2, bl), (GameEndReason)((Object)object));
    }
}
