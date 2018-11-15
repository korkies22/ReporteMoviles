/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONOpenGamesParser
extends JSONAPIResultParser<List<PlayzoneGameSessionInfo>> {
    @Override
    public List<PlayzoneGameSessionInfo> parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        LinkedList<PlayzoneGameSessionInfo> linkedList = new LinkedList<PlayzoneGameSessionInfo>();
        if ((jSONObject = jSONObject.optJSONArray("openGames")) != null) {
            for (int i = 0; i < jSONObject.length(); ++i) {
                try {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(i);
                    String string = jSONObject2.getString("token");
                    String string2 = jSONObject2.getString("role");
                    String string3 = jSONObject2.getString("host");
                    int n = jSONObject2.getInt("port");
                    linkedList.add(new PlayzoneGameSessionInfo(string, string2.equals("w"), string3, n));
                    continue;
                }
                catch (JSONException jSONException) {
                    Logger.getInstance().error(JSONOpenGamesParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                }
            }
        }
        return linkedList;
    }
}
