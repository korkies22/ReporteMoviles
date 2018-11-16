// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.playzone.model.TimeControl;

public class JSONTimeControlParser extends JSONAPIResultParser<TimeControl>
{
    @Override
    public TimeControl parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new TimeControl(jsonObject.getInt("clock") / 60, jsonObject.optInt("increment", 0) / 1);
        }
        catch (JSONException ex) {
            final StringBuilder sb = new StringBuilder();
            sb.append("Invalid JSON ");
            sb.append(jsonObject);
            throw new InvalidJsonForObjectException(sb.toString(), (Throwable)ex);
        }
    }
}
