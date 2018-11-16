// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.chess.util.Logger;
import java.util.Date;
import org.json.JSONObject;
import de.cisha.android.board.service.ServerApiCheckStatusResult;

public class JSONServerAPIStatusParser extends JSONAPIResultParser<ServerApiCheckStatusResult>
{
    @Override
    public ServerApiCheckStatusResult parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        if (jsonObject == null) {
            throw new InvalidJsonForObjectException("dataObject was null");
        }
        final boolean optBoolean = jsonObject.optBoolean("deprecated", false);
        Date date = null;
        if (optBoolean) {
            final long optLong = jsonObject.optLong("expires", -1L);
            if (optLong == -1L) {
                date = new Date(new Date().getTime() + 604800000L);
            }
            else {
                date = new Date(new Date().getTime() + optLong * 1000L);
            }
        }
        try {
            return new ServerApiCheckStatusResult(true, optBoolean, date, jsonObject.getString("baseUrl"));
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(JSONServerAPIStatusParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("field baseUrl was missing");
        }
    }
}
