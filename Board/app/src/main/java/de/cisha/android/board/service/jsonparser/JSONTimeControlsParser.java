// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import java.util.Iterator;
import org.json.JSONArray;
import de.cisha.chess.util.Logger;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;
import de.cisha.android.board.playzone.model.TimeControl;
import java.util.List;

public class JSONTimeControlsParser extends JSONAPIResultParser<List<TimeControl>>
{
    @Override
    public List<TimeControl> parseResult(JSONObject optJSONObject) throws InvalidJsonForObjectException {
        final LinkedList<TimeControl> list = new LinkedList<TimeControl>();
        try {
            final JSONArray jsonArray = optJSONObject.getJSONArray("availableClocks");
            final JSONTimeControlParser jsonTimeControlParser = new JSONTimeControlParser();
            for (int i = 0; i < jsonArray.length(); ++i) {
                list.add(jsonTimeControlParser.parseResult(jsonArray.getJSONObject(i)));
            }
            optJSONObject = optJSONObject.optJSONObject("defaultClock");
            if (optJSONObject != null) {
                final TimeControl result = jsonTimeControlParser.parseResult(optJSONObject);
                if (result != null) {
                    result.setDefault(true);
                    if (list.contains(result)) {
                        for (final TimeControl timeControl : list) {
                            if (timeControl.equals(result)) {
                                timeControl.setDefault(true);
                                return list;
                            }
                        }
                    }
                    else {
                        list.add(result);
                    }
                }
            }
            return list;
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(JSONTimeControlsParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("Exception parsing TimeControl json:", (Throwable)ex);
        }
    }
}
