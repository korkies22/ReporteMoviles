// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONArray;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.playzone.model.PlayzoneGameSessionInfo;
import java.util.List;

public class JSONOpenGamesParser extends JSONAPIResultParser<List<PlayzoneGameSessionInfo>>
{
    @Override
    public List<PlayzoneGameSessionInfo> parseResult(JSONObject optJSONArray) throws InvalidJsonForObjectException {
        final LinkedList<PlayzoneGameSessionInfo> list = new LinkedList<PlayzoneGameSessionInfo>();
        optJSONArray = (JSONObject)optJSONArray.optJSONArray("openGames");
        if (optJSONArray != null) {
            for (int i = 0; i < ((JSONArray)optJSONArray).length(); ++i) {
                try {
                    final JSONObject jsonObject = ((JSONArray)optJSONArray).getJSONObject(i);
                    list.add(new PlayzoneGameSessionInfo(jsonObject.getString("token"), jsonObject.getString("role").equals("w"), jsonObject.getString("host"), jsonObject.getInt("port")));
                }
                catch (JSONException ex) {
                    Logger.getInstance().error(JSONOpenGamesParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
                }
            }
        }
        return list;
    }
}
