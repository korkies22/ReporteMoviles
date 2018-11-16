// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import java.util.Iterator;
import org.json.JSONArray;
import de.cisha.chess.util.Logger;
import android.graphics.Color;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.playzone.model.OnlineEngineOpponent;
import java.util.List;

public class JSONOnlineEngineOpponentParser extends JSONAPIResultParser<List<OnlineEngineOpponent>>
{
    @Override
    public List<OnlineEngineOpponent> parseResult(JSONObject iterator) throws InvalidJsonForObjectException, JSONException {
        final LinkedList<OnlineEngineOpponent> list = new LinkedList<OnlineEngineOpponent>();
        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        final JSONObject jsonObject = iterator.getJSONObject("extendedEngineData");
        final JSONArray jsonArray = jsonObject.getJSONArray("indices");
        for (int i = 0; i < jsonArray.length(); ++i) {
            final String string = jsonArray.getString(i);
            final OnlineEngineOpponent onlineEngineOpponent = new OnlineEngineOpponent();
            hashMap.put(string, onlineEngineOpponent);
            list.add(onlineEngineOpponent);
        }
        final JSONObject optJSONObject = jsonObject.optJSONObject("uuids");
        final JSONObject optJSONObject2 = jsonObject.optJSONObject("names");
        final JSONObject optJSONObject3 = jsonObject.optJSONObject("descriptions");
        final JSONObject optJSONObject4 = jsonObject.optJSONObject("colors");
        final JSONObject optJSONObject5 = jsonObject.optJSONObject("clocks");
        final JSONObject optJSONObject6 = jsonObject.optJSONObject("profilePictureIDs");
        final JSONObject optJSONObject7 = iterator.optJSONObject("lockedEngines");
        final JSONObject optJSONObject8 = iterator.optJSONObject("ratedEngines");
        iterator = (JSONObject)hashMap.entrySet().iterator();
        Map.Entry<K, OnlineEngineOpponent> entry;
        OnlineEngineOpponent onlineEngineOpponent2;
        String s;
        String optString;
        String optString2;
        String optString3;
        String optString4;
        StringBuilder sb;
        String optString5;
        boolean b;
        JSONObject optJSONObject9;
        IllegalArgumentException ex;
        final IllegalArgumentException ex2;
        Label_0376_Outer:Label_0361_Outer:
        while (true) {
            if (!((Iterator)iterator).hasNext()) {
                return list;
            }
            entry = ((Iterator<Map.Entry<K, OnlineEngineOpponent>>)iterator).next();
            onlineEngineOpponent2 = entry.getValue();
            s = (String)entry.getKey();
            if (optJSONObject != null) {
                optString = optJSONObject.optString(s);
            }
            else {
                optString = "";
            }
            onlineEngineOpponent2.setUuidString(optString);
            if (optJSONObject2 != null) {
                optString2 = optJSONObject2.optString(s);
            }
            else {
                optString2 = "";
            }
            onlineEngineOpponent2.setName(optString2);
            if (optJSONObject3 != null) {
                optString3 = optJSONObject3.optString(s);
            }
            else {
                optString3 = "";
            }
            onlineEngineOpponent2.setDescription(optString3);
            while (true) {
                Label_0320: {
                    if (optJSONObject4 == null) {
                        optString4 = "";
                        break Label_0320;
                    }
                    try {
                        optString4 = optJSONObject4.optString(s);
                        sb = new StringBuilder();
                        sb.append("#");
                        sb.append(optString4);
                        onlineEngineOpponent2.setColor(Color.parseColor(sb.toString()));
                        while (true) {
                            if (optJSONObject6 != null) {
                                optString5 = optJSONObject6.optString(s);
                            }
                            else {
                                optString5 = "";
                            }
                            onlineEngineOpponent2.setProfilePictureCouchIdString(optString5);
                            b = true;
                            onlineEngineOpponent2.setRateGame(optJSONObject8 != null && optJSONObject8.optBoolean(s));
                            onlineEngineOpponent2.setLocked(optJSONObject7 != null && optJSONObject7.optBoolean(s) && b);
                            if (optJSONObject5 == null) {
                                continue Label_0376_Outer;
                            }
                            optJSONObject9 = optJSONObject5.optJSONObject(s);
                            if (optJSONObject9 == null) {
                                continue Label_0376_Outer;
                            }
                            onlineEngineOpponent2.setTimeControlSeconds(optJSONObject9.optInt("clock"));
                            onlineEngineOpponent2.setTimeControlIncrementSeconds(optJSONObject9.optInt("increment"));
                            continue Label_0376_Outer;
                            Logger.getInstance().error(JSONOnlineEngineOpponentParser.class.getName(), "error while parsing color of engine", ex);
                            continue Label_0361_Outer;
                        }
                    }
                    catch (IllegalArgumentException ex2) {}
                }
                ex = ex2;
                continue;
            }
        }
    }
}
