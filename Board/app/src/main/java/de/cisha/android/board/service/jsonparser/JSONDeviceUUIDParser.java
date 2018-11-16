// 
// Decompiled by Procyon v0.5.30
// 

package de.cisha.android.board.service.jsonparser;

import org.json.JSONException;
import de.cisha.chess.util.Logger;
import org.json.JSONObject;
import de.cisha.chess.model.CishaUUID;

public class JSONDeviceUUIDParser extends JSONAPIResultParser<CishaUUID>
{
    @Override
    public CishaUUID parseResult(final JSONObject jsonObject) throws InvalidJsonForObjectException {
        try {
            return new CishaUUID(jsonObject.getString("duuid"), true);
        }
        catch (JSONException ex) {
            Logger.getInstance().debug(JSONDeviceUUIDParser.class.getName(), JSONException.class.getName(), (Throwable)ex);
            throw new InvalidJsonForObjectException("Error parsing uui Json:", (Throwable)ex);
        }
    }
}
