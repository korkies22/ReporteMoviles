/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.ServerApiCheckStatusResult;
import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.util.Logger;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONServerAPIStatusParser
extends JSONAPIResultParser<ServerApiCheckStatusResult> {
    @Override
    public ServerApiCheckStatusResult parseResult(JSONObject object) throws InvalidJsonForObjectException {
        if (object == null) {
            throw new InvalidJsonForObjectException("dataObject was null");
        }
        boolean bl = object.optBoolean("deprecated", false);
        Date date = null;
        if (bl) {
            long l = object.optLong("expires", -1L);
            date = l == -1L ? new Date(new Date().getTime() + 604800000L) : new Date(new Date().getTime() + l * 1000L);
        }
        try {
            object = object.getString("baseUrl");
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(JSONServerAPIStatusParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("field baseUrl was missing");
        }
        return new ServerApiCheckStatusResult(true, bl, date, (String)object);
    }
}
