/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.android.board.service.jsonparser.JSONTimeControlParser;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTimeControlsParser
extends JSONAPIResultParser<List<TimeControl>> {
    @Override
    public List<TimeControl> parseResult(JSONObject object) throws InvalidJsonForObjectException {
        LinkedList<TimeControl> linkedList;
        block9 : {
            block10 : {
                linkedList = new LinkedList<TimeControl>();
                JSONArray object22 = object.getJSONArray("availableClocks");
                JSONTimeControlParser jSONTimeControlParser = new JSONTimeControlParser();
                int n = 0;
                do {
                    if (n >= object22.length()) break;
                    linkedList.add(jSONTimeControlParser.parseResult(object22.getJSONObject(n)));
                    ++n;
                    continue;
                    break;
                } while (true);
                object = object.optJSONObject("defaultClock");
                if (object == null) break block9;
                object = jSONTimeControlParser.parseResult((JSONObject)object);
                if (object == null) break block9;
                try {
                    object.setDefault(true);
                    if (!linkedList.contains(object)) break block10;
                    for (TimeControl timeControl : linkedList) {
                        if (!timeControl.equals(object)) continue;
                        timeControl.setDefault(true);
                        return linkedList;
                    }
                    break block9;
                }
                catch (JSONException jSONException) {
                    Logger.getInstance().debug(JSONTimeControlsParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
                    throw new InvalidJsonForObjectException("Exception parsing TimeControl json:", (Throwable)jSONException);
                }
            }
            linkedList.add((TimeControl)object);
        }
        return linkedList;
    }
}
