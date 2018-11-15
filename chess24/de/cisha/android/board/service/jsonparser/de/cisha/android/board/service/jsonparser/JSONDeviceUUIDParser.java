/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package de.cisha.android.board.service.jsonparser;

import de.cisha.android.board.service.jsonparser.InvalidJsonForObjectException;
import de.cisha.android.board.service.jsonparser.JSONAPIResultParser;
import de.cisha.chess.model.CishaUUID;
import de.cisha.chess.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONDeviceUUIDParser
extends JSONAPIResultParser<CishaUUID> {
    @Override
    public CishaUUID parseResult(JSONObject object) throws InvalidJsonForObjectException {
        try {
            object = new CishaUUID(object.getString("duuid"), true);
            return object;
        }
        catch (JSONException jSONException) {
            Logger.getInstance().debug(JSONDeviceUUIDParser.class.getName(), JSONException.class.getName(), (Throwable)jSONException);
            throw new InvalidJsonForObjectException("Error parsing uui Json:", (Throwable)jSONException);
        }
    }
}
