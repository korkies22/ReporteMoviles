/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.playzone.model.TimeControl;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTimeControlParser
extends JSONAPIResultParser<TimeControl> {
    @Override
    public TimeControl parseResult(JSONObject jSONObject) throws InvalidJsonForObjectException {
        try {
            TimeControl timeControl = new TimeControl(jSONObject.getInt("clock") / 60, jSONObject.optInt("increment", 0) / 1);
            return timeControl;
        }
        catch (JSONException jSONException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid JSON ");
            stringBuilder.append((Object)jSONObject);
            throw new InvalidJsonForObjectException(stringBuilder.toString(), (Throwable)jSONException);
        }
    }
}
