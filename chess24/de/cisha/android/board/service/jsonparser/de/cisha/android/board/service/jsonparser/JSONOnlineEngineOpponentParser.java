/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import android.graphics.Color;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.util.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONOnlineEngineOpponentParser
extends JSONAPIResultParser<List<OnlineEngineOpponent>> {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<OnlineEngineOpponent> parseResult(JSONObject object) throws InvalidJsonForObjectException, JSONException {
        OnlineEngineOpponent onlineEngineOpponent;
        String string;
        LinkedList<OnlineEngineOpponent> linkedList = new LinkedList<OnlineEngineOpponent>();
        Object object2 = new HashMap<String, OnlineEngineOpponent>();
        JSONObject jSONObject = object.getJSONObject("extendedEngineData");
        JSONArray jSONArray = jSONObject.getJSONArray("indices");
        for (int i = 0; i < jSONArray.length(); ++i) {
            string = jSONArray.getString(i);
            onlineEngineOpponent = new OnlineEngineOpponent();
            object2.put(string, onlineEngineOpponent);
            linkedList.add(onlineEngineOpponent);
        }
        jSONArray = jSONObject.optJSONObject("uuids");
        string = jSONObject.optJSONObject("names");
        onlineEngineOpponent = jSONObject.optJSONObject("descriptions");
        JSONObject jSONObject2 = jSONObject.optJSONObject("colors");
        JSONObject jSONObject3 = jSONObject.optJSONObject("clocks");
        jSONObject = jSONObject.optJSONObject("profilePictureIDs");
        JSONObject jSONObject4 = object.optJSONObject("lockedEngines");
        JSONObject jSONObject5 = object.optJSONObject("ratedEngines");
        object = object2.entrySet().iterator();
        while (object.hasNext()) {
            object2 = (Map.Entry)object.next();
            OnlineEngineOpponent onlineEngineOpponent2 = (OnlineEngineOpponent)object2.getValue();
            String string2 = (String)object2.getKey();
            object2 = jSONArray != null ? jSONArray.optString(string2) : "";
            onlineEngineOpponent2.setUuidString((String)object2);
            object2 = string != null ? string.optString(string2) : "";
            onlineEngineOpponent2.setName((String)object2);
            object2 = onlineEngineOpponent != null ? onlineEngineOpponent.optString(string2) : "";
            onlineEngineOpponent2.setDescription((String)object2);
            if (jSONObject2 != null) {
                try {
                    object2 = jSONObject2.optString(string2);
                    break block7;
                }
                catch (IllegalArgumentException illegalArgumentException) {}
                Logger.getInstance().error(JSONOnlineEngineOpponentParser.class.getName(), "error while parsing color of engine", illegalArgumentException);
            } else {
                block7 : {
                    object2 = "";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("#");
                stringBuilder.append((String)object2);
                onlineEngineOpponent2.setColor(Color.parseColor((String)stringBuilder.toString()));
            }
            object2 = jSONObject != null ? jSONObject.optString(string2) : "";
            onlineEngineOpponent2.setProfilePictureCouchIdString((String)object2);
            boolean bl = true;
            boolean bl2 = jSONObject5 != null && jSONObject5.optBoolean(string2);
            onlineEngineOpponent2.setRateGame(bl2);
            bl2 = jSONObject4 != null && jSONObject4.optBoolean(string2) ? bl : false;
            onlineEngineOpponent2.setLocked(bl2);
            if (jSONObject3 == null || (object2 = jSONObject3.optJSONObject(string2)) == null) continue;
            onlineEngineOpponent2.setTimeControlSeconds(object2.optInt("clock"));
            onlineEngineOpponent2.setTimeControlIncrementSeconds(object2.optInt("increment"));
        }
        return linkedList;
    }
}
